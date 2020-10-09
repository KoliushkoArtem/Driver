package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.driver.dto.AdviceDTO;
import pl.coderslab.driver.exceptions.AdviceNotFoundException;
import pl.coderslab.driver.model.Advice;
import pl.coderslab.driver.repository.AdviceRatingRepository;
import pl.coderslab.driver.repository.AdviceRepository;
import pl.coderslab.driver.utils.ConverterDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdviceService {

    private final AdviceRepository adviceRepository;
    private final AdviceRatingRepository ratingRepository;

    public AdviceService(AdviceRepository adviceRepository, AdviceRatingRepository ratingRepository) {
        this.adviceRepository = adviceRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<AdviceDTO> getAll() {
        return adviceRepository.findAll()
                .stream()
                .map(ConverterDTO::convertToAdviceDTO)
                .collect(Collectors.toList());
    }

    public void save(AdviceDTO adviceDTO) {
        adviceRepository.save(ConverterDTO.convertToAdvice(adviceDTO));
    }

    public void update(AdviceDTO adviceDto) {
        Advice adviceToUpdate = ConverterDTO.convertToAdvice(adviceDto);
        long ratingId = adviceRepository.findRatingIdByAdviceId(adviceToUpdate.getId());

        adviceRepository.update(adviceToUpdate.getName(), adviceToUpdate.getDescription(),
                adviceToUpdate.getMediaFileDownloadUrl(), adviceToUpdate.getId());

        ratingRepository.update(adviceToUpdate.getRating().getLikeCount(), adviceToUpdate.getRating().getDislikeCount(), ratingId);
    }


    public void delete(long adviceId) {
        adviceRepository.deleteById(adviceId);
    }

    public AdviceDTO findById(long adviceId) {
        Advice adviceToReturn = adviceRepository.findById(adviceId).orElseThrow(() -> new AdviceNotFoundException(adviceId));

        return ConverterDTO.convertToAdviceDTO(adviceToReturn);
    }

    public AdviceDTO getAdviceByRatingForLast7Days() {
        Advice bestAdvise = adviceRepository.findFirstByRatingForLast7Days();

        return ConverterDTO.convertToAdviceDTO(bestAdvise);
    }
}