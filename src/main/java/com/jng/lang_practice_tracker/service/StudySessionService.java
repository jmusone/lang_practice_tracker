package com.jng.lang_practice_tracker.service;

import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import com.jng.lang_practice_tracker.domain.StudySession;
import com.jng.lang_practice_tracker.repo.LanguageStudySessionEntity;
import com.jng.lang_practice_tracker.repo.StudySessionRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public class StudySessionService {
    private final StudySessionRepository sessionRepository;

    public Flux<LanguageStudySession> findAll() {
        return sessionRepository.findAll()
                .map(LanguageStudySession::from);
    }

    public Mono<LanguageStudySession> findById(UUID id) {
        return sessionRepository.findById(id)
                .map(LanguageStudySession::from);
    }

    public Mono<LanguageStudySession> create(LanguageStudySession session) {
        return sessionRepository.save(LanguageStudySessionEntity.from(session)
                .toBuilder()
                .created(Instant.now())
                .status(StudySession.Status.PRESENT)
                .build())
                .map(LanguageStudySession::from);
    }

    public Mono<LanguageStudySession> update(LanguageStudySession session) {
        return sessionRepository.findById(session.getId())
                .map(entity -> entity.toBuilder()
                        .id(session.getId())
                        .description(session.getDescription())
                        .language(session.getLanguage())
                        .resourceLink(session.getResourceLink())
                        .resourceMaterial(session.getResourceMaterial())
                        .timeSpent(session.getTimeSpent())
                        .method(session.getMethod())
                        .studyDate(session.getStudyDate())
                        .status(session.getStatus())
                        .updated(Instant.now())
                        .build())
                .flatMap(updated -> sessionRepository.save(updated))
                .map(LanguageStudySession::from);
    }

    public Mono<Void> delete(UUID id) {
        return sessionRepository.findById(id)
                .map(entity -> entity.toBuilder()
                        .updated(Instant.now())
                        .status(StudySession.Status.DELETED)
                        .build())
                .flatMap(deleted -> sessionRepository.save(deleted))
                .then();
    }
}
