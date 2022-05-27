package io.github.krzysiekagi.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TestStep {

    @Id
    @GeneratedValue
    private Long testStepId;

    @ManyToOne
    @JoinColumn(name="testCaseId")
    private TestCase testCase;

    private String name;
    private String description;
}
