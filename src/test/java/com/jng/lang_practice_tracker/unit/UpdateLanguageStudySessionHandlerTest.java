package com.jng.lang_practice_tracker.unit;

import com.jng.lang_practice_tracker.config.RouteConfig;
import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import com.jng.lang_practice_tracker.route.UpdateLanguageStudySessionHandler;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UpdateLanguageStudySessionHandlerTest {

    @Mock
    StudySessionService studySessionService;

    @InjectMocks
    UpdateLanguageStudySessionHandler updateLanguageStudySessionHandler;

    @Test
    @DisplayName("PUT Update Study Session Handler: Update Study Session")
    public void shouldUpdateStudySessionHandler() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest request = mock(ServerRequest.class);
        LanguageStudySession languageStudySession = LanguageStudySession.builder().build();
        UpdateLanguageStudySessionHandler.Request handlerRequest = UpdateLanguageStudySessionHandler.Request.builder().build();

        //When
        when(studySessionService.update(any(LanguageStudySession.class)))
                .thenReturn(Mono.just(languageStudySession));
        when(request.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(request.bodyToMono(UpdateLanguageStudySessionHandler.Request.class))
                .thenReturn(Mono.just(handlerRequest));

        //Then
        updateLanguageStudySessionHandler.handle(request)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
        verify(studySessionService, times(1)).update(any(LanguageStudySession.class));
    }

    @Test
    @DisplayName("PUT Update Study Session Handler: Service call returns error")
    public void shouldReturnErrorFromServiceCallInUpdateHandler() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest request = mock(ServerRequest.class);
        UpdateLanguageStudySessionHandler.Request handlerRequest = mock(UpdateLanguageStudySessionHandler.Request.class);

        //When
        when(studySessionService.update(any(LanguageStudySession.class)))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));
        when(request.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(request.bodyToMono(UpdateLanguageStudySessionHandler.Request.class))
                .thenReturn(Mono.just(handlerRequest));

        //Then
        updateLanguageStudySessionHandler.handle(request)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionService, times(1)).update(any(LanguageStudySession.class));
    }

    @Test
    @DisplayName("PUT Update Study Session Handler: Id path variable is not set")
    public void shouldNotUpdateStudySessionBecauseNoIdFoundInPathVariable() {
        //Given
        ServerRequest request = mock(ServerRequest.class);
        UpdateLanguageStudySessionHandler.Request handlerRequest = mock(UpdateLanguageStudySessionHandler.Request.class);

        //When
        when(request.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn("");
        when(request.bodyToMono(UpdateLanguageStudySessionHandler.Request.class))
                .thenReturn(Mono.just(handlerRequest));

        //Then
        updateLanguageStudySessionHandler.handle(request)
                .as(StepVerifier::create)
                .verifyError(IllegalArgumentException.class);
        verify(studySessionService, times(0)).update(any(LanguageStudySession.class));
    }

    @Test
    @DisplayName("PUT Update Study Session Handler: Service call returns no Id found")
    public void shouldNotUpdateStudySessionBecauseNoIdFoundInDatabase() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest request = mock(ServerRequest.class);
        UpdateLanguageStudySessionHandler.Request handlerRequest = mock(UpdateLanguageStudySessionHandler.Request.class);

        //When
        when(studySessionService.update(any(LanguageStudySession.class)))
                .thenReturn(Mono.empty());
        when(request.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(request.bodyToMono(UpdateLanguageStudySessionHandler.Request.class))
                .thenReturn(Mono.just(handlerRequest));

        //Then
        updateLanguageStudySessionHandler.handle(request)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        verify(studySessionService, times(1)).update(any(LanguageStudySession.class));
    }
}
