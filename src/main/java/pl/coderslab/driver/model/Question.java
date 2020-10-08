package pl.coderslab.driver.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Data
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "question")
    @Size(max = 3)
    private Set<AnswerVariant> answers;

    @ManyToOne(fetch = FetchType.LAZY)
    private AdviceTest test;
}
