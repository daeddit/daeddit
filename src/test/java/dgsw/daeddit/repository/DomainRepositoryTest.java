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

    @Sql("/data.sql")
    @Test
    void existsByDomainNameTest() {
        assertThat(domainRepository.existsByDomainName("백엔드")).isTrue();
        assertThat(domainRepository.existsByDomainName("게임")).isFalse();
    }

    @Sql("/data.sql")
    @Test
    void existsByDomainNameAndIdNotTest1() {

        Long backendId = domainRepository.findByDomainName("백엔드");

        assertThat(domainRepository.existsByDomainNameAndIdNot("백엔드", backendId)).isFalse();
    }

    @Sql("/data.sql")
    @Test
    void existsByDomainNameAndIdNotTest2() {

        Long frontendId = domainRepository.findByDomainName("프론트엔드");

        assertThat(domainRepository.existsByDomainNameAndIdNot("백엔드", frontendId)).isTrue();

    }

}