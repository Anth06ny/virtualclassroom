package com.monteiro.virtualclassroom.virtualclassroom.controller;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


@Controller
public class LoginController {
    //render login page
    @RequestMapping(value = "/LoginPage", method = RequestMethod.GET)
    public String loginRender() {
        return "LoginPage";
    }

    //checking for credentials
    @PostMapping("/LoginPage")
    public String handleLoginRequest(
            @RequestParam String login_name,
            @RequestParam String login_password,
            HttpSession session,
            //we are also sending back the model from the DispatcherServlet controller to the view
            Model model) throws IOException, SQLException, NoSuchAlgorithmException {

        System.out.println("POST /LoginPage (LoginController)");

        User connectedUser = UserDao.getUserLogin(login_name, login_password);
        if (connectedUser == null) {
            model.addAttribute("invalidCredentials", true);
            return "LoginPage";
        }

        //connected user is Admin
        if (connectedUser.getIsAdmin()) {
            //admin connected and added to the session
            addUserInSession(connectedUser, session);
            return "redirect:/adminConnected";
        }
        //connected student
        else {
            //there is no class yet created
            if (session.getAttribute("classroom") == null) {
                model.addAttribute("NotAdmin", true);
                System.out.println("my classroom Object" + session.getAttribute("classroom"));
                return "LoginPage";
            }
            //student is not registered to this classroom
            else if (connectedUser.get_UserClassroomId() != (((Classroom) session.getAttribute("classroom")).getId_classroom())) {
                model.addAttribute("invalidClassroom", true);
                return "LoginPage";
            }

            System.out.println("I am in the correct condition");
            System.out.println(connectedUser.getUser_email());
            System.out.println("entered name" + login_name);
            System.out.println("class" + ((Classroom) session.getAttribute("classroom")).getId_classroom());

            addUserInSession(connectedUser, session);
            return "redirect:/userConnected";
//            }
        }
    }


    private void addUserInSession(User user, HttpSession session) {
        session.setAttribute("user", user);
        session.setAttribute("login", user.getUser_email());
        session.setAttribute("login_psw", user.getUser_password());
        session.setAttribute("login_first", user.getUser_name());
        session.setAttribute("login_last", user.getUser_lastname());
        session.setAttribute("is_Admin", user.getIsAdmin());
        session.setAttribute("userID", user.getUser_id());
        if (user.getIsAdmin() != true) {
            session.setAttribute("user_classroom", user.get_UserClassroomId());
        }
    }


    @RequestMapping(value = "/Logout")
    public String logout(HttpSession session) {
        System.out.println("in logout");
        session.invalidate();
        return "redirect:/";
    }
}