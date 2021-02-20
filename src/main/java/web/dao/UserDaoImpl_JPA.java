package web.dao;

import org.springframework.stereotype.Repository;
import web.models.Role;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class UserDaoImpl_JPA implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;






    @Override
    public Role getRoleByName(String name) {
        try {entityManager
                    .createQuery("select r from Role r where r.role = :name", Role.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            Role r = new Role();
            r.setRole(name);
            entityManager.persist(r);
        }

        return entityManager
                .createQuery("select r from Role r where r.role = :name", Role.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User getUserByLogin(String login) {
        return entityManager.createQuery("select u from User u where u.login = :login", User.class)
                .setParameter("login", login).getSingleResult();
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
        entityManager.createQuery("delete from User u where u.id = :id")
                .setParameter("id", id).executeUpdate();
    }

    @Override
    public void alterUser(long id,
                          String log,
                          String pas,
                          String rol,
                          String fn,
                          String sn,
                          String c) {

        User u = entityManager.getReference(User.class, id);

        if (log != null && log.length() > 0) {
            u.setLogin(log);
        }

        if (pas != null && pas.length() > 0) {
            u.setPassword(pas);
        }


        if (rol != null && rol.length() > 0) {
            rol = rol.toLowerCase();
            Set<Role> newRoles = new HashSet<>();

            if ( rol.contains("admin") ) {
                Role role = getRoleByName("ROLE_ADMIN");
                newRoles.add(role);
            }

            if ( rol.contains("user") ) {
                Role role = getRoleByName("ROLE_USER");
                newRoles.add(role);
            }
            u.setRoles(newRoles);
        }


        if (fn != null && fn.length() > 0) {
            u.setFirstName(fn);
        }

        if (sn != null && sn.length() > 0) {
            u.setSecondName(sn);
        }

        if (c != null && c.length() > 0) {
            u.setCellphone(c);
        }
    }

}
