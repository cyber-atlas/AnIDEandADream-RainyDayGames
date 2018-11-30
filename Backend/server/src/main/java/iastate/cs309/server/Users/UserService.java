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

    public boolean isPassword(String password, User user) {
        return passwordEncoder.matches(password, user.getPassword());
    }

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

    public static boolean isAdminRole(User user) {
        for (RoleType rt :
                user.getRoles()) {
            if (rt.getTitle().toLowerCase().contains("admin")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isFreeUser(User user) {
        for (RoleType rt :
                user.getRoles()) {
            if (rt.getTitle().toLowerCase().contains("free")) {
                return true;
            }
        }
        return false;
    }

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
