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

    private int like;

    private int dislike;

    private int rating;

    public void setRating() {
        this.rating = like - dislike;
    }
}

