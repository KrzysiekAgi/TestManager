package io.github.krzysiekagi.service;

import io.github.krzysiekagi.exception.InvalidTestNameException;
import io.github.krzysiekagi.model.TestCase;
import io.github.krzysiekagi.exception.TestNotFoundException;
import io.github.krzysiekagi.model.TestStatus;
import io.github.krzysiekagi.repository.TestCaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        logger.info("Fetching testcase with id: {}", id);
        return testCaseRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
    }

    public TestCase updateStatus(Long id, String status) {
        TestCase updatedTest = testCaseRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        logger.info("Changing test's {} status to: {}", id, status);
        updatedTest.setStatus(TestStatus.valueOf(status.toUpperCase()));
        return testCaseRepository.save(updatedTest);
    }

    public TestCase updateTestName(Long id, String testName) {
        TestCase updatedTest = testCaseRepository.findById(id).orElseThrow(() -> new TestNotFoundException(id));
        logger.info("Updating test {}", id);
        if (isTestNameValid(testName))
            updatedTest.setTestName(testName);
        else throw new InvalidTestNameException(testName);
        return testCaseRepository.save(updatedTest);
    }

    public TestCase addTest(String name) {
        logger.info("Adding a new test with name: {}", name);
        if (isTestNameValid(name))
            return testCaseRepository.save(new TestCase(name));
        else throw new InvalidTestNameException(name);
    }

    public String deleteTest(Long id) {
        logger.info("Removing test {}", id);
        testCaseRepository.deleteById(id);
        return "Removed test " + id;
    }

    private boolean isTestNameValid(String name) {
        Pattern pattern = Pattern.compile("\\w*");
        Matcher matcher = pattern.matcher(name);
        return !name.isBlank() && name.length() < 255 && matcher.matches();
    }
}
