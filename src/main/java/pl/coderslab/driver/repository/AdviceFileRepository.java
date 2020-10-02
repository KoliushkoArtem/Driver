package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.AdviceFile;

@Repository
public interface AdviceFileRepository extends JpaRepository<AdviceFile, Long> {
}
