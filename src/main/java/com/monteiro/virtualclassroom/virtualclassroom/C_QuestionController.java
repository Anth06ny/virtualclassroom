package com.monteiro.virtualclassroom.virtualclassroom;

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Option;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Question;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.OptionDao;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.QuestionDao;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.net.ssl.HttpsURLConnection;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;


@Controller
public class C_QuestionController {
    // render CreateQuestion Page
    @GetMapping("/CreateQuestionPage")
    public String signUpRender() {
        System.out.println("GET /CreateQuestion (CreateQuestionController)");
        return "CreateQuestionPage"; //view
    }

    @PostMapping("/CreateQuestionPage")
    public String handleCreateQuestionRequest(
            @RequestParam String question_content,
            String option,
            Question question,
            @RequestParam(value= "option_content[]")String[] options_content, // specify value request in tab
            Model model,
            HttpSession session
        ) throws Exception {

        System.out.println("POST /CreateQuestion (CreateQuestionController)");

        // Verification if a field is empty on the question head
        if(question_content.isEmpty()) {
            System.out.println("No label");
            model.addAttribute("emptyField", true);
            return "CreateQuestionPage";
        } else if(option == null){
            System.out.println("Radio empty");
            System.out.println(options_content[0]);
            model.addAttribute("isRadioEmpty", true);
            return "CreateQuestionPage";
        } else if(options_content[0].isEmpty()) {
            System.out.println("option_content empty");
            model.addAttribute("emptyContent", true);
            return "CreateQuestionPage";
        }
        else if(option == "radio") {
//                System.out.println(" in the if ");
            question.setRadio(true);
        } else if(option != "radio") {
//                System.out.println(" in the else");
            question.setRadio(false);
        }
            long id_classroom = (long) session.getAttribute("classroomID");
            Question newQuestion = new Question(question_content, id_classroom , question.getIsRadio());
//            System.out.println(" isRadio ? " + question.getIsRadio());
            QuestionDao.saveQuestion(newQuestion);
            int id_question = newQuestion.getId_question();
            System.out.println("writing question successful");
            // answer
            for (String option_content : options_content) {
                Option newOption = new Option(option_content,id_question);
                System.out.println(option_content);
                OptionDao.saveOption(newOption);
                System.out.println("writing options successful");
            }
            return "TeacherPage";
        }
}
