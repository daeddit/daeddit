package dgsw.daeddit.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import dgsw.daeddit.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostServiceTest {

    @Autowired
    private PostService postService;

    // 없는 ID로 삭제 시도하는 테스트.
    @Test
    void delete_fail() {
        // given
        final Long id = 999L;
        // when & then
        assertThrows(NotFoundException.class, () -> postService.delete(id));
    }
}
