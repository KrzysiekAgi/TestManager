package io.github.krzysiekagi.model.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class TestHistory {

    @Id
    @GeneratedValue
    private Long testHistoryId;

    @ManyToOne
    @JoinColumn(name="id")
    private TestCase testCase;

    private TestStatus status;
    private LocalDateTime changedAt;

}
