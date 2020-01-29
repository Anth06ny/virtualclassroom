package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Question;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.monteiro.virtualclassroom.virtualclassroom.ConstantsKt.*;

/**
 * La classe pour les requetes sur les questions
 */
public class QuestionDao {
    // constructor
    public QuestionDao(){
    }

    /**
     * Sauvegarde ou modifie la question en base
     */
    public static void saveQuestion(Question question) throws Exception {
        JdbcConnectionSource connectionSource = null;
        // instantiate the dao with the connection source
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Question, String> clashUserDao = DaoManager.createDao(connectionSource, Question.class);

            clashUserDao.createOrUpdate(question);
        } finally {
            connectionSource.close();
        }
    }

    // retrieve all results

    /**
     * @param idClassRoom id de la classe
     * @return les questions de la classe
     */
    public static List<Question> getAllQuestionFromId(int idClassRoom) throws Exception {
        JdbcConnectionSource connectionSource = null;
        try{
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String> clashQuestionDao = DaoManager.createDao(connectionSource, Question.class);
            return clashQuestionDao.queryBuilder().where().eq("id_classroom", idClassRoom).query();
        } finally {
            connectionSource.close();
        }
    }

    // retrieve question method
    public static Question getQuestion(Question question) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String> clashUserDao = DaoManager.createDao(connectionSource, Question.class); //creates a new dao object
            return clashUserDao.queryBuilder().where().eq("question_content", question).queryForFirst();
        }  finally {
            connectionSource.close();
        }
    }

    // delete question method
    public static void deleteQuestion(int id) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String> question = DaoManager.createDao(connectionSource, Question.class);

            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<Question, String> deleteBuilder = question.deleteBuilder();
            // request initialization
            deleteBuilder.where().eq("id_question", id);
            // request execution
            deleteBuilder.delete();
        } finally {
            connectionSource.close();
        }
    }

    // update question
    public static void updateQuestion(int id,String targetColumn, String newValue) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String>  questionUpdate = DaoManager.createDao(connectionSource, Question.class);

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<Question,String > updateBuilder = questionUpdate.updateBuilder();
            // set the criteria
            updateBuilder.where().eq("id_question", id);
            // update the value of the target fields
            updateBuilder.updateColumnValue(targetColumn, newValue);
            // update execution
            updateBuilder.update();
        } finally {
            connectionSource.close();
        }
    }
}
