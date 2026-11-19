package dgsw.daeddit.dto.request;

import dgsw.daeddit.domain.Domain;
import dgsw.daeddit.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostCreateRequest {

    @NotBlank(message = "제목은 필수입니다.")
    @Size(max = Post.TITLE_MAX_LENGTH, message = "제목은 100자 이하여야 합니다.")
    private String title;

    @NotBlank(message = "내용은 필수입니다.")
    @Size(max = Post.CONTENT_MAX_LENGTH, message = "내용은 1000자 이하여야 합니다.")
    private String content;

    @NotBlank(message = "작성자는 필수입니다.")
    @Size(max = Post.AUTHOR_MAX_LENGTH, message = "작성자는 30자 이하여야 합니다.")
    private String author;

    @NotNull(message = "도메인은 필수입니다.")
    @Positive
    private Long domainId;

    public Post toEntity(Domain domain) { return Post.create(title, content, author, domain); }
}
