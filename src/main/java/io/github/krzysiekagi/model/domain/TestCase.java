package io.github.krzysiekagi.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Test")
@Data
@NoArgsConstructor
public class TestCase {

    @Id
    @GeneratedValue
    private Long testCaseId;

    private String testName;
    private TestStatus status;

    @Access(AccessType.FIELD)
    @OneToMany(targetEntity=TestStep.class, mappedBy="testCase", fetch=FetchType.EAGER)
    private List<TestStep> steps;

    public TestCase(String testName){
        this.testName = testName;
        this.status = TestStatus.UNDEFINED;
    }
}
