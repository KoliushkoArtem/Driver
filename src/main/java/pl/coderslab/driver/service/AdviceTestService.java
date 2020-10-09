package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.coderslab.driver.dto.AdviceTestDTO;
import pl.coderslab.driver.repository.AdviceTestRepository;
import pl.coderslab.driver.utils.ConverterDTO;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AdviceTestService {

    private final AdviceTestRepository testRepository;

    public AdviceTestService(AdviceTestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<AdviceTestDTO> getAll() {
        return testRepository.findAll()
                .stream()
                .map(ConverterDTO::convertToAdviceTestDTO)
                .collect(Collectors.toList());
    }
}