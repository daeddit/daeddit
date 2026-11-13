package dgsw.daeddit.controller;

import dgsw.daeddit.dto.ApiResponse;
import dgsw.daeddit.dto.request.DomainCreateRequest;
import dgsw.daeddit.dto.request.DomainUpdateRequest;
import dgsw.daeddit.dto.response.DomainResponse;
import dgsw.daeddit.service.DomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/domains")
@RequiredArgsConstructor
public class DomainController {

    private static final String NOT_FOUND_MESSAGE = "도메인을 찾을 수 없습니다.";

    private final DomainService domainService;

    @PostMapping
    public ResponseEntity<ApiResponse<DomainResponse>> create(
            @RequestBody DomainCreateRequest request
    ) {
        DomainResponse response = domainService.create(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();
        return ResponseEntity
                .created(location)
                .body(ApiResponse.success(response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<DomainResponse>>> findAll() {
        List<DomainResponse> result = domainService.findAll();
        return ResponseEntity.ok().body(ApiResponse.success(result));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<DomainResponse>> findById(
            @PathVariable Long id
    ) {
        Optional<DomainResponse> resultOpt = domainService.findById(id);
        return resultOpt
                .map(domainResponse -> ResponseEntity.ok().body(ApiResponse.success(domainResponse)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail(NOT_FOUND_MESSAGE))
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DomainResponse>> update(
            @PathVariable Long id,
            @RequestBody DomainUpdateRequest request
    ) {
        Optional<DomainResponse> resultOpt = domainService.update(id, request);
        return resultOpt
                .map(domainResponse -> ResponseEntity.ok().body(ApiResponse.success(domainResponse)))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(ApiResponse.fail(NOT_FOUND_MESSAGE))
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        boolean deleted = domainService.delete(id);
        return deleted ?
                ResponseEntity.ok().body(ApiResponse.success("도메인 삭제에 성공했습니다."))
                : ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.fail(NOT_FOUND_MESSAGE));
    }
}
