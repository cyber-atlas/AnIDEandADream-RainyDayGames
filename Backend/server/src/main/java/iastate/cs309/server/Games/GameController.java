package iastate.cs309.server.Games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * provide api access to game names and descriptions
 */
@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameRepository repo;

    /**
     *
     * @return all the games
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<Game> getAllGames() {
        List<Game> results = repo.findAll();
        return results;
    }

    /**
     *
     * @param id the game id to match
     * @return a game matching the given ID
     */
    @RequestMapping(method = RequestMethod.GET, params = "id")
    public Game getGameById(@RequestParam Integer id){
        Optional<Game> results = repo.findById(id);
        if (results.isPresent()){
            return results.get();
        }
        return new Game();
    }



}
