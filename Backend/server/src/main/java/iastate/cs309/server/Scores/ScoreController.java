package iastate.cs309.server.Scores;

import iastate.cs309.server.Users.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/scores")
public class ScoreController {
    @Autowired
    ScoreRepository repo;

    @RequestMapping(method = RequestMethod.GET)
    public List<Score> getAllScores() {
        List<Score> results = repo.findAll();
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, params = "userid")
    public List<Score> getScoresLatestByUserid(@RequestParam Integer userid) {
        List<Score> results = repo.findByUseridOrderByDateDesc(userid);
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, params = "game")
    public List<Score> getScoresBestByGame(@RequestParam Integer game) {
        List<Score> results = repo.findByGameOrderByScoreDesc(game);
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, params = {"userid", "game"})
    public List<Score> getScoresBestByUseridAndGame(@RequestParam Integer userid, @RequestParam Integer game) {
        List<Score> results = repo.findByUseridAndGameOrderByScoreDesc(userid, game);
        return results;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/new", params = {"userid", "game", "score"})
    public void addScore(@RequestParam Integer userid, @RequestParam Integer game, @RequestParam Integer score) {
        Score newScore = new Score(userid, game, score, Timestamp.valueOf(LocalDateTime.now()));
        repo.save(newScore);
    }

    //Todo: let admins delete scores from cheaters
    @RequestMapping(method = RequestMethod.POST, path = "/delete", params = {"userid", "id"})
    public void deleteScore(@RequestParam Integer userid, @RequestParam Integer id){
        Optional<Score> tbdScore = repo.findById(id);
        if (tbdScore.isPresent()){

        }
    }
}
