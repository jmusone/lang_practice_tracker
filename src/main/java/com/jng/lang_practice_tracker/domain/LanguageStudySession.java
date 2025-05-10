package com.jng.lang_practice_tracker.domain;

import com.jng.lang_practice_tracker.repo.LanguageStudySessionEntity;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@Getter
@Setter
public class LanguageStudySession extends StudySession {
    private String language;

    public static LanguageStudySession from(LanguageStudySessionEntity languageStudySessionEntity) {
        return builder()
                .id(languageStudySessionEntity.getId())
                .description(languageStudySessionEntity.getDescription())
                .language(languageStudySessionEntity.getLanguage())
                .resourceLink(languageStudySessionEntity.getResourceLink())
                .resourceMaterial(languageStudySessionEntity.getResourceMaterial())
                .timeSpent(languageStudySessionEntity.getTimeSpent())
                .method(languageStudySessionEntity.getMethod())
                .studyDate(languageStudySessionEntity.getStudyDate())
                .status(languageStudySessionEntity.getStatus())
                .build();
    }
}
