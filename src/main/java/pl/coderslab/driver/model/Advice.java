package pl.coderslab.driver.model;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "advices")
public class Advice {

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

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;
}
