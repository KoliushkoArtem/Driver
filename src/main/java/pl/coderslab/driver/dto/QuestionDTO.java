package pl.coderslab.driver.dto;

import lombok.Data;

import java.util.List;

@Data
public class QuestionDTO {

    private String question;

    private List<AnswerVariantDTO> answers;

    private AnswerVariantDTO correctAnswer;
}
