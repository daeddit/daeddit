package dgsw.daeddit.dto.request;

import dgsw.daeddit.domain.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentCreateRequest {

    @NotBlank(message = "댓글 내용은 필수입니다.")
    @Size(max = Comment.CONTENT_MAX_LENGTH, message = "댓글 내용은 500자 이하여야 합니다.")
    private String content;

    @NotBlank(message = "작성자는 필수입니다.")
    @Size(max = Comment.AUTHOR_MAX_LENGTH, message = "작성자는 30자 이하여야 합니다.")
    private String author;

    private Long parentId;
}
