package com.monteiro.virtualclassroom.virtualclassroom.controller;


// imports

import com.monteiro.virtualclassroom.virtualclassroom.model.bean.*;
import com.monteiro.virtualclassroom.virtualclassroom.model.dao.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

@Controller
public class AnswerController {

    // render CreateQuestion Page
    @GetMapping("/userConnected")
    public String handleDisplayQuestionRequest(
            HttpSession session,
            Model model
    ) throws Exception {
        System.out.println("GET /DisplayQuestion (AnswerController)");
        // get the selected class stored in the session
        Classroom classroom = (Classroom) session.getAttribute("classroom");
        long classroomId = classroom.getId_classroom();

        // creation of the list question
        int startRow = 0;

        // creation of a list which will be used by thymeleaf and store the result of the function call in the list
        List<Question> questionList = QuestionDao.getAllQuestionsFromId(classroomId, startRow, QuestionDao.getQuestionCount());

        // add to the model
        model.addAttribute("questions", questionList);

        List<Information> informationList = InformationDao.showInformation(classroomId);
        model.addAttribute("information", informationList);

        AnswerForm answerForm = new AnswerForm();
        model.addAttribute("answerForm", answerForm);

        for (Question value : questionList) {
            // store the id_question from the current displayed question
            int questionIdentification = value.getId_question();
            // retrieve options store in optionDao
            value.setOptions(OptionDao.getAllOptionsFromQuestion(questionIdentification, startRow, OptionDao.getOptionCount()));
        }
        return "TeacherPage"; //view
    }

    @PostMapping("/sendAnswer")  //use the save answer
    public String saveUserAnswer(
            @ModelAttribute("answerForm") AnswerForm answerForm,
            HttpSession session,
            int questionHiddenValue,
            Model model) throws Exception {
        Option checkBoxOptions;
        Option radioOption = OptionDao.getOption(answerForm.getRadioOption());
        Question questionValue = QuestionDao.getQuestion(questionHiddenValue);
        // get the selected class stored in the session
        Classroom classroom = (Classroom) session.getAttribute("classroom");
        long classroomId = classroom.getId_classroom();
        // get the user_id
        User userInSession = (User) session.getAttribute("user");
        if (radioOption == null) {
            AnswerDao.deleteAnswerOfUser(userInSession.getUser_id(), questionValue.getId_question());
            long formLength = answerForm.getCheckboxOptions().length;
            for (int i = 0; i < formLength; i++) {
                checkBoxOptions = OptionDao.getOption(answerForm.getCheckboxOptions()[i]);
                System.out.println("my checkboxOptions" + checkBoxOptions.getId_option());
                Answer newAnswer1 = new Answer();
                newAnswer1.setOption(checkBoxOptions);
                newAnswer1.setUser(userInSession);
                AnswerDao.saveAnswer(newAnswer1);
            }
        } else {
            Answer newAnswer = new Answer();
            newAnswer.setOption(radioOption);
            newAnswer.setUser(userInSession);
            AnswerDao.saveAnswer(newAnswer);
        }
        return "redirect:/userConnected";
    }


    @GetMapping("/adminConnected")
    public String adminPageRender(Model model, HttpSession session) throws Exception {
        System.out.println("My Admin");

        // get the selected class stored in the session
        Classroom classroom = (Classroom) session.getAttribute("classroom");
        System.out.println("classroom1 " + classroom);

        if (classroom != null) {

            long classroomId = classroom.getId_classroom();
            System.out.println("classroomId" + classroomId);

            // creation of the list question
            int startRow = 0;

            // creation of a list which will be used by thymeleaf and store the result of the function call in the list
            List<Question> questionList = QuestionDao.getAllQuestionsFromId(classroomId, startRow, QuestionDao.getQuestionCount());
            questionList.sort(Comparator.comparing(Question::getId_question));
            // add to the model
            model.addAttribute("questions", questionList);

            List<Information> informationList = InformationDao.showInformation(classroomId);
            model.addAttribute("information", informationList);

            List<User> listOfUsers = UserDao.getStudentsList(classroom);
            model.addAttribute("students", listOfUsers);

            List<Answer> listOfAnswers = AnswerDao.getAnswers();
            model.addAttribute("answers", listOfAnswers);


            for (Question value : questionList) {
                // store the id_question from the current displayed question
                int questionId = value.getId_question();
                System.out.println(questionId);

                // retrieve options store in optionDao
                value.setOptions(OptionDao.getAllOptionsFromQuestion(questionId, startRow, OptionDao.getOptionCount()));
                value.answerQuestion(AnswerDao.getAnswersList(questionId));
            }
            return "TeacherPage"; //view
        } else {
            return "TeacherPage";
        }
    }

    @PostMapping("/deleteQuestion")
    public String deleteQuestion(int questionId) throws IOException, SQLException {
        System.out.println("I try to display the question value");

        Question question = QuestionDao.getQuestion(questionId);
        List<Option> options = OptionDao.getOptionList(questionId);
        List<Answer> answers = AnswerDao.getAnswersList(questionId);

        for (Answer answer : answers) {
            AnswerDao.deleteAnswer(answer);
        }
        for (Option value : options) {
            OptionDao.deleteOption(value);
        }
        QuestionDao.deleteQuestion(question);

        return "redirect:/adminConnected";
    }

    @PostMapping("/updateQuestion")
    public String updateQuestion(int questionToModify, String newQuestionContent) throws IOException, SQLException {
        System.out.println("I try to display the question value");
        System.out.println(questionToModify);
        Question q = QuestionDao.getQuestion(questionToModify);
        if (!newQuestionContent.equals("") && !newQuestionContent.equals(q.getQuestion_content())) {
            QuestionDao.updateQuestion(questionToModify, newQuestionContent);
        }
        return "redirect:/adminConnected";
    }

    @PostMapping("/updateOption")
    public String updateOption(int optionToModify, String newOptionContent) throws Exception {
        System.out.println(optionToModify);
        Option o = OptionDao.getOption(optionToModify);
        if (!newOptionContent.equals("") && !newOptionContent.equals(o.getOption_content())) {
            OptionDao.updateOption(optionToModify, newOptionContent);
        }
        return "redirect:/adminConnected";
    }
}


