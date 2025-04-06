package com.jng.lang_practice_tracker.config;

import com.jng.lang_practice_tracker.constants.Constants;
import com.jng.lang_practice_tracker.route.*;
import com.jng.lang_practice_tracker.service.StudySessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;

@Configuration
public class RouteConfig {
    private final StudySessionService studySessionService;

    @Autowired
    public RouteConfig(StudySessionService studySessionService) {
        this.studySessionService = studySessionService;
    }

    @Bean
    public RouterFunction<ServerResponse> findAllStudySessionRoute() {
        return route(GET(Constants.STUDY_SESSION_PATH), findAllStudySessionsHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> findAllStudySessionsHandler() {
        return new FindAllStudySessionsHandler(studySessionService);
    }

    @Bean
    public RouterFunction<ServerResponse> findStudySessionByIdRoute() {
        return route(GET(Constants.STUDY_SESSION_BY_ID_PATH), findStudySessionByIdHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> findStudySessionByIdHandler() {
        return new FindStudySessionByIdHandler(studySessionService);
    }

    @Bean
    public RouterFunction<ServerResponse> createStudySessionRoute() {
        return route(POST(Constants.STUDY_SESSION_PATH), createStudySessionHander());
    }

    @Bean
    public HandlerFunction<ServerResponse> createStudySessionHander() {
        return new CreateStudySessionHandler(studySessionService);
    }

    @Bean
    public RouterFunction<ServerResponse> updateStudySessionRoute() {
        return route(PUT(Constants.STUDY_SESSION_BY_ID_PATH), updateStudySessionHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> updateStudySessionHandler() {
        return new UpdateStudySessionHandler(studySessionService);
    }

    @Bean
    public  RouterFunction<ServerResponse> deleteStudySessionRoute() {
        return route(DELETE(Constants.STUDY_SESSION_BY_ID_PATH), deleteStudySessionHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> deleteStudySessionHandler() {
        return new DeleteStudySessionHandler(studySessionService);
    }
}
