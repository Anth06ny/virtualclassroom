package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;


@Controller
//@SessionAttributes("login_first", "login", "login_last", "login_psw")

public class LoginController{

    //render login page
    @GetMapping("/LoginPage")
    public String loginRender(){
        System.out.println("GET /LoginPage (LoginController)");
        //return html page
        return "LoginPage";
    }


    //checking for credentials
    @PostMapping("/LoginPage")
    public String handleLoginRequest(
            @RequestParam String login_name,
            @RequestParam String login_password,
            //we are also sending back the model from the DispatcherServlet controller to the view
            HttpSession session,
            Model model) throws IOException, SQLException {

        System.out.println("POST /LoginPage (LoginController)");
        //System.out.println(user_email);
        User myMail = UserDao.getUser(login_name, login_password);

        if(myMail == null) {
            model.addAttribute("invalidCredentials", true);
            return "LoginPage";
        }

        else if((myMail.getIsAdmin())) {
            System.out.println("I try to verify the mail and psw");
            if (myMail.getUser_email().equals(login_name) &&
                    myMail.getUser_password().equals(login_password)){
                addUserInSession(myMail,session);
                System.out.println("empty classroomList or admin - admin post method");
                return "redirect:/adminConnected";
            }
        }

        else if(!myMail.getIsAdmin()) {
            if(session.getAttribute("classroom") == null){
                model.addAttribute("NotAdmin", true);
                System.out.println(session.getAttribute("classroom"));
                return "LoginPage";
            }

            else if(myMail.get_UserClassroomId()!= (((Classroom) session.getAttribute("classroom")).getId_classroom())) {
                model.addAttribute("invalidClassroom", true);
                return "LoginPage";
            }

            else if (myMail.getUser_email().equals(login_name) &&
                    myMail.getUser_password().equals(login_password)&&
                    myMail.get_UserClassroomId()==(((Classroom) session.getAttribute("classroom")).getId_classroom())) {
                addUserInSession(myMail,session);
                System.out.println("getting logged in");
                return "redirect:/userConnected";
            }

        } return "LoginPage";
    }


    private void addUserInSession(User user, HttpSession session){
        session.setAttribute("user", user);
        session.setAttribute("login", user.getUser_email());
        session.setAttribute("login_psw", user.getUser_password());
        session.setAttribute("login_first", user.getUser_name());
        session.setAttribute("login_last", user.getUser_lastname());
        session.setAttribute("is_Admin", user.getIsAdmin());
        session.setAttribute("user_classroom", user.get_UserClassroomId());
        session.setAttribute("userID", user.getUser_id());
    }


//    @GetMapping("/userConnected")
//        public String userPageRender(Model model) {
//            System.out.println("my not admin");
//            return "TeacherPage";
//        }

    @RequestMapping(value = "/Logout")
    public String logout(HttpSession session) {
        System.out.println("in logout");
        session.invalidate();
        return "redirect:/";
    }
}