package com.monteiro.virtualclassroom.virtualclassroom.controller;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.*;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Controller
public class CreateQuestionController {
    // render CreateQuestion Page
    @GetMapping("/CreateQuestionPage")

    public String createQuestionRender(Model model, HttpSession session) throws IOException, SQLException {
        System.out.println("GET /CreateQuestion (CreateQuestionController)");
        Classroom currentClassroom = (Classroom) session.getAttribute("classroom");
        long classroomId = currentClassroom.getId_classroom();
        // information frame display
        List<Information> informationList = InformationDao.showInformation(classroomId);
        model.addAttribute("information", informationList);
        // Student list display
        List<User> studentsList;
        studentsList = UserDao.getStudentsList(currentClassroom);
        System.out.println(studentsList);
        model.addAttribute("students", studentsList);
        return "CreateQuestionPage"; //view
    }

    @PostMapping("/sendQuestion")
    @ResponseBody
    public String handleCreateQuestionRequest(
            @RequestParam String question_content,
            String option,
            Question question,
            HttpSession session,
            @RequestParam(value = "option_content[]") String[] options_content, // specify value request in tab
            Model model) throws Exception {
        Classroom currentClassroom = (Classroom) session.getAttribute("classroom");

        System.out.println("POST /CreateQuestion (CreateQuestionController)");

        // Verification if a field is empty on the question head
        if (question_content.isEmpty()) {
            model.addAttribute("emptyField", true);
            return ("emptyQuestionContent");
        } else if (option == null) {
            model.addAttribute("isRadioEmpty", true);
//            return "CreateQuestionPage";
            return ("emptyType");
        } else if (options_content[0].isEmpty()) {
//            System.out.println("option_content empty");
//            model.addAttribute("emptyContent", true);
            return ("emptyOption");
        } else if (ClassroomDao.getClassroom(((Classroom) session.getAttribute("classroom")).getId_classroom()) == null) {
            return ("classroomNotExist");
        } else {
            if (option.equals("radio")) {
                question.setRadio(true);
            } else if (option.equals("checkbox")) {
                question.setRadio(false);
            }

            Question newQuestion = new Question(question_content, question.getIsRadio());
            System.out.println("current class" + currentClassroom);
            newQuestion.setClassroom(currentClassroom);
            QuestionDao.saveQuestion(newQuestion);

            System.out.println("writing question successful");
            // answer
            for (String option_content : options_content) {
                if (!option_content.equals("")) {
                    Option newOption = new Option(option_content);
                    newOption.setQuestion(newQuestion);
                    System.out.println(option_content);
                    System.out.println("new option is : " + newOption);

                    OptionDao.saveOption(newOption);
                }

                System.out.println("writing options successful");
            }
            model.addAttribute("questionSuccess", true);
            return ("successful");
        }
    }
}