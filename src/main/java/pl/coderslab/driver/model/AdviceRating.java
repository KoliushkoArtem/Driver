package pl.coderslab.driver.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "rating")
public class AdviceRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int likeCount;

    private int dislikeCount;
}

