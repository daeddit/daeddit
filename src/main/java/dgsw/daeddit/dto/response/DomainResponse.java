package dgsw.daeddit.dto.response;

import dgsw.daeddit.domain.Domain;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class DomainResponse {

    private Long id;

    private String domainName;

    private String description;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static DomainResponse from(Domain domain) {
        return DomainResponse.builder()
                .id(domain.getId())
                .domainName(domain.getDomainName())
                .description(domain.getDescription())
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getCreatedAt())
                .build();
    }

}
