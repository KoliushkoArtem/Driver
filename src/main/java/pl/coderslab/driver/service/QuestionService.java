package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.repository.QuestionRepository;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
}
