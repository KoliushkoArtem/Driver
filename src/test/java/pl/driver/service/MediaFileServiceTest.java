package pl.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;
import pl.driver.converter.MediaFileDtoConverter;
import pl.driver.dto.MediaFileDto;
import pl.driver.exceptions.MediaFileNotFoundException;
import pl.driver.model.MediaFile;
import pl.driver.repository.MediaFileRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@Slf4j
class MediaFileServiceTest {

    private final byte[] mediaFile = "Test Byte Array".getBytes();
    private MediaFileRepository mediaFileRepositoryMock;
    private MediaFileService mediaFileService;
    private MediaFile testMediaFile;
    private MediaFileDto testMediaFileDto;
    private MockMultipartFile fileMock;

    @BeforeEach
    void setUp(TestInfo testInfo) {
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

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call findById method with exist id assert that MediaFile from DB will be converted to MediaFileDto and returned")
    void getById() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(testMediaFile));

        MediaFileDto mediaFileDtoToTest = mediaFileService.getById(testMediaFile.getId());

        assertEquals(testMediaFileDto, mediaFileDtoToTest);
    }

    @Test
    @DisplayName("When call findById method with not exist id assert that MediaFileNotFoundException will be thrown")
    void getByIdFail() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.empty());
        assertThrows(MediaFileNotFoundException.class, () -> mediaFileService.getById(testMediaFile.getId()));
    }

    @Test
    @DisplayName("When call save method with MultipartFile asserted that MultipartFile will be converted to MediaFile and saved, then MediaFile will be returned")
    void save() {
        when(mediaFileRepositoryMock.save(any())).thenReturn(testMediaFile);

        MediaFile mediaFileToTest = mediaFileService.save(fileMock);

        assertEquals(testMediaFile, mediaFileToTest);
    }

    @Test
    @DisplayName("When call update method with MultipartFile and exist media file id asserted that media file will be found in DB, updated and saved")
    void update() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(testMediaFile));
        when(mediaFileRepositoryMock.save(any())).thenReturn(testMediaFile);

        mediaFileService.update(fileMock, testMediaFile.getId());

        verify(mediaFileRepositoryMock, times(1)).findById(testMediaFile.getId());
        verify(mediaFileRepositoryMock, times(1)).save(testMediaFile);
    }

    @Test
    @DisplayName("When call update method with MultipartFile but not exist media file id asserted that MediaFileNotFoundException will be thrown")
    void updateFail() {
        when(mediaFileRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(MediaFileNotFoundException.class, () -> mediaFileService.update(fileMock, testMediaFile.getId()));
    }

    //TODO
    @Test
    void delete() {
        mediaFileService.delete(1L);
        verify(mediaFileRepositoryMock, times(1)).deleteById(1L);
    }
}