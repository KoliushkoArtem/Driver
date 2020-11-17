package pl.coderslab.driver.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.coderslab.driver.configuration.YamlUrlConfiguration;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.exceptions.AdviceNotFoundException;
import pl.coderslab.driver.service.AdviceService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class AdviceControllerTest {

    private AdviceService adviceServiceMock;
    private AdviceController adviceController;
    private AdviceDto testAdviceDto;
    private final String mediaFileDownloadUrl = "mediaFileUrl";
    private final String testUrl = "testUrl";


    @BeforeEach
    void setUp() {
        adviceServiceMock = mock(AdviceService.class);
        YamlUrlConfiguration yamlUrlConfiguration = new YamlUrlConfiguration();
        yamlUrlConfiguration.setMediaFileDownloadUrl(mediaFileDownloadUrl);
        yamlUrlConfiguration.setTestUrl(testUrl);
        adviceController = new AdviceController(adviceServiceMock, yamlUrlConfiguration);

        testAdviceDto = new AdviceDto();
        testAdviceDto.setId(123L);
        testAdviceDto.setAdviceName("Test Advice Name");
        testAdviceDto.setAdviceDescription("Test Advice Description");
        testAdviceDto.setMediaFileId(321L);
        testAdviceDto.setTestId(231L);
        testAdviceDto.setLikeCount(50);
        testAdviceDto.setDislikeCount(10);
    }

    @Test
    void getAllAdvices() {
        AdviceDto[] adviceArray = new AdviceDto[]{testAdviceDto};
        when(adviceServiceMock.getAll()).thenReturn(Arrays.asList(adviceArray));

        ResponseEntity<List<AdviceDto>> allAdvicesToTest = adviceController.getAllAdvices();

        assertEquals(HttpStatus.OK, allAdvicesToTest.getStatusCode());
        assertEquals(1, Objects.requireNonNull(allAdvicesToTest.getBody()).size());
        assertEquals(testAdviceDto, allAdvicesToTest.getBody().get(0));
    }

    @Test
    void getAdviceByIdSuccess() {
        when(adviceServiceMock.findById(testAdviceDto.getId())).thenReturn(testAdviceDto);

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.getAdviceById(testAdviceDto.getId());

        assertEquals(HttpStatus.OK, adviceByIdToTest.getStatusCode());
        assertEquals(mediaFileDownloadUrl + testAdviceDto.getMediaFileId(),
                Objects.requireNonNull(adviceByIdToTest.getBody()).getMediaFileDownloadLink());
        assertEquals(testUrl + testAdviceDto.getTestId(),
                Objects.requireNonNull(adviceByIdToTest.getBody()).getTestLink());
    }

    @Test
    void getAdviceByIdFailByUserNotFoundException() {
        when(adviceServiceMock.findById(testAdviceDto.getId())).thenThrow(new AdviceNotFoundException(testAdviceDto.getId()));

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.getAdviceById(testAdviceDto.getId());

        assertEquals(HttpStatus.NOT_FOUND, adviceByIdToTest.getStatusCode());
    }

    @Test
    void getAdviceByRatingForLast7Days() {
        when(adviceServiceMock.getAdviceByRatingForLast7Days()).thenReturn(testAdviceDto);

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.getAdviceByRatingForLast7Days();

        assertEquals(HttpStatus.OK, adviceByIdToTest.getStatusCode());
        assertNotNull(adviceByIdToTest.getBody());
    }

    @Test
    void addAdvice() {
        when(adviceServiceMock.save(testAdviceDto)).thenReturn(testAdviceDto);

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.addAdvice(testAdviceDto);

        assertEquals(HttpStatus.OK, adviceByIdToTest.getStatusCode());
        assertNotNull(Objects.requireNonNull(adviceByIdToTest.getBody()).getMediaFileDownloadLink());

    }

    @Test
    void updateAdvice() {
        when(adviceServiceMock.update(testAdviceDto)).thenReturn(testAdviceDto);

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.updateAdvice(testAdviceDto);

        assertEquals(HttpStatus.OK, adviceByIdToTest.getStatusCode());
        assertNotNull(Objects.requireNonNull(adviceByIdToTest.getBody()).getMediaFileDownloadLink());
    }

    @Test
    void updateAdviceFailByUserNotFoundException() {
        when(adviceServiceMock.update(testAdviceDto)).thenThrow(new AdviceNotFoundException(testAdviceDto.getId()));

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.updateAdvice(testAdviceDto);

        assertEquals(HttpStatus.NOT_FOUND, adviceByIdToTest.getStatusCode());
    }

    @Test
    void deleteAdvice() {
        adviceController.deleteAdvice(1L);
        verify(adviceServiceMock, times(1)).delete(1L);
    }
}