package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.MediaFile;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {

}
