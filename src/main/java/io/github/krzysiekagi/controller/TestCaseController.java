package io.github.krzysiekagi.controller;

import io.github.krzysiekagi.model.TestCase;
import io.github.krzysiekagi.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TestCaseController {

    @Autowired
    TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @GetMapping("/tests")
    public ResponseEntity<List<TestCase>> getTestCases() {
        return ResponseEntity.ok().body(testCaseService.getTests());
    }

    @GetMapping("/tests/{id}")
    public ResponseEntity<TestCase> getTestCase(@PathVariable Long id) {
        return ResponseEntity.ok().body(testCaseService.getTestById(id));
    }

    @PutMapping("/tests/{id}")
    public ResponseEntity<String> changeTestCase(@PathVariable Long id, @RequestBody TestCase test) {
        return ResponseEntity.ok(testCaseService.updateTest(id, test).toString());
    }

    @PutMapping("/tests/{id}/status")
    public ResponseEntity<String> changeTestStatus(@PathVariable Long id, @RequestBody String status) {
        return ResponseEntity.ok(testCaseService.updateStatus(id, status).toString());
    }

    @PostMapping("/tests")
    public ResponseEntity<?> createTestCase(@RequestBody String name) throws URISyntaxException {
        TestCase savedTestCase = testCaseService.addTest(name);
        return ResponseEntity.created(new URI("/tests/" + savedTestCase.getId())).body(savedTestCase);
    }

    @DeleteMapping("/tests/{id}")
    public ResponseEntity<String> deleteTestCase(@PathVariable Long id) {
        return ResponseEntity.ok().body(testCaseService.deleteTest(id));
    }
    
}
