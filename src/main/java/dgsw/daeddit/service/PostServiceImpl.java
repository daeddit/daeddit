package dgsw.daeddit.service;

import dgsw.daeddit.domain.Post;
import dgsw.daeddit.dto.request.PostCreateRequest;
import dgsw.daeddit.dto.request.PostUpdateRequest;
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

    @Override
    @Transactional
    public Optional<PostResponse> update(Long id, PostUpdateRequest request) {
        Optional<Post> resultOpt = postRepository.findById(id);
        if(resultOpt.isEmpty()) return Optional.empty();

        Post post = resultOpt.get();
        post.update(request.getTitle(), request.getContent(), post.getLikes());
        postRepository.flush();

        return Optional.of(PostResponse.from(post));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if(!postRepository.existsById(id)) return false;
        postRepository.deleteById(id);
        return true;
    }

    @Override
    public List<PostResponse> search(String query) {
        return postRepository.findByTitleStartsWith(query).stream().map(PostResponse::from).toList();
    }

    @Override
    @Transactional
    public boolean like(Long id) {
        Optional<Post> resultOpt = postRepository.findById(id);
        if(resultOpt.isEmpty()) return false;

        Post post = resultOpt.get();
        post.update(post.getTitle(), post.getContent(), post.getLikes() + 1);
        postRepository.flush();

        return true;
    }

    @Override
    @Transactional
    public boolean unlike(Long id) {
        Optional<Post> resultOpt = postRepository.findById(id);
        if(resultOpt.isEmpty()) return false;

        Post post = resultOpt.get();
        post.update(post.getTitle(), post.getContent(), post.getLikes() - 1);
        postRepository.flush();

        return true;
    }
}
