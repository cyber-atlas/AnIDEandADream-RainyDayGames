package iastate.cs309.server.Users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    List<User> findByUsernameContainsIgnoreCase(@Param("username") String username);

    Optional<User> findDistinctFirstByUsernameIgnoreCase(@Param("username") String username);

    Optional<User> findDistinctFirstByEmailIgnoreCase(@Param("email") String email);

//    @Modifying //PRESERVING AS AN EXAMPLE OF A CUSTOM QUERY. ITS MORE ACCEPTABLE TO MODIFY THE CLASS THEN SAVE
//    @Query(value = "UPDATE Test.users u SET u.username = ?2 WHERE u.id = ?1", nativeQuery = true)
//    void setUsernameById(Integer id, String username);

    void deleteDistinctById(int userid);

    User save(User user);

}
