package iastate.cs309.server.Users;

import iastate.cs309.server.Roles.RoleType;
import iastate.cs309.server.Roles.RoleTypeRepository;
import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * service for controlling user data.
 * Handles password encryption
 */
@Service
public class UserService {
    @Autowired
    UserRepository repo;

    @Autowired
    RoleTypeRepository roleRepo;

    PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repo) {
        this.repo = repo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public Optional<User> addEntity(User user) {
        if (repo.findDistinctFirstByUsernameIgnoreCase(user.getUsername()).isPresent() ||
                repo.findDistinctFirstByEmailIgnoreCase(user.getEmail()).isPresent())
            return Optional.empty();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return Optional.of(repo.save(user));
    }

    /**
     * does the users password match
     * @param password  a string
     * @param user compare password field
     * @return true when the passwords match
     */
    public boolean isPassword(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * updates the password and encrypts it aswell
     * @param oldPassword the password to confirms you are the owner
     * @param newPassword the password to change to
     * @param user the user this change is for
     * @return true if password validation passed
     */
    public boolean updatePassword(String oldPassword, String newPassword, User user) {
        if (passwordEncoder.matches(oldPassword, newPassword)) {
            return false;
        } else {
            user.setPassword(passwordEncoder.encode(newPassword));
            repo.save(user);
            return true;
        }
    }

    public Optional<RoleType> getRoleType(Integer role_id){
        return roleRepo.findByRoleid(role_id);
    }

    /**
     * determine if given user is admin
     * @param user check if this one is an admin
     * @return true if user is admin
     */
    public static boolean isAdminRole(User user) {
        for (RoleType rt :
                user.getRoles()) {
            if (rt.getTitle().toLowerCase().contains("admin")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param user the user to check role of
     * @return true when given user is admin
     */
    public static boolean isFreeUser(User user) {
        for (RoleType rt :
                user.getRoles()) {
            if (rt.getTitle().toLowerCase().contains("free")) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param user the user to check
     * @return true when user ia premium
     */
    public static boolean isPremiumUser(User user) {
        for (RoleType rt :
                user.getRoles()) {
            if (rt.getTitle().toLowerCase().contains("premium")) {
                return true;
            }
        }
        return false;
    }


}
