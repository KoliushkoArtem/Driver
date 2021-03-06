package pl.driver.converter;

import pl.driver.dto.AnswerVariantDto;
import pl.driver.model.AnswerVariant;

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
        if (answerVariantDto.getId() != null && answerVariantDto.getId() != 0) {
            convertedAnswerVariant.setId(answerVariantDto.getId());
        }
        convertedAnswerVariant.setAnswer(answerVariantDto.getAnswer());
        convertedAnswerVariant.setMediaFileId(answerVariantDto.getMediaFileId());
        convertedAnswerVariant.setVariantCorrect(answerVariantDto.isVariantCorrect());

        return convertedAnswerVariant;
    }
}