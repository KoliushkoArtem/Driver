package pl.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.model.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}