package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

//on ne touche pas

@SpringBootApplication
public class VirtualclassroomApplication extends SpringBootServletInitializer {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(VirtualclassroomApplication.class, args);
//        UserDao dao1= new UserDao();
//        User user1 = dao1.getUser("mLobb@toto.com");
//        System.out.println(user1.getUser_name());
//
//        UserDao.saveUser(new User("Hola", "Hihi", "hh@hh.fr", "123456",false,5));

        //User test1 = UserDao.getUser("Martin");
        //System.out.println(test1.getUser_email());
        //List<User> allUsers= UserDao.readAll();
        //for(User user: allUsers)
            //System.out.println( user.toString());

       // Classroom classroom = ClassroomDao.getClassroom(1);
       // System.out.println(classroom.getClassroom_name());
    }
}
