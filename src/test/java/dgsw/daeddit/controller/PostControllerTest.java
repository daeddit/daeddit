package dgsw.daeddit.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import dgsw.daeddit.dto.request.PostCreateRequest;
import dgsw.daeddit.dto.response.PostResponse;
import dgsw.daeddit.service.PostService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.json.JsonMapper;

@WebMvcTest(PostController.class)
class PostControllerTest {

    private final String url = "/api/posts";

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PostService postService;

    @Autowired
    private JsonMapper jsonMapper;

    @Test
    void create_success() throws Exception {
        // POST /api/articles
        // given
        PostCreateRequest request = new PostCreateRequest("제목1", "내용1", "작성자1", "주제1");
        LocalDateTime now = LocalDateTime.now();
        PostResponse postResponse = PostResponse
            .builder()
            .id(1L)
            .title("제목1")
            .content("내용1")
            .author("작성자1")
            .likes(0L)
            .domain("주제1")
            .createdAt(now)
            .updatedAt(now)
            .build();
        BDDMockito.given(
            postService.create(
                BDDMockito.any(PostCreateRequest.class)
            )
        ).willReturn(
            postResponse
        );
        // when
        ResultActions result = mockMvc.perform(
            MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonMapper.writeValueAsString(request))
        );
        // then
        result.andExpect(status().isCreated())
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.title").value(request.getTitle()))
            .andExpect(jsonPath("$.data.content").value(request.getContent()))
            .andExpect(jsonPath("$.data.author").value(request.getAuthor()))
            .andExpect(jsonPath("$.data.likes").value(0L))
            .andExpect(jsonPath("$.data.domain").value(request.getDomain()))
            .andExpect(jsonPath("$.data.createdAt").value(now.toString()))
            .andExpect(jsonPath("$.data.updatedAt").value(now.toString()));
    }
}