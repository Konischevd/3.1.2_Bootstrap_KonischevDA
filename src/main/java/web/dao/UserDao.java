package web.dao;

import web.models.Role;
import web.models.User;

import java.util.List;

public interface UserDao {

    Role getRoleByName(String name);

    List<User> getAllUsers();

    User getUser(long id);

    User getUserByLogin(String login);

    void addUser(User user);

    void deleteUser(Long id);

    void alterUser(long id,
                   String log,
                   String pas,
                   String rol,
                   String fn,
                   String sn,
                   String c);

}
