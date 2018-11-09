package iastate.cs309.server.Roles;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role_types" )
public class RoleType {

    @Id
    private Integer roleid;

    public Integer getRoleid() {
        return roleid;
    }

    private String title;

    public String getTitle() {
        return title;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
