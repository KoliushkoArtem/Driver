package pl.driver.converter;

import pl.driver.dto.AdviceDto;
import pl.driver.model.Advice;
import pl.driver.model.AdviceRating;

public class AdviceDtoConverter {


    public static AdviceDto convertToAdviceDto(Advice advice) {
        AdviceDto convertedAdviceDto = new AdviceDto();
        convertedAdviceDto.setId(advice.getId());
        convertedAdviceDto.setAdviceName(advice.getName());
        convertedAdviceDto.setAdviceDescription(advice.getDescription());
        convertedAdviceDto.setMediaFileId(advice.getMediaFileId());
        convertedAdviceDto.setLikeCount(advice.getRating().getLikeCount());
        convertedAdviceDto.setDislikeCount(advice.getRating().getDislikeCount());
        convertedAdviceDto.setTestId(advice.getTestId());
        return convertedAdviceDto;
    }

    public static Advice convertToAdvice(AdviceDto adviceDto) {
        Advice convertedAdvice = new Advice();
        if (adviceDto.getId() != null) {
            convertedAdvice.setId(adviceDto.getId());
        }
        convertedAdvice.setName(adviceDto.getAdviceName());
        convertedAdvice.setDescription(adviceDto.getAdviceDescription());
        convertedAdvice.setMediaFileId(adviceDto.getMediaFileId());
        convertedAdvice.setRating(new AdviceRating());
        convertedAdvice.getRating().setLikeCount(adviceDto.getLikeCount());
        convertedAdvice.getRating().setDislikeCount(adviceDto.getDislikeCount());
        if (adviceDto.getTestId() != null) {
            convertedAdvice.setTestId(adviceDto.getTestId());
        }

        return convertedAdvice;
    }
}
