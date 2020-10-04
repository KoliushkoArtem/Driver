package pl.coderslab.driver.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AnswerVariantDTO {

    private String answer;

    private MultipartFile image;
}
