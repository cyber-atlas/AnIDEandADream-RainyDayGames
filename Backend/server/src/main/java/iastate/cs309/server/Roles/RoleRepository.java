package iastate.cs309.server.Roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
    Boolean findByRoleidAndUserid(@Param("roleid") Integer roleid, @Param("userid") Integer userid);

    List<Role> findByUserid(@Param("userid") Integer userid);

    Role save(Role score);
}
