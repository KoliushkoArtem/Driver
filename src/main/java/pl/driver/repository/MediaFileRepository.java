package pl.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.driver.model.MediaFile;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {

}