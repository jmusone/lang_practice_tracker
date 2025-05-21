package com.jng.lang_practice_tracker.unit;

import com.jng.lang_practice_tracker.config.RouteConfig;
import com.jng.lang_practice_tracker.route.DeleteLanguageStudySessionHandler;
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
public class DeleteLanguageStudySessionHandlerTest {
    @Mock
    StudySessionService studySessionService;

    @InjectMocks
    DeleteLanguageStudySessionHandler deleteLanguageStudySessionHandler;

    @Test
    @DisplayName("DEL Delete Study Session Handler: Delete Study Session")
    public void shouldDeleteStudySessionHandler() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(studySessionService.delete(uuid))
                .thenReturn(Mono.empty());

        //Then
        deleteLanguageStudySessionHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();
        verify(studySessionService, times(1)).delete(uuid);
    }

    @Test
    @DisplayName("DEL Delete Study Session Handler: Service call returns error")
    public void shouldReturnErrorFromServiceCallInDeleteHandler() {
        //Given
        UUID uuid = UUID.randomUUID();
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn(uuid.toString());
        when(studySessionService.delete(uuid))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        deleteLanguageStudySessionHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionService, times(1)).delete(uuid);
    }

    @Test
    @DisplayName("PUT Update Study Session Handler: Id path variable is not set")
    public void shouldNotDeleteStudySessionBecauseNoIdFoundInPathVariable() {
        //Given
        ServerRequest serverRequest = mock(ServerRequest.class);

        //When
        when(serverRequest.pathVariable(RouteConfig.ID_VARIABLE))
                .thenReturn("");

        //Then
        deleteLanguageStudySessionHandler.handle(serverRequest)
                .as(StepVerifier::create)
                .verifyError(IllegalArgumentException.class);
        verify(studySessionService, times(0)).delete(any(UUID.class));
    }
}
