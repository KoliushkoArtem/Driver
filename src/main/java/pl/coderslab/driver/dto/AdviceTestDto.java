package pl.coderslab.driver.dto;

import lombok.Data;

import java.util.Set;


@Data
public class AdviceTestDto {

    private Long id;

    private String name;

    private Set<QuestionDto> questions;

    private Long adviceId;
}
