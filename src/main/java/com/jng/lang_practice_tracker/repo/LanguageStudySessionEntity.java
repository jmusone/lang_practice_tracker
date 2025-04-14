package com.jng.lang_practice_tracker.repo;

import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
@Getter
@Setter
@Table("langStudySession")
public class LanguageStudySessionEntity extends StudySessionEntity {

    @Column(value = "lang")
    private String language;

    public static LanguageStudySessionEntity from(LanguageStudySession languageStudySession) {
        return builder()
                .id(languageStudySession.getId())
                .description(languageStudySession.getDescription())
                .language(languageStudySession.getLanguage())
                .resourceLink(languageStudySession.getResourceLink())
                .resourceMaterial(languageStudySession.getResourceMaterial())
                .timeSpent(languageStudySession.getTimeSpent())
                .method(languageStudySession.getMethod())
                .studyDate(languageStudySession.getStudyDate())
                .status(languageStudySession.getStatus())
                .build();
    }
}
