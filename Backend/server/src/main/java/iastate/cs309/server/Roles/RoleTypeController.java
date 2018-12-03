package iastate.cs309.server.Roles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * provides rest api access to role descriptions
 */
@RestController
@RequestMapping("/roletypes")
public class RoleTypeController {
    @Autowired
    RoleTypeRepository repo;

    /**
     *
     * @return all the role types
     */
    @RequestMapping(method = RequestMethod.GET)
    public List<RoleType> getAllRoleTypes() {
        List<RoleType> results = repo.findAll();
        return results;
    }

    /**
     *
     * @param id the id to match
     * @return all the role types matching id
     */
    @RequestMapping(method = RequestMethod.GET, params = "id")
    public Optional<RoleType> getRoleTypesById(@RequestParam Integer id) {
        Optional<RoleType> results = repo.findById(id);
        if (results.isPresent())
            return results;
        return Optional.empty();
    }
}
