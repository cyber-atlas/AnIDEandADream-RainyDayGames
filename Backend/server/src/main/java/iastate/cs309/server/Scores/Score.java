package iastate.cs309.server.Scores;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Table(name = "scores")
public class Score {

    //Must have a primary key otherwise Spring JPA is useless
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @Column(name = "userid", nullable = false)
    private Integer userid;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    @Column(name = "game", nullable = false)
    private Integer game;

    public Integer getGame() {
        return game;
    }

    public void setGame(Integer game) {
        this.game = game;
    }

    @Column(name = "score", nullable = false)
    private Integer score;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score){
        this.score = score;
    }

    @Column(name = "date", nullable = false)
    private Timestamp date;

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Score() {

    }

    public Score(Integer userid, Integer game, Integer score, Timestamp date) {
        this.userid = userid;
        this.game = game;
        this.score = score;
        this.date = date;
    }

}
