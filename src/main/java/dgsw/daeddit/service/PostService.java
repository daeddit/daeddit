package dgsw.daeddit.service;

import dgsw.daeddit.dto.request.PostCreateRequest;
import dgsw.daeddit.dto.request.PostUpdateRequest;
import dgsw.daeddit.dto.response.PostResponse;
import java.util.List;
import java.util.Optional;

public interface PostService {

    PostResponse create(PostCreateRequest request);
    List<PostResponse> findAll();
    Optional<PostResponse> findById(Long id);
    Optional<PostResponse> update(Long id, PostUpdateRequest request);
    boolean delete(Long id);
    List<PostResponse> search(String query);
    boolean like(Long id);
    boolean unlike(Long id);
}
