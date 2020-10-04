package pl.coderslab.driver.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AdviceDTO {

    private long id;

    private String adviceName;

    private String adviceDescription;

    private MultipartFile file;

    private int likeCount;

    private int dislikeCount;

    private long testId;
}
