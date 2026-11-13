package dgsw.daeddit.dto.response;

import dgsw.daeddit.domain.Comment;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentResponse {

    private static final String DELETED_CONTENT = "삭제된 댓글입니다.";

    private Long id;
    private Long postId;
    private Long parentId;
    private String content;
    private String author;
    private long likeCount;
    private long dislikeCount;
    private boolean deleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Entity -> DTO
    public static CommentResponse from(Comment comment) {
        return CommentResponse.builder()
            .id(comment.getId())
            .postId(comment.getPost().getId())
            .parentId(comment.getParent() == null ? null : comment.getParent().getId())
            .content(comment.isDeleted() ? DELETED_CONTENT : comment.getContent())
            .author(comment.getAuthor())
            .likeCount(comment.getLikeCount())
            .dislikeCount(comment.getDislikeCount())
            .deleted(comment.isDeleted())
            .createdAt(comment.getCreatedAt())
            .updatedAt(comment.getUpdatedAt())
            .build();
    }
}
