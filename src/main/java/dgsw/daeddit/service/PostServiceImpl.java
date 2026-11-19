package dgsw.daeddit.service;

import dgsw.daeddit.domain.Domain;
import dgsw.daeddit.domain.Post;
import dgsw.daeddit.dto.request.PostCreateRequest;
import dgsw.daeddit.dto.request.PostUpdateRequest;
import dgsw.daeddit.dto.response.PostResponse;
import dgsw.daeddit.exception.NotFoundException;
import dgsw.daeddit.repository.DomainRepository;
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
    private final DomainRepository domainRepository;

    @Override
    @Transactional
    public PostResponse create(PostCreateRequest request) {
       Optional<Domain> domainOpt = domainRepository.findById(request.getDomainId());
       if(domainOpt.isEmpty()) throw new NotFoundException();

       return PostResponse.from(postRepository.save(request.toEntity(domainOpt.get())));
    }

    @Override
    public List<PostResponse> findAll() {
        return postRepository.findAll().stream().map(PostResponse::from).toList();
    }

    @Override
    public PostResponse findById(Long id) {
        Optional<Post> result = postRepository.findById(id);
        if(result.isEmpty()) throw new NotFoundException();
        return PostResponse.from(result.get());
    }

    @Override
    @Transactional
    public PostResponse update(Long id, PostUpdateRequest request) {
        Optional<Post> resultOpt = postRepository.findById(id);
        if(resultOpt.isEmpty()) throw new NotFoundException();

        Post post = resultOpt.get();
        post.update(request.getTitle(), request.getContent(), post.getLikes());
        postRepository.flush();

        return PostResponse.from(post);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if(!postRepository.existsById(id)) throw new NotFoundException();
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
        if(resultOpt.isEmpty()) throw new NotFoundException();

        Post post = resultOpt.get();
        post.update(post.getTitle(), post.getContent(), post.getLikes() + 1);
        postRepository.flush();

        return true;
    }

    @Override
    @Transactional
    public boolean unlike(Long id) {
        Optional<Post> resultOpt = postRepository.findById(id);
        if(resultOpt.isEmpty()) throw new NotFoundException();

        Post post = resultOpt.get();
        post.update(post.getTitle(), post.getContent(), post.getLikes() - 1);
        postRepository.flush();

        return true;
    }
}
