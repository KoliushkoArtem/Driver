package pl.coderslab.driver.utils;

import pl.coderslab.driver.dto.*;
import pl.coderslab.driver.model.*;

import java.util.stream.Collectors;

public class ConverterDTO {


    public static AdviceDTO convertToAdviceDTO(Advice advice) {
        AdviceDTO convertedAdviceDto = new AdviceDTO();
        convertedAdviceDto.setId(advice.getId());
        convertedAdviceDto.setAdviceName(advice.getName());
        convertedAdviceDto.setAdviceDescription(advice.getDescription());
        convertedAdviceDto.setMediaFileDownloadUrl(advice.getMediaFileDownloadUrl());
        convertedAdviceDto.setLikeCount(advice.getRating().getLikeCount());
        convertedAdviceDto.setDislikeCount(advice.getRating().getDislikeCount());
        convertedAdviceDto.setTestId(advice.getTest().getId());

        return convertedAdviceDto;
    }

    public static Advice convertToAdvice(AdviceDTO adviceDTO) {
        Advice convertedAdvice = new Advice();
        convertedAdvice.setId(adviceDTO.getId());
        convertedAdvice.setName(adviceDTO.getAdviceName());
        convertedAdvice.setDescription(adviceDTO.getAdviceDescription());
        convertedAdvice.setMediaFileDownloadUrl(adviceDTO.getMediaFileDownloadUrl());
        convertedAdvice.getRating().setLikeCount(adviceDTO.getLikeCount());
        convertedAdvice.getRating().setDislikeCount(adviceDTO.getDislikeCount());
        convertedAdvice.getTest().setId(adviceDTO.getTestId());
        return convertedAdvice;
    }

    public static AdviceTestDTO convertToAdviceTestDTO(AdviceTest adviceTest) {
        AdviceTestDTO adviceTestDto = new AdviceTestDTO();
        adviceTestDto.setId(adviceTest.getId());
        adviceTestDto.setQuestions(adviceTest.getQuestions()
                .stream()
                .map(ConverterDTO::convertToQuestionDTO)
                .collect(Collectors.toSet()));

        return adviceTestDto;
    }

    public static AdviceTest convertToAdviceTest(AdviceTestDTO adviceTestDto) {
        AdviceTest adviceTest = new AdviceTest();
        adviceTest.setId(adviceTestDto.getId());
        adviceTest.setQuestions(adviceTestDto.getQuestions()
                .stream()
                .map(ConverterDTO::convertToQuestion)
                .collect(Collectors.toSet()));

        return adviceTest;
    }

    public static MediaFileDTO convertToMediaFileDTO(MediaFile mediaFile) {
        MediaFileDTO convertedMediaFileDto = new MediaFileDTO();
        convertedMediaFileDto.setName(mediaFile.getName());
        convertedMediaFileDto.setContentType(mediaFile.getContentType());
        convertedMediaFileDto.setMediaFile(mediaFile.getMediaFile());

        return convertedMediaFileDto;
    }

    public static AnswerVariantDTO convertToAnswerVariantDTO(AnswerVariant answerVariant) {
        AnswerVariantDTO convertedAnswerVariantDTO = new AnswerVariantDTO();
        convertedAnswerVariantDTO.setAnswer(answerVariant.getAnswer());
        convertedAnswerVariantDTO.setMediaFileDownloadUrl(answerVariant.getMediaFileDownloadUrl());
        convertedAnswerVariantDTO.setVariantCorrect(answerVariant.isVariantCorrect());

        return convertedAnswerVariantDTO;
    }

    public static AnswerVariant convertToAnswerVariant(AnswerVariantDTO answerVariantDto) {
        AnswerVariant convertedAnswerVariant = new AnswerVariant();
        convertedAnswerVariant.setAnswer(answerVariantDto.getAnswer());
        convertedAnswerVariant.setMediaFileDownloadUrl(answerVariantDto.getMediaFileDownloadUrl());
        convertedAnswerVariant.setVariantCorrect(answerVariantDto.isVariantCorrect());

        return convertedAnswerVariant;
    }

    public static QuestionDTO convertToQuestionDTO(Question question) {
        QuestionDTO questionDto = new QuestionDTO();
        questionDto.setQuestion(question.getQuestion());
        questionDto.setAnswers(question.getAnswers()
                .stream()
                .map(ConverterDTO::convertToAnswerVariantDTO)
                .collect(Collectors.toSet()));

        return questionDto;
    }

    public static Question convertToQuestion(QuestionDTO questionDto) {
        Question question = new Question();
        question.setQuestion(questionDto.getQuestion());
        question.setAnswers(questionDto.getAnswers()
                .stream()
                .map(ConverterDTO::convertToAnswerVariant)
                .collect(Collectors.toSet()));

        return question;
    }
}
