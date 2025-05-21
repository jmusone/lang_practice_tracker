package com.jng.lang_practice_tracker.unit;

import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import com.jng.lang_practice_tracker.route.FindAllLanguageStudySessionsHandler;
import com.jng.lang_practice_tracker.service.StudySessionService;
import io.r2dbc.spi.R2dbcBadGrammarException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FindAllLanguageStudySessionHandlerTest {

    @Mock
    StudySessionService studySessionService;

    @InjectMocks
    FindAllLanguageStudySessionsHandler findAllLanguageStudySessionsHandler;

    @Test
    @DisplayName("GET Find ALL Study Sessions Handler: Return a study sessions")
    public void shouldReturnOneStudySessionFindAllStudySessionsHandler() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);
        LanguageStudySession languageStudySession = LanguageStudySession.builder().build();

        //When
        when(studySessionService.findAll())
                .thenReturn(Flux.just(languageStudySession));

        //Then
        findAllLanguageStudySessionsHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        verify(studySessionService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET Find ALL Study Sessions Handler: Return multiple study sessions")
    public void shouldReturnMultipleStudySessionsFindAllStudySessionsHandler() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);
        LanguageStudySession languageStudySession1 = LanguageStudySession.builder().build();
        LanguageStudySession languageStudySession2 = LanguageStudySession.builder().build();

        //When
        when(studySessionService.findAll())
                .thenReturn(Flux.just(languageStudySession1, languageStudySession2));

        //Then
        findAllLanguageStudySessionsHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        verify(studySessionService, times(1)).findAll();
    }

    @Test
    @DisplayName("GET Find ALL Study Sessions Handler: Service call returns error")
    public void shouldReturnErrorFromServiceCallInFindAllHandler() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(studySessionService.findAll())
                .thenReturn(Flux.error(new R2dbcBadGrammarException()));

        //Then
        findAllLanguageStudySessionsHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionService, times(1)).findAll();
    }
}
