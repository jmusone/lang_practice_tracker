package com.jng.lang_practice_tracker.domain;

import com.jng.lang_practice_tracker.constants.DataEnum;
import com.jng.lang_practice_tracker.repo.StudySessionEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@Getter
@Setter
public class StudySession implements Trackable {
    private final UUID id;
    private String description;
    private URL resourceLink;
    private DataEnum.Material resourceMaterial;
    private Duration timeSpent;
    private DataEnum.Method method;
    private LocalDate studyDate;
    private DataEnum.Status status;

    public static StudySession from(StudySessionEntity studySessionEntity) {
        return builder()
                .id(studySessionEntity.getId())
                .description(studySessionEntity.getDescription())
                .resourceLink(studySessionEntity.getResourceLink())
                .resourceMaterial(studySessionEntity.getResourceMaterial())
                .timeSpent(studySessionEntity.getTimeSpent())
                .method(studySessionEntity.getMethod())
                .studyDate(studySessionEntity.getStudyDate())
                .status(studySessionEntity.getStatus())
                .build();
    }
}
