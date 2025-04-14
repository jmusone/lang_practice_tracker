package com.jng.lang_practice_tracker.repo;

import com.jng.lang_practice_tracker.domain.StudySession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.net.URL;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class StudySessionEntity {

    @Id
    private UUID id;

    @Column
    private String description;

    @Column(value = "resourceLink")
    private URL resourceLink;

    @Column(value = "resourceMaterial")
    private StudySession.Material resourceMaterial;

    @Column(value = "timeSpent")
    private Duration timeSpent;

    @Column
    private StudySession.Method method;

    @Column(value = "studyDate")
    private LocalDate studyDate;

    @Column
    private Instant created;

    @Column
    private Instant updated;

    @Column
    private StudySession.Status status;
}
