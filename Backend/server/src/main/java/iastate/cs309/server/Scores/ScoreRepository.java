package iastate.cs309.server.Scores;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

/**
 * this repository provides functions to call the database and return scores based on input criteria
 */
@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

    List<Score> findByUseridOrderByDateDesc(@Param("userid") Integer userid);

    List<Score> findByUseridAndGameOrderByScoreDesc(@Param("userid") Integer userid, @Param("game") Integer game);

    List<Score> findByGameOrderByScoreDesc(@Param("game") Integer game);

    Score save(Score score);
}
