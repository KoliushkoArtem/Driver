package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.repository.AnswerVariantRepository;

@Service
public class AnswerVariantService {

    private final AnswerVariantRepository answerVariantRepository;

    public AnswerVariantService(AnswerVariantRepository answerVariantRepository) {
        this.answerVariantRepository = answerVariantRepository;
    }
}
