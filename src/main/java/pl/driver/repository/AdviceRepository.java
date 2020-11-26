package pl.driver.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.driver.model.Advice;

@Repository
public interface AdviceRepository extends JpaRepository<Advice, Long> {

    @Query(value = "select * from advice a join rating r on a.rating_id = r.id where a.created_date_time > (CURDATE() - 7) order by (r.like_count - r.dislike_count) DESC limit 1", nativeQuery = true)
    Advice findFirstByRatingForLast7Days();
}