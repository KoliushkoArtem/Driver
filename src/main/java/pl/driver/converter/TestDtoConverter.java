package pl.driver.converter;

import pl.driver.dto.AdviceTestDto;
import pl.driver.model.AdviceTest;

import java.util.stream.Collectors;

public class TestDtoConverter {

    public static AdviceTestDto convertToAdviceTestDto(AdviceTest adviceTest) {
        AdviceTestDto adviceTestDto = new AdviceTestDto();
        adviceTestDto.setId(adviceTest.getId());
        adviceTestDto.setName(adviceTest.getName());
        adviceTestDto.setQuestions(adviceTest.getQuestions()
                .stream()
                .map(QuestionDtoConverter::convertToQuestionDto)
                .collect(Collectors.toSet()));
        adviceTestDto.setAdviceId(adviceTest.getAdviceId());

        return adviceTestDto;
    }

    public static AdviceTest convertToAdviceTest(AdviceTestDto adviceTestDto) {
        AdviceTest adviceTest = new AdviceTest();
        if (adviceTestDto.getId() != null) {
            adviceTest.setId(adviceTestDto.getId());
        }
        adviceTest.setName(adviceTestDto.getName());
        adviceTest.setQuestions(adviceTestDto.getQuestions()
                .stream()
                .map(QuestionDtoConverter::convertToQuestion)
                .collect(Collectors.toSet()));
        if (adviceTestDto.getAdviceId() != null) {
            adviceTest.setAdviceId(adviceTestDto.getAdviceId());
        }

        return adviceTest;
    }
}