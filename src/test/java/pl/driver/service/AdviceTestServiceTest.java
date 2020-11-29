package pl.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.driver.converter.TestDtoConverter;
import pl.driver.dto.AdviceTestDto;
import pl.driver.exceptions.AdviseTestNotFoundException;
import pl.driver.model.AdviceTest;
import pl.driver.model.AnswerVariant;
import pl.driver.model.Question;
import pl.driver.repository.AdviceTestRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class AdviceTestServiceTest {

    private AdviceTestRepository testRepositoryMock;
    private AdviceTestService testService;
    private AdviceTest test;
    private AdviceTestDto testDto;


    @BeforeEach
    void setUp(TestInfo testInfo) {
        testRepositoryMock = mock(AdviceTestRepository.class);
        testService = new AdviceTestService(testRepositoryMock);

        test = new AdviceTest();
        test.setId(123L);
        test.setName("Test Name");
        test.setAdviceId(321L);
        Set<Question> testQuestions = new HashSet<>();
        long idCounter = 0;
        for (int i = 1; i <= 3; i++) {
            Question question = new Question();
            question.setId(++idCounter);
            question.setQuestion("Test Question " + i);
            Set<AnswerVariant> testAnswers = new HashSet<>();
            boolean correctAnswer = true;
            for (int j = 1; j <= 3; j++) {
                AnswerVariant variant = new AnswerVariant();
                variant.setId(++idCounter);
                variant.setAnswer("Test Answer");
                variant.setVariantCorrect(correctAnswer);
                correctAnswer = false;
                variant.setMediaFileId(++idCounter);

                testAnswers.add(variant);
            }
            question.setAnswers(testAnswers);
            testQuestions.add(question);
        }
        test.setQuestions(testQuestions);

        testDto = TestDtoConverter.convertToAdviceTestDto(test);

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call getAll method assert that received from DB List of AdviceTests will be converted to AdviseTestDto and returned List of AdviceTestDto")
    void getAll() {
        AdviceTest[] tests = new AdviceTest[]{test};
        List<AdviceTest> testList = Arrays.asList(tests);
        when(testRepositoryMock.findAll()).thenReturn(testList);

        List<AdviceTestDto> allTestsToTest = testService.getAll();

        assertTrue(allTestsToTest.contains(testDto));
        assertEquals(1, allTestsToTest.size());
    }

    @Test
    @DisplayName("When call findById method with exist id assert that AdviceTest from DB will be converted to AdviceTestDto and returned")
    void findByID() {
        when(testRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(test));

        AdviceTestDto testDtoToTest = testService.findByID(test.getId());

        assertEquals(testDto, testDtoToTest);
    }

    @Test
    @DisplayName("when call findById method with no exist id assert that AdviceTestNotFoundException will be thrown")
    void findByIDFail() {
        when(testRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdviseTestNotFoundException.class, () -> testService.findByID(test.getId()));
    }

    //TODO
    @Test
    void delete() {
        testService.delete(1L);
        verify(testRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("When call save method with AdviceTestDto asserted that AdviceTestDto will be converted to AdviceTest and saved, then saved AdviseTest converted to AdviseTestDto and returned")
    void save() {
        when(testRepositoryMock.save(any())).thenReturn(test);

        AdviceTestDto testDtoToTest = testService.save(testDto);

        assertEquals(testDto, testDtoToTest);
    }

    @Test
    @DisplayName("When call update method with exist AdviceTestDto asserted that AdviceTestDto will be converted to AdviceTest and updated, then saved AdviseTest converted to AdviseTestDto and returned")
    void update() {

        AnswerVariant newAnswer = new AnswerVariant();
        newAnswer.setId(654L);
        newAnswer.setAnswer("New Answer");
        newAnswer.setVariantCorrect(true);
        newAnswer.setMediaFileId(111111L);

        Question newQuestion = new Question();
        newQuestion.setId(456L);
        newQuestion.setQuestion("New Question");
        Set<AnswerVariant> newAnswers = new HashSet<>();
        newAnswers.add(newAnswer);
        newQuestion.setAnswers(newAnswers);

        AdviceTest updatedTest = test;
        updatedTest.getQuestions().add(newQuestion);

        AdviceTestDto updatedTestDto = TestDtoConverter.convertToAdviceTestDto(updatedTest);

        when(testRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(test));
        when(testRepositoryMock.save(any())).thenReturn(updatedTest);

        AdviceTestDto testDtoToTest = testService.update(testDto);

        assertEquals(updatedTestDto, testDtoToTest);
    }

    @Test
    @DisplayName("When call update method with not exist AdviceTestDto asserted that AdviceTestNotFoundException will be thrown")
    void updateFail() {
        when(testRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdviseTestNotFoundException.class, () -> testService.update(testDto));
    }
}