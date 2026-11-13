package dgsw.daeddit.service;

import dgsw.daeddit.dto.request.PostCreateRequest;
import dgsw.daeddit.dto.response.PostResponse;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {

    PostResponse create(PostCreateRequest request);
    List<PostResponse> findAll();
    Optional<PostResponse> findById(Long id);
}
