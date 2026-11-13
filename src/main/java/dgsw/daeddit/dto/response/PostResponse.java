package dgsw.daeddit.dto.response;

import dgsw.daeddit.domain.Post;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostResponse {
    private Long id;
    private String title;
    private String content;
    private String author;
    private String domain;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity -> DTO
    public static PostResponse from(Post post) {
        return PostResponse.builder()
            .id(post.getId())
            .title(post.getTitle())
            .content(post.getContent())
            .author(post.getAuthor())
            .domain(post.getDomain())
            .createdAt(post.getCreatedAt())
            .updatedAt(post.getUpdatedAt())
            .build();
    }
}
