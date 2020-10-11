package pl.coderslab.driver.dto;

import lombok.Data;

@Data
public class AdviceDto {


    private Long id;

    private String adviceName;

    private String adviceDescription;

    private Long mediaFileId;

    private int likeCount;

    private int dislikeCount;

    private Long testId;
}
