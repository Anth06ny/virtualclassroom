package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@Controller
//@SessionAttributes("login")
public class UpdateProfileController {
    // render profile page
    @GetMapping("/ProfilePage")
    public String profileRender() {
        System.out.println("Profile render Get");
        return "ProfilePage";
    }

    @PostMapping ("/ProfilePage")
    public String profileUpdate(
        String user_name_value,
        String user_lastname_value,
        String user_email_value,
        String user_password_value,
        HttpSession session,
        Model model) throws IOException, SQLException {
        System.out.println("Profile render post");

        User theOne = UserDao.getUser(user_email_value, user_password_value);
        String oldName = theOne.getUser_name();
        String oldLastName = theOne.getUser_lastname();
        String oldEmail = theOne.getUser_email();
        String oldPassword = theOne.getUser_password();
        System.out.println(oldPassword);
        System.out.println(user_password_value);
        if((user_name_value==oldName)&&(user_lastname_value==oldLastName)&&
                (user_email_value==oldEmail)&&(user_password_value==oldPassword )){
            System.out.println("missing");
            return "TeacherPage";
        }
        else if(oldName != user_name_value){
            System.out.println(" name reussi");
            System.out.println(user_name_value);
            System.out.println(oldName);
            UserDao.updateUser("user_name", oldName, user_name_value);
            if(oldLastName != user_lastname_value){
                System.out.println("last name reussi");
                System.out.println(user_lastname_value);
                System.out.println(oldLastName);
                UserDao.updateUser("user_lastName",oldLastName, user_lastname_value);
                if(oldEmail != user_email_value){
                    UserDao.updateUser("user_email",oldEmail, user_email_value);
//                    if(oldPassword != user_password_value){
//                        UserDao.updateUser("user_password",oldPassword, user_password_value);
//                    }return "TeacherPage";
                }return "TeacherPage";
            }return "TeacherPage";
        }return "TeacherPage";
        }

}
