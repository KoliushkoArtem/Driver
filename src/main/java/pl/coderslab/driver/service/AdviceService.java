package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.converter.AdviceDtoConverter;
import pl.coderslab.driver.dto.AdviceDto;
import pl.coderslab.driver.exceptions.AdviceNotFoundException;
import pl.coderslab.driver.model.Advice;
import pl.coderslab.driver.repository.AdviceRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdviceService {

    private final AdviceRepository adviceRepository;

    public AdviceService(AdviceRepository adviceRepository) {
        this.adviceRepository = adviceRepository;
    }

    public List<AdviceDto> getAll() {
        return adviceRepository.findAll()
                .stream()
                .map(AdviceDtoConverter::convertToAdviceDTO)
                .collect(Collectors.toList());
    }

    public AdviceDto save(AdviceDto adviceDTO) {
        Advice savedAdvice = adviceRepository.save(AdviceDtoConverter.convertToAdvice(adviceDTO));

        return AdviceDtoConverter.convertToAdviceDTO(savedAdvice);
    }

    public AdviceDto update(AdviceDto adviceDto) throws AdviceNotFoundException {
        Advice adviceToUpdate = adviceRepository
                .findById(adviceDto.getId()).orElseThrow(() -> new AdviceNotFoundException(adviceDto.getId()));
        Advice updatedAdvice = updateAdvice(adviceToUpdate, adviceDto);

        return AdviceDtoConverter.convertToAdviceDTO(adviceRepository.save(updatedAdvice));
    }

    public void delete(long adviceId) {
        adviceRepository.deleteById(adviceId);
    }

    public AdviceDto findById(long adviceId) throws AdviceNotFoundException {
        Advice adviceFromDb = adviceRepository.findById(adviceId).orElseThrow(() -> new AdviceNotFoundException(adviceId));

        return AdviceDtoConverter.convertToAdviceDTO(adviceFromDb);
    }

    public AdviceDto getAdviceByRatingForLast7Days() {
        return AdviceDtoConverter.convertToAdviceDTO(adviceRepository.findFirstByRatingForLast7Days());
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