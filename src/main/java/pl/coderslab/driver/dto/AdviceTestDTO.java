package pl.coderslab.driver.dto;

import lombok.Data;

import java.util.Set;

@Data
public class AdviceTestDTO {

    private long id;

    private Set<QuestionDTO> questions;
}
