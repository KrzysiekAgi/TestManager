package io.github.krzysiekagi.service;

import io.github.krzysiekagi.model.TestCase;
import io.github.krzysiekagi.model.TestNotFoundException;
import io.github.krzysiekagi.model.TestStatus;
import io.github.krzysiekagi.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestCaseService {

    @Autowired
    TestCaseRepository testCaseRepository;

    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public List<TestCase> getTests() {
        return testCaseRepository.findAll();
    }

    public TestCase getTestById(Long id) {
        //TODO Add exception handling
        return testCaseRepository.findById(id).orElseThrow(TestNotFoundException::new);
    }

    public TestCase updateStatus(Long id, String status) {
        TestCase updatedTest = testCaseRepository.findById(id).orElseThrow(TestNotFoundException::new);
        try {
            updatedTest.setStatus(TestStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            //TBD: this theoretically allows for coming back to undefined state
            updatedTest.setStatus(TestStatus.UNDEFINED);
        }
        return testCaseRepository.save(updatedTest);
    }

    public TestCase updateTest(Long id, TestCase test) {
        TestCase updatedTest = testCaseRepository.findById(id).orElseThrow(TestNotFoundException::new);
        updatedTest.setTestName(test.getTestName());
        return testCaseRepository.save(updatedTest);
    }

    public TestCase addTest(String name) {
        return testCaseRepository.save(new TestCase(name));
    }

    public String deleteTest(Long id) {
        testCaseRepository.deleteById(id);
        return "Removed test " + id;
    }
}
