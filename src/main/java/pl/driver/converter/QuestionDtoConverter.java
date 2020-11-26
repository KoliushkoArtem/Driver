package pl.driver.converter;

import pl.driver.dto.QuestionDto;
import pl.driver.model.Question;

import java.util.stream.Collectors;

public class QuestionDtoConverter {

    public static QuestionDto convertToQuestionDto(Question question) {
        QuestionDto questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setQuestion(question.getQuestion());
        questionDto.setAnswers(question.getAnswers()
                .stream()
                .map(AnswerVariantDtoConverter::convertToAnswerVariantDto)
                .collect(Collectors.toSet()));

        return questionDto;
    }

    public static Question convertToQuestion(QuestionDto questionDto) {
        Question question = new Question();
        if (questionDto.getId() != null && questionDto.getId() != 0) {
            question.setId(questionDto.getId());
        }
        question.setQuestion(questionDto.getQuestion());
        question.setAnswers(questionDto.getAnswers()
                .stream()
                .map(AnswerVariantDtoConverter::convertToAnswerVariant)
                .collect(Collectors.toSet()));

        return question;
    }
}