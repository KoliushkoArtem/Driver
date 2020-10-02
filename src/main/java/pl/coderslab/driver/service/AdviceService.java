package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.model.Advice;
import pl.coderslab.driver.repository.AdviceFileRepository;
import pl.coderslab.driver.repository.AdviceRatingRepository;
import pl.coderslab.driver.repository.AdviceRepository;

import java.util.List;

@Service
public class AdviceService {

    private final AdviceRepository adviceRepository;
    private final AdviceFileRepository fileRepository;
    private final AdviceRatingRepository ratingRepository;

    public AdviceService(AdviceRepository adviceRepository, AdviceFileRepository fileRepository, AdviceRatingRepository ratingRepository) {
        this.adviceRepository = adviceRepository;
        this.fileRepository = fileRepository;
        this.ratingRepository = ratingRepository;
    }

    public List<Advice> getAll() {
        return adviceRepository.findAll();
    }
}
