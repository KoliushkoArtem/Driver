package pl.coderslab.driver.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.coderslab.driver.converter.TestDtoConverter;
import pl.coderslab.driver.dto.AdviceTestDto;
import pl.coderslab.driver.exceptions.AdviseTestNotFoundException;
import pl.coderslab.driver.model.AdviceTest;
import pl.coderslab.driver.model.AnswerVariant;
import pl.coderslab.driver.model.Question;
import pl.coderslab.driver.repository.AdviceTestRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdviceTestService {

    private final AdviceTestRepository testRepository;

    public AdviceTestService(AdviceTestRepository testRepository) {
        this.testRepository = testRepository;
    }

    public List<AdviceTestDto> getAll() {
        List<AdviceTestDto> result = testRepository.findAll()
                .stream()
                .map(TestDtoConverter::convertToAdviceTestDto)
                .collect(Collectors.toList());

        log.info("IN adviceTestService getAll - {} tests was successfully loaded", result.size());

        return result;
    }

    public AdviceTestDto findByID(long testId) throws AdviseTestNotFoundException {
        AdviceTest testFromDb = testRepository.findById(testId)
                .orElseThrow(() -> new AdviseTestNotFoundException(testId));

        log.info("IN adviceTestService findById - test {} was successfully loaded", testFromDb);

        return TestDtoConverter.convertToAdviceTestDto(testFromDb);
    }

    public void delete(long testId) {
        testRepository.deleteById(testId);

        log.info("IN adviceTestService delete - tests with Id: {} was deleted", testId);
    }

    public AdviceTestDto save(AdviceTestDto testDto) {
        AdviceTest adviceTestCandidate = TestDtoConverter.convertToAdviceTest(testDto);
        AdviceTest savedTest = testRepository.save(adviceTestCandidate);

        log.info("IN adviceTestService save - test {} was successfully saved", savedTest);

        return TestDtoConverter.convertToAdviceTestDto(savedTest);
    }


    public AdviceTestDto update(AdviceTestDto testDto) {
        AdviceTest testToUpdate = testRepository.findById(testDto.getId())
                .orElseThrow(() -> new AdviseTestNotFoundException(testDto.getId()));
        AdviceTest testCandidate = TestDtoConverter.convertToAdviceTest(testDto);
        testToUpdate.setName(testCandidate.getName());
        testToUpdate.setAdviceId(testDto.getAdviceId());
        testToUpdate.setQuestions(questionsUpdate(testToUpdate.getQuestions(), testCandidate.getQuestions()));

        AdviceTest updatedTest = testRepository.save(testToUpdate);

        log.info("IN adviceTestService update - test {} was successfully updated", updatedTest);

        return TestDtoConverter.convertToAdviceTestDto(updatedTest);
    }

    private Set<Question> questionsUpdate(Set<Question> questionsToUpdate, Set<Question> questionsCandidates) {
        for (Question questionCandidate : questionsCandidates) {
            boolean isExist = false;
            for (Question questionToUpdate : questionsToUpdate) {
                if (questionCandidate.getId() != null && questionCandidate.getId().equals(questionToUpdate.getId())) {
                    questionToUpdate.setQuestion(questionCandidate.getQuestion());
                    questionToUpdate.setAnswers(answersUpdate(questionToUpdate.getAnswers(), questionCandidate.getAnswers()));
                    isExist = true;
                }
            }

            if (!isExist) {
                questionsToUpdate.add(questionCandidate);
            }
        }
        return questionsToUpdate;
    }

    private Set<AnswerVariant> answersUpdate(Set<AnswerVariant> answersToUpdate, Set<AnswerVariant> answersCandidates) {
        for (AnswerVariant answerCandidate : answersCandidates) {
            boolean isExist = false;
            for (AnswerVariant answerToUpdate : answersToUpdate) {
                if (answerCandidate.getId() != null && answerToUpdate.getId().equals(answerCandidate.getId())) {
                    answerToUpdate.setAnswer(answerCandidate.getAnswer());
                    answerToUpdate.setVariantCorrect(answerCandidate.isVariantCorrect());
                    if (answerCandidate.getMediaFileId() != null) {
                        answerToUpdate.setMediaFileId(answerCandidate.getMediaFileId());
                    }
                    isExist = true;
                }
            }

            if (!isExist) {
                answersToUpdate.add(answerCandidate);
            }
        }
        return answersToUpdate;
    }
}