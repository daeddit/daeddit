package dgsw.daeddit.repository;

import dgsw.daeddit.domain.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DomainRepository extends JpaRepository<Domain, Long> {

    boolean existsByDomainName(String domainName);

    boolean existsByDomainNameAndIdNot(String domainName, Long id);
}
