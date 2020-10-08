package pl.coderslab.driver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "answers")
public class AnswerVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String answer;

    private String mediaFileDownloadUrl;

    private boolean isVariantCorrect;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;
}
