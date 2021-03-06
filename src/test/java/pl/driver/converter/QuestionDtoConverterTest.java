package pl.driver.converter;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.jupiter.api.*;
import pl.driver.dto.AnswerVariantDto;
import pl.driver.dto.QuestionDto;
import pl.driver.model.AnswerVariant;
import pl.driver.model.Question;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
class QuestionDtoConverterTest {

    private final Long id = 123L;
    private final String question = "Test Question";
    private final Set<AnswerVariantDto> answersDto = new HashSet<>();
    private final Set<AnswerVariant> answers = new HashSet<>();

    @Before
    public void setUp() {
        AnswerVariantDto answerVariantDto = new AnswerVariantDto();
        AnswerVariant answerVariant = new AnswerVariant();
        for (int i = 0; i < 5; i++) {
            answerVariantDto.setId((long) i);
            answerVariantDto.setAnswer("Test Answer" + i);
            answerVariantDto.setMediaFileId((long) i);
            answerVariantDto.setVariantCorrect(Boolean.TRUE);
            answersDto.add(answerVariantDto);

            answerVariant.setId((long) i);
            answerVariant.setAnswer("Test Answer" + i);
            answerVariant.setMediaFileId((long) i);
            answerVariant.setVariantCorrect(Boolean.TRUE);
            answers.add(answerVariant);
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
    @DisplayName("When call convertToQuestionDto method assert that all similar fields would be an equals in incoming Question and outputted QuestionDto")
    void convertToQuestionDto() {
        Question questionT = new Question();
        questionT.setId(id);
        questionT.setQuestion(question);
        questionT.setAnswers(answers);

        QuestionDto questionDtoToTest = QuestionDtoConverter.convertToQuestionDto(questionT);

        assertEquals(id, questionDtoToTest.getId());
        assertEquals(question, questionDtoToTest.getQuestion());
        assertTrue(answersDto.containsAll(questionDtoToTest.getAnswers()));
        assertEquals(answersDto.size(), questionDtoToTest.getAnswers().size());
    }

    @Test
    @DisplayName("When call convertToQuestion method assert that all similar fields would be an equals in incoming QuestionDto and outputted Question")
    void convertToQuestion() {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(id);
        questionDto.setQuestion(question);
        questionDto.setAnswers(answersDto);

        Question questionToTest = QuestionDtoConverter.convertToQuestion(questionDto);

        assertEquals(id, questionToTest.getId());
        assertEquals(question, questionToTest.getQuestion());
        assertEquals(answers.size(), questionToTest.getAnswers().size());
        for (AnswerVariant answer : answers) {
            for (AnswerVariant questionToTestAnswer : questionToTest.getAnswers()) {
                if (answer.getId().equals(questionToTestAnswer.getId())) {
                    assertEquals(answer.getId(), questionToTestAnswer.getId());
                    assertEquals(answer.getMediaFileId(), questionToTestAnswer.getMediaFileId());
                    assertEquals(answer.getAnswer(), questionToTestAnswer.getAnswer());
                    assertEquals(answer.isVariantCorrect(), questionToTestAnswer.isVariantCorrect());
                }
            }
        }
    }
}