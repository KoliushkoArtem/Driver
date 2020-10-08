package pl.coderslab.driver.dto;

import lombok.Data;

@Data
public class AnswerVariantDTO {

    private String answer;

    private String mediaFileDownloadUrl;

    private boolean isVariantCorrect;
}
