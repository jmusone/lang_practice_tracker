package com.jng.lang_practice_tracker.repo;

import com.jng.lang_practice_tracker.domain.StudySession;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table("studySession")
public class StudySessionEntity {
    @Column
    private UUID id;

    @Column
    private String description;

    @Column
    private URL resourceLink;

    @Column
    private StudySession.Material resourceMaterial;

    @Column
    private Duration timeSpent;

    @Column
    private StudySession.Method method;

    @Column
    private LocalDate studyDate;
}
