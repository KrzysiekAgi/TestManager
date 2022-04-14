package io.github.krzysiekagi.controller;

import io.github.krzysiekagi.model.TestCase;
import io.github.krzysiekagi.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/tests")
public class TestCaseController {

    @Autowired
    private final TestCaseRepository testCaseRepository;

    public TestCaseController(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
        this.testCaseRepository.save(new TestCase("test1", "passing"));
    }

    @GetMapping
    public List<TestCase> getTestCases() {
        return testCaseRepository.findAll();
    }

    @GetMapping("/{id}")
    public TestCase getTestCase(@PathVariable Long id) {
        //TODO gracefully handle non-existing testcase
        return testCaseRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    @PutMapping("/{id}")
    public ResponseEntity changeTestCaseStatus(@PathVariable Long id, @RequestBody boolean passing) {
        //TODO implement
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public ResponseEntity createTestCase(@RequestBody TestCase testCase) throws URISyntaxException {
        TestCase savedTestCase = testCaseRepository.save(testCase);
        return ResponseEntity.created(new URI("/tests/" + savedTestCase.getId())).body(savedTestCase);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTestCase(@PathVariable Long id) {
        testCaseRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
    
}
