package io.github.krzysiekagi.model.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Steps")
@Data
@RequiredArgsConstructor
public class TestStep {

    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String stepName;
}
