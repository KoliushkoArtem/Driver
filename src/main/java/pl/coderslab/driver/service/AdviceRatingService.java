package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.repository.AdviceRatingRepository;

@Service
public class AdviceRatingService {

    private final AdviceRatingRepository ratingRepository;

    public AdviceRatingService(AdviceRatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }
}
