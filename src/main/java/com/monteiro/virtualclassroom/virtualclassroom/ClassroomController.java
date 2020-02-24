package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.ClassroomDao;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@Controller
public class ClassroomController {

    @GetMapping("/")
    public String homePageRender(Model model, HttpSession session) throws IOException, SQLException {
        System.out.println("GET /HomePage (ClassroomController)");
        List<Classroom> classroomList;
        long n = ClassroomDao.getClassroomCount();
        int id = 0;
        classroomList = ClassroomDao.getClassroomRowsList(id, n);
        //sorting the classroom's list by alphabetical order
        classroomList.sort(Comparator.comparing(Classroom::getClassroom_name));
        System.out.println("classes count" + n);
        System.out.println("classrooms :" + classroomList);
        model.addAttribute("classrooms", classroomList);

        if ((classroomList.isEmpty()) && (session.getAttribute("login_first") == null)) {
            model.addAttribute("adminAccess", true);
        } else if ((classroomList.isEmpty()) && (session.getAttribute("login_first") != null)) {
            model.addAttribute("adminClassList", true);
            model.addAttribute("adminAddClass", true);
        } else if ((!classroomList.isEmpty()) && (session.getAttribute("login_first") != null)) {
            model.addAttribute("adminAddClass", true);
        }
        return "HomePage";
    }

    @RequestMapping(value = "/studentFrame/{className}", method = RequestMethod.GET)
    public String getStudentsOfTheClass(@PathVariable("className") String className, Model model) throws IOException, SQLException {
        System.out.println("enters the studentFrame Controller");
        model.addAttribute("studentFrameActive", true);
        List<User> studentsList;
        Classroom myClass = ClassroomDao.getClassroomByName(className);
        long classroomID = myClass.getId_classroom();
        System.out.println("classroom Id" + classroomID);
        studentsList = UserDao.getStudentsList(classroomID);
        System.out.println("List of students" + studentsList);
        model.addAttribute("students", studentsList);
        return "fragments/studentFrame";
    }

    @RequestMapping(value = "/deleteStudent")
    public String deleteStudentFromClassroomList(int studentDelete) throws IOException, SQLException {
        System.out.println("student to be deleted" + studentDelete);
        UserDao.deleteUser(studentDelete);
        return "redirect:/";
    }

    @PostMapping("/addClassroom")
    @ResponseBody
    public String createClassroom(@RequestParam String classroomName, Model model) throws IOException, SQLException {
        Classroom newClass = new Classroom(classroomName);
        System.out.println(ClassroomDao.getClassroomByName(classroomName));
        if (classroomName.equals("")) {
            return ("empty");
        } else if (ClassroomDao.getClassroomByName(classroomName) != null) {
            return ("exists");
        } else if (ClassroomDao.getClassroomByName(classroomName) == null) {
            System.out.println("saving a new class" + classroomName);
            ClassroomDao.saveClassroom(newClass);
            return ("success");
        }
        return "dunno what happened";
    }

    @PostMapping("/deleteClassroom")
    public String deleteClassroomFromList(@RequestParam String classDelete) throws IOException, SQLException {
        System.out.println(classDelete);
        ClassroomDao.deleteClassroom(classDelete);
        return "redirect:/";
    }

    @RequestMapping(value = "/updateClassroom")
    public String updateClassroomFromList(@RequestParam String classNameModify, @RequestParam String newClassroomName) throws IOException, SQLException {
        System.out.println(classNameModify);
        System.out.println(newClassroomName);
        if (!newClassroomName.equals("")) {
            System.out.println("le nom n'est pas vide");
            ClassroomDao.updateClassroomName(classNameModify, newClassroomName);
        }
        System.out.println("le nom est vide");
        return "redirect:/";
    }

    @RequestMapping("/LoginClass")
    public String loginClassRender(HttpSession session, @RequestParam(value = "id") long parameter) throws IOException, SQLException {
        Classroom myClass = ClassroomDao.getClassroom(parameter);
        addClassroomInSession(myClass, session);
        System.out.println("GET /LoginPage (LoginClassController)");
        //return html page
        return "LoginPage";
    }

    private void addClassroomInSession(Classroom classroom, HttpSession session) {
        session.setAttribute("classroom", classroom);
    }

}
