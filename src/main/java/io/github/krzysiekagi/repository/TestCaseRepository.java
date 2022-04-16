package io.github.krzysiekagi.repository;

import io.github.krzysiekagi.model.TestCase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestCaseRepository extends JpaRepository<TestCase, Long> {
}
