<<<<<<< HEAD:src/main/java/com/monteiro/virtualclassroom/virtualclassroom/Controller/SignUpController.java
package com.monteiro.virtualclassroom.virtualclassroom.Controller;
=======
package com.monteiro.virtualclassroom.virtualclassroom.controller;
>>>>>>> 270ef89c797761dee41927236ca6077a3cdeae7d:src/main/java/com/monteiro/virtualclassroom/virtualclassroom/controller/SignUpController.java

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class SignUpController {

    // render sign up Page
    @GetMapping("/SignUpPage")
    public String signUpRender() {
        System.out.println("GET /SignUpPage (SignUpController)");
        return "SignUpPage"; //view
    }

    @PostMapping("/SignUp")
    public String handleSignUpRequest(
            @RequestParam String user_name,
            @RequestParam String user_lastname,
            @RequestParam String user_email,
            @RequestParam String user_password,
            HttpSession session,
            Model model) throws Exception {
        User newOne;

        System.out.println("POST /SignUpPage (SignUpController)");
        if ((user_name.isEmpty()) || (user_lastname.isEmpty()) || (user_password.isEmpty()) || (user_email.isEmpty())) {
            model.addAttribute("emptyField", true);
            return "SignUpPage";
        } else if (UserDao.getUserLogin(user_email, user_password) != null) {
            model.addAttribute("existingUser", true);
            return "SignUpPage";
        } else {
            newOne = new User(user_name, user_lastname, user_email, false);
            // classroom set separately
            newOne.setClassroom((Classroom) session.getAttribute("classroom"));
            newOne.setPassword(user_password);
            // new user creation
            UserDao.saveUser(newOne);
            return "LoginPage";
        }
    }
}
