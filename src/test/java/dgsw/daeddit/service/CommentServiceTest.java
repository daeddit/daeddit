package dgsw.daeddit.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import dgsw.daeddit.dto.request.CommentCreateRequest;
import dgsw.daeddit.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    // 없는 ID로 댓글을 조회하는 테스트
    @Test
    void findById_fail() {
        final Long id = 999L;

        assertThrows(NotFoundException.class, () -> commentService.findById(id));
    }

    // 없는 게시글에 댓글을 다는 테스트
    @Test
    void create_fail() {
        final Long postId = 999L;

        CommentCreateRequest request = new CommentCreateRequest("내용", "민선영", null);
        assertThrows(NotFoundException.class, () -> commentService.create(postId, request));
    }
}
