package com.jng.lang_practice_tracker.route;

import com.jng.lang_practice_tracker.constants.DataEnum;
import com.jng.lang_practice_tracker.constants.Endpoint;
import com.jng.lang_practice_tracker.domain.StudySession;
import com.jng.lang_practice_tracker.service.StudySessionService;
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
public class FindStudySessionByIdHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return studySessionService.findById(UUID.fromString(serverRequest.pathVariable(Endpoint.ID_VARIABLE)))
                .flatMap(studySession -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(studySession))));
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
        private DataEnum.Status status;

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
