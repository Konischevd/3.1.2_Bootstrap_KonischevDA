package web.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.dao.UserDao;
import web.dao.UserDaoImpl_JPA;
import web.models.User;
import web.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;

//@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl service;

    @GetMapping("/users")
    public String index(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        model.addAttribute("user", service.getUser(id));
        return "show";
    }



}
