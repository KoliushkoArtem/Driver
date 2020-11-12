package pl.coderslab.driver.converter;

import org.junit.jupiter.api.Test;
import pl.coderslab.driver.dto.AnswerVariantDto;
import pl.coderslab.driver.dto.QuestionDto;
import pl.coderslab.driver.model.AnswerVariant;
import pl.coderslab.driver.model.Question;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class QuestionDtoConverterTest {

    private final Long id = 123L;
    private final String question = "Test Question";
    private final Set<AnswerVariantDto> answersDto;
    private final Set<AnswerVariant> answers;

    QuestionDtoConverterTest() {
        Set<AnswerVariantDto> answerVariantDtoSeed = new HashSet<>();
        Set<AnswerVariant> answerVariantSeed = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            AnswerVariantDto answerVariantDto = new AnswerVariantDto();
            answerVariantDto.setId((long) i);
            answerVariantDto.setAnswer("Test Answer" + i);
            answerVariantDto.setMediaFileId((long) i);
            answerVariantDto.setVariantCorrect(Boolean.TRUE);

            answerVariantDtoSeed.add(answerVariantDto);

            AnswerVariant answerVariant = new AnswerVariant();
            answerVariant.setId((long) i);
            answerVariant.setAnswer("Test Answer" + i);
            answerVariant.setMediaFileId((long) i);
            answerVariant.setVariantCorrect(Boolean.TRUE);

            answerVariantSeed.add(answerVariant);
        }

        this.answersDto = answerVariantDtoSeed;
        this.answers = answerVariantSeed;
    }

    @Test
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