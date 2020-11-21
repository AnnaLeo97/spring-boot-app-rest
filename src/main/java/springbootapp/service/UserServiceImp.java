package springbootapp.service;

import springbootapp.model.Role;
import springbootapp.model.User;
import springbootapp.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void createUser(String firstName, String lastName, Byte age, String email, String password, String role) {
        userDao.createUser(firstName, lastName, age, email, password, role);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public void editUser(Long id, String firstName, String lastName, Byte age, String email, String password, String role) {
        userDao.editUser(id, firstName, lastName, age, email, password, role);
    }

    @Override
    public User getUser(Long id) {
        return userDao.getUser(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public Role getRoleByName(String name) {
        return userDao.getRoleByName(name);
    }
}
