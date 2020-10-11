package pl.coderslab.driver.service;

import org.springframework.stereotype.Service;
import pl.coderslab.driver.converter.TestDtoConverter;
import pl.coderslab.driver.dto.AdviceTestDto;
import pl.coderslab.driver.exceptions.AdviseTestNotFoundException;
import pl.coderslab.driver.model.AdviceTest;
import pl.coderslab.driver.model.AnswerVariant;
import pl.coderslab.driver.model.Question;
import pl.coderslab.driver.repository.AdviceTestRepository;
import pl.coderslab.driver.repository.AnswerVariantRepository;
import pl.coderslab.driver.repository.QuestionRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdviceTestService {

    private final AdviceTestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerVariantRepository answerRepository;

    public AdviceTestService(AdviceTestRepository testRepository, QuestionRepository questionRepository, AnswerVariantRepository answerRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public List<AdviceTestDto> getAll() {
        return testRepository.findAll()
                .stream()
                .map(TestDtoConverter::convertToAdviceTestDTO)
                .collect(Collectors.toList());
    }

    public AdviceTestDto findByID(long testId) throws AdviseTestNotFoundException {
        AdviceTest testFromDb = testRepository.findById(testId)
                .orElseThrow(() -> new AdviseTestNotFoundException(testId));
        return TestDtoConverter.convertToAdviceTestDTO(testFromDb);
    }

    public void delete(long testId) {
        testRepository.deleteById(testId);
    }

    public AdviceTestDto save(AdviceTestDto testDto) {
        AdviceTest adviceTestCandidate = TestDtoConverter.convertToAdviceTest(testDto);
        AdviceTest savedTest = testRepository.save(adviceTestCandidate);

        return TestDtoConverter.convertToAdviceTestDTO(savedTest);
    }


    public AdviceTestDto update(AdviceTestDto testDto) {
        AdviceTest testToUpdate = testRepository.findById(testDto.getId())
                .orElseThrow(() -> new AdviseTestNotFoundException(testDto.getId()));
        AdviceTest testCandidate = TestDtoConverter.convertToAdviceTest(testDto);
        testToUpdate.setName(testCandidate.getName());
        testToUpdate.setAdviceId(testDto.getAdviceId());
        testToUpdate.setQuestions(questionsUpdate(testToUpdate.getQuestions(), testCandidate.getQuestions()));

        AdviceTest updatedTest = testRepository.save(testToUpdate);

        return TestDtoConverter.convertToAdviceTestDTO(updatedTest);
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