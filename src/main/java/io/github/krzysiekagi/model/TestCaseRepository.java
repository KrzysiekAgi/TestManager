package io.github.krzysiekagi.model;

import io.github.krzysiekagi.model.domain.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
