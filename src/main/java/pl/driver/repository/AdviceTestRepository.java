package pl.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.model.AdviceTest;

@Repository
public interface AdviceTestRepository extends JpaRepository<AdviceTest, Long> {

}
