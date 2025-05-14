package com.jng.lang_practice_tracker.unit;

import com.jng.lang_practice_tracker.domain.LanguageStudySession;
import com.jng.lang_practice_tracker.domain.StudySession;
import com.jng.lang_practice_tracker.repo.LanguageStudySessionEntity;
import com.jng.lang_practice_tracker.repo.StudySessionRepository;
import com.jng.lang_practice_tracker.service.StudySessionService;
import io.r2dbc.spi.R2dbcBadGrammarException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudySessionServiceTest {

    @Mock
    StudySessionRepository studySessionRepository;

    @InjectMocks
    StudySessionService studySessionService;

    @Test
    @DisplayName("GET Find All: Return all study sessions from database")
    public void shouldReturnAllStudySessions() throws MalformedURLException {
        //Given
        LanguageStudySessionEntity languageStudySessionEntity = LanguageStudySessionEntity.builder()
                .id(UUID.randomUUID())
                .description("This is a description of a language study session for testing")
                .language("Mandarin")
                .resourceLink(new URL("https://www.youtube.com/@comprehensiblechinese/playlists"))
                .resourceMaterial(StudySession.Material.VIDEO)
                .timeSpent(Duration.ofMinutes(30))
                .method(StudySession.Method.COMPREHENSIVE_INPUT)
                .studyDate(LocalDate.now())
                .build();

        //When
        when(studySessionRepository.findAll())
                .thenReturn(Flux.just(languageStudySessionEntity));

        //Then
        studySessionService.findAll()
                .as(StepVerifier::create)
                .assertNext(studySession -> assertEquals(studySession, LanguageStudySession.from(languageStudySessionEntity)))
                .verifyComplete();
        verify(studySessionRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("GET Find by Id: Return given study session based on Id")
    public void shouldReturnStudySessionById() {
        //Given
        final UUID uuid = UUID.randomUUID();
        LanguageStudySessionEntity languageStudySessionEntity = mock(LanguageStudySessionEntity.class);

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.just(languageStudySessionEntity));
        when(languageStudySessionEntity.getId())
                .thenReturn(uuid);

        //Then
        studySessionService.findById(uuid)
                .as(StepVerifier::create)
                .assertNext(studySession -> assertEquals(uuid, studySession.getId()))
                .verifyComplete();
        verify(studySessionRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("GET Find by Id: Should not return study session by Id if does not exist")
    public void shouldNotReturnStudySessionIfNotFoundById() {
        //Given
        UUID uuid = UUID.randomUUID();

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.empty());

        //Then
        studySessionService.findById(uuid)
                .as(StepVerifier::create)
                .verifyComplete();
        verify(studySessionRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("GET Find by Id: Service should return errors when database throws error")
    public void shouldReturnErrorFromDatabaseWhenAttemptingToFindById() {
        //Given
        UUID uuid = UUID.randomUUID();

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        studySessionService.findById(uuid)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionRepository, times(1)).findById(uuid);
    }

    @Test
    @DisplayName("POST Create Study Session: Service should add new entry into the database")
    public void shouldCreateNewStudySession() {
        //Given
        UUID uuid = UUID.randomUUID();
        LanguageStudySession languageStudySession = mock(LanguageStudySession.class);
        LanguageStudySessionEntity languageStudySessionEntity = LanguageStudySessionEntity.builder()
                .id(uuid)
                .build();

        //When
        when(studySessionRepository.save(any(LanguageStudySessionEntity.class)))
                .thenReturn(Mono.just(languageStudySessionEntity));

        //Then
        studySessionService.create(languageStudySession)
                .as(StepVerifier::create)
                .assertNext(studySession -> assertEquals(uuid, studySession.getId()))
                .verifyComplete();
        verify(studySessionRepository, times(1)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("POST Create Study Session: Service should return errors when database throws error")
    public void shouldReturnErrorFromDatabaseWhenAttemptingToCreate() {
        //Given
        LanguageStudySession languageStudySession = mock(LanguageStudySession.class);

        //When
        when(studySessionRepository.save(any(LanguageStudySessionEntity.class)))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        studySessionService.create(languageStudySession)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionRepository, times(1)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("PUT Update Study Session: Should update study session based on given Id")
    public void shouldUpdateStudySession() {
        //Given
        UUID uuid = UUID.randomUUID();
        LanguageStudySession languageStudySession = mock(LanguageStudySession.class);
        LanguageStudySessionEntity languageStudySessionEntity = LanguageStudySessionEntity.builder()
                .id(uuid)
                .build();

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.just(languageStudySessionEntity));
        when(studySessionRepository.save(any(LanguageStudySessionEntity.class)))
                .thenReturn(Mono.just(languageStudySessionEntity));
        when(languageStudySession.getId())
                .thenReturn(uuid);

        //Then
        studySessionService.update(languageStudySession)
                .as(StepVerifier::create)
                .assertNext(studySession -> assertEquals(uuid, studySession.getId()))
                .verifyComplete();
        verify(studySessionRepository, times(1)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("PUT Update Clothing: Should not update study session if ID if does not exist")
    public void shouldNotUpdateStudySessionIfNotFoundById() {
        //Given
        UUID uuid = UUID.randomUUID();
        LanguageStudySession languageStudySession = mock(LanguageStudySession.class);

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.empty());
        when(languageStudySession.getId())
                .thenReturn(uuid);

        //Then
        studySessionService.update(languageStudySession)
                .as(StepVerifier::create)
                .verifyComplete();
        verify(studySessionRepository, times(1)).findById(uuid);
        verify(studySessionRepository, times(0)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("PUT Update Clothing: Should not call save if find by Id throws error")
    public void shouldNotSaveUpdatedStudySessionIfFindByIdReturnsError() {
        //Given
        UUID uuid = UUID.randomUUID();
        LanguageStudySession languageStudySession = mock(LanguageStudySession.class);

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));
        when(languageStudySession.getId())
                .thenReturn(uuid);

        //Then
        studySessionService.update(languageStudySession)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionRepository, times(1)).findById(uuid);
        verify(studySessionRepository, times(0)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("PUT Update Clothing: Service should return errors when saving to database throws error")
    public void shouldNotUpdateClothingWhenSaveReturnsError() {
        //Given
        UUID uuid = UUID.randomUUID();
        LanguageStudySession languageStudySession = mock(LanguageStudySession.class);
        LanguageStudySessionEntity languageStudySessionEntity = LanguageStudySessionEntity.builder().build();

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.just(languageStudySessionEntity));
        when(languageStudySession.getId())
                .thenReturn(uuid);
        when(studySessionRepository.save(any(LanguageStudySessionEntity.class)))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        studySessionService.update(languageStudySession)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionRepository, times(1)).findById(uuid);
        verify(studySessionRepository, times(1)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("DELETE Delete Study Session: Should delete Study Session based on id")
    public void shouldDeleteStudySession() {
        //Given
        UUID uuid = UUID.randomUUID();
        LanguageStudySessionEntity languageStudySessionEntity = LanguageStudySessionEntity.builder()
                .id(uuid)
                .build();
        LanguageStudySessionEntity savedlanguageStudySessionEntity = LanguageStudySessionEntity.builder()
                .id(uuid)
                .status(StudySession.Status.DELETED)
                .build();
        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.just(languageStudySessionEntity));
        when(studySessionRepository.save(any(LanguageStudySessionEntity.class)))
                .thenReturn(Mono.just(savedlanguageStudySessionEntity));

        //Then
        studySessionService.delete(uuid)
                .as(StepVerifier::create)
                .verifyComplete();
        verify(studySessionRepository, times(1)).findById(uuid);
        verify(studySessionRepository, times(1)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("DELETE Delete Study Session: Should not call save if Id does not exist")
    public void shouldNotDeleteStudySessionIfNotFoundById() {
        //Given
        UUID uuid = UUID.randomUUID();

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.empty());

        //Then
        studySessionService.delete(uuid)
                .as(StepVerifier::create)
                .verifyComplete();
        verify(studySessionRepository, times(1)).findById(uuid);
        verify(studySessionRepository, times(0)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("DELETE Delete Study Session: Should not call save if find by Id returns error")
    public void shouldNotDeleteStudySessionIfFindByIdReturnsError() {
        //Given
        UUID uuid = UUID.randomUUID();

        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        studySessionService.delete(uuid)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionRepository, times(1)).findById(uuid);
        verify(studySessionRepository, times(0)).save(any(LanguageStudySessionEntity.class));
    }

    @Test
    @DisplayName("DELETE Delete Study Session: Service should return errors when saving to database throws error")
    public void shouldNotDeleteClothingWhenSaveReturnsError() {
        //Given
        UUID uuid = UUID.randomUUID();
        LanguageStudySessionEntity languageStudySessionEntity = LanguageStudySessionEntity.builder()
                .id(uuid)
                .build();
        //When
        when(studySessionRepository.findById(uuid))
                .thenReturn(Mono.just(languageStudySessionEntity));
        when(studySessionRepository.save(any(LanguageStudySessionEntity.class)))
                .thenReturn(Mono.error(new R2dbcBadGrammarException()));

        //Then
        studySessionService.delete(uuid)
                .as(StepVerifier::create)
                .verifyError(R2dbcBadGrammarException.class);
        verify(studySessionRepository, times(1)).findById(uuid);
        verify(studySessionRepository, times(1)).save(any(LanguageStudySessionEntity.class));
    }
}
