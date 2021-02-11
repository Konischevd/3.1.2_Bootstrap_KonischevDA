package web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.UserService;

@RequestMapping("/users")
@Controller
public class UserController {


    private UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("all_users", service.getAllUsers());
        model.addAttribute("new_user", new User());

        return "users/index";
    }

//    @GetMapping("/{id}")
//    public String show(@PathVariable long id, Model model) {
//        model.addAttribute("user", service.getUser(id));
//        return "users/show";
//    }

    @PostMapping
    public String create(@ModelAttribute("new_user") User u) {
        service.addUser(u);
        return "redirect:/users";
    }

    @PostMapping("/putin")
    public String addPutin() {
        service.addUser(new User("Vor", "Putin", "99"));
        return "redirect:/users";
    }

    @PatchMapping("/update")
    public String update(@RequestParam("update_id") long id,
                         @RequestParam("update_fn") String fn,
                         @RequestParam("update_sn") String sn,
                         @RequestParam("update_c") String c) {
        System.out.println(id + " " + fn + " " + sn + " " + c);

        service.alterUser(id, fn, sn, c);
        return "redirect:/users";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("delete_id") Long id) {
        service.deleteUser(id);
        return "redirect:/users";
    }


}
