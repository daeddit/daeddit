package dgsw.daeddit;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import dgsw.daeddit.domain.Domain;
import dgsw.daeddit.domain.Post;
import dgsw.daeddit.repository.DomainRepository;
import dgsw.daeddit.repository.PostRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(properties = "spring.datasource.url=jdbc:h2:mem:temp-status-test")
@AutoConfigureMockMvc
class TempStatus {

    @Autowired private MockMvc mvc;
    @Autowired private DomainRepository domainRepository;
    @Autowired private PostRepository postRepository;

    @Test
    void 검증실패_응답확인() throws Exception {
        Domain domain = domainRepository.save(Domain.create("backend", "test"));
        Long postId = postRepository.save(Post.create("제목", "내용", "작성자", domain)).getId();

        mvc.perform(post("/api/posts/{id}/comments", postId)
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"content\":\"정상\",\"author\":\"민선영\"}"));

        try {
            var r = mvc.perform(post("/api/posts/{id}/comments", postId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"content\":\"   \",\"author\":\"민선영\"}")).andReturn();
            System.out.println("[공백 content] status=" + r.getResponse().getStatus()
                + "  body=" + r.getResponse().getContentAsString());
        } catch (Exception e) {
            System.out.println("[공백 content] 예외가 밖으로 전파됨: "
                + e.getClass().getSimpleName() + " -> " + e.getCause());
        }

        try {
            var r = mvc.perform(post("/api/posts/{id}/comments", postId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content("{\"content\":\"3단계\",\"author\":\"민선영\",\"parentId\":1}")).andReturn();
            System.out.println("[정상 대댓글] status=" + r.getResponse().getStatus());
        } catch (Exception e) {
            System.out.println("[정상 대댓글] 예외: " + e.getClass().getSimpleName());
        }
    }
}
