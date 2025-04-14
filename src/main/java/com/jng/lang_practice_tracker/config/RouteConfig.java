package com.jng.lang_practice_tracker.config;

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

    public static final String VERSION_VARIABLE = "v1";
    public static final String ID_VARIABLE = "id";

    public static final String STUDY_SESSION_PATH = "/" + VERSION_VARIABLE + "/studysession/";
    public static final String LANG_STUDY_SESSION_PATH = STUDY_SESSION_PATH + "lang/";
    public static final String LANG_STUDY_SESSION_BY_ID_PATH = LANG_STUDY_SESSION_PATH + "{" + ID_VARIABLE + "}/";

    @Autowired
    public RouteConfig(StudySessionService studySessionService) {
        this.studySessionService = studySessionService;
    }

    @Bean
    public RouterFunction<ServerResponse> findAllStudySessionRoute() {
        return route(GET(LANG_STUDY_SESSION_PATH), findAllStudySessionsHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> findAllStudySessionsHandler() {
        return new FindAllLanguageStudySessionsHandler(studySessionService);
    }

    @Bean
    public RouterFunction<ServerResponse> findStudySessionByIdRoute() {
        return route(GET(LANG_STUDY_SESSION_BY_ID_PATH), findStudySessionByIdHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> findStudySessionByIdHandler() {
        return new FindLanguageStudySessionByIdHandler(studySessionService);
    }

    @Bean
    public RouterFunction<ServerResponse> createStudySessionRoute() {
        return route(POST(LANG_STUDY_SESSION_PATH), createStudySessionHander());
    }

    @Bean
    public HandlerFunction<ServerResponse> createStudySessionHander() {
        return new CreateLanguageStudySessionHandler(studySessionService);
    }

    @Bean
    public RouterFunction<ServerResponse> updateStudySessionRoute() {
        return route(PUT(LANG_STUDY_SESSION_BY_ID_PATH), updateStudySessionHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> updateStudySessionHandler() {
        return new UpdateLanguageStudySessionHandler(studySessionService);
    }

    @Bean
    public  RouterFunction<ServerResponse> deleteStudySessionRoute() {
        return route(DELETE(LANG_STUDY_SESSION_BY_ID_PATH), deleteStudySessionHandler());
    }

    @Bean
    public HandlerFunction<ServerResponse> deleteStudySessionHandler() {
        return new DeleteLanguageStudySessionHandler(studySessionService);
    }
}
