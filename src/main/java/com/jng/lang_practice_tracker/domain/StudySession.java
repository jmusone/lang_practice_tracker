package com.jng.lang_practice_tracker.domain;

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
    public enum Method {
        ACTIVE_STUDYING,
        COMPREHENSIVE_INPUT,
        PASSIVE_INPUT
    }

    public enum Material {
        TEXTBOOK,
        BOOK,
        WRITING_BOOK,
        FLASHCARDS,
        ANKI,
        VIDEO,
        CLASS,
        TUTORING,
        MOBILE_APP,
        WEBSITE
    }

    private final UUID id;
    private String description;
    private URL resourceLink;
    private Material resourceMaterial;
    private Duration timeSpent;
    private Method method;
    private LocalDate studyDate;

    public static StudySession from(StudySessionEntity studySessionEntity) {
        return builder()
                .id(studySessionEntity.getId())
                .description(studySessionEntity.getDescription())
                .resourceLink(studySessionEntity.getResourceLink())
                .resourceMaterial(studySessionEntity.getResourceMaterial())
                .timeSpent(studySessionEntity.getTimeSpent())
                .method(studySessionEntity.getMethod())
                .studyDate(studySessionEntity.getStudyDate())
                .build();
    }
}
