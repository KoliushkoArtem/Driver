package pl.coderslab.driver.dto;

import lombok.Data;

import java.util.Set;

@Data
public class QuestionDTO {

    private String question;

    private Set<AnswerVariantDTO> answers;
}
