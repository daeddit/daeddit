package dgsw.daeddit.repository;

import static org.assertj.core.api.Assertions.assertThat;

import dgsw.daeddit.domain.Comment;
import dgsw.daeddit.domain.Domain;
import dgsw.daeddit.domain.Post;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private DomainRepository domainRepository;

    // 대댓글이 달린 댓글과 달리지 않은 댓글을 구분하는 테스트
    @Test
    void existsByParentIdTest() {
        Domain domain = domainRepository.findAll().get(0);
        Post post = postRepository.save(Post.create("제목", "내용", "작성자", domain));

        Comment root = commentRepository.save(Comment.create(post, null, "루트 댓글", "민선영"));
        Comment reply = commentRepository.save(Comment.create(post, root, "대댓글", "권대형"));

        assertThat(commentRepository.existsByParentId(root.getId())).isTrue();
        assertThat(commentRepository.existsByParentId(reply.getId())).isFalse();
    }

    // 게시글의 댓글과 대댓글이 id 순으로 함께 조회되는 테스트
    @Test
    void findByPostIdOrderByIdAscTest() {
        Domain domain = domainRepository.findAll().get(0);
        Post post = postRepository.save(Post.create("제목", "내용", "작성자", domain));

        Comment root = commentRepository.save(Comment.create(post, null, "루트 댓글", "민선영"));
        commentRepository.save(Comment.create(post, root, "대댓글", "권대형"));

        List<Comment> comments = commentRepository.findByPostIdOrderByIdAsc(post.getId());

        assertThat(comments).hasSize(2);
        assertThat(comments.get(0).getParent()).isNull();
        assertThat(comments.get(1).getParent().getId()).isEqualTo(root.getId());
    }
}
