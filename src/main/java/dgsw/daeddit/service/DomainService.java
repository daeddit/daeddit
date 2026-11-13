package dgsw.daeddit.service;

import dgsw.daeddit.dto.request.DomainCreateRequest;
import dgsw.daeddit.dto.request.DomainUpdateRequest;
import dgsw.daeddit.dto.response.DomainResponse;

import java.util.List;
import java.util.Optional;

public interface DomainService {

    DomainResponse create(DomainCreateRequest request);

    List<DomainResponse> findAll();

    Optional<DomainResponse> findById(Long id);

    Optional<DomainResponse> update(Long id, DomainUpdateRequest request);

    boolean delete(Long id);
}