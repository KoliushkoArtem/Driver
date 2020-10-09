package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.AdviceRating;

@Repository
public interface AdviceRatingRepository extends JpaRepository<AdviceRating, Long> {

    @Modifying
    @Query(value = "update rating r set r.like_count = ?1, r.dislike_count = ?2 where r.id = ?3", nativeQuery = true)
    void update(int likeCount, int dislikeCount, long ratingId);
}
