package dgsw.daeddit.repository;

import dgsw.daeddit.domain.Domain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DomainRepositoryTest {

    @Autowired
    private DomainRepository domainRepository;

    @Test
    void existsByDomainNameTest() {
        assertThat(domainRepository.existsByDomainName("백엔드")).isTrue();
        assertThat(domainRepository.existsByDomainName("게임")).isFalse();
    }

    @Test
    void existsByDomainNameAndIdNotTest1() {

        Long backendId = findIdByName("백엔드");

        assertThat(domainRepository.existsByDomainNameAndIdNot("백엔드", backendId)).isFalse();
    }

    @Test
    void existsByDomainNameAndIdNotTest2() {

        Long frontendId = findIdByName("프론트엔드");

        assertThat(domainRepository.existsByDomainNameAndIdNot("백엔드", frontendId)).isTrue();

    }

    private Long findIdByName(String domainName) {
        return domainRepository.findAll().stream()
                .filter(domain -> domain.getDomainName().equals(domainName))
                .findFirst()
                .orElseThrow()
                .getId();
    }

}