package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.*;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.InformationDao;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.OptionDao;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.QuestionDao;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.UserDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Controller
public class C_QuestionController {
    // render CreateQuestion Page
    @GetMapping("/CreateQuestionPage")
    public String signUpRender(Model model, HttpSession session) throws IOException, SQLException {
        System.out.println("GET /CreateQuestion (CreateQuestionController)");
        Classroom currentClassroom = (Classroom) session.getAttribute("classroom");
        if (currentClassroom == null) {
            model.addAttribute("noExistingClassroom", true);
            return "redirect:/AdminPage";
        }
        long classroomId = currentClassroom.getId_classroom();
        List<Information> informationList = InformationDao.showInformation(classroomId);
        model.addAttribute("information", informationList);
        List<User> studentsList;
        studentsList = UserDao.getStudentsList(classroomId);
        System.out.println(studentsList);
        model.addAttribute("students", studentsList);
        return "CreateQuestionPage"; //view
    }

    @PostMapping("/CreateQuestionPage")
    public String handleCreateQuestionRequest(
            @RequestParam String question_content,
            String option,
            Question question,
            HttpSession session,
            @RequestParam(value = "option_content[]") String[] options_content, // specify value request in tab
            Model model) throws Exception {

        System.out.println("POST /CreateQuestion (CreateQuestionController)");

        // Verification if a field is empty on the question head
        if (question_content.isEmpty()) {
//            System.out.println("No label");
            model.addAttribute("emptyField", true);
            return "CreateQuestionPage";
        } else if (option == null) {
//            System.out.println("Radio empty");
//            System.out.println(options_content[0]);
            model.addAttribute("isRadioEmpty", true);
            return "CreateQuestionPage";
        } else if (options_content[0].isEmpty()) {
            System.out.println("option_content empty");
            model.addAttribute("emptyContent", true);
            return "CreateQuestionPage";
        } else if (option.equals("radio")) {
            question.setRadio(true);
        } else if (option.equals("checkbox")) {
            question.setRadio(false);
        }
        Classroom currentClassroom = (Classroom) session.getAttribute("classroom");
        long classroomId = currentClassroom.getId_classroom();
        Question newQuestion = new Question(question_content, question.getIsRadio());
        System.out.println("current class" + currentClassroom);
        newQuestion.setClassroom(currentClassroom);
        QuestionDao.saveQuestion(newQuestion);

        System.out.println("writing question successful");
        // answer
        for (String option_content : options_content) {
            Option newOption = new Option(option_content);
            newOption.setQuestion(newQuestion);
            System.out.println(option_content);
            OptionDao.saveOption(newOption);
            System.out.println("writing options successful");
        }
        model.addAttribute("questionSuccess", true);
        return "CreateQuestionPage";
    }
}