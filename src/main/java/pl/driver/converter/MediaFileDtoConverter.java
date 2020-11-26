package pl.driver.converter;

import org.springframework.web.multipart.MultipartFile;
import pl.driver.dto.MediaFileDto;
import pl.driver.model.MediaFile;

import java.io.IOException;

public class MediaFileDtoConverter {

    public static MediaFileDto convertToMediaFileDto(MediaFile mediaFile) {
        MediaFileDto convertedMediaFileDto = new MediaFileDto();
        convertedMediaFileDto.setName(mediaFile.getName());
        convertedMediaFileDto.setContentType(mediaFile.getContentType());
        convertedMediaFileDto.setMediaFile(mediaFile.getMediaFile());

        return convertedMediaFileDto;
    }

    public static MediaFile convertMultipartFileToMediaFile(MultipartFile file) throws RuntimeException {
        MediaFile convertedMediaFile = new MediaFile();
        convertedMediaFile.setName(file.getOriginalFilename());
        convertedMediaFile.setContentType(file.getContentType());
        try {
            convertedMediaFile.setMediaFile(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException();
        }

        return convertedMediaFile;
    }
}
