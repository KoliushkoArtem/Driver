package pl.coderslab.driver.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Data
@ApiModel(description = "Advice for improving driving skills")
public class Advice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(notes = "Unique advice identificator")
    private long id;

    @NotBlank(message = "Cannot be blank")
    @ApiModelProperty(notes = "Advice name")
    private String name;

    @NotBlank(message = "Advice")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "Image or Video for advise")
    private AdviceFile file;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @ApiModelProperty(notes = "Advice rating with likes and dislikes")
    private AdviceRating rating;

    @CreationTimestamp
    private LocalDateTime creationDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;


}
