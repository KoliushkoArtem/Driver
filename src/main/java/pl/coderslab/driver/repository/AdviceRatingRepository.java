package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.AdviceRating;

@Repository
public interface AdviceRatingRepository extends JpaRepository<AdviceRating, Long> {
}
