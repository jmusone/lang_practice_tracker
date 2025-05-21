package com.jng.lang_practice_tracker.unit;

import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import com.jng.lang_practice_tracker.route.CreateLanguageStudySessionHandler;
import com.jng.lang_practice_tracker.service.StudySessionService;
import io.r2dbc.spi.R2dbcBadGrammarException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateLanguageStudySessionHandlerTest {

    @Mock
    StudySessionService studySessionService;

    @InjectMocks
    CreateLanguageStudySessionHandler createLanguageStudySessionHandler;

    @Test
    @DisplayName("POST Create Study Session Handler: Create Study Session")
    public void shouldCreateStudySessionHandler() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);
        CreateLanguageStudySessionHandler.Request request = CreateLanguageStudySessionHandler.Request.builder().build();
        LanguageStudySession languageStudySession = LanguageStudySession.builder().build();

        //When
        when(studySessionService.create(any(LanguageStudySession.class)))
                .thenReturn(Mono.just(languageStudySession));
        when(serverRequest.bodyToMono(CreateLanguageStudySessionHandler.Request.class))
                .thenReturn(Mono.just(request));

        //Then
        createLanguageStudySessionHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        verify(studySessionService, times(1)).create(any(LanguageStudySession.class));
    }

    @Test
    @DisplayName("POST Create Study Session Handler: Service call returns error")
    public void shouldReturnErrorFromServiceCallInCreateHandler() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);
        CreateLanguageStudySessionHandler.Request request = CreateLanguageStudySessionHandler.Request.builder().build();
        LanguageStudySession languageStudySession = LanguageStudySession.builder().build();

        //When
        when(studySessionService.create(any(LanguageStudySession.class)))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));
        when(serverRequest.bodyToMono(CreateLanguageStudySessionHandler.Request.class))
                .thenReturn(Mono.just(request));

        //Then
        createLanguageStudySessionHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .assertNext(serverResponse -> Assertions.assertTrue(HttpStatus.INTERNAL_SERVER_ERROR == serverResponse.statusCode()))
                .verifyComplete();
        verify(studySessionService, times(1)).create(any(LanguageStudySession.class));
    }
}
