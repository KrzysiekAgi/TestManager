package io.github.krzysiekagi;

import io.github.krzysiekagi.model.domain.TestCase;
import io.github.krzysiekagi.model.domain.TestStatus;
import io.github.krzysiekagi.model.TestCaseRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TestCaseRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Test
    public void testDefaultValues() {
        //given
        String name = "test";
        TestCase test = new TestCase(name);
        entityManager.persist(test);
        Object id = entityManager.getId(test);
        entityManager.flush();

        //when
        Optional<TestCase> found = testCaseRepository.findById((Long) id);

        //then
        assertThat(found.isPresent()).isEqualTo(true);
        assertThat(found.get().getTestName()).isEqualTo(name);
        assertThat(found.get().getStatus()).isEqualTo(TestStatus.UNDEFINED);
    }
}
