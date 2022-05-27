package io.github.krzysiekagi.model.dto;

import io.github.krzysiekagi.model.domain.TestStatus;
import io.github.krzysiekagi.model.domain.TestStep;

import java.util.List;

public class TestDto {

    private String testName;
    private TestStatus status;
    private List<TestStep> testSteps;
    private List<TestStatus> testHistory;
}
