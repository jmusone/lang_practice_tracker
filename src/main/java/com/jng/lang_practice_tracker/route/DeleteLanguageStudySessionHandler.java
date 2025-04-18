package com.jng.lang_practice_tracker.route;

import com.jng.lang_practice_tracker.config.RouteConfig;
import com.jng.lang_practice_tracker.service.StudySessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RequiredArgsConstructor
public class DeleteLanguageStudySessionHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return studySessionService.delete(UUID.fromString(serverRequest.pathVariable(RouteConfig.ID_VARIABLE)))
                .flatMap(languageStudySession -> ServerResponse.noContent()
                        .build());
    }
}
