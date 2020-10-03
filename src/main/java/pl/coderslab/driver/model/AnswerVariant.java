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

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MediaFile image;

    @ManyToOne(fetch = FetchType.EAGER)
    private Question question;
}
