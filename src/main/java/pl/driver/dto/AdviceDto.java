package pl.driver.dto;

import lombok.Data;

@Data
public class AdviceDto {

    private Long id;

    private String adviceName;

    private String adviceDescription;

    private Long mediaFileId;

    private String mediaFileDownloadLink;

    private int likeCount;

    private int dislikeCount;

    private Long testId;

    private String testLink;
}