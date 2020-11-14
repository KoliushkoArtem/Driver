package pl.coderslab.driver.converter;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import pl.coderslab.driver.dto.AdviceTestDto;
import pl.coderslab.driver.dto.QuestionDto;
import pl.coderslab.driver.model.AdviceTest;
import pl.coderslab.driver.model.Question;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
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