package dgsw.daeddit.repository;

import dgsw.daeddit.domain.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostIdOrderByIdAsc(Long postId);

    boolean existsByParentId(Long parentId);
}
