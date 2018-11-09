package iastate.cs309.server.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import iastate.cs309.server.Roles.Role;
import iastate.cs309.server.Roles.RoleType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
    @NotEmpty
    @Column(name = "username")
    private String username;
    @NotEmpty
    @Column(name = "password")
    private String password;
    @NotEmpty
    @Column(name = "email")
    private String email;

    @JoinTable
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<RoleType> roles;

    public User(){
    }

    public User(String name, String password, String email) {
        this.username = name;
        this.password = password;
        this.email = email;
    }

    public Integer getId()
    {
        return id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email=email;
    }

    public List<RoleType> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleType> roles) {
        this.roles = roles;
    }
}
