package pl.coderslab.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.coderslab.driver.model.Advice;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {

    @Modifying
    @Query(value = "update advices a set a.name = ?1, a.description = ?2, a.media_file_download_url = ?3 where a.id = ?4", nativeQuery = true)
    void update(String name, String description, String mediaFileDownloadUrl, long id);

    @Query(value = "select rating_id from advices where rating_id = ?", nativeQuery = true)
    long findRatingIdByAdviceId(long id);

    @Query(value = "select * from advices a join rating r on a.rating_id = r.id where created_date_time > (CURDATE() - 7) order by (r.like_count - r.dislike_count) DESC limit 1", nativeQuery = true)
    Advice findFirstByRatingForLast7Days();
}
