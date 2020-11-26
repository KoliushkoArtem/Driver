package pl.driver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.driver.configuration.YamlUrlConfiguration;
import pl.driver.controller.AdviceTestController;
import pl.driver.dto.AdviceTestDto;
import pl.driver.dto.AnswerVariantDto;
import pl.driver.dto.QuestionDto;
import pl.driver.exceptions.AdviseTestNotFoundException;
import pl.driver.service.AdviceTestService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdviceTestControllerTest {

    private AdviceTestService testServiceMock;
    private AdviceTestController testController;
    private AdviceTestDto testDto;

    @BeforeEach
    void setUp() {
        testServiceMock = mock(AdviceTestService.class);
        testController = new AdviceTestController(new YamlUrlConfiguration(), testServiceMock);

        testDto = new AdviceTestDto();
        testDto.setId(123L);
        testDto.setName("Test Name");
        testDto.setAdviceId(321L);
        Set<QuestionDto> testQuestions = new HashSet<>();
        long idCounter = 0;
        for (int i = 1; i <= 3; i++) {
            QuestionDto questionDto = new QuestionDto();
            questionDto.setId(++idCounter);
            questionDto.setQuestion("Test Question " + i);
            Set<AnswerVariantDto> testAnswersDto = new HashSet<>();
            boolean correctAnswer = true;
            for (int j = 1; j <= 3; j++) {
                AnswerVariantDto variantDto = new AnswerVariantDto();
                variantDto.setId(++idCounter);
                variantDto.setAnswer("Test Answer");
                variantDto.setVariantCorrect(correctAnswer);
                correctAnswer = false;
                variantDto.setMediaFileId(++idCounter);

                testAnswersDto.add(variantDto);
            }
            questionDto.setAnswers(testAnswersDto);
            testQuestions.add(questionDto);
        }
        testDto.setQuestions(testQuestions);
    }

    @Test
    void getAllTests() {
        AdviceTestDto[] adviceTestDto = new AdviceTestDto[]{testDto};
        when(testServiceMock.getAll()).thenReturn(Arrays.asList(adviceTestDto));

        ResponseEntity<List<AdviceTestDto>> allTestsToTest = testController.getAllTests();

        assertEquals(HttpStatus.OK, allTestsToTest.getStatusCode());
        assertEquals(testDto, Objects.requireNonNull(allTestsToTest.getBody()).get(0));
        assertEquals(1, allTestsToTest.getBody().size());
    }

    @Test
    void getByIdSuccess() {
        when(testServiceMock.findByID(testDto.getId())).thenReturn(testDto);

        ResponseEntity<AdviceTestDto> testByIdToTest = testController.getById(testDto.getId());

        assertEquals(HttpStatus.OK, testByIdToTest.getStatusCode());
        assertEquals(testDto, testByIdToTest.getBody());
    }

    @Test
    void getByIdFailByTestNotFoundException() {
        when(testServiceMock.findByID(testDto.getId())).thenThrow(new AdviseTestNotFoundException(testDto.getId()));

        ResponseEntity<AdviceTestDto> testByIdToTest = testController.getById(testDto.getId());

        assertEquals(HttpStatus.NOT_FOUND, testByIdToTest.getStatusCode());
    }

    @Test
    void add() {
        when(testServiceMock.save(testDto)).thenReturn(testDto);

        ResponseEntity<AdviceTestDto> addedTestToTest = testController.add(testDto);

        assertEquals(HttpStatus.OK, addedTestToTest.getStatusCode());
        assertEquals(testDto, addedTestToTest.getBody());
    }

    @Test
    void updateAdviceTestSuccess() {
        when(testServiceMock.update(testDto)).thenReturn(testDto);

        ResponseEntity<AdviceTestDto> updatedTestToTest = testController.updateAdviceTest(testDto);

        assertEquals(HttpStatus.OK, updatedTestToTest.getStatusCode());
        assertEquals(testDto, updatedTestToTest.getBody());
    }

    @Test
    void updateAdviceTestFailByTeatNotFoundException() {
        when(testServiceMock.update(testDto)).thenThrow(new AdviseTestNotFoundException(testDto.getId()));

        ResponseEntity<AdviceTestDto> updatedTestToTest = testController.updateAdviceTest(testDto);

        assertEquals(HttpStatus.NOT_FOUND, updatedTestToTest.getStatusCode());
    }

    @Test
    void delete() {
        testController.delete(1L);
        verify(testServiceMock, times(1)).delete(1L);
    }
}