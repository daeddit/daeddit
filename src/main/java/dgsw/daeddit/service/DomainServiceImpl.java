package dgsw.daeddit.service;

import dgsw.daeddit.domain.Domain;
import dgsw.daeddit.dto.request.DomainCreateRequest;
import dgsw.daeddit.dto.request.DomainUpdateRequest;
import dgsw.daeddit.dto.response.DomainResponse;
import dgsw.daeddit.exception.NotFoundException;
import dgsw.daeddit.repository.DomainRepository;
import jakarta.persistence.Table;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DomainServiceImpl implements DomainService {

    private final DomainRepository domainRepository;

    @Override
    @Transactional
    public DomainResponse create(DomainCreateRequest request) {
        String name = validateName(request.getDomainName());
        if (domainRepository.existsByDomainName(name)) {
            throw new IllegalStateException("이미 존재하는 도메인 이름입니다.");
        }
        Domain domain = domainRepository.save(Domain.create(name, normalize(request.getDescription())));
        return DomainResponse.from(domain);
    }

    @Override
    public List<DomainResponse> findAll() {
        return domainRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(DomainResponse::from)
                .toList();
    }

    @Override
    public DomainResponse findById(Long id) {
        Optional<Domain> result = domainRepository.findById(id);
        if(result.isEmpty()) throw new NotFoundException();
        return DomainResponse.from(result.get());
    }

    @Override
    @Transactional
    public DomainResponse update(Long id, DomainUpdateRequest request) {
        Optional<Domain> domainOpt = domainRepository.findById(id);
        if (domainOpt.isEmpty()) throw new NotFoundException();
        String name = validateName(request.getDomainName());
        if (domainRepository.existsByDomainNameAndIdNot(name, id)) {
            throw new IllegalStateException("이미 존재하는 도메인 이름입니다.");
        }
        Domain domain = domainOpt.get();
        domain.update(name, normalize(request.getDescription()));
        domainRepository.flush();
        return DomainResponse.from(domain);
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        if (!domainRepository.existsById(id)) throw new NotFoundException();
        domainRepository.deleteById(id);
        return true;
    }

    private String validateName(String name) {
        String normalized = normalize(name);
        if (normalized == null || normalized.isEmpty() || normalized.length() > 30) {
            throw new IllegalArgumentException("도메인 이름은 1~30자여야 합니다.");
        }
        return normalized;
    }

    private String normalize(String value) {
        return value == null ? null : value.strip();
    }
}
