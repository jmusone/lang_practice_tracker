package com.jng.lang_practice_tracker.unit;

import com.jng.lang_practice_tracker.config.RouteConfig;
import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import com.jng.lang_practice_tracker.route.FindLanguageStudySessionByIdHandler;
import com.jng.lang_practice_tracker.service.StudySessionService;
import io.r2dbc.spi.R2dbcBadGrammarException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindLanguageStudySessionByIdHandlerTest {

    @Mock
    StudySessionService studySessionService;

    @InjectMocks
    FindLanguageStudySessionByIdHandler findLanguageStudySessionByIdHandler;

    @Test
    @DisplayName("GET Find Study Session By Id Handler: Find study session by id")
    public void shouldFindStudySessionByIdHandler() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);
        LanguageStudySession languageStudySession = LanguageStudySession.builder().build();

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(studySessionService.findById(uuid))
                .thenReturn(Mono.just(languageStudySession));

        //Then
        findLanguageStudySessionByIdHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        verify(studySessionService, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("GET Find Study Session By Id Handler: Service call returns no Id found")
    public void shouldNotFindStudySessionByIdBecauseNoIdFoundInDatabase() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(studySessionService.findById(uuid))
                .thenReturn(Mono.empty());

        //Then
        findLanguageStudySessionByIdHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        verify(studySessionService, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("GET Find Study Session By Id Handler: Id path variable is not set")
    public void shouldNotFindStudySessionByIdBecauseNoIdFoundInPathVariable() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn("");

        //Then
        findLanguageStudySessionByIdHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .verifyError(IllegalArgumentException.class);
        verify(studySessionService, times(0)).findById(any(UUID.class));
    }

    @Test
    @DisplayName("GET Find Study Session By Id Handler: Service call returns error")
    public void shouldReturnErrorFromServiceCallInFindByIdHandler() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(studySessionService.findById(uuid))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        findLanguageStudySessionByIdHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionService, times(1)).findById(uuid);
    }
}
