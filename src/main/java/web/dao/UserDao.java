package web.dao;

import web.models.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUser(long id);

    void addUser(User user);

    void deleteUser(long id);

    void alterUser(long id, String fn, String  sn, String c);
}
