package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
public class UpdatePasswordController {
    UserDao dao = new UserDao();

    // render password update form
    @GetMapping("/UpdatePassword")
    public String updatePwdRender() {
        System.out.println("get UpdatePsw html");
        return "UpdatePassword"; //view
    }

    @PostMapping("/UpdatePassword")
    public String handleUpdatePswRequest(
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String repeatPassword,
            HttpSession session,
            Model model) throws IOException, SQLException {
        System.out.println("handleUpdateRequest - UpdatePasswordController");

        // retrieve target user in the db
        User myUser = (User) session.getAttribute("user");

        if (myUser == null) {
            // display error msg
            model.addAttribute("invalidCredentials", true);
            return "UpdatePassword";
        } else if (myUser.getUser_password().equals(currentPassword)) {
            if (newPassword.isEmpty()) {
                // display emptyField msg
                model.addAttribute("emptyField", true);
                return "UpdatePassword";
            } else if (newPassword.equals(repeatPassword)) {
                // update function call
                dao.updatePwdUser(myUser.getUser_email(), newPassword);
                return "ProfilePage";
            } else {
                // display errorPwd msg
                model.addAttribute("differentPassword", true);
            }
            return "UpdatePassword";
        } else {
            // display errorPwd msg
            model.addAttribute("invalidPassword", true);
            return "UpdatePassword";
        }
    }
}

