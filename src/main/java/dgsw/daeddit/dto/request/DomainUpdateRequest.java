package dgsw.daeddit.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DomainUpdateRequest {

    private String domainName;

    private String description;

}
