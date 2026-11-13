package dgsw.daeddit.dto.request;

import dgsw.daeddit.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {
    
    private String title;
    private String content;
    private String author;
    private String domain;

    public Post toEntity() { return Post.create(title, content, author, domain); }
}
