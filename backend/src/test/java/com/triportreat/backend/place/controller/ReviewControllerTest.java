package com.triportreat.backend.place.controller;

import com.triportreat.backend.place.domain.ReviewListDto;
import com.triportreat.backend.place.service.ReviewListService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReviewController.class)
public class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReviewListService reviewListService;

    private ReviewListDto reviewListDto;

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
    }

    @Test
    @DisplayName("리뷰 목록을 조회하면 응답은 성공한다")
    public void getReviewListTest() throws Exception {
        given(reviewListService.getReviewList(1L, PageRequest.of(0, 10)))
                .willReturn(Arrays.asList(reviewListDto));

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
    @DisplayName("리뷰 목록 조회 결과가 없으면 빈 리스트를 반환한다.")
    public void getReviewList_empty() throws Exception {
        // given
        given(reviewListService.getReviewList(1L, PageRequest.of(0, 10)))
                .willReturn(Collections.emptyList());

        // when & then
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