package pl.coderslab.driver.dto;

import lombok.Data;

@Data
public class AdviceDTO {

    private long id;

    private String adviceName;

    private String adviceDescription;

    private String mediaFileDownloadUrl;

    private int likeCount;

    private int dislikeCount;

    private long testId;
}
