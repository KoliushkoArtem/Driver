package pl.driver.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import pl.driver.converter.MediaFileDtoConverter;
import pl.driver.dto.MediaFileDto;
import pl.driver.exceptions.MediaFileNotFoundException;
import pl.driver.model.MediaFile;
import pl.driver.service.MediaFileService;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@Slf4j
class MediaFileControllerTest {

    private MediaFileService mediaFileServiceMok;
    private MediaFileController mediaFileController;
    private MultipartFile multipartFileMok;
    private MediaFile testMediaFile;
    private MediaFileDto testMediaFileDto;


    @BeforeEach
    void setUp(TestInfo testInfo) {
        mediaFileServiceMok = mock(MediaFileService.class);
        multipartFileMok = new MockMultipartFile("TestName", "TestFulName", ".jpg/IMAGE", "Test Byte Array".getBytes());
        mediaFileController = new MediaFileController(mediaFileServiceMok);

        testMediaFile = MediaFileDtoConverter.convertMultipartFileToMediaFile(multipartFileMok);
        testMediaFile.setId(123L);
        testMediaFileDto = MediaFileDtoConverter.convertToMediaFileDto(testMediaFile);

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call downloadFile method with exist media file id assert ResponseEntity with ByteArrayResource and HTTP status OK")
    void downloadFile() {
        when(mediaFileServiceMok.getById(1L)).thenReturn(testMediaFileDto);

        ResponseEntity<ByteArrayResource> mediaFileByteArray = mediaFileController.downloadFile(1L);

        assertEquals(HttpStatus.OK, mediaFileByteArray.getStatusCode());
        assertEquals(testMediaFileDto.getMediaFile(), Objects.requireNonNull(mediaFileByteArray.getBody()).getByteArray());
    }

    @Test
    @DisplayName("When call downloadFile method with not exist media file id assert ResponseEntity with HTTP status NOT_FOUND")
    void downloadFileFailByMediaFileNotFoundException() {
        when(mediaFileServiceMok.getById(testMediaFile.getId())).thenThrow(new MediaFileNotFoundException(testMediaFile.getId()));

        ResponseEntity<ByteArrayResource> mediaFileByteArray = mediaFileController.downloadFile(testMediaFile.getId());

        assertEquals(HttpStatus.NOT_FOUND, mediaFileByteArray.getStatusCode());
    }

    @Test
    @DisplayName("When call uploadFileAndGetFileId with not damaged file asserted ResponseEntity with uploaded file id and HTTP status OK")
    void uploadFileAndGetFileId() {
        when(mediaFileServiceMok.save(multipartFileMok)).thenReturn(testMediaFile);

        ResponseEntity<Long> testResponseEntityWitMediaFileId = mediaFileController.uploadFileAndGetFileId(multipartFileMok);

        assertEquals(testMediaFile.getId(), testResponseEntityWitMediaFileId.getBody());
        assertEquals(HttpStatus.OK, testResponseEntityWitMediaFileId.getStatusCode());
    }

    @Test
    @DisplayName("When call uploadFileAndGetFileId with damaged file asserted ResponseEntity with HTTP status BAD_REQUEST")
    void uploadFileAndGetFileIdFailByRuntimeException() {
        when(mediaFileServiceMok.save(multipartFileMok)).thenThrow(new RuntimeException());

        ResponseEntity<Long> testResponseEntityWitMediaFileId = mediaFileController.uploadFileAndGetFileId(multipartFileMok);

        assertEquals(HttpStatus.BAD_REQUEST, testResponseEntityWitMediaFileId.getStatusCode());
    }

    @Test
    @DisplayName("When call updateFile method with exist file id and not damaged file assert HTTP status OK")
    void updateFile() {
        HttpStatus testHttpStatus = mediaFileController.updateFile(multipartFileMok, testMediaFile.getId());

        assertEquals(HttpStatus.OK, testHttpStatus);
    }

    @Test
    @DisplayName("When call updateFile method with not exist file id and not damaged file assert HTTP status BAD_REQUEST")
    void updateFileFailByMediaFileId() {
        doThrow(new MediaFileNotFoundException(testMediaFile.getId())).when(mediaFileServiceMok).update(multipartFileMok, testMediaFile.getId());

        HttpStatus testHttpStatus = mediaFileController.updateFile(multipartFileMok, testMediaFile.getId());

        assertEquals(HttpStatus.BAD_REQUEST, testHttpStatus);
    }

    @Test
    @DisplayName("When call updateFile method with exist file id but damaged file assert HTTP status BAD_REQUEST")
    void updateFileFailByMediaFile() {
        doThrow(new RuntimeException()).when(mediaFileServiceMok).update(multipartFileMok, testMediaFile.getId());

        HttpStatus testHttpStatus = mediaFileController.updateFile(multipartFileMok, testMediaFile.getId());

        assertEquals(HttpStatus.BAD_REQUEST, testHttpStatus);
    }

    @Test
    @DisplayName("When call delete method with exist id assert that delete method in MediaFileService will be called 1 time and HTTP status OK")
    void delete() {
        HttpStatus httpStatus = mediaFileController.delete(testMediaFile.getId());

        verify(mediaFileServiceMok, times(1)).delete(testMediaFile.getId());
        assertEquals(HttpStatus.OK, httpStatus);
    }

    @Test
    @DisplayName("When call delete method with not exist id assert that delete method in MediaFileService will throw MediaFileNotFoundException and HTTP status NOT_FOUND")
    void deleteFailByMediaFileNotFoundException() {
        doThrow(new MediaFileNotFoundException(testMediaFile.getId())).when(mediaFileServiceMok).delete(testMediaFile.getId());

        HttpStatus testHttpStatus = mediaFileController.delete(testMediaFile.getId());

        verify(mediaFileServiceMok, times(1)).delete(testMediaFile.getId());
        assertEquals(HttpStatus.NOT_FOUND, testHttpStatus);
    }
}