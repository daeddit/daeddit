package dgsw.daeddit.repository;

import dgsw.daeddit.domain.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByTitleStartsWith(String query);
}
