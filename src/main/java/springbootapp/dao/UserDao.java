package springbootapp.dao;

import springbootapp.model.Role;
import springbootapp.model.User;

import java.util.List;

public interface UserDao {
    void createUser(String firstName, String lastName, Byte age, String email, String password, String role);

    void editUser(Long id, String firstName, String lastName, Byte age, String email, String password, String role);

    void deleteUser(Long id);

    User getUser(Long id);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    Role getRoleByName(String name);
}