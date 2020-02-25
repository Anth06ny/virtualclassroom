package com.monteiro.virtualclassroom.virtualclassroom;


import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


//on ne touche pas
//url de départ http://localhost:8080
@SpringBootApplication
public class VirtualclassroomApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VirtualclassroomApplication.class, args);

        //On créer l'admin s'il n'hesiste pas déja
        if (!UserDao.isAdmin()) {
            //on insert un Admin
            User user = new User("Admin", "Admin", "Admin", true);
            user.setPassword(ConstantsKt.BDD_PSW);
            UserDao.saveUser(user);
        }

    }
}
