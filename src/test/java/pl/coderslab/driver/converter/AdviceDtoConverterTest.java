package pl.coderslab.driver.converter;

import org.junit.jupiter.api.Test;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.model.Advice;
import pl.coderslab.driver.model.AdviceRating;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AdviceDtoConverterTest {

    private final Long id = 123L;
    private final String adviceName = "Test Advice Name";
    private final String adviceDescription = "Test  Advice description";
    private final Long mediaFileId = 12L;
    private final int likeCount = 25;
    private final int dislikeCount = 10;
    private final Long testId = 33L;

    @Test
    void convertToAdviceDto() {
        Advice advice = new Advice();
        advice.setId(id);
        advice.setName(adviceName);
        advice.setDescription(adviceDescription);
        advice.setMediaFileId(mediaFileId);
        AdviceRating adviceRating = new AdviceRating();
        adviceRating.setLikeCount(likeCount);
        adviceRating.setDislikeCount(dislikeCount);
        advice.setRating(adviceRating);
        advice.setTestId(testId);

        AdviceDto adviceDtoToTest = AdviceDtoConverter.convertToAdviceDto(advice);

        assertEquals(id, adviceDtoToTest.getId());
        assertEquals(adviceName, adviceDtoToTest.getAdviceName());
        assertEquals(adviceDescription, adviceDtoToTest.getAdviceDescription());
        assertEquals(mediaFileId, adviceDtoToTest.getMediaFileId());
        assertEquals(likeCount, adviceDtoToTest.getLikeCount());
        assertEquals(dislikeCount, adviceDtoToTest.getDislikeCount());
        assertEquals(testId, adviceDtoToTest.getTestId());
    }

    @Test
    void convertToAdvice() {
        AdviceDto adviceDto = new AdviceDto();
        adviceDto.setId(id);
        adviceDto.setAdviceName(adviceName);
        adviceDto.setAdviceDescription(adviceDescription);
        adviceDto.setMediaFileId(mediaFileId);
        adviceDto.setLikeCount(likeCount);
        adviceDto.setDislikeCount(dislikeCount);
        adviceDto.setTestId(testId);

        Advice adviceToTest = AdviceDtoConverter.convertToAdvice(adviceDto);

        assertEquals(id, adviceToTest.getId());
        assertEquals(adviceName, adviceToTest.getName());
        assertEquals(adviceDescription, adviceToTest.getDescription());
        assertEquals(mediaFileId, adviceToTest.getMediaFileId());
        assertEquals(likeCount, adviceToTest.getRating().getLikeCount());
        assertEquals(dislikeCount, adviceToTest.getRating().getDislikeCount());
        assertEquals(testId, adviceToTest.getTestId());
    }
}