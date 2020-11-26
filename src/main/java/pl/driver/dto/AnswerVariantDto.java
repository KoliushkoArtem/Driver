package pl.driver.dto;

import lombok.Data;

@Data
public class AnswerVariantDto {

    private Long id;

    private String answer;

    private Long mediaFileId;

    private String mediaFileDownloadLink;

    private boolean isVariantCorrect;
}
