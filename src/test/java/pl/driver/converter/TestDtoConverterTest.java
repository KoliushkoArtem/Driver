package pl.driver.converter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.*;
import pl.driver.dto.AdviceTestDto;
import pl.driver.dto.QuestionDto;
import pl.driver.model.AdviceTest;
import pl.driver.model.Question;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class TestDtoConverterTest {

    private final Long id = 123L;
    private final String name = "Test Name For Test";
    private final Set<QuestionDto> questionsDto = new HashSet<>();
    private final Set<Question> questions = new HashSet<>();
    private final Long adviceId = 456L;

    @Before
    public void setUp() {
        Question question = new Question();
        QuestionDto questionDto = new QuestionDto();
        for (int i = 0; i < 5; i++) {
            question.setId((long) i);
            question.setQuestion("Test Question " + i);
            questions.add(question);

            questionDto.setId((long) i);
            questionDto.setQuestion("Test Question " + i);
            questionsDto.add(questionDto);
        }
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call convertToAdviceTestDto method assert that all similar fields would be an equals in incoming AdviceTest and outputted AdviceTestDto")
    void convertToAdviceTestDto() {
        AdviceTest adviceTest = new AdviceTest();
        adviceTest.setId(id);
        adviceTest.setName(name);
        adviceTest.setQuestions(questions);
        adviceTest.setAdviceId(adviceId);

        AdviceTestDto adviceTestDtoToTest = TestDtoConverter.convertToAdviceTestDto(adviceTest);

        assertEquals(id, adviceTestDtoToTest.getId());
        assertEquals(adviceId, adviceTestDtoToTest.getAdviceId());
        assertEquals(name, adviceTestDtoToTest.getName());
        assertEquals(questionsDto.size(), adviceTestDtoToTest.getQuestions().size());
        assertTrue(questionsDto.containsAll(adviceTestDtoToTest.getQuestions()));
    }

    @Test
    @DisplayName("When call convertToAdviceTest method assert that all similar fields would be an equals in incoming AdviceTestDto and outputted AdviceTest")
    void convertToAdviceTest() {
        AdviceTestDto adviceTestDto = new AdviceTestDto();
        adviceTestDto.setId(id);
        adviceTestDto.setName(name);
        adviceTestDto.setQuestions(questionsDto);
        adviceTestDto.setAdviceId(adviceId);

        AdviceTest adviceTestToTest = TestDtoConverter.convertToAdviceTest(adviceTestDto);
        assertEquals(id, adviceTestToTest.getId());
        assertEquals(adviceId, adviceTestToTest.getAdviceId());
        assertEquals(name, adviceTestToTest.getName());
        assertEquals(questionsDto.size(), adviceTestToTest.getQuestions().size());
        for (QuestionDto questionDto : questionsDto) {
            for (Question question : adviceTestToTest.getQuestions()) {
                if (question.getId().equals(questionDto.getId())) {
                    assertEquals(questionDto.getId(), question.getId());
                    assertEquals(questionDto.getQuestion(), question.getQuestion());
                }
            }
        }
    }
}