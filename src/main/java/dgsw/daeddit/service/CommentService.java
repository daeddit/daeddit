package dgsw.daeddit.service;

import dgsw.daeddit.dto.request.CommentCreateRequest;
import dgsw.daeddit.dto.response.CommentResponse;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    Optional<CommentResponse> create(Long postId, CommentCreateRequest request);

    Optional<List<CommentResponse>> findByPostId(Long postId);

    Optional<CommentResponse> findById(Long id);
}
