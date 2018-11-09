package iastate.cs309.server.Users;

import iastate.cs309.server.Roles.RoleType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users") //http://proj309-vc-04.misc.iastate.edu:8080/users?username=jim&password=jojjojjoj
public class UserController {
    @Autowired
    UserRepository repo;

    @Autowired
    UserService svc;

      /*
_____/\\\\\\\\\\\\__/\\\\\\\\\\\\\\\__/\\\\\\\\\\\\\\\______________/\\\\\\\\\______/\\\\\\\\\\\\\\\________/\\\________/\\\________/\\\__/\\\\\\\\\\\\\\\_____/\\\\\\\\\\\____/\\\\\\\\\\\\\\\_____/\\\\\\\\\\\___
 ___/\\\//////////__\/\\\///////////__\///////\\\/////_____________/\\\///////\\\___\/\\\///////////______/\\\\/\\\\____\/\\\_______\/\\\_\/\\\///////////____/\\\/////////\\\_\///////\\\/////____/\\\/////////\\\_
  __/\\\_____________\/\\\___________________\/\\\_________________\/\\\_____\/\\\___\/\\\_______________/\\\//\////\\\__\/\\\_______\/\\\_\/\\\______________\//\\\______\///________\/\\\________\//\\\______\///__
   _\/\\\____/\\\\\\\_\/\\\\\\\\\\\___________\/\\\_________________\/\\\\\\\\\\\/____\/\\\\\\\\\\\______/\\\______\//\\\_\/\\\_______\/\\\_\/\\\\\\\\\\\_______\////\\\_______________\/\\\_________\////\\\_________
    _\/\\\___\/////\\\_\/\\\///////____________\/\\\_________________\/\\\//////\\\____\/\\\///////______\//\\\______/\\\__\/\\\_______\/\\\_\/\\\///////___________\////\\\____________\/\\\____________\////\\\______
     _\/\\\_______\/\\\_\/\\\___________________\/\\\_________________\/\\\____\//\\\___\/\\\______________\///\\\\/\\\\/___\/\\\_______\/\\\_\/\\\_____________________\////\\\_________\/\\\_______________\////\\\___
      _\/\\\_______\/\\\_\/\\\___________________\/\\\_________________\/\\\_____\//\\\__\/\\\________________\////\\\//_____\//\\\______/\\\__\/\\\______________/\\\______\//\\\________\/\\\________/\\\______\//\\\__
       _\//\\\\\\\\\\\\/__\/\\\\\\\\\\\\\\\_______\/\\\_________________\/\\\______\//\\\_\/\\\\\\\\\\\\\\\_______\///\\\\\\___\///\\\\\\\\\/___\/\\\\\\\\\\\\\\\_\///\\\\\\\\\\\/_________\/\\\_______\///\\\\\\\\\\\/___
        __\////////////____\///////////////________\///__________________\///________\///__\///////////////__________\//////______\/////////_____\///////////////____\///////////___________\///__________\///////////_____
         */

    @RequestMapping(method = RequestMethod.GET)
    public List<User> getAllUsers() {
        List<User> results = repo.findAll();
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, params = "id")
    public Optional<User> getUsersById(@RequestParam Integer id) {
        Optional<User> results = repo.findById(id);
        return results;
    }

    @RequestMapping(method = RequestMethod.GET, params = "username")
    public List<User> getUsersByUsername(@RequestParam String username) {
        List<User> results = repo.findByUsernameContainsIgnoreCase(username);
        return results;
    }

    //Returns the user object if login was correct, otherwise an empty optional is returned
    @RequestMapping(method = RequestMethod.GET, params = {"username", "password"})
    public Optional<User> isUserLoginValid(@RequestParam String username, @RequestParam String password) {
        Optional<User> user = repo.findDistinctFirstByUsernameIgnoreCase(username);
        return passwordCompare(user, password);
    }

    //Returns the user object if login was correct, otherwise an empty optional is returned
    @RequestMapping(method = RequestMethod.GET, params = {"email", "password"})
    public Optional<User> isEmailLoginValid(@RequestParam String email, @RequestParam String password) {
        Optional<User> user = repo.findDistinctFirstByEmailIgnoreCase(email);
        return passwordCompare(user, password);
    }


