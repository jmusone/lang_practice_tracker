package com.jng.lang_practice_tracker.repo;

import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudySessionRepository extends R2dbcRepository<LanguageStudySessionEntity, UUID> {
}
