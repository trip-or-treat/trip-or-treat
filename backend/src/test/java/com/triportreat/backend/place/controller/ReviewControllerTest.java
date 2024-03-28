package com.triportreat.backend.place.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triportreat.backend.auth.filter.JwtAuthenticationFilter;
import com.triportreat.backend.auth.filter.JwtExceptionFilter;
import com.triportreat.backend.auth.utils.AuthUserArgumentResolver;
import com.triportreat.backend.common.config.WebConfig;
import com.triportreat.backend.common.error.exception.AuthenticateFailException;
import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.domain.ReviewRequestDto;
import com.triportreat.backend.place.domain.ReviewUpdateRequestDto;
import com.triportreat.backend.place.error.handler.exception.PlaceNotFoundException;
import com.triportreat.backend.place.error.handler.exception.ReviewNotBelongPlaceException;
import com.triportreat.backend.place.error.handler.exception.ReviewNotFoundException;
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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static com.triportreat.backend.common.response.FailMessage.*;
import static com.triportreat.backend.common.response.SuccessMessage.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    private ReviewUpdateRequestDto reviewUpdateRequestDto;

    @BeforeEach
    public void setUp() {
        reviewListDto = ReviewListDto.builder()
                .id(1L)
                .nickname("testUser")
                .imageThumbnail("testImage")
                .content("testContent")
                .tip("testTip")
                .score(4)
                .createdDate(LocalDateTime.now())
                .build();

        reviewRequestDto = ReviewRequestDto.builder()
                .userId(1L)
                .placeId(1L)
                .content("testContent")
                .tip("testTip")
                .score(5)
                .build();

        reviewUpdateRequestDto = ReviewUpdateRequestDto.builder()
                .placeId(1L)
                .content("testContent")
                .tip("testTip")
                .score(3)
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

            mockMvc.perform(post("/reviews")
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
            mockMvc.perform(post("/reviews")
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
            mockMvc.perform(post("/reviews")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reviewRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(VALIDATION_FAILED.getMessage()))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data.content").value("내용은 필수 입력값입니다."))
                    .andExpect(jsonPath("$.data.score").value("별점은 필수 입력값입니다."));
        }
    }

    @Nested
    @DisplayName("리뷰 수정")
    class UpdateReview {

        @Test
        @DisplayName("성공")
        public void updateReview() throws Exception {

            mockMvc.perform(put("/reviews/{id}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(reviewUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value(PUT_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 장소 정보가 없을시 예외 발생")
        public void updateReview_PlaceNotFoundException() throws Exception {

            //given
            doThrow(new PlaceNotFoundException(reviewUpdateRequestDto.getPlaceId())).when(reviewService).updateReview(anyLong(), any(ReviewUpdateRequestDto.class));

            //when & then
            mockMvc.perform(put("/reviews/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(reviewUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(PLACE_NOT_FOUND.getMessage()+reviewUpdateRequestDto.getPlaceId()))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof PlaceNotFoundException))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 리뷰 정보가 없을시 예외 발생")
        public void updateReview_ReviewNotFoundException() throws Exception {

            //given
            doThrow(new ReviewNotFoundException()).when(reviewService).updateReview(anyLong(), any(ReviewUpdateRequestDto.class));

            //when & then
            mockMvc.perform(put("/reviews/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(reviewUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(REVIEW_NOT_FOUND.getMessage()))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ReviewNotFoundException))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 리뷰가 요청된 장소에 속하지 않을시 예외발생")
        public void updateReview_ReviewNotBelongPlaceException() throws Exception {

            //given
            doThrow(new ReviewNotBelongPlaceException()).when(reviewService).updateReview(anyLong(), any(ReviewUpdateRequestDto.class));

            //when & then
            mockMvc.perform(put("/reviews/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(reviewUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(REVIEW_NOT_BELONG_TO_PLACE.getMessage()))
                    .andExpect(result -> assertTrue(result.getResolvedException() instanceof ReviewNotBelongPlaceException))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 유효성 검증 실패")
        void updateReview_ValidationFail() throws Exception {

            //given
            reviewUpdateRequestDto = ReviewUpdateRequestDto.builder()
                    .placeId(null)
                    .content("")
                    .tip("testTip")
                    .score(null)
                    .build();

            //when & then
            mockMvc.perform(put("/reviews/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(reviewUpdateRequestDto)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(VALIDATION_FAILED.getMessage()))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data.placeId").value("placeId는 필수입니다."))
                    .andExpect(jsonPath("$.data.content").value("내용은 필수 입력값입니다."))
                    .andExpect(jsonPath("$.data.score").value("별점은 필수 입력값입니다."));
        }
    }

    @Nested
    @DisplayName("리뷰 삭제")
    class DeleteReview {

        @Test
        @DisplayName("성공")
        void deleteReview() throws Exception {

            //given
            doNothing().when(reviewService).deleteReview(any(), anyLong());

            //when & then
            mockMvc.perform(delete("/reviews/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(true))
                    .andExpect(jsonPath("$.message").value(DELETE_SUCCESS.getMessage()))
                    .andExpect(jsonPath("$.status").value(200))
                    .andExpect(jsonPath("$.data").isEmpty());

            verify(reviewService).deleteReview(any(), anyLong());
        }

        @Test
        @DisplayName("실패 - 리뷰가 존재하지 않을 때 예외 발생")
        void deleteReview_UserNotFoundException() throws Exception {

            //given
            doThrow(new ReviewNotFoundException()).when(reviewService).deleteReview(any(), anyLong());

            //when & then
            mockMvc.perform(delete("/reviews/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(REVIEW_NOT_FOUND.getMessage()))
                    .andExpect(jsonPath("$.status").value(400))
                    .andExpect(jsonPath("$.data").isEmpty());
        }

        @Test
        @DisplayName("실패 - 사용자 ID가 일치하지 않을 때 예외 발생")
        void deleteReview_AuthenticateFailException() throws Exception {

            //given
            doThrow(new AuthenticateFailException()).when(reviewService).deleteReview(any(), anyLong());

            //when & then
            mockMvc.perform(delete("/reviews/{id}", 1L)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.result").value(false))
                    .andExpect(jsonPath("$.message").value(AUTHENTICATION_FAILED.getMessage()))
                    .andExpect(jsonPath("$.status").value(UNAUTHORIZED.value()))
                    .andExpect(jsonPath("$.data").isEmpty());
        }
    }
}