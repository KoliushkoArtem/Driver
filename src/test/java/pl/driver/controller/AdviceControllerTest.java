package pl.driver.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.driver.configuration.YamlUrlConfiguration;
import pl.driver.dto.AdviceDto;
import pl.driver.exceptions.AdviceNotFoundException;
import pl.driver.service.AdviceService;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@Slf4j
class AdviceControllerTest {

    private final String mediaFileDownloadUrl = "mediaFileUrl";
    private final String testUrl = "testUrl";
    private AdviceService adviceServiceMock;
    private AdviceController adviceController;
    private AdviceDto testAdviceDto;

    @BeforeEach
    void setUp(TestInfo testInfo) {
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

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call getAllAdvices method asserted ResponseEntity with List of AdviseDto and HTTP status OK")
    void getAllAdvices() {
        AdviceDto[] adviceArray = new AdviceDto[]{testAdviceDto};
        when(adviceServiceMock.getAll()).thenReturn(Arrays.asList(adviceArray));

        ResponseEntity<List<AdviceDto>> allAdvicesToTest = adviceController.getAllAdvices();

        assertEquals(HttpStatus.OK, allAdvicesToTest.getStatusCode());
        assertEquals(1, Objects.requireNonNull(allAdvicesToTest.getBody()).size());
        assertEquals(testAdviceDto, allAdvicesToTest.getBody().get(0));
    }

    @Test
    @DisplayName("When call getAdviceById method with exist id asserted Response entity with AdviceDto with correct id and set mediaFileDownloadUrl and adviceTestUrl")
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
    @DisplayName("When call getAdviceById method with not exist id asserted ResponseEntity with HTTP status NOT_FOUND")
    void getAdviceByIdFailByUserNotFoundException() {
        when(adviceServiceMock.findById(testAdviceDto.getId())).thenThrow(new AdviceNotFoundException(testAdviceDto.getId()));

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.getAdviceById(testAdviceDto.getId());

        assertEquals(HttpStatus.NOT_FOUND, adviceByIdToTest.getStatusCode());
    }

    @Test
    @DisplayName("When call getAdviceByRatingForLast7Days method asserted ResponseEntity with AdviceDto and HTTP status OK")
    void getAdviceByRatingForLast7Days() {
        when(adviceServiceMock.getAdviceByRatingForLast7Days()).thenReturn(testAdviceDto);

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.getAdviceByRatingForLast7Days();

        assertEquals(HttpStatus.OK, adviceByIdToTest.getStatusCode());
        assertNotNull(adviceByIdToTest.getBody());
    }

    @Test
    @DisplayName("When call addAdvice method asserted ResponseEntity with saved AdviceDto with set mediaFileDownloadUrl and adviceTestUrl and HTTP status OK")
    void addAdvice() {
        when(adviceServiceMock.save(testAdviceDto)).thenReturn(testAdviceDto);

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.addAdvice(testAdviceDto);

        assertEquals(HttpStatus.OK, adviceByIdToTest.getStatusCode());
        assertNotNull(Objects.requireNonNull(adviceByIdToTest.getBody()).getMediaFileDownloadLink());
        assertNotNull(Objects.requireNonNull(adviceByIdToTest.getBody()).getTestLink());

    }

    @Test
    @DisplayName("When call updateAdvice method wit exist advise assert ResponseEntity with updated AdviseDto and HTTP status OK")
    void updateAdvice() {
        when(adviceServiceMock.update(testAdviceDto)).thenReturn(testAdviceDto);

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.updateAdvice(testAdviceDto);

        assertEquals(HttpStatus.OK, adviceByIdToTest.getStatusCode());
        assertNotNull(Objects.requireNonNull(adviceByIdToTest.getBody()).getMediaFileDownloadLink());
    }

    @Test
    @DisplayName("When call updateAdvice method wit not exist advise assert ResponseEntity with HTTP status NOT_FOUND")
    void updateAdviceFailByUserNotFoundException() {
        when(adviceServiceMock.update(testAdviceDto)).thenThrow(new AdviceNotFoundException(testAdviceDto.getId()));

        ResponseEntity<AdviceDto> adviceByIdToTest = adviceController.updateAdvice(testAdviceDto);

        assertEquals(HttpStatus.NOT_FOUND, adviceByIdToTest.getStatusCode());
    }

    @Test
    @DisplayName("When call deleteAdvice method with exist id assert delete method in AdviseService will be called 1 time and HTTP status OK")
    void deleteAdvice() {
        HttpStatus httpStatus = adviceController.deleteAdvice(testAdviceDto.getId());

        verify(adviceServiceMock, times(1)).delete(testAdviceDto.getId());
        assertEquals(HttpStatus.OK, httpStatus);

    }

    @Test
    @DisplayName("When call deleteAdvice method with not exist id assert delete method will throw AdviseNotFoundException and HTTP status NOT_FOUND")
    void deleteAdviceByAdviceNotFoundException() {
        doThrow(new AdviceNotFoundException(testAdviceDto.getId())).when(adviceServiceMock).delete(testAdviceDto.getId());

        HttpStatus httpStatus = adviceController.deleteAdvice(testAdviceDto.getId());

        verify(adviceServiceMock, times(1)).delete(testAdviceDto.getId());
        assertEquals(HttpStatus.NOT_FOUND, httpStatus);
    }
}