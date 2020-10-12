package pl.coderslab.driver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "question")
public class Question extends Auditable{

    private String question;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<AnswerVariant> answers;

}
