package io.github.krzysiekagi.service;

import io.github.krzysiekagi.model.TestCase;
import io.github.krzysiekagi.model.TestNotFoundException;
import io.github.krzysiekagi.model.TestStatus;
import io.github.krzysiekagi.repository.TestCaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TestCaseRepository testCaseRepository;

    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public List<TestCase> getTests() {
        logger.info("Fetching all testcases");
        return testCaseRepository.findAll();
    }

    public TestCase getTestById(Long id) {
        //TODO Add exception handling
        logger.info("Fetching testcase with id: {}", id);
        return testCaseRepository.findById(id).orElseThrow(TestNotFoundException::new);
    }

    public TestCase updateStatus(Long id, String status) {
        TestCase updatedTest = testCaseRepository.findById(id).orElseThrow(TestNotFoundException::new);
        try {
            logger.info("Changing test's {} status to: {}", id, status);
            updatedTest.setStatus(TestStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            //TBD: this theoretically allows for coming back to undefined state
            logger.warn("Provided incorrect status for test {}. Falling back to: {}", id, TestStatus.UNDEFINED);
            updatedTest.setStatus(TestStatus.UNDEFINED);
        }
        return testCaseRepository.save(updatedTest);
    }

    public TestCase updateTest(Long id, TestCase test) {
        TestCase updatedTest = testCaseRepository.findById(id).orElseThrow(TestNotFoundException::new);
        logger.info("Updating test {}", id);
        updatedTest.setTestName(test.getTestName());
        return testCaseRepository.save(updatedTest);
    }

    public TestCase addTest(String name) {
        logger.info("Adding a new test with name: {}", name);
        return testCaseRepository.save(new TestCase(name));
    }

    public String deleteTest(Long id) {
        logger.info("Removing test {}", id);
        testCaseRepository.deleteById(id);
        return "Removed test " + id;
    }
}
