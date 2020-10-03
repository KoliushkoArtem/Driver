package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.AnswerVariant;

@Repository
public interface AnswerVariantRepository extends JpaRepository<AnswerVariant, Long> {
}
