package com.monteiro.virtualclassroom.virtualclassroom.controller;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class UpdateProfileController {
    // render profile page
    @GetMapping("/ProfilePage")
    public String profileRender() {
        System.out.println("Profile render: Get");
        return "ProfilePage";
    }

    @PostMapping("/updateProfilePage")
    public String profileUpdate(
            String user_name_value,
            String user_lastname_value,
            String user_email_value,
            HttpSession session,
            Model model) throws IOException, SQLException {
        System.out.println("Profile render: post");

        User theOne = (User) session.getAttribute("user");
        String oldName = theOne.getUser_name();
        String oldLastName = theOne.getUser_lastname();
        String oldEmail = theOne.getUser_email();
        if ((user_name_value.isEmpty()) || (user_lastname_value.isEmpty()) ||
                (user_email_value.isEmpty())) {
            model.addAttribute("emptyField", true);
            return "ProfilePage";
        } else {
            if (oldName != user_name_value) {
                UserDao.updateUser("user_name", oldName, user_name_value);
                session.setAttribute("login_first", user_name_value);
                return "redirect:/ProfilePage";
            }

            if (oldLastName != user_lastname_value) {
                UserDao.updateUser("user_lastName", oldLastName, user_lastname_value);
                session.setAttribute("login_last", user_lastname_value);
                return "redirect:/ProfilePage";
            }
            if (oldEmail != user_email_value) {
                UserDao.updateUser("user_email", oldEmail, user_email_value);
                return "redirect:/ProfilePage";
            }
        }
        return "redirect:/ProfilePage";
    }
}
