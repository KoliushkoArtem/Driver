package pl.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.driver.converter.AdviceDtoConverter;
import pl.driver.dto.AdviceDto;
import pl.driver.exceptions.AdviceNotFoundException;
import pl.driver.model.Advice;
import pl.driver.repository.AdviceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdviceService {

    private final AdviceRepository adviceRepository;

    public AdviceService(AdviceRepository adviceRepository) {
        this.adviceRepository = adviceRepository;
    }

    public List<AdviceDto> getAll() {
        List<AdviceDto> result = adviceRepository.findAll()
                .stream()
                .map(AdviceDtoConverter::convertToAdviceDto)
                .collect(Collectors.toList());

        log.info("IN adviceService getAll - {} advices successfully load", result.size());

        return result;
    }

    public AdviceDto save(AdviceDto adviceDTO) {
        Advice savedAdvice = adviceRepository.save(AdviceDtoConverter.convertToAdvice(adviceDTO));

        log.info("IN adviceService save - advice {} vas saved", savedAdvice);

        return AdviceDtoConverter.convertToAdviceDto(savedAdvice);
    }

    public AdviceDto update(AdviceDto adviceDto) throws AdviceNotFoundException {
        Advice adviceToUpdate = adviceRepository
                .findById(adviceDto.getId()).orElseThrow(() -> new AdviceNotFoundException(adviceDto.getId()));
        Advice updatedAdvice = updateAdvice(adviceToUpdate, adviceDto);

        AdviceDto updated = AdviceDtoConverter.convertToAdviceDto(adviceRepository.save(updatedAdvice));

        log.info("IN adviceService update - advice {} was successfully updated", updated);

        return updated;
    }

    public void delete(long adviceId) {
        adviceRepository.deleteById(adviceId);

        log.info("IN adviceService delete - advice with id: {} was successfully deleted", adviceId);
    }

    public AdviceDto findById(long adviceId) throws AdviceNotFoundException {
        Advice adviceFromDb = adviceRepository.findById(adviceId).orElseThrow(() -> new AdviceNotFoundException(adviceId));

        log.info("IN adviceService findById - advice {} was successfully loaded", adviceFromDb);

        return AdviceDtoConverter.convertToAdviceDto(adviceFromDb);
    }

    public AdviceDto getAdviceByRatingForLast7Days() {
        Advice adviceFromDb = adviceRepository.findFirstByRatingForLast7Days();

        log.info("IN adviceService findById - advice {} was successfully loaded", adviceFromDb);

        return AdviceDtoConverter.convertToAdviceDto(adviceFromDb);
    }

    private Advice updateAdvice(Advice adviceToUpdate, AdviceDto adviceDto) {
        adviceToUpdate.setName(adviceDto.getAdviceName());
        adviceToUpdate.setDescription(adviceDto.getAdviceDescription());
        adviceToUpdate.setMediaFileId(adviceDto.getMediaFileId());
        adviceToUpdate.getRating().setLikeCount(adviceDto.getLikeCount());
        adviceToUpdate.getRating().setDislikeCount(adviceDto.getDislikeCount());
        adviceToUpdate.setTestId(adviceDto.getTestId());

        return adviceToUpdate;
    }
}