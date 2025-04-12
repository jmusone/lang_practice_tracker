package com.jng.lang_practice_tracker.route;

import com.jng.lang_practice_tracker.constants.Constants;
import com.jng.lang_practice_tracker.domain.StudySession;
import com.jng.lang_practice_tracker.service.StudySessionService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

@RequiredArgsConstructor
public class UpdateStudySessionHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return serverRequest.bodyToMono(Request.class)
                .flatMap(request -> studySessionService.update(StudySession.builder()
                        .id(UUID.fromString(serverRequest.pathVariable(Constants.ID_VARIABLE)))
                        .description(request.getDescription())
                        .resourceLink(request.getResourceLink())
                        .resourceMaterial(request.getResourceMaterial())
                        .timeSpent(request.getTimeSpent())
                        .method(request.getMethod())
                        .studyDate(request.getStudyDate())
                        .status(request.getStatus())
                        .build()))
                .flatMap(studySession -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(studySession))));
    }

    @Data
    @Builder
    @AllArgsConstructor
    private static final class Request {
        private UUID id;
        private String description;
        private URL resourceLink;
        private StudySession.Material resourceMaterial;
        private Duration timeSpent;
        private StudySession.Method method;
        private LocalDate studyDate;
        private StudySession.Status status;
    }

    @Data
    @Builder
    private static final class Response {
        private UUID id;
        private String description;
        private URL resourceLink;
        private StudySession.Material resourceMaterial;
        private Duration timeSpent;
        private StudySession.Method method;
        private LocalDate studyDate;
        private StudySession.Status status;

        public static Response from(StudySession studySession) {
            return builder()
                    .id(studySession.getId())
                    .description(studySession.getDescription())
                    .resourceLink(studySession.getResourceLink())
                    .resourceMaterial(studySession.getResourceMaterial())
                    .timeSpent(studySession.getTimeSpent())
                    .method(studySession.getMethod())
                    .studyDate(studySession.getStudyDate())
                    .status(studySession.getStatus())
                    .build();
        }
    }
}
