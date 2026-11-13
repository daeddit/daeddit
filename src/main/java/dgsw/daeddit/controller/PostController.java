package dgsw.daeddit.controller;

import dgsw.daeddit.dto.ApiResponse;
import dgsw.daeddit.dto.request.PostCreateRequest;
import dgsw.daeddit.dto.response.PostResponse;
import dgsw.daeddit.service.PostService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<ApiResponse<PostResponse>> create(@RequestBody PostCreateRequest request) {
        PostResponse response = postService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();
        return ResponseEntity
            .created(location)
            .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PostResponse>>> findAll() {
        List<PostResponse> result = postService.findAll();
        return ResponseEntity.ok().body(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PostResponse>> findById(@PathVariable Long id) {
        Optional<PostResponse> resultOpt = postService.findById(id);
        return resultOpt
            .map(postResponse -> ResponseEntity.ok().body(ApiResponse.success(postResponse)))
            .orElseGet(() -> ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail("게시글을 찾을 수 없습니다."))
            );
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id) {

    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
    }

    @GetMapping("/search")
    public void search(@RequestParam String query) {
    }

    @PutMapping("/{id}/likes")
    public void like(@PathVariable Long id) {
    }

    @DeleteMapping("/{id}/likes")
    public void unlike(@PathVariable Long id) {
    }
}
