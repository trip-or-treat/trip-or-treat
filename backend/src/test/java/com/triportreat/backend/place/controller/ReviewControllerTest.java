package com.triportreat.backend.place.controller;

import com.triportreat.backend.auth.filter.JwtAuthenticationFilter;
import com.triportreat.backend.auth.filter.JwtExceptionFilter;
import com.triportreat.backend.auth.utils.AuthUserArgumentResolver;
import com.triportreat.backend.common.config.WebConfig;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.triportreat.backend.common.response.FailMessage.PLACE_NOT_FOUND;
import static com.triportreat.backend.common.response.FailMessage.VALIDATION_FAILED;
import static com.triportreat.backend.common.response.SuccessMessage.POST_SUCCESS;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ReviewController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {JwtExceptionFilter.class,
                        JwtAuthenticationFilter.class,
                        AuthUserArgumentResolver.class,
                        WebConfig.class}))
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private ReviewListDto reviewListDto;
    private ReviewRequestDto reviewRequestDto;

    @BeforeEach
    public void setUp() {
        reviewListDto = ReviewListDto.builder()
                .id(1L)
                .nickname("testUser")
                .imageThumbnail("testImage")
                .content("testContent")
                .tip("testTip")
                .score(4.0f)
                .createdDate(LocalDateTime.now())
                .build();

        reviewRequestDto = ReviewRequestDto.builder()
                .userId(1L)
                .placeId(1L)
                .content("testContent")
                .tip("testTip")
                .score(5.0F)
                .build();
    }

    @Nested
    @DisplayName("리뷰 목록 조회")
    class GetReviewList{

        @Test
        @DisplayName("성공")
        public void getReviewListTest() throws Exception {
            //given
            given(reviewService.getReviewList(1L, PageRequest.of(0, 10)))
                    .willReturn(Arrays.asList(reviewListDto));

            //when & then
            mockMvc.perform(get("/places/1/review")
                            .param("page", "0")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value("조회에 성공하였습니다."))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data[0].id").value(reviewListDto.getId()))
                    .andExpect(jsonPath("$.data[0].nickname").value(reviewListDto.getNickname()))
                    .andExpect(jsonPath("$.data[0].imageThumbnail").value(reviewListDto.getImageThumbnail()))
                    .andExpect(jsonPath("$.data[0].content").value(reviewListDto.getContent()))
                    .andExpect(jsonPath("$.data[0].tip").value(reviewListDto.getTip()))
                    .andExpect(jsonPath("$.data[0].score").value(reviewListDto.getScore()))
                    .andExpect(jsonPath("$.data[0].createdDate").exists())
                    .andDo(print());
        }

        @Test
        @DisplayName("성공 - 리뷰가 없으면 빈 리스트를 반환한다.")
        public void getReviewList_empty() throws Exception {
            //given
            given(reviewService.getReviewList(1L, PageRequest.of(0, 10)))
                    .willReturn(Collections.emptyList());

            //when & then
            mockMvc.perform(get("/places/1/review")
                            .param("page", "0")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value("조회에 성공하였습니다."))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data").isEmpty())
                    .andDo(print());
        }
    }

    @Nested
    @DisplayName("리뷰 저장")
    class CreateReview{

        @Test
        @DisplayName("성공")
        void createReview() throws Exception {

            mockMvc.perform(post("/places/review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reviewRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value(POST_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 장소 정보가 없을시 예외 발생")
        void createReview_PlaceNotFoundException() throws Exception{

            //given
            doThrow(new PlaceNotFoundException(reviewRequestDto.getPlaceId())).when(reviewService).createReview(reviewRequestDto);

            //when & then
            mockMvc.perform(post("/places/review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reviewRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(PLACE_NOT_FOUND.getMessage()+reviewRequestDto.getPlaceId()))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 유효성 검증 실패")
        void createReview_ValidationFail() throws Exception {
            //given
            reviewRequestDto = ReviewRequestDto.builder()
                    .userId(1L)
                    .placeId(1L)
                    .content("")
                    .tip("testTip")
                    .score(null)
                    .build();
            //when & then
            mockMvc.perform(post("/places/review")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reviewRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(VALIDATION_FAILED.getMessage()))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data.content").value("내용은 비워둘 수 없습니다."))
                    .andExpect(jsonPath("$.data.score").value("별점은 필수 입력값입니다."));
        }
    }
}