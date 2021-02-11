package web.dao;

import org.springframework.stereotype.Repository;
import web.models.User;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@Repository
public class UserDaoImpl_JPA implements UserDao {

    private final EntityManagerFactory factory;

    public UserDaoImpl_JPA(EntityManagerFactory factory) {
        this.factory = factory;
    }

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
    public void deleteUser(Long id) {
        factory.createEntityManager().createQuery("delete from User u where u.id = :id")
                .setParameter("id", id);
    }

    @Override
    public void alterUser(long id, String fn, String  sn, String c) {
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
