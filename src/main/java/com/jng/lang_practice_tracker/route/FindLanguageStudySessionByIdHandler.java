package com.jng.lang_practice_tracker.route;

import com.jng.lang_practice_tracker.config.RouteConfig;
import com.jng.lang_practice_tracker.domain.LanguageStudySession;
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
public class FindLanguageStudySessionByIdHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return studySessionService.findById(UUID.fromString(serverRequest.pathVariable(RouteConfig.ID_VARIABLE)))
                .flatMap(languageStudySession -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(languageStudySession))));
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
        private StudySession.Status status;

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
                    .status(languageStudySession.getStatus())
                    .build();
        }
    }
}
