package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.repository.AdviceTestRepository;

@Service
public class AdviceTestService {

    private final AdviceTestRepository testRepository;

    public AdviceTestService(AdviceTestRepository testRepository) {
        this.testRepository = testRepository;
    }
}
