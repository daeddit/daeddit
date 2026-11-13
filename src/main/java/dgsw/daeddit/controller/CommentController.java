package dgsw.daeddit.controller;

import dgsw.daeddit.dto.ApiResponse;
import dgsw.daeddit.dto.request.CommentCreateRequest;
import dgsw.daeddit.dto.request.CommentUpdateRequest;
import dgsw.daeddit.dto.response.CommentResponse;
import dgsw.daeddit.service.CommentService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private static final String POST_NOT_FOUND_MESSAGE = "게시글을 찾을 수 없습니다.";
    private static final String COMMENT_NOT_FOUND_MESSAGE = "댓글을 찾을 수 없습니다.";

    private final CommentService commentService;

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<CommentResponse>> create(
        @PathVariable Long postId,
        @RequestBody CommentCreateRequest request
    ) {
        return commentService.create(postId, request)
            .map(response -> ResponseEntity
                .created(locationOf(response.getId()))
                .body(ApiResponse.success(response)))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(POST_NOT_FOUND_MESSAGE)));
    }

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<ApiResponse<List<CommentResponse>>> findByPostId(
        @PathVariable Long postId
    ) {
        return commentService.findByPostId(postId)
            .map(comments -> ResponseEntity.ok().body(ApiResponse.success(comments)))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(POST_NOT_FOUND_MESSAGE)));
    }

    @GetMapping("/api/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> findById(@PathVariable Long id) {
        return commentService.findById(id)
            .map(response -> ResponseEntity.ok().body(ApiResponse.success(response)))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(COMMENT_NOT_FOUND_MESSAGE)));
    }

    @PutMapping("/api/comments/{id}")
    public ResponseEntity<ApiResponse<CommentResponse>> update(
        @PathVariable Long id,
        @RequestBody CommentUpdateRequest request
    ) {
        return commentService.update(id, request)
            .map(response -> ResponseEntity.ok().body(ApiResponse.success(response)))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(COMMENT_NOT_FOUND_MESSAGE)));
    }

    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        return commentService.delete(id)
            ? ResponseEntity.ok().body(ApiResponse.success("댓글 삭제에 성공했습니다."))
            : ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(COMMENT_NOT_FOUND_MESSAGE));
    }

    @PostMapping("/api/comments/{id}/likes")
    public ResponseEntity<ApiResponse<CommentResponse>> like(@PathVariable Long id) {
        return commentService.like(id)
            .map(response -> ResponseEntity.ok().body(ApiResponse.success(response)))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(COMMENT_NOT_FOUND_MESSAGE)));
    }

    @PostMapping("/api/comments/{id}/dislikes")
    public ResponseEntity<ApiResponse<CommentResponse>> dislike(@PathVariable Long id) {
        return commentService.dislike(id)
            .map(response -> ResponseEntity.ok().body(ApiResponse.success(response)))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(COMMENT_NOT_FOUND_MESSAGE)));
    }

    private URI locationOf(Long commentId) {
        return ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/comments/{id}")
            .buildAndExpand(commentId)
            .toUri();
    }
}
