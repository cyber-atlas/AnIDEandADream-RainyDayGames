package iastate.cs309.server.Roles;

import iastate.cs309.server.Users.User;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "roles")
public class Role implements Serializable {

    //Must have a primary key otherwise Spring JPA is useless
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "roleid", nullable = false)
    private Integer roleid;

    @Column(name = "userid", nullable = false)
    private Integer userid;

    //@ManyToMany(mappedBy = "roles")
    //private Set<User> users;

    public Role() {

    }

    public Role(Integer id, Integer roleid, Integer userid) {
        this.id = id;
        this.roleid = roleid;
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public Integer getRoleid() { return roleid; }

    public void setRoleid(Integer roleid) { this.roleid = roleid; }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

}
