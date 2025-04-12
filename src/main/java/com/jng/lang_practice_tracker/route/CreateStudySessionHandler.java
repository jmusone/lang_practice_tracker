package com.jng.lang_practice_tracker.route;

import com.jng.lang_practice_tracker.constants.DataEnum;
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
public class CreateStudySessionHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(request -> studySessionService.create(StudySession.builder()
                        .description(request.getDescription())
                        .resourceLink(request.getResourceLink())
                        .resourceMaterial(request.getResourceMaterial())
                        .timeSpent(request.getTimeSpent())
                        .method(request.getMethod())
                        .studyDate(request.getStudyDate())
                        .build()))
                .flatMap(studySession -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(studySession))))
                .onErrorResume(ex -> {
                    log.error("[Error] " + ex, ex);
                    return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                });
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static final class Request {
        private String description;
        private URL resourceLink;
        private DataEnum.Material resourceMaterial;
        private Duration timeSpent;
        private DataEnum.Method method;
        private LocalDate studyDate;
    }

    @Data
    @Builder
    private static final class Response {
        private UUID id;
        private String description;
        private URL resourceLink;
        private DataEnum.Material resourceMaterial;
        private Duration timeSpent;
        private DataEnum.Method method;
        private LocalDate studyDate;

        public static Response from(StudySession studySession) {
            return builder()
                    .id(studySession.getId())
                    .description(studySession.getDescription())
                    .resourceLink(studySession.getResourceLink())
                    .resourceMaterial(studySession.getResourceMaterial())
                    .timeSpent(studySession.getTimeSpent())
                    .method(studySession.getMethod())
                    .studyDate(studySession.getStudyDate())
                    .build();
        }
    }
}
