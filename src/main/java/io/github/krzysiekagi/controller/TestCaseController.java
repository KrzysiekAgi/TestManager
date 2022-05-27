package io.github.krzysiekagi.controller;

import io.github.krzysiekagi.model.domain.TestCase;
import io.github.krzysiekagi.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class TestCaseController {

    @Autowired
    TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping("/tests")
    public List<TestCase> getTestCases() {
        return testCaseService.getTests();
    }

    @GetMapping("/tests/{id}")
    public TestCase getTestCase(@PathVariable Long id) {
        return testCaseService.getTestById(id);
    }

    @PutMapping("/tests/{id}/status")
    public TestCase changeTestStatus(@PathVariable Long id, @RequestBody String status) {
        return testCaseService.updateStatus(id, status);
    }

    @PutMapping("/tests/{id}/name")
    public TestCase changeTestName(@PathVariable Long id, @RequestBody String name) {
        return testCaseService.updateTestName(id, name);
    }

    @PostMapping("/tests")
    @ResponseStatus(HttpStatus.CREATED)
    public TestCase createTestCase(@RequestBody String name) {
        return testCaseService.addTest(name);
    }

    @DeleteMapping("/tests/{id}")
    public String deleteTestCase(@PathVariable Long id) {
        return testCaseService.deleteTest(id);
    }
    
}
