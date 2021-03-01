package web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.models.User;
import web.service.UserService;

import java.security.Principal;

@Controller
public class UserController {

    private UserService service;
    public UserController(UserService service) {
        this.service = service;
    }


    @GetMapping
    public String index(Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        } else if (principal.getName().equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        } return "redirect:/user";
    }

    @GetMapping("/user")
    public String user(Model model, Principal principal) {
        User user = service.getUserByEmail( principal.getName() );
        model.addAttribute("auth_roles", user.getRoles());
        model.addAttribute("current_user", user);

        return "bootstrap";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        User user = service.getUserByEmail( principal.getName() );
        model.addAttribute("auth_roles", user.getRoles());
        model.addAttribute("all_users", service.getAllUsers());
        model.addAttribute("current_user", user);
        model.addAttribute("new_user", new User());
        return "bootstrap";
    }

    @PostMapping("/admin/create")
    public String create(@ModelAttribute("new_user") User user,
                         @RequestParam(required = false) String[] new_roles,
                         @RequestParam(required = false) String new_age) {
        // проверяем - существует ли другой пользователь с таким же e-mail
        try {
            User bdUser = service.getUserByEmail(  user.getEmail()  );
            return "redirect:/admin";
        } catch (Exception e) {
            /* ignore - пользователь с таким же e-mail не найден */
        }
        // проверяем введено ли пустое значение в поле Age
        try {
            user.setAge(Byte.parseByte(new_age));
        } catch (NumberFormatException e) {
            /* ignore */
        }
        // добавляем роли
        user.setRoles(  service.getRolesFromArray(new_roles)  );
        // добавляем запись в БД
        service.addUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/admin/update")
    public String update(@ModelAttribute("new_user") User user,
                         @RequestParam(required = false) String[] new_roles,
                         @RequestParam(required = false) String new_age) {
        // проверяем - существует ли другой пользователь с таким же e-mail
        try {
            User bdUser = service.getUserByEmail(  user.getEmail()  );
            if ( bdUser.getId() != user.getId() ) {
                return "redirect:/admin";
            }
        } catch (Exception e) {
            /* ignore - пользователь с таким же e-mail не найден */
        }
        // проверяем введено ли пустое значение в поле Age
        try {
            user.setAge(Byte.parseByte(new_age));
        } catch (NumberFormatException e) {
            /* ignore */
        }
        // добавляем роли
        user.setRoles(  service.getRolesFromArray(new_roles)  );
        // обновляем запись в БД
        service.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/delete")
    public String delete(@RequestParam("delete_id") Long id) {
        service.deleteUser(id);
        return "redirect:/admin";
    }

}
