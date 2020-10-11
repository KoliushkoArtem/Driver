package pl.coderslab.driver.converter;

import pl.coderslab.driver.dto.AdviceTestDto;
import pl.coderslab.driver.model.AdviceTest;

import java.util.stream.Collectors;

public class TestDtoConverter {

    public static AdviceTestDto convertToAdviceTestDTO(AdviceTest adviceTest) {
        AdviceTestDto adviceTestDto = new AdviceTestDto();
        adviceTestDto.setId(adviceTest.getId());
        adviceTestDto.setName(adviceTest.getName());
        adviceTestDto.setQuestions(adviceTest.getQuestions()
                .stream()
                .map(QuestionDtoConverter::convertToQuestionDTO)
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
