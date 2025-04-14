package com.jng.lang_practice_tracker.domain;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
public class StudySession implements Trackable {
    public enum Method {
        ACTIVE_STUDYING,
        COMPREHENSIVE_INPUT,
        PASSIVE_INPUT
    }

    public enum Material {
        TEXTBOOK,
        WORKBOOK,
        WRITING_BOOK,
        FLASHCARDS,
        ANKI,
        VIDEO,
        CLASS,
        TUTORING,
        MOBILE_APP,
        WEBSITE
    }

    public enum Status {
        PRESENT,
        DELETED
    }

    private final UUID id;
    private String description;
    private URL resourceLink;
    private Material resourceMaterial;
    private Duration timeSpent;
    private Method method;
    private LocalDate studyDate;
    private Status status;
}
