package dgsw.daeddit.service;

import dgsw.daeddit.dto.request.CommentCreateRequest;
import dgsw.daeddit.dto.request.CommentUpdateRequest;
import dgsw.daeddit.dto.response.CommentResponse;
import java.util.List;
import java.util.Optional;

public interface CommentService {

    CommentResponse create(Long postId, CommentCreateRequest request);

    List<CommentResponse> findByPostId(Long postId);

    CommentResponse findById(Long id);

    CommentResponse update(Long id, CommentUpdateRequest request);

    boolean delete(Long id);

    CommentResponse like(Long id);

    CommentResponse dislike(Long id);
}
