package pl.driver.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.driver.configuration.YamlUrlConfiguration;
import pl.driver.dto.AdviceTestDto;
import pl.driver.dto.AnswerVariantDto;
import pl.driver.dto.QuestionDto;
import pl.driver.exceptions.AdviseTestNotFoundException;
import pl.driver.service.AdviceTestService;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
class AdviceTestControllerTest {

    private AdviceTestService testServiceMock;
    private AdviceTestController testController;
    private AdviceTestDto testDto;

    @BeforeEach
    void setUp(TestInfo testInfo) {
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

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call getAllTests method asserted ResponseEntity with List of AdviseTestDto and HTTP status OK")
    void getAllTests() {
        AdviceTestDto[] adviceTestDto = new AdviceTestDto[]{testDto};
        when(testServiceMock.getAll()).thenReturn(Arrays.asList(adviceTestDto));

        ResponseEntity<List<AdviceTestDto>> allTestsToTest = testController.getAllTests();

        assertEquals(HttpStatus.OK, allTestsToTest.getStatusCode());
        assertEquals(testDto, Objects.requireNonNull(allTestsToTest.getBody()).get(0));
        assertEquals(1, allTestsToTest.getBody().size());
    }

    @Test
    @DisplayName("When call getById method with exist id assert ResponseEntity with AdviceTestDto and HTTP status OK")
    void getByIdSuccess() {
        when(testServiceMock.findByID(testDto.getId())).thenReturn(testDto);

        ResponseEntity<AdviceTestDto> testByIdToTest = testController.getById(testDto.getId());

        assertEquals(HttpStatus.OK, testByIdToTest.getStatusCode());
        assertEquals(testDto, testByIdToTest.getBody());
    }

    @Test
    @DisplayName("When call getById method with not exist id assert ResponseEntity with HTTP status NOT_FOUND")
    void getByIdFailByTestNotFoundException() {
        when(testServiceMock.findByID(testDto.getId())).thenThrow(new AdviseTestNotFoundException(testDto.getId()));

        ResponseEntity<AdviceTestDto> testByIdToTest = testController.getById(testDto.getId());

        assertEquals(HttpStatus.NOT_FOUND, testByIdToTest.getStatusCode());
    }

    @Test
    @DisplayName("When Call add method assert ResponseEntity with AdviseTestDto and HTTP status OK")
    void add() {
        when(testServiceMock.save(testDto)).thenReturn(testDto);

        ResponseEntity<AdviceTestDto> addedTestToTest = testController.add(testDto);

        assertEquals(HttpStatus.OK, addedTestToTest.getStatusCode());
        assertEquals(testDto, addedTestToTest.getBody());
    }

    @Test
    @DisplayName("When call updateAdviceTest method with exist test assert ResponseEntity with updated AdviceTestDto and HTTP status OK")
    void updateAdviceTestSuccess() {
        when(testServiceMock.update(testDto)).thenReturn(testDto);

        ResponseEntity<AdviceTestDto> updatedTestToTest = testController.updateAdviceTest(testDto);

        assertEquals(HttpStatus.OK, updatedTestToTest.getStatusCode());
        assertEquals(testDto, updatedTestToTest.getBody());
    }

    @Test
    @DisplayName("When call updateAdviceTest method with not exist test assert ResponseEntity with HTTP status NOT_FOUND")
    void updateAdviceTestFailByTeatNotFoundException() {
        when(testServiceMock.update(testDto)).thenThrow(new AdviseTestNotFoundException(testDto.getId()));

        ResponseEntity<AdviceTestDto> updatedTestToTest = testController.updateAdviceTest(testDto);

        assertEquals(HttpStatus.NOT_FOUND, updatedTestToTest.getStatusCode());
    }

    @Test
    @DisplayName("When call delete method with exist id assert delete method in AdviseTestService will be called 1 time and HTTP status OK")
    void delete() {
        HttpStatus httpStatus = testController.delete(testDto.getId());

        verify(testServiceMock, times(1)).delete(testDto.getId());
        assertEquals(HttpStatus.OK, httpStatus);
    }

    @Test
    @DisplayName("When call delete method with not exist id assert delete method wil be throw AdviseTestNotFoundException and HTTP status NOT_FOUND")
    void deleteFailByAdviseTestNotFoundException() {
        doThrow(new AdviseTestNotFoundException(testDto.getId())).when(testServiceMock).delete(testDto.getId());

        HttpStatus httpStatus = testController.delete(testDto.getId());

        verify(testServiceMock, times(1)).delete(testDto.getId());
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
    }
}