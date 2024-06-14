package com.triportreat.backend.batch.config;

import com.triportreat.backend.batch.processor.PlaceUpdateItemProcessor;
import com.triportreat.backend.batch.reader.PlacePartitioningUpdateItemReader;
import com.triportreat.backend.batch.utils.CustomSkipPolicy;
import com.triportreat.backend.batch.writer.PlaceUpdateItemWriter;
import com.triportreat.backend.place.domain.TourApiPlaceResponseDto.Item;
import com.triportreat.backend.place.service.ExternalApiService;
import java.net.http.HttpTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.core.partition.support.TaskExecutorPartitionHandler;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.client.HttpClientErrorException;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PlaceUpdateJobConfig {

    private final PlacePartitioningUpdateItemReader placePartitioningUpdateItemReader;
    private final PlaceUpdateItemProcessor placeUpdateItemProcessor;
    private final PlaceUpdateItemWriter placeUpdateItemWriter;
    private final ExternalApiService externalApiService;

    private static final int numOfRows = 10000;
    private static final int poolSize = 2;
    private static int pageSize;

    @Bean
    public Job upsertPlacesJob(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new JobBuilder("upsertPlacesJob", jobRepository)
                .start(getTotalPages(jobRepository, platformTransactionManager))
                .next(upsertPlacesManager(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step getTotalPages(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("getTotalPages", jobRepository)
                .tasklet((contribution, chunkContext) -> {
                    Integer totalCount = externalApiService.getTotalCount();
                    pageSize = (totalCount + numOfRows - 1) / numOfRows;
                    return null;
                }, platformTransactionManager)
                .build();
    }

    @Bean
    public Step upsertPlacesManager(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("upsertPlacesManager", jobRepository)
                .partitioner(upsertPlacesStep(jobRepository, platformTransactionManager).getName(), partitioner())
                .step(upsertPlacesStep(jobRepository, platformTransactionManager))
                .partitionHandler(partitionHandler(jobRepository, platformTransactionManager))
                .build();
    }

    @Bean
    public Step upsertPlacesStep(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        return new StepBuilder("upsertPlacesStep", jobRepository)
                .<List<Item>, List<Item>> chunk(1, platformTransactionManager)
                .reader(placePartitioningUpdateItemReader)
                .processor(placeUpdateItemProcessor)
                .writer(placeUpdateItemWriter)
                .faultTolerant()
                .skipPolicy(skipPolicy())
                .transactionManager(platformTransactionManager)
                .build();
    }

    @Bean
    public TaskExecutorPartitionHandler partitionHandler(JobRepository jobRepository, PlatformTransactionManager platformTransactionManager) {
        TaskExecutorPartitionHandler partitionHandler = new TaskExecutorPartitionHandler();
        partitionHandler.setStep(upsertPlacesStep(jobRepository, platformTransactionManager));
        partitionHandler.setTaskExecutor(executor());
        partitionHandler.setGridSize(poolSize);
        return partitionHandler;
    }

    @Bean
    public TaskExecutor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(poolSize);
        executor.setMaxPoolSize(poolSize);
        executor.setThreadNamePrefix("partition-");
        executor.setWaitForTasksToCompleteOnShutdown(Boolean.TRUE);
        executor.initialize();
        return executor;
    }

    @Bean
    @StepScope
    public Partitioner partitioner() {
        return gridSize -> {
            Map<String, ExecutionContext> partitionMap = new HashMap<>();
            int pagesPerThread = pageSize / poolSize;
            int remainder = pageSize % poolSize;

            for (int i = 0; i < poolSize; i++) {
                // 각 스레드가 처리할 시작 페이지와 끝 페이지 계산
                int startPage = i * pagesPerThread + Math.min(i, remainder) + 1;
                int endPage = startPage + pagesPerThread - 1;
                if (i < remainder) {
                    endPage += 1;
                }

                // 실행 컨텍스트에 시작 페이지와 끝 페이지 설정
                ExecutionContext context = new ExecutionContext();
                context.putInt("startPage", startPage);
                context.putInt("endPage", endPage);
                context.putInt("numOfRows", numOfRows);
                partitionMap.put("partition" + i, context);
            }
            return partitionMap;
        };
    }

    public SkipPolicy skipPolicy() {
        CustomSkipPolicy skipPolicy = new CustomSkipPolicy();
        skipPolicy.setExceptionClassMap(HttpClientErrorException.class, 2);
        skipPolicy.setExceptionClassMap(HttpTimeoutException.class, 1);

        return skipPolicy;
    }

}