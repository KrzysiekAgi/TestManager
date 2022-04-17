package io.github.krzysiekagi.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Tests")
@Data
@NoArgsConstructor
public class TestCase {

    @Id
    @GeneratedValue
    private Long id;

    private String testName;
    private TestStatus status;

    public TestCase(String testName){
        this.testName = testName;
        this.status = TestStatus.UNDEFINED;
    }
}
