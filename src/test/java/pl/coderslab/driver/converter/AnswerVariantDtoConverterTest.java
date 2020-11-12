package pl.coderslab.driver.converter;

import org.junit.jupiter.api.Test;
import pl.coderslab.driver.dto.AnswerVariantDto;
import pl.coderslab.driver.model.AnswerVariant;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AnswerVariantDtoConverterTest {

    private final Long id = 123L;
    private final String answer = "Test Answer Variant";
    private final Long mediaFileId = 321L;
    private final boolean isVariantCorrect = false;

    @Test
    void convertToAnswerVariantDto() {
        AnswerVariant answerVariant = new AnswerVariant();
        answerVariant.setId(id);
        answerVariant.setAnswer(answer);
        answerVariant.setMediaFileId(mediaFileId);
        answerVariant.setVariantCorrect(isVariantCorrect);

        AnswerVariantDto answerVariantDtoToTest = AnswerVariantDtoConverter.convertToAnswerVariantDto(answerVariant);

        assertEquals(id, answerVariantDtoToTest.getId());
        assertEquals(answer, answerVariantDtoToTest.getAnswer());
        assertEquals(mediaFileId, answerVariantDtoToTest.getMediaFileId());
        assertEquals(isVariantCorrect, answerVariantDtoToTest.isVariantCorrect());
    }

    @Test
    void convertToAnswerVariant() {
        AnswerVariantDto answerVariantDto = new AnswerVariantDto();
        answerVariantDto.setId(id);
        answerVariantDto.setAnswer(answer);
        answerVariantDto.setMediaFileId(mediaFileId);
        answerVariantDto.setVariantCorrect(isVariantCorrect);

        AnswerVariant answerVariantToTest = AnswerVariantDtoConverter.convertToAnswerVariant(answerVariantDto);

        assertEquals(id, answerVariantToTest.getId());
        assertEquals(answer, answerVariantToTest.getAnswer());
        assertEquals(mediaFileId, answerVariantToTest.getMediaFileId());
        assertEquals(isVariantCorrect, answerVariantToTest.isVariantCorrect());
    }
}