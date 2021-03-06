package iastate.cs309.server.Games;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * the game repository holds game names and descriptions
 */
@Repository
public interface GameRepository extends JpaRepository<Game, Integer> {

    Game save(Game game);
}
