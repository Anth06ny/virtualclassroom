package com.monteiro.virtualclassroom.virtualclassroom;


import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


//url de départ http://localhost:8080
//l'annotation qui dit que le projet est auto-configuré par spring-boot - il scannera toutes les données de configuration dans le projet et il ira le déclarer dans  le fichier de configuration
@SpringBootApplication
public class VirtualclassroomApplication extends SpringBootServletInitializer {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(VirtualclassroomApplication.class, args);

        //On crée l'admin s'il n'existe pas déjà
        if (!UserDao.isAdmin()) {
            //on insert un Admin
            User user = new User("Admin", "Admin", ConstantsKt.BDD_ADMIN, true);
            user.setPassword(ConstantsKt.BDD_PSW);
            UserDao.saveUser(user);
        }
    }
}
