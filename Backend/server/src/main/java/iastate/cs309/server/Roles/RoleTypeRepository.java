package iastate.cs309.server.Roles;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleTypeRepository extends JpaRepository<RoleType, Integer> {
    Optional<RoleType> findByRoleid(Integer roleid);

    RoleType save(RoleType roletype);
}
