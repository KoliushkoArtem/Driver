package pl.driver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

class MediaFileControllerTest {

    private MediaFileService mediaFileServiceMok;
    private MediaFileController mediaFileController;
    private MultipartFile multipartFileMok;
    private MediaFile testMediaFile;
    private MediaFileDto testMediaFileDto;


    @BeforeEach
    void setUp() {
        mediaFileServiceMok = mock(MediaFileService.class);
        multipartFileMok = new MockMultipartFile("TestName", "TestFulName", ".jpg/IMAGE", "Test Byte Array".getBytes());
        mediaFileController = new MediaFileController(mediaFileServiceMok);

        testMediaFile = MediaFileDtoConverter.convertMultipartFileToMediaFile(multipartFileMok);
        testMediaFile.setId(123L);
        testMediaFileDto = MediaFileDtoConverter.convertToMediaFileDto(testMediaFile);
    }

    @Test
    void downloadFile() {
        when(mediaFileServiceMok.getById(1L)).thenReturn(testMediaFileDto);

        ResponseEntity<ByteArrayResource> mediaFileByteArray = mediaFileController.downloadFile(1L);

        assertEquals(HttpStatus.OK, mediaFileByteArray.getStatusCode());
        assertEquals(testMediaFileDto.getMediaFile(), Objects.requireNonNull(mediaFileByteArray.getBody()).getByteArray());
    }

    @Test
    void downloadFileFailByMediaFileNotFoundException() {
        when(mediaFileServiceMok.getById(testMediaFile.getId())).thenThrow(new MediaFileNotFoundException(testMediaFile.getId()));

        ResponseEntity<ByteArrayResource> mediaFileByteArray = mediaFileController.downloadFile(testMediaFile.getId());

        assertEquals(HttpStatus.NOT_FOUND, mediaFileByteArray.getStatusCode());
    }

    @Test
    void uploadFileAndGetFileId() {
        when(mediaFileServiceMok.save(multipartFileMok)).thenReturn(testMediaFile);

        ResponseEntity<Long> testResponseEntityWitMediaFileId = mediaFileController.uploadFileAndGetFileId(multipartFileMok);

        assertEquals(testMediaFile.getId(), testResponseEntityWitMediaFileId.getBody());
        assertEquals(HttpStatus.OK, testResponseEntityWitMediaFileId.getStatusCode());
    }

    @Test
    void uploadFileAndGetFileIdFailByRuntimeException() {
        when(mediaFileServiceMok.save(multipartFileMok)).thenThrow(new RuntimeException());

        ResponseEntity<Long> testResponseEntityWitMediaFileId = mediaFileController.uploadFileAndGetFileId(multipartFileMok);

        assertEquals(HttpStatus.BAD_REQUEST, testResponseEntityWitMediaFileId.getStatusCode());
    }

    @Test
    void updateFile() {
        HttpStatus testHttpStatus = mediaFileController.updateFile(multipartFileMok, testMediaFile.getId());

        assertEquals(HttpStatus.OK, testHttpStatus);
    }

    @Test
    void updateFileFail() {
        doThrow(new MediaFileNotFoundException(testMediaFile.getId())).when(mediaFileServiceMok).update(multipartFileMok, testMediaFile.getId());

        HttpStatus testHttpStatus = mediaFileController.updateFile(multipartFileMok, testMediaFile.getId());

        assertEquals(HttpStatus.BAD_REQUEST, testHttpStatus);
    }

    @Test
    void delete() {
        mediaFileController.delete(1L);
        verify(mediaFileServiceMok, times(1)).delete(1L);
    }
}