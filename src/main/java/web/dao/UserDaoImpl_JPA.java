package web.dao;

import org.springframework.beans.factory.annotation.Autowired;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl_JPA implements UserDao {

    @Autowired
    private EntityManagerFactory factory;

    @Override
    public List<User> getAllUsers() {
        return factory.createEntityManager().createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(long id) {
        return factory.createEntityManager().find(User.class, id);
    }

    @Override
    public void addUser(User user) {
        factory.createEntityManager().persist(user);
    }

    @Override
    public void deleteUser(long id) {
        factory.createEntityManager().createQuery("delete from User u where u.id = :id", User.class)
                .setParameter("id", id);
    }

    @Override
    public void alterUser(long id, String fn, String  sn, String c) {
        if (id != 0) {
            User u = factory.createEntityManager().getReference(User.class, id);

            if (fn != null) {
                u.setFirstName(fn);
            }

            if (sn != null) {
                u.setSecondName(sn);
            }

            if (c != null) {
                u.setCellphone(c);
            }
        }
    }

}
