package io.github.krzysiekagi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "Tests")
@NoArgsConstructor
public class TestCase {

    @Id
    @GeneratedValue
    private Long id;

    private String testName;
    private String status; //TODO change to ENUM

    public TestCase(String testName, String status){
        this.testName = testName;
        this.status = status;
    }
}
