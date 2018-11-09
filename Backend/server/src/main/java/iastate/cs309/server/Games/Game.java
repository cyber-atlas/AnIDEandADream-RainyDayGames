package iastate.cs309.server.Games;

import javax.persistence.*;

@Entity
@Table(name = "games")
public class Game {

    //Must have a primary key otherwise Spring JPA is useless
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Integer getId() {
        return id;
    }

    @Column(name = "name")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }
}

