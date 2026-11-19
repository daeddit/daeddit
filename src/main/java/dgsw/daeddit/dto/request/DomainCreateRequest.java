package dgsw.daeddit.dto.request;

import dgsw.daeddit.domain.Domain;
import dgsw.daeddit.domain.Post;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DomainCreateRequest {

    @NotBlank(message = "도메인 이름은 필수입니다.")
    @Size(max = 30, message = "도메인 이름은 30자 이하여야 합니다.")
    private String domainName;

    @Size(max = 200, message = "설명은 200자 이하여야 합니다.")
    private String description;

    public Domain toEntity() { return Domain.create(domainName, description); }

}