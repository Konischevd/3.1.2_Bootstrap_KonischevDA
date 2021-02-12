package web.dao;

import org.springframework.stereotype.Repository;
import web.models.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDaoImpl_JPA implements UserDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultList();
    }

    @Override
    public User getUser(long id) {
        return entityManager.find(User.class, id);
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
    public void alterUser(long id, String fn, String  sn, String c) {
        User u = entityManager.getReference(User.class, id);
        System.out.println("id = " + id + "\nfn = " + fn + "\nsn = " + sn + "\nc = " + c);
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
