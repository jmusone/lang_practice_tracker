package com.jng.lang_practice_tracker.config;

import com.jng.lang_practice_tracker.repo.StudySessionRepository;
import com.jng.lang_practice_tracker.service.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServiceConfig {
    private final StudySessionRepository studySessionRepository;

    @Autowired
    public ServiceConfig(StudySessionRepository studySessionRepository) {
        this.studySessionRepository = studySessionRepository;
    }

    @Bean
    public StudySessionService studySessionService() {
        return new StudySessionService(studySessionRepository);
    }
}
