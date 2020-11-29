package pl.driver.converter;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.driver.dto.MediaFileDto;
import pl.driver.model.MediaFile;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Slf4j
class MediaFileDtoConverterTest {

    private final String name = "Test File Name";
    private final String contentType = ".jpg";
    private final byte[] mediaFile = "Test Byte Array".getBytes();

    @BeforeEach
    void setUp(TestInfo testInfo) {
        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call convertToMediaFileDto method assert that all similar fields would be an equals in incoming MediaFile and outputted MediaFileDto")
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
    @DisplayName("When call convertToMediaFile method assert that all similar fields would be an equals in incoming MediaFileDto and outputted MediaFile")
    void convertMultipartFileToMediaFile() {
        MockMultipartFile file = new MockMultipartFile(name, name, contentType, mediaFile);

        MediaFile fileToTest = MediaFileDtoConverter.convertMultipartFileToMediaFile(file);

        assertEquals(name, fileToTest.getName());
        assertEquals(contentType, fileToTest.getContentType());
        assertArrayEquals(mediaFile, fileToTest.getMediaFile());
    }

    @Test
    @DisplayName("When call convertMultipartFileToMediaFile method with damaged file assert that RuntimeException will be thrown")
    void convertMultipartFileToMediaFileExceptionCheck() throws IOException {
        MultipartFile file = mock(MultipartFile.class);

        when(file.getBytes()).thenThrow(new IOException());

        assertThrows(RuntimeException.class, () -> MediaFileDtoConverter.convertMultipartFileToMediaFile(file));
    }
}