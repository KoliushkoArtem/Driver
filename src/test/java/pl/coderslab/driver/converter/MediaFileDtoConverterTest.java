package pl.coderslab.driver.converter;


import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.driver.dto.MediaFileDto;
import pl.coderslab.driver.model.MediaFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class MediaFileDtoConverterTest {

    private final String name = "Test File Name";
    private final String contentType = ".jpg";
    private final byte[] mediaFile = "Test Byte Array".getBytes();

    @Test
    void convertToMediaFileDto() {
        MediaFile file = new MediaFile();
        file.setName(name);
        file.setContentType(contentType);
        file.setMediaFile(mediaFile);

        MediaFileDto fileDtoToTest = MediaFileDtoConverter.convertToMediaFileDto(file);

        assertEquals(name, fileDtoToTest.getName());
        assertEquals(contentType, fileDtoToTest.getContentType());
        assertArrayEquals(mediaFile, fileDtoToTest.getMediaFile());
    }

    @Test
    void convertMultipartFileToMediaFile() {
        MockMultipartFile file = new MockMultipartFile(name, name, contentType, mediaFile);

        MediaFile fileToTest = MediaFileDtoConverter.convertMultipartFileToMediaFile(file);

        assertEquals(name, fileToTest.getName());
        assertEquals(contentType, fileToTest.getContentType());
        assertArrayEquals(mediaFile, fileToTest.getMediaFile());
    }

    @Test
    void convertMultipartFileToMediaFileExceptionCheck() throws IOException {
        MultipartFile file = mock(MultipartFile.class);

        when(file.getBytes()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> MediaFileDtoConverter.convertMultipartFileToMediaFile(file));
    }
}