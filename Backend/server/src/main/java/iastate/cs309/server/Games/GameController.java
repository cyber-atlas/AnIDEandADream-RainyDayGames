package iastate.cs309.server.Games;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    GameRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List<Game> getAllGames() {
        List<Game> results = repo.findAll();
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public Game getGameById(@RequestParam Integer id){
        Optional<Game> results = repo.findById(id);
        if (results.isPresent()){
            return results.get();
        }
        return new Game();
    }



}
