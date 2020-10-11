package pl.coderslab.driver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answers")
public class AnswerVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String answer;

    private Long mediaFileId;

    private boolean isVariantCorrect;
}
