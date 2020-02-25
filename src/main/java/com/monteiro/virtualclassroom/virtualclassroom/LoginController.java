package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;


@Controller

public class LoginController {

    //render login page
    @GetMapping("/LoginPage")
    public String loginRender() {
        System.out.println("GET /LoginPage (LoginController)");
        //return html page
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
        boolean isPswOk;

        System.out.println("POST /LoginPage (LoginController)");

        User connectedUser = UserDao.getUser(login_name, login_password);
        isPswOk = connectedUser.isPasswordCorrect(login_password);
//        if (!isPswOk){
//            model.addAttribute("user not existing", true);
//        }

        System.out.println("connected User: " + connectedUser);
        System.out.println("is the password OK? " + isPswOk);
        //could not find the combination of login and psw in db
        if (connectedUser == null) {
            model.addAttribute("invalidCredentials", true);
            return "LoginPage";
        }
        //connected user is Admin
        else if (connectedUser.getIsAdmin() && connectedUser.getUser_email().equals(login_name) &&
                isPswOk) {
            //admin connected and added to the session
            addUserInSession(connectedUser, session);
            return "redirect:/adminConnected";
        }
        //connected student
        else if (!connectedUser.getIsAdmin()) {
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
            //the credentials and the classroom are both valid, logging in
            else if (isPswOk) {
                System.out.println("I am in the correct condition");
                System.out.println(connectedUser.getUser_email());
                System.out.println("entered name" + login_name);
                System.out.println("class" + ((Classroom) session.getAttribute("classroom")).getId_classroom());
                if ((connectedUser.getUser_email().toUpperCase().equals(login_name.toUpperCase())) &&
                        (connectedUser.get_UserClassroomId()) == (((Classroom) session.getAttribute("classroom")).getId_classroom())) {
                    addUserInSession(connectedUser, session);
                    System.out.println("getting logged in");
                    return "redirect:/userConnected";
                }
            }
            System.out.println("I am out of conditions");
        }
        return "LoginPage";
    }


    private void addUserInSession(User user, HttpSession session) {
        session.setAttribute("user", user);
        session.setAttribute("login", user.getUser_email());
        session.setAttribute("login_psw", user.getUser_password());
        session.setAttribute("login_first", user.getUser_name());
        session.setAttribute("login_last", user.getUser_lastname());
        session.setAttribute("is_Admin", user.getIsAdmin());
        session.setAttribute("user_classroom", user.get_UserClassroomId());
        session.setAttribute("userID", user.getUser_id());
    }


    @RequestMapping(value = "/Logout")
    public String logout(HttpSession session) {
        System.out.println("in logout");
        session.invalidate();
        return "redirect:/";
    }
}