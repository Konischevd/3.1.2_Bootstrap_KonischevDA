package web.service;

import web.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getUser(long id);

    void addUser(User user);

    void deleteUser(long id);

    void alterUser(long id, String fn, String  sn, String c);
}
