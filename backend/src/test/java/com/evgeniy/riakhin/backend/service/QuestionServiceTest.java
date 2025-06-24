package com.evgeniy.riakhin.backend.service;

import com.evgeniy.riakhin.backend.dto.CorrectAnswerCreateDTO;
import com.evgeniy.riakhin.backend.dto.QuestionCreateDTO;
import com.evgeniy.riakhin.backend.dto.QuestionResponseDTO;
import com.evgeniy.riakhin.backend.dto.WrongAnswerCreateDTO;
import com.evgeniy.riakhin.backend.entity.CorrectAnswer;
import com.evgeniy.riakhin.backend.entity.Question;
import com.evgeniy.riakhin.backend.entity.VariantOfAnswer;
import com.evgeniy.riakhin.backend.entity.WrongAnswer;
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

import java.util.Arrays;
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
    private QuestionResponseDTO expectedResponseDTO;
    private QuestionCreateDTO questionCreateDTO;
    private QuestionCreateDTO updateQuestionCreateDTO;

    @BeforeEach
    void setUp() {
        existingQuestion = new Question();
        existingQuestion.setId(1L);
        existingQuestion.setQuestion("What is Java?");

        CorrectAnswer initialCorrectAnswer = new CorrectAnswer("Java is programming language");
        initialCorrectAnswer.setVariantOfAnswer(VariantOfAnswer.CORRECT);
        existingQuestion.setCorrectAnswer(initialCorrectAnswer);
        initialCorrectAnswer.setQuestion(existingQuestion);

        WrongAnswer initialWrongAnswer1 = new WrongAnswer("Original wrong answer 1");
        initialWrongAnswer1.setVariantOfAnswer(VariantOfAnswer.INCORRECT);

        WrongAnswer initialWrongAnswer2 = new WrongAnswer("Original wrong answer 2");
        initialWrongAnswer2.setVariantOfAnswer(VariantOfAnswer.INCORRECT);

        existingQuestion.addCorrectAnswer(initialCorrectAnswer);
        existingQuestion.addWrongAnswer(initialWrongAnswer1);
        existingQuestion.addWrongAnswer(initialWrongAnswer2);

        CorrectAnswerCreateDTO newCorrectAnswerCreateDTO = new CorrectAnswerCreateDTO(
                "New correct answer", VariantOfAnswer.CORRECT);
        WrongAnswerCreateDTO newWrongAnswerCreateDTO1 = new WrongAnswerCreateDTO(
                "New wrong answer 1", VariantOfAnswer.INCORRECT);

        WrongAnswerCreateDTO newWrongAnswerCreateDTO2 = new WrongAnswerCreateDTO(
                "New wrong answer 2", VariantOfAnswer.INCORRECT);

        updateQuestionCreateDTO = new QuestionCreateDTO(
                "Updated question text?",
                newCorrectAnswerCreateDTO,
                Arrays.asList(newWrongAnswerCreateDTO1, newWrongAnswerCreateDTO2)
        );

        expectedResponseDTO = new QuestionResponseDTO(
                existingQuestion.getId(),
                updateQuestionCreateDTO.textQuestion(),
                null,
                null
        );

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
//        assertEquals(existResponseDTO, actualResponseDTO);
    }

    @Test
    void findByIdShouldThrowExceptionWhenQuestionDoesNotExist() {
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
//        assertEquals(existResponseDTO, actualResponseDTO);

        Question captureQuestion = argumentCaptor.getValue();
        assertNotNull(captureQuestion, "The question isn't null");
        assertEquals(existResponseDTO.questionText(), captureQuestion.getQuestion());
        assertNull(captureQuestion.getId());
    }

    @Test
    void questionUpdateShouldThrowExceptionWhenQuestionDoesNotExist() {
        Long notExistingQuestionId = -1L;
        Mockito.when(questionRepository.findQuestionById(notExistingQuestionId)).thenReturn(Optional.empty());
        QuestionNotFoundById throwException = assertThrows(
                QuestionNotFoundById.class,
                () -> questionService.questionUpdate(notExistingQuestionId, questionCreateDTO));

        assertEquals(NameException.QUESTION_NOT_FOUND_BY_ID + notExistingQuestionId, throwException.getMessage());
        assertNotNull(throwException, "The question isn't null");
        Mockito.verify(questionRepository, Mockito.times(1)).findQuestionById(notExistingQuestionId);
        Mockito.verify(questionRepository, Mockito.never()).save(Mockito.any(Question.class));
    }

    @Test
    void questionUpdateShouldUpdateAllFieldsSuccessfully() {
        Long existingQuestionId = existingQuestion.getId();
        Mockito.when(questionRepository.findQuestionById(existingQuestionId)).thenReturn(Optional.of(existingQuestion));
        Mockito.when(questionRepository.save(Mockito.any(Question.class))).thenAnswer(invocation -> invocation.<Question>getArgument(0));

        QuestionResponseDTO actualResponseDTO = questionService.questionUpdate(existingQuestionId, updateQuestionCreateDTO);

        Mockito.verify(questionRepository, Mockito.times(1)).findQuestionById(existingQuestionId);

        ArgumentCaptor<Question> argumentCaptor = ArgumentCaptor.forClass(Question.class);
        Mockito.verify(questionRepository, Mockito.times(1)).save(argumentCaptor.capture());

        assertNotNull(actualResponseDTO, "The question isn't null");
        assertEquals(expectedResponseDTO.id(), actualResponseDTO.id());
        assertEquals(expectedResponseDTO.questionText(), actualResponseDTO.questionText());

        Question captureQuestion = argumentCaptor.getValue();
        assertNotNull(captureQuestion.getCorrectAnswer(), "The question isn't null");
        assertEquals(
                updateQuestionCreateDTO.textQuestion(),
                captureQuestion.getQuestion(),
                "Correct answer text should be updated");
        assertEquals(
                updateQuestionCreateDTO.correctAnswerCreateDTO().correctAnswer(),
                captureQuestion.getCorrectAnswer().getCorrectAnswer(),
                "Correct answer should be updated");
        assertEquals(
                updateQuestionCreateDTO.correctAnswerCreateDTO().variantOfAnswer(),
                captureQuestion.getCorrectAnswer().getVariantOfAnswer(),
                "Variant of answer should be updated");
        assertEquals(captureQuestion, captureQuestion.getCorrectAnswer().getQuestion(), "Correct answer should be updated");

        assertNotNull(captureQuestion.getWrongAnswers(), "Wrong answers should not be null");
        assertEquals(updateQuestionCreateDTO.wrongAnswers().size(), captureQuestion.getWrongAnswers().size(), "Number of wrong answers should match");

        assertTrue(
                captureQuestion.getWrongAnswers()
                        .stream().
                        anyMatch(wa -> wa.getWrongAnswer()
                                .equals(updateQuestionCreateDTO.wrongAnswers().getFirst().answer())
                                &&
                                wa.getVariantOfAnswer()
                                        .equals(updateQuestionCreateDTO.wrongAnswers().getFirst().variantOfAnswer())),
                "First new wrong answer should be present");

        assertTrue(
                captureQuestion.getWrongAnswers()
                        .stream()
                        .anyMatch(wa -> wa.getWrongAnswer()
                                .equals(updateQuestionCreateDTO.wrongAnswers().get(1).answer())
                                &&
                                wa.getVariantOfAnswer().equals(updateQuestionCreateDTO.wrongAnswers().get(1).variantOfAnswer())),
                "Second new wrong answer should be present");

        captureQuestion.getWrongAnswers()
                .forEach(wa -> assertEquals(captureQuestion, wa.getQuestion(),
                        "Wrong answer should be linked back to question"));
    }

    @Test
    void deleteQuestion() {
    }
}