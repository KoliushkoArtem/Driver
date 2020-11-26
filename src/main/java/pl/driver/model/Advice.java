package pl.driver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "advice")
public class Advice extends Auditable {

    @NotBlank(message = "Cannot be blank")
    private String name;

    @NotBlank(message = "Cannot be blank")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "rating_id")
    private AdviceRating rating;

    private Long testId;

    private Long mediaFileId;
}