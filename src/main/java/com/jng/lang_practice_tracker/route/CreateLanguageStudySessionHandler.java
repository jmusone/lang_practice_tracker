package com.jng.lang_practice_tracker.route;

import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import com.jng.lang_practice_tracker.domain.StudySession;
import com.jng.lang_practice_tracker.service.StudySessionService;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class CreateLanguageStudySessionHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(request -> studySessionService.create(LanguageStudySession.builder()
                        .description(request.getDescription())
                        .language(request.getLanguage())
                        .resourceLink(request.getResourceLink())
                        .resourceMaterial(request.getResourceMaterial())
                        .timeSpent(request.getTimeSpent())
                        .method(request.getMethod())
                        .studyDate(request.getStudyDate())
                        .build()))
                .flatMap(languageStudySession -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(languageStudySession))))
                .onErrorResume(ex -> {
                    log.error("[Error] " + ex, ex);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @Data
    @Builder
    @AllArgsConstructor
    public static final class Request {
        private String description;
        private String language;
        private URL resourceLink;
        private StudySession.Material resourceMaterial;
        private Duration timeSpent;
        private StudySession.Method method;
        private LocalDate studyDate;
    }

    @Data
    @Builder
    public static final class Response {
        private UUID id;
        private String description;
        private String language;
        private URL resourceLink;
        private StudySession.Material resourceMaterial;
        private Duration timeSpent;
        private StudySession.Method method;
        private LocalDate studyDate;

        public static Response from(LanguageStudySession languageStudySession) {
            return builder()
                    .id(languageStudySession.getId())
                    .description(languageStudySession.getDescription())
                    .language(languageStudySession.getLanguage())
                    .resourceLink(languageStudySession.getResourceLink())
                    .resourceMaterial(languageStudySession.getResourceMaterial())
                    .timeSpent(languageStudySession.getTimeSpent())
                    .method(languageStudySession.getMethod())
                    .studyDate(languageStudySession.getStudyDate())
                    .build();
        }
    }
}
