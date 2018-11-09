package iastate.cs309.server.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    RoleRepository repo;

    @RequestMapping(method = RequestMethod.GET, params = "userid")
    public List<Role> getRolesByUserid(@RequestParam Integer userid) {
        List<Role> results = repo.findByUserid(userid);
        return results;
    }
}
