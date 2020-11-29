package pl.driver.converter;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.driver.dto.AnswerVariantDto;
import pl.driver.model.AnswerVariant;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
class AnswerVariantDtoConverterTest {

    private final Long id = 123L;
    private final String answer = "Test Answer Variant";
    private final Long mediaFileId = 321L;
    private final boolean isVariantCorrect = false;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call convertToAnswerVariantDto method assert that all similar fields would be an equals in incoming AnswerVariant and outputted AnswerVariantDto")
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
    @DisplayName("When call convertToAnswerVariant method assert that all similar fields would be an equals in incoming AnswerVariantDto and outputted AnswerVariant")
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