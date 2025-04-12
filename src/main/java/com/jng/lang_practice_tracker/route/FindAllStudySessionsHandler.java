package com.jng.lang_practice_tracker.route;

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
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FindAllStudySessionsHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return studySessionService.findAll()
                .collectList()
                .flatMap(studySessions -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(studySessions))));
    }

    @Data
    @Builder
   private static final class Response {
        List<Session> sessionList;
        public static Response from(Collection<StudySession> studySessionList) {
            return builder()
                    .sessionList(studySessionList.stream()
                            .map(Session::from)
                            .collect(Collectors.toList()))
                    .build();
        }
   }

   @Data
    @Builder
    private static final class Session {
       private UUID id;
       private String description;
       private URL resourceLink;
       private StudySession.Material resourceMaterial;
       private Duration timeSpent;
       private StudySession.Method method;
       private LocalDate studyDate;
       private StudySession.Status status;

       public static Session from(StudySession studySession) {
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
