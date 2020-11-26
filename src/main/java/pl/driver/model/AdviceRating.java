package pl.driver.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "rating")
public class AdviceRating extends Auditable {

    private Integer likeCount;

    private Integer dislikeCount;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "rating")
    private Advice advice;
}