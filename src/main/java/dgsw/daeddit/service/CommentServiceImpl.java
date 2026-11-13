package dgsw.daeddit.service;

import dgsw.daeddit.domain.Comment;
import dgsw.daeddit.domain.Post;
import dgsw.daeddit.dto.request.CommentCreateRequest;
import dgsw.daeddit.dto.request.CommentUpdateRequest;
import dgsw.daeddit.dto.response.CommentResponse;
import dgsw.daeddit.repository.CommentRepository;
import dgsw.daeddit.repository.PostRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    @Transactional
    public Optional<CommentResponse> create(Long postId, CommentCreateRequest request) {
        Optional<Post> postOpt = postRepository.findById(postId);
        if (postOpt.isEmpty()) {
            return Optional.empty();
        }
        Post post = postOpt.get();

        String content = validateContent(request.getContent());
        String author = validateAuthor(request.getAuthor());
        Comment parent = resolveParent(request.getParentId(), post);

        Comment saved = commentRepository.save(Comment.create(post, parent, content, author));
        return Optional.of(CommentResponse.from(saved));
    }

    @Override
    public Optional<List<CommentResponse>> findByPostId(Long postId) {
        if (!postRepository.existsById(postId)) {
            return Optional.empty();
        }
        List<CommentResponse> comments = commentRepository.findByPostIdOrderByIdAsc(postId).stream()
            .map(CommentResponse::from)
            .toList();
        return Optional.of(comments);
    }

    @Override
    public Optional<CommentResponse> findById(Long id) {
        return commentRepository.findById(id).map(CommentResponse::from);
    }

    @Override
    @Transactional
    public Optional<CommentResponse> update(Long id, CommentUpdateRequest request) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty() || commentOpt.get().isDeleted()) {
            return Optional.empty();
        }
        Comment comment = commentOpt.get();
        comment.update(validateContent(request.getContent()));
        commentRepository.flush();
        return Optional.of(CommentResponse.from(comment));
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Optional<Comment> commentOpt = commentRepository.findById(id);
        if (commentOpt.isEmpty() || commentOpt.get().isDeleted()) {
            return false;
        }
        Comment comment = commentOpt.get();
        if (commentRepository.existsByParentId(id)) {
            comment.softDelete();
            commentRepository.flush();
        } else {
            commentRepository.delete(comment);
        }
        return true;
    }

    private Comment resolveParent(Long parentId, Post post) {
        if (parentId == null) {
            return null;
        }
        Comment parent = commentRepository.findById(parentId)
            .orElseThrow(() -> new IllegalArgumentException("부모 댓글을 찾을 수 없습니다."));

        if (!parent.getPost().getId().equals(post.getId())) {
            throw new IllegalArgumentException("부모 댓글이 해당 게시글의 댓글이 아닙니다.");
        }
        if (parent.isReply()) {
            throw new IllegalArgumentException("대댓글에는 다시 댓글을 달 수 없습니다.");
        }
        return parent;
    }

    private String validateContent(String content) {
        String normalized = normalize(content);
        if (normalized == null || normalized.isEmpty() || normalized.length() > Comment.CONTENT_MAX_LENGTH) {
            throw new IllegalArgumentException(
                "댓글 내용은 1~" + Comment.CONTENT_MAX_LENGTH + "자여야 합니다.");
        }
        return normalized;
    }

    private String validateAuthor(String author) {
        String normalized = normalize(author);
        if (normalized == null || normalized.isEmpty() || normalized.length() > Comment.AUTHOR_MAX_LENGTH) {
            throw new IllegalArgumentException(
                "작성자는 1~" + Comment.AUTHOR_MAX_LENGTH + "자여야 합니다.");
        }
        return normalized;
    }

    private String normalize(String value) {
        return value == null ? null : value.strip();
    }
}
