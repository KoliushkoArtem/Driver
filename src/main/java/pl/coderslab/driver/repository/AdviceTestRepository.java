package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.AdviceTest;

@Repository
public interface AdviceTestRepository extends JpaRepository<AdviceTest, Long> {

}
