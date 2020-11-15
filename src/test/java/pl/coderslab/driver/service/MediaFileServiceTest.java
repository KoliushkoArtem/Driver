package pl.coderslab.driver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import pl.coderslab.driver.converter.MediaFileDtoConverter;
import pl.coderslab.driver.dto.MediaFileDto;
import pl.coderslab.driver.exceptions.MediaFileNotFoundException;
import pl.coderslab.driver.model.MediaFile;
import pl.coderslab.driver.repository.MediaFileRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MediaFileServiceTest {

    private MediaFileRepository mediaFileRepositoryMock;
    private MediaFileService mediaFileService;
    private MediaFile testMediaFile;
    private MediaFileDto testMediaFileDto;
    private MockMultipartFile fileMock;
    private final byte[] mediaFile = "Test Byte Array".getBytes();

    @BeforeEach
    void setUp() {
        mediaFileRepositoryMock = mock(MediaFileRepository.class);
        mediaFileService = new MediaFileService(mediaFileRepositoryMock);
        String contentType = ".jpg";
        String name = "Test File Name";
        fileMock = new MockMultipartFile(name, name, contentType, mediaFile);

        testMediaFile = new MediaFile();
        testMediaFile.setId(123L);
        testMediaFile.setName(name);
        testMediaFile.setContentType(contentType);
        testMediaFile.setMediaFile(mediaFile);

        testMediaFileDto = MediaFileDtoConverter.convertToMediaFileDto(testMediaFile);
    }

    @Test
    void getById() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(testMediaFile));

        MediaFileDto mediaFileDtoToTest = mediaFileService.getById(testMediaFile.getId());

        assertEquals(testMediaFileDto, mediaFileDtoToTest);
    }

    @Test
    void getByIdFail() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(MediaFileNotFoundException.class, () -> mediaFileService.getById(testMediaFile.getId()));
    }

    @Test
    void save() {
        when(mediaFileRepositoryMock.save(any())).thenReturn(testMediaFile);

        MediaFile mediaFileToTest = mediaFileService.save(fileMock);

        assertEquals(testMediaFile, mediaFileToTest);
    }

    @Test
    void update() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(testMediaFile));
        when(mediaFileRepositoryMock.save(any())).thenReturn(testMediaFile);

        mediaFileService.update(fileMock, testMediaFile.getId());

        verify(mediaFileRepositoryMock, times(1)).findById(testMediaFile.getId());
        verify(mediaFileRepositoryMock, times(1)).save(testMediaFile);
    }

    @Test
    void updateFail() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(MediaFileNotFoundException.class, () -> mediaFileService.update(fileMock, testMediaFile.getId()));
    }

    @Test
    void delete() {
        mediaFileService.delete(1L);
        verify(mediaFileRepositoryMock, times(1)).deleteById(1L);
    }
}