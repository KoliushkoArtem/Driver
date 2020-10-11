package pl.coderslab.driver.dto;

import lombok.Data;

import java.util.Set;

@Data
public class QuestionDto {

    private Long id;

    private String question;

    private Set<AnswerVariantDto> answers;
}
