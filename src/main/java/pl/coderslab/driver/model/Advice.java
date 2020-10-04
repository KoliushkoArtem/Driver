package pl.coderslab.driver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "advices")
public class Advice extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank(message = "Cannot be blank")
    private String name;

    @NotBlank(message = "Advice")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private MediaFile file;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private AdviceRating rating;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AdviceTest test;
}
