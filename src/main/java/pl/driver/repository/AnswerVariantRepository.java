package pl.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.model.AnswerVariant;

@Repository
public interface AnswerVariantRepository extends JpaRepository<AnswerVariant, Long> {

}