package dgsw.daeddit.dto.request;

import dgsw.daeddit.domain.Domain;
import dgsw.daeddit.domain.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DomainCreateRequest {

    private String domainName;

    private String description;

    public Domain toEntity() { return Domain.create(domainName, description); }

}