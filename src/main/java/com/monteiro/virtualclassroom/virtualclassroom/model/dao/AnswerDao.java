package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Answer;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Option;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Question;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.monteiro.virtualclassroom.virtualclassroom.ConstantsKt.*;

public class AnswerDao {

    public AnswerDao() {
    }

    // save answer method
    public static void saveAnswer(Answer answer) throws Exception {
        // instantiate the dao with the connection source
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class); //creates a new dao object
            Dao<Option, String> optionDao = DaoManager.createDao(connectionSource, Option.class);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);
            Dao<Question, String> questionDao = DaoManager.createDao(connectionSource, Question.class);

            //create the link between the foreign keys and their tables
            optionDao.refresh(answer.getOption());
            userDao.refresh(answer.getUser());
            questionDao.refresh(answer.getOption().getQuestion());

            //build the query of all answers of this user
            QueryBuilder<Answer, String> answerQb = answerDao.queryBuilder();
            answerQb.where().eq("id_user", answer.getUser().getUser_id());

            //build the query of all options for this question
            QueryBuilder<Option, String> optionQb = optionDao.queryBuilder();
            optionQb.where().eq("id_question", answer.getOption().getQuestion().getId_question());

            //join both builders and query, it creates a joined list
            List<Answer> answersOfUserOnQuestion = answerQb.join(optionQb).query();

            QueryBuilder<Question, String> questionQb = questionDao.queryBuilder();
            questionQb.where().eq("isRadio", 0);
            List<Option> allCheckboxes = optionQb.join(questionQb).query();
            List<Option> selectedCheckbox = new ArrayList<>();
            for (int i = 0; i < allCheckboxes.size(); i++) {
                if (allCheckboxes.get(i).getId_option() == answer.getOption().getId_option()) {
                    selectedCheckbox.add(allCheckboxes.get(i));
                }
            }

            if (selectedCheckbox.size() == 0) {
                if (answerDao.queryBuilder().where().eq("id_user", answer.getUser().getUser_id()).countOf() == 0) {
                    answerDao.create(answer);
                } else if (answersOfUserOnQuestion.size() == 0) {
                    answerDao.create(answer);
                }
                //update the existing answer of this user to this question
                else if (answersOfUserOnQuestion.size() > 0) {

                    UpdateBuilder<Answer, String> updateBuilder = answerDao.updateBuilder();

                    // set the criteria
                    updateBuilder.where().eq("id_option", answersOfUserOnQuestion.get(0).getOption().getId_option());
                    // update the value of the target fields
                    updateBuilder.updateColumnValue("id_option", answer.getOption().getId_option());
                    // update execution
                    updateBuilder.update();
                }
            }
            //the join done before has no result : user has not answered this very question yet
            if (selectedCheckbox.size() != 0) {
                QueryBuilder<Option, String> newOptionQb = optionDao.queryBuilder();
                newOptionQb.where().eq("id_option", selectedCheckbox.get(0).getId_option());
                QueryBuilder<Answer, String> newAnswerQb = answerDao.queryBuilder();
                newAnswerQb.where().eq("id_user", answer.getUser().getUser_id());

                List<Answer> answerOption = newAnswerQb.join(newOptionQb).query();
                if (answerDao.queryBuilder().where().eq("id_user", answer.getUser().getUser_id()).countOf() == 0) {
                    answerDao.create(answer);
                } else if (answerOption.size() == 0) {
                    answerDao.create(answer);
                } else if (answerDao.queryBuilder().where().eq("id_option", selectedCheckbox.get(0).getId_option()).countOf() > 0) {
                    UpdateBuilder<Answer, String> updateBuilder = answerDao.updateBuilder();
                    // set the criteria
                    updateBuilder.where().eq("id_user", answer.getUser().getUser_id());
                    // update the value of the target fields
                    updateBuilder.updateColumnValue("id_option", selectedCheckbox.get(0).getId_option());
                    // update execution
                    updateBuilder.update();
                }
            }
        } finally {
            connectionSource.close();
        }
    }

    public static void deleteAnswerOfUser(int userId, int questionId) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class);
            Dao<Option, String> optionDao = DaoManager.createDao(connectionSource, Option.class);

            QueryBuilder<Answer, String> answerToDelete = answerDao.queryBuilder();
            answerToDelete.where().eq("id_user", userId);
            QueryBuilder<Option, String> optionToDelete = optionDao.queryBuilder();
            optionToDelete.where().eq("id_question", questionId);
            List<Answer> answersOfQuestion = answerToDelete.join(optionToDelete).query();
            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<Answer, String> deleteAnswer = answerDao.deleteBuilder();

            for (int i = 0; i < answersOfQuestion.size(); i++) {
                // request initialization
                deleteAnswer.where().eq("id_user", userId).and().eq("id_option", answersOfQuestion.get(i).getOption().getId_option());
                // request execution
                deleteAnswer.delete();
            }
        } finally {
            connectionSource.close();
        }
    }

    public static long getAnswerCount() throws Exception {
        JdbcConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class);
            return answerDao.queryBuilder().countOf();
        } finally {
            connectionSource.close();
        }
    }


    public static List<Answer> getAnswersList(int questionID) throws SQLException, IOException {
        List<Answer> AnswerList = new ArrayList<>();
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class);
            Dao<Option, String> optionDao = DaoManager.createDao(connectionSource, Option.class);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);

            QueryBuilder<User, String> userQb = userDao.queryBuilder();
            QueryBuilder<Option, String> optionQb = optionDao.queryBuilder();
            optionQb.where().eq("id_question", questionID);
            AnswerList = answerDao.queryBuilder().join(userQb).join(optionQb).query();
            return AnswerList;

        } finally {
            connectionSource.close();
        }
    }

    public static List<Answer> getAnswers() throws SQLException, IOException {
        List<Answer> AnswerList = new ArrayList<>();
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class);
            Dao<Option, String> optionDao = DaoManager.createDao(connectionSource, Option.class);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);

            QueryBuilder<User, String> userQb = userDao.queryBuilder();
            QueryBuilder<Option, String> optionQb = optionDao.queryBuilder();
            AnswerList = answerDao.queryBuilder().join(userQb).join(optionQb).query();
            return AnswerList;

        } finally {
            connectionSource.close();
        }
    }

//    public static List<Answer> getAllAnswersOfQuestion(int id_question, long startRow, long endRow) throws SQLException, IOException {
//        JdbcConnectionSource connectionSource = null;
//        try {
//            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
//
//            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class);
//            Dao<Option, String> optionDao = DaoManager.createDao(connectionSource, Option.class);
//
//            QueryBuilder<Option, String> optionQb = optionDao.queryBuilder();
//            optionQb.where().eq("id_question", id_question);
//            QueryBuilder<Answer, String> answerQb = answerDao.queryBuilder();
//            // join with the order query
//            List<Answer> results = answerQb.join(optionQb).offset(startRow).limit(endRow).query();
//            return results;
//
//        } finally {
//            connectionSource.close();
//        }
//    }

    public static void deleteAnswer(Answer answer) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class);
//
            DeleteBuilder<Answer, String> deleteAnswer = answerDao.deleteBuilder();
            deleteAnswer.where().eq("id_option", answer.getOption().getId_option());
            // request execution
            deleteAnswer.delete();
        } finally {
            connectionSource.close();
        }
    }

    public static List<Answer> getUserAnswersList(User user) throws SQLException, IOException {
        List<Answer> AnswerList = new ArrayList<>();
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Answer, String> answerDao = DaoManager.createDao(connectionSource, Answer.class);
            return answerDao.queryBuilder().where().eq("id_user", user.getUser_id()).query();

        } finally {
            connectionSource.close();
        }
    }
}
