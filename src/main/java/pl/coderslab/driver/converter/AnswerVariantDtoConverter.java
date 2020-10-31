package pl.coderslab.driver.converter;

import pl.coderslab.driver.dto.AnswerVariantDto;
import pl.coderslab.driver.model.AnswerVariant;

public class AnswerVariantDtoConverter {

    public static AnswerVariantDto convertToAnswerVariantDto(AnswerVariant answerVariant) {
        AnswerVariantDto convertedAnswerVariantDTO = new AnswerVariantDto();
        convertedAnswerVariantDTO.setId(answerVariant.getId());
        convertedAnswerVariantDTO.setAnswer(answerVariant.getAnswer());
        convertedAnswerVariantDTO.setMediaFileId(answerVariant.getMediaFileId());
        convertedAnswerVariantDTO.setVariantCorrect(answerVariant.isVariantCorrect());

        return convertedAnswerVariantDTO;
    }

    public static AnswerVariant convertToAnswerVariant(AnswerVariantDto answerVariantDto) {
        AnswerVariant convertedAnswerVariant = new AnswerVariant();
        if (answerVariantDto.getId() != null) {
            convertedAnswerVariant.setId(answerVariantDto.getId());
        }
        convertedAnswerVariant.setAnswer(answerVariantDto.getAnswer());
        convertedAnswerVariant.setMediaFileId(answerVariantDto.getMediaFileId());
        convertedAnswerVariant.setVariantCorrect(answerVariantDto.isVariantCorrect());

        return convertedAnswerVariant;
    }
}
