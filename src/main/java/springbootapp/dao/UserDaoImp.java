package springbootapp.dao;

import springbootapp.model.Role;
import springbootapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createUser(String firstName, String lastName, Byte age, String email, String password, String role) {
        User user = new User(firstName, lastName, age, email, password);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoles(new HashSet<>());

        if (role.equals("ROLE_ADMIN")) {
            user.getRoles().add(getRoleByName("ROLE_ADMIN"));
            user.getRoles().add(getRoleByName("ROLE_USER"));
        } else {
            user.getRoles().add(getRoleByName("ROLE_USER"));
        }
        entityManager.persist(user);
    }

    @Override
    @Transactional
    public void editUser(Long id, String firstName, String lastName,
                           Byte age, String email, String password, String role) {
        User user = getUser(id);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        user.setPassword(password);
        user.setPasswordConfirm(password);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().clear();

        if (role.equals("ROLE_ADMIN")) {
            user.getRoles().add(getRoleByName("ROLE_ADMIN"));
            user.getRoles().add(getRoleByName("ROLE_USER"));
        } else {
            user.getRoles().add(getRoleByName("ROLE_USER"));
        }
        entityManager.merge(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        entityManager.remove(entityManager.find(User.class, id));
    }

    @Override
    public User getUser(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> getAllUsers() {
        List<User> listUsers = (List<User>) entityManager.createQuery("SELECT u FROM User u").getResultList();
        return listUsers;
    }

    @Override
    public User getUserByEmail(String email) {
        return (User) entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email")
                .setParameter("email", email).getSingleResult();
    }

    @Override
    public Role getRoleByName(String name) {
        return (Role) entityManager.createQuery("SELECT r FROM Role r WHERE r.name = :name")
                .setParameter("name", name).getSingleResult();
    }
}
