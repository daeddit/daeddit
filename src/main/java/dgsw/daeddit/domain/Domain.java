package dgsw.daeddit.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "domains")
@Getter
public class Domain extends BaseEntity{

    @Column(nullable = false, unique = true, length = 30)
    private String domainName;

    @Column(length = 200)
    private String description;

    private Domain(String domainName, String description) {
        this.domainName = Objects.requireNonNull(domainName, "domainName");
        this.description = description;
    }

    public static Domain create(String name, String description) {
        return new Domain(name, description);
    }

    public void update(String domainName, String description) {
        this.domainName = Objects.requireNonNull(domainName, "domainName");
        this.description = description;
    }
}
