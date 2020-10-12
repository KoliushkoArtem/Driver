package pl.coderslab.driver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "answers")
public class AnswerVariant extends Auditable {

    private String answer;

    private Long mediaFileId;

    private boolean isVariantCorrect;
}
