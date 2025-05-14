package com.jng.lang_practice_tracker.route;

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
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class FindAllLanguageStudySessionsHandler implements HandlerFunction<ServerResponse> {
    private final StudySessionService studySessionService;

    public Mono<ServerResponse> handle(ServerRequest serverRequest) {
        return studySessionService.findAll()
                .collectList()
                .flatMap(studySessions -> ServerResponse.ok()
                        .body(BodyInserters.fromValue(Response.from(studySessions))));
    }

    @Data
    @Builder
   public static final class Response {
        List<Session> sessionList;
        public static Response from(Collection<LanguageStudySession> languageStudySessionList) {
            return builder()
                    .sessionList(languageStudySessionList.stream()
                            .map(Session::from)
                            .collect(Collectors.toList()))
                    .build();
        }
   }

   @Data
   @Builder
   public static final class Session {
       private UUID id;
       private String description;
       private String language;
       private URL resourceLink;
       private StudySession.Material resourceMaterial;
       private Duration timeSpent;
       private StudySession.Method method;
       private LocalDate studyDate;
       private StudySession.Status status;

       public static Session from(LanguageStudySession languageStudySession) {
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
