package dgsw.daeddit.service;

import dgsw.daeddit.domain.Post;
import dgsw.daeddit.dto.request.PostCreateRequest;
import dgsw.daeddit.dto.response.PostResponse;
import dgsw.daeddit.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    @Transactional
    public PostResponse create(PostCreateRequest request) {
        return PostResponse.from(postRepository.save(request.toEntity()));
    }

    @Override
    public List<PostResponse> findAll() {
        return postRepository.findAll().stream().map(PostResponse::from).toList();
    }

    @Override
    public Optional<PostResponse> findById(Long id) {
        Optional<Post> result = postRepository.findById(id);
        return result.map(PostResponse::from);
    }
}
