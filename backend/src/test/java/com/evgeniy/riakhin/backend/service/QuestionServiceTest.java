package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.QuestionCreateDTO;
import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.entity.Question;
import com.evgeniy.riakhin.backend.exception.QuestionNotFoundById;
import com.evgeniy.riakhin.backend.repository.QuestionRepository;
import com.evgeniy.riakhin.backend.util.NameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private Question existingQuestion;
    private QuestionResponseDTO existResponseDTO;
    private QuestionCreateDTO questionCreateDTO;

    @BeforeEach
    void setUp() {
        existingQuestion = new Question();
        existingQuestion.setId(1L);
        existingQuestion.setQuestion("What is Java?");

        existResponseDTO = new QuestionResponseDTO(
                existingQuestion.getId(),
                existingQuestion.getQuestion(),
                null,
                List.of()
        );
        questionCreateDTO = new QuestionCreateDTO(
                existingQuestion.getQuestion(),
                null,
                List.of()
        );
    }

    @Test
    void findAllShouldReturnListOfQuestionDTOsWhenQuestionsExist() {
        Question question1 = new Question();
        question1.setId(1L);
        question1.setQuestion("What is Java?");

        Question question2 = new Question();
        question2.setId(2L);
        question2.setQuestion("What is Python?");

        List<Question> questionsFromDB = List.of(question1, question2);

        QuestionResponseDTO questionResponseDTO1 = new QuestionResponseDTO(
                1L,
                "What is Java?",
                null,
                List.of());

        QuestionResponseDTO questionResponseDTO2 = new QuestionResponseDTO(
                2L,
                "What is Python?",
                null,
                List.of());

        List<QuestionResponseDTO> expectedDTOs = List.of(questionResponseDTO1, questionResponseDTO2);

        Mockito.when(questionRepository.getAllQuestions()).thenReturn(questionsFromDB);

        List<QuestionResponseDTO> actualDTOs = questionService.findAll();

        Mockito.verify(questionRepository, Mockito.times(1)).getAllQuestions();

        assertNotNull(actualDTOs, "The list of questions isn't null");
        assertEquals(actualDTOs.size(), expectedDTOs.size(), "The list of questions isn't the same size");
        assertEquals(expectedDTOs, actualDTOs);
        assertEquals(questionResponseDTO1.id(), actualDTOs.getFirst().id());
    }

    @Test
    void findAllShouldReturnEmptyListWhenNoQuestionsExist() {
        Mockito.when(questionRepository.getAllQuestions()).thenReturn(List.of());
        List<QuestionResponseDTO> actualDTOs = questionService.findAll();

        Mockito.verify(questionRepository, Mockito.times(1)).getAllQuestions();

        assertNotNull(actualDTOs, "The list of questions isn't null");
        assertEquals(actualDTOs.size(), 0, "The list of questions isn't empty");
        assertTrue(actualDTOs.isEmpty(), "The list of questions isn't empty");
    }

    @Test
    void findByIdShouldReturnQuestionResponseDTOWhenQuestionExists() {
        Long existingQuestionId = 1L;
        Mockito.when(questionRepository.findQuestionById(existingQuestionId)).thenReturn(Optional.of(existingQuestion));

        QuestionResponseDTO actualResponseDTO = questionService.findById(existingQuestionId);

        Mockito.verify(questionRepository, Mockito.times(1)).findQuestionById(existingQuestionId);
        assertNotNull(actualResponseDTO, "The question isn't null");
        assertEquals(existResponseDTO.id(), actualResponseDTO.id());
        assertEquals(existResponseDTO.questionText(), actualResponseDTO.questionText());
        assertEquals(existResponseDTO, actualResponseDTO);
    }

    @Test
    void findById_ShouldThrowException_WhenQuestionDoesNotExist() {
        Long notExistingQuestionId = -1L;
        Mockito.when(questionRepository.findQuestionById(notExistingQuestionId)).thenReturn(Optional.empty());

        QuestionNotFoundById throwException = assertThrows(
                QuestionNotFoundById.class, () -> questionService.findById(notExistingQuestionId));

        Mockito.verify(questionRepository, Mockito.times(1)).findQuestionById(notExistingQuestionId);
        assertNotNull(throwException, "The question isn't null");

        String expectedMessage = NameException.QUESTION_NOT_FOUND_BY_ID + notExistingQuestionId;
        assertEquals(expectedMessage, throwException.getMessage());
    }

    @Test
    void saveQuestionShouldSaveQuestionAndReturnResponseDTO() {
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenReturn(existingQuestion);

        QuestionResponseDTO actualResponseDTO = questionService.saveQuestion(questionCreateDTO);
        ArgumentCaptor<Question> argumentCaptor = ArgumentCaptor.forClass(Question.class);

        Mockito.verify(questionRepository, Mockito.times(1)).save(argumentCaptor.capture());

        assertNotNull(actualResponseDTO, "The question isn't null");
        assertEquals(existResponseDTO.questionText(), actualResponseDTO.questionText());
        assertEquals(existResponseDTO.id(), actualResponseDTO.id());
        assertEquals(existResponseDTO, actualResponseDTO);

        Question captureQuestion = argumentCaptor.getValue();
        assertNotNull(captureQuestion, "The question isn't null");
        assertEquals(existResponseDTO.questionText(), captureQuestion.getQuestion());
        assertNull(captureQuestion.getId());
    }

    @Test
    void questionUpdate() {
    }

    @Test
    void deleteQuestion() {
    }
}