package pl.coderslab.driver.dto;

import lombok.Data;

import java.util.List;

@Data
public class AdviceTestDTO {

    private long id;

    private List<QuestionDTO> questions;
}
