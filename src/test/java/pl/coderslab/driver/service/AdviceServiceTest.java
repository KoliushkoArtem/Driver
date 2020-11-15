package pl.coderslab.driver.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.coderslab.driver.converter.AdviceDtoConverter;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.exceptions.AdviceNotFoundException;
import pl.coderslab.driver.model.Advice;
import pl.coderslab.driver.model.AdviceRating;
import pl.coderslab.driver.repository.AdviceRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdviceServiceTest {

    private AdviceRepository adviceRepositoryMock;
    private AdviceService adviceService;
    private Advice testAdvice;
    private AdviceDto testAdviceDto;

    @BeforeEach
    void setUp() {
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
    }

    @Test
    void getAll() {
        Advice[] adviceArray = new Advice[]{testAdvice};
        when(adviceRepositoryMock.findAll()).thenReturn(Arrays.asList(adviceArray));

        List<AdviceDto> adviceDtoTestList = adviceService.getAll();

        assertTrue(adviceDtoTestList.contains(testAdviceDto));
        assertEquals(1, adviceDtoTestList.size());
    }

    @Test
    void save() {
        when(adviceRepositoryMock.save(any())).thenReturn(testAdvice);

        AdviceDto adviceDtoToTest = adviceService.save(testAdviceDto);

        assertEquals(testAdviceDto, adviceDtoToTest);
    }

    @Test
    void update() {
        when(adviceRepositoryMock.findById(testAdvice.getId())).thenReturn(Optional.ofNullable(testAdvice));
        when(adviceRepositoryMock.save(testAdvice)).thenReturn(testAdvice);

        AdviceDto adviceDtoToTest = adviceService.update(testAdviceDto);

        assertEquals(testAdviceDto, adviceDtoToTest);
    }

    @Test
    void updateExceptionCall () {
        when(adviceRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdviceNotFoundException.class, () -> adviceService.update(testAdviceDto));
    }

    @Test
    void delete() {
        adviceService.delete(1L);
        verify(adviceRepositoryMock, times(1)).deleteById(1L);
    }

    @Test
    void findByIdSuccess() {
        when(adviceRepositoryMock.findById(any())).thenReturn(Optional.ofNullable(testAdvice));

        AdviceDto adviceDtoToTest = adviceService.findById(testAdvice.getTestId());

        assertEquals(testAdviceDto, adviceDtoToTest);
    }

    @Test
    void findByIdFail() {
        when(adviceRepositoryMock.findById(any())).thenReturn(Optional.empty());

        assertThrows(AdviceNotFoundException.class, () -> adviceService.findById(testAdviceDto.getId()));
    }

    @Test
    void getAdviceByRatingForLast7Days() {
        when(adviceRepositoryMock.findFirstByRatingForLast7Days()).thenReturn(testAdvice);

        AdviceDto adviceDtoToTest = adviceService.getAdviceByRatingForLast7Days();

        assertEquals(testAdviceDto, adviceDtoToTest);
    }
}