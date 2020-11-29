package pl.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import pl.driver.converter.AdviceDtoConverter;
import pl.driver.dto.AdviceDto;
import pl.driver.exceptions.AdviceNotFoundException;
import pl.driver.model.Advice;
import pl.driver.model.AdviceRating;
import pl.driver.repository.AdviceRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Slf4j
class AdviceServiceTest {

    private AdviceRepository adviceRepositoryMock;
    private AdviceService adviceService;
    private Advice testAdvice;
    private AdviceDto testAdviceDto;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        adviceRepositoryMock = mock(AdviceRepository.class);
        adviceService = new AdviceService(adviceRepositoryMock);

        testAdvice = new Advice();
        testAdvice.setId(123L);
        testAdvice.setName("Test Advice Name");
        testAdvice.setDescription("Test Advice Description");
        testAdvice.setMediaFileId(321L);
        testAdvice.setTestId(231L);
        AdviceRating rating = new AdviceRating();
        rating.setId(567L);
        rating.setLikeCount(50);
        rating.setDislikeCount(11);
        testAdvice.setRating(rating);

        testAdviceDto = AdviceDtoConverter.convertToAdviceDto(testAdvice);

        log.info(String.format("test started: %s", testInfo.getDisplayName()));
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        log.info(String.format("test finished: %s", testInfo.getDisplayName()));
    }

    @Test
    @DisplayName("When call getAll method assert that received from DB List of Advices will be converted to AdviseDto and returned List of AdviceDto")
    void getAll() {
        Advice[] adviceArray = new Advice[]{testAdvice};
        when(adviceRepositoryMock.findAll()).thenReturn(Arrays.asList(adviceArray));

        List<AdviceDto> adviceDtoTestList = adviceService.getAll();

        assertTrue(adviceDtoTestList.contains(testAdviceDto));
        assertEquals(1, adviceDtoTestList.size());
    }

    @Test
    @DisplayName("When call save method with AdviceDto asserted that AdviceDto will be converted to Advice and saved, then saved Advise converted to AdviseDto and returned")
    void save() {
        when(adviceRepositoryMock.save(any())).thenReturn(testAdvice);

        AdviceDto adviceDtoToTest = adviceService.save(testAdviceDto);

        assertEquals(testAdviceDto, adviceDtoToTest);
    }

    @Test
    @DisplayName("When call update method with exist AdviceDto asserted that AdviceDto will be converted to Advice and updated, then saved Advise converted to AdviseDto and returned")
    void update() {
        when(adviceRepositoryMock.findById(testAdvice.getId())).thenReturn(Optional.ofNullable(testAdvice));
        when(adviceRepositoryMock.save(testAdvice)).thenReturn(testAdvice);

        AdviceDto adviceDtoToTest = adviceService.update(testAdviceDto);

        assertEquals(testAdviceDto, adviceDtoToTest);
    }

    @Test
    @DisplayName("When call update method with not exist AdviceDto asserted that AdviceNotFoundException will be thrown")
    void updateExceptionCall() {
        when(adviceRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdviceNotFoundException.class, () -> adviceService.update(testAdviceDto));
    }

    @Test
    @DisplayName("When call delete method with exist id assert that at AdviceRepository will be called findDyId and delete methods one time each")
    void delete() {
        when(adviceRepositoryMock.findById(testAdvice.getTestId())).thenReturn(Optional.ofNullable(testAdvice));

        adviceService.delete(testAdvice.getTestId());

        verify(adviceRepositoryMock, times(1)).delete(testAdvice);
        verify(adviceRepositoryMock, times(1)).findById(testAdvice.getTestId());
    }

    @Test
    @DisplayName("When call delete method with not exist id assert that AdviceNotFoundException will be thrown")
    void deleteFailByAdviseTestNotFoundException() {
        when(adviceRepositoryMock.findById(testAdvice.getTestId())).thenReturn(Optional.empty());

        assertThrows(AdviceNotFoundException.class, () -> adviceService.delete(testAdviceDto.getId()));
    }

    @Test
    @DisplayName("When call findById method with exist id assert that Advice from DB will be converted to AdviceDto and returned")
    void findByIdSuccess() {
        when(adviceRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(testAdvice));

        AdviceDto adviceDtoToTest = adviceService.findById(testAdvice.getTestId());

        assertEquals(testAdviceDto, adviceDtoToTest);
    }

    @Test
    @DisplayName("when call findById method with no exist id assert that AdviceNotFoundException will be thrown")
    void findByIdFail() {
        when(adviceRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdviceNotFoundException.class, () -> adviceService.findById(testAdviceDto.getId()));
    }

    @Test
    @DisplayName("When call getAdviceByRatingForLast7Days method assert that Advice from DB will be converted to AdviceDto and returned")
    void getAdviceByRatingForLast7Days() {
        when(adviceRepositoryMock.findFirstByRatingForLast7Days()).thenReturn(testAdvice);

        AdviceDto adviceDtoToTest = adviceService.getAdviceByRatingForLast7Days();

        assertEquals(testAdviceDto, adviceDtoToTest);
    }
}