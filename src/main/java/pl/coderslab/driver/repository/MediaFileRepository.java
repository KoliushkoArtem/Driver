package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.MediaFile;

@Repository
public interface MediaFileRepository extends JpaRepository<MediaFile, Long> {

    @Modifying
    @Query(value = "update media_file f set f.name = ?, f.content_type = ?, f.media_file = ? where f.id = ?", nativeQuery = true)
    void update(String filename, String contentType, byte[] bytes, long id);
}
