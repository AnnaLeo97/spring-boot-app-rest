package springbootapp.service;

import springbootapp.model.Role;
import springbootapp.model.User;

import java.util.List;

public interface UserService {
    void createUser(String firstName, String lastName, Byte age, String email, String password, String role);

    void editUser(User user, String role);

    void updateUser(Long id, String firstName, String lastName, Byte age, String email, String password, String role);

    void deleteUser(Long id);

    User getUser(Long id);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    Role getRoleByName(String name);
}
