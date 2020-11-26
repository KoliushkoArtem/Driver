package pl.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.model.AdviceRating;

@Repository
public interface AdviceRatingRepository extends JpaRepository<AdviceRating, Long> {

}