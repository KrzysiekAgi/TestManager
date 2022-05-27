package io.github.krzysiekagi.model.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TestHistory {

    @Id
    @GeneratedValue
    private Long testHistoryId;

    @ManyToOne
    @JoinColumn(name="id")
    private TestCase testCase;


}