   /*
__/\\\\\\\\\\\\\_________/\\\\\__________/\\\\\\\\\\\____/\\\\\\\\\\\\\\\______________/\\\\\\\\\______/\\\\\\\\\\\\\\\________/\\\________/\\\________/\\\__/\\\\\\\\\\\\\\\_____/\\\\\\\\\\\____/\\\\\\\\\\\\\\\_____/\\\\\\\\\\\___
 _\/\\\/////////\\\_____/\\\///\\\______/\\\/////////\\\_\///////\\\/////_____________/\\\///////\\\___\/\\\///////////______/\\\\/\\\\____\/\\\_______\/\\\_\/\\\///////////____/\\\/////////\\\_\///////\\\/////____/\\\/////////\\\_
  _\/\\\_______\/\\\___/\\\/__\///\\\___\//\\\______\///________\/\\\_________________\/\\\_____\/\\\___\/\\\_______________/\\\//\////\\\__\/\\\_______\/\\\_\/\\\______________\//\\\______\///________\/\\\________\//\\\______\///__
   _\/\\\\\\\\\\\\\/___/\\\______\//\\\___\////\\\_______________\/\\\_________________\/\\\\\\\\\\\/____\/\\\\\\\\\\\______/\\\______\//\\\_\/\\\_______\/\\\_\/\\\\\\\\\\\_______\////\\\_______________\/\\\_________\////\\\_________
    _\/\\\/////////____\/\\\_______\/\\\______\////\\\____________\/\\\_________________\/\\\//////\\\____\/\\\///////______\//\\\______/\\\__\/\\\_______\/\\\_\/\\\///////___________\////\\\____________\/\\\____________\////\\\______
     _\/\\\_____________\//\\\______/\\\__________\////\\\_________\/\\\_________________\/\\\____\//\\\___\/\\\______________\///\\\\/\\\\/___\/\\\_______\/\\\_\/\\\_____________________\////\\\_________\/\\\_______________\////\\\___
      _\/\\\______________\///\\\__/\\\_____/\\\______\//\\\________\/\\\_________________\/\\\_____\//\\\__\/\\\________________\////\\\//_____\//\\\______/\\\__\/\\\______________/\\\______\//\\\________\/\\\________/\\\______\//\\\__
       _\/\\\________________\///\\\\\/_____\///\\\\\\\\\\\/_________\/\\\_________________\/\\\______\//\\\_\/\\\\\\\\\\\\\\\_______\///\\\\\\___\///\\\\\\\\\/___\/\\\\\\\\\\\\\\\_\///\\\\\\\\\\\/_________\/\\\_______\///\\\\\\\\\\\/___
        _\///___________________\/////_________\///////////___________\///__________________\///________\///__\///////////////__________\//////______\/////////_____\///////////////____\///////////___________\///__________\///////////_____
        */

    //Used to remove offending username of an inappropriate variety. Can be used by any user on themselves or by any admin on any other user.
    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "/edit", params = {"id", "old_username", "new_username"})
    public void updateUsernameById(@RequestParam Integer id, @RequestParam String old_username, @RequestParam String new_username) {
        Optional<User> requestingUser = repo.findById(id);
        Optional<User> toBeModifiedUser = repo.findDistinctFirstByUsernameIgnoreCase(old_username);
        if (requestingUser.isPresent()
                && toBeModifiedUser.isPresent()) {
            if (requestingUser.get().equals(toBeModifiedUser.get()) || svc.isAdminRole(requestingUser.get())) {
                toBeModifiedUser.get().setUsername(new_username);
                repo.save(toBeModifiedUser.get());
            }
        }
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, path = "/new", params = {"username", "password", "email"})
    public ResponseEntity createUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        User user = new User(username, password, email);
        if (svc.addEntity(user).isPresent())
            return new ResponseEntity(HttpStatus.CREATED);
        else
            return new ResponseEntity(HttpStatus.CONFLICT);
    }

 /*
/\\\________/\\\__/\\\\\\\\\\\\\\\__/\\\______________/\\\\\\\\\\\\\____/\\\\\\\\\\\\\\\____/\\\\\\\\\_________/\\\\\\\\\\\___
_\/\\\_______\/\\\_\/\\\///////////__\/\\\_____________\/\\\/////////\\\_\/\\\///////////___/\\\///////\\\_____/\\\/////////\\\_
 _\/\\\_______\/\\\_\/\\\_____________\/\\\_____________\/\\\_______\/\\\_\/\\\_____________\/\\\_____\/\\\____\//\\\______\///__
  _\/\\\\\\\\\\\\\\\_\/\\\\\\\\\\\_____\/\\\_____________\/\\\\\\\\\\\\\/__\/\\\\\\\\\\\_____\/\\\\\\\\\\\/______\////\\\_________
   _\/\\\/////////\\\_\/\\\///////______\/\\\_____________\/\\\/////////____\/\\\///////______\/\\\//////\\\_________\////\\\______
    _\/\\\_______\/\\\_\/\\\_____________\/\\\_____________\/\\\_____________\/\\\_____________\/\\\____\//\\\___________\////\\\___
     _\/\\\_______\/\\\_\/\\\_____________\/\\\_____________\/\\\_____________\/\\\_____________\/\\\_____\//\\\___/\\\______\//\\\__
      _\/\\\_______\/\\\_\/\\\\\\\\\\\\\\\_\/\\\\\\\\\\\\\\\_\/\\\_____________\/\\\\\\\\\\\\\\\_\/\\\______\//\\\_\///\\\\\\\\\\\/___
       _\///________\///__\///////////////__\///////////////__\///______________\///////////////__\///________\///____\///////////_____
       */

    public Optional<User> passwordCompare(Optional<User> user, String password) {
        if (user.isPresent()) {
            if (user.get().getPassword().length() < 10) //legacy password support
                if (password.equals(user.get().getPassword()))
                    return user;
            if (svc.isPassword(password, user.get()))
                return user;
        }
        return Optional.empty();
    }
}
