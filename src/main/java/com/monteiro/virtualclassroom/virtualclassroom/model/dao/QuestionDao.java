package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Option;
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
    public QuestionDao() {
    }

    /**
     * Save or update the question in the database
     */
    public static void saveQuestion(Question question) throws Exception {
        JdbcConnectionSource connectionSource = null;
        // instantiate the dao with the connection source
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Question, String> questionDao = DaoManager.createDao(connectionSource, Question.class);
            Dao<Classroom, String> classDao = DaoManager.createDao(connectionSource, Classroom.class);

            classDao.refresh(question.getClassroom());
            questionDao.createOrUpdate(question);
        } finally {
            connectionSource.close();
        }
    }

    // retrieve all results

    /**
     * @param idClassRoom id de la classe
     * @return les questions de la classe
     */
    public static List<Question> getAllQuestionsFromId(long idClassRoom, long startRow, long endRow) throws Exception {

        // initiate connectionSource
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, Long> clashQuestionDao = DaoManager.createDao(connectionSource, Question.class);
            // retrieve options from it
            return clashQuestionDao.queryBuilder().offset(startRow).limit(endRow).where().eq("id_classroom", idClassRoom).query();
        } finally {
            connectionSource.close();
        }
    }


    public static long getQuestionCount() throws Exception {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String> clashQuestionDao = DaoManager.createDao(connectionSource, Question.class);
            return clashQuestionDao.queryBuilder().countOf();
        } finally {
            connectionSource.close();
        }
    }

    // retrieve question method
    public static Question getQuestion(int id) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String> questionDao = DaoManager.createDao(connectionSource, Question.class); //creates a new dao object
            return questionDao.queryBuilder().where().eq("id_question", id).queryForFirst();
        } finally {
            connectionSource.close();
        }
    }

    // delete question method
    public static void deleteQuestion(Question question) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String> questionDao = DaoManager.createDao(connectionSource, Question.class);
            Dao<Option, String> optionDao = DaoManager.createDao(connectionSource, Option.class);

            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<Question, String> deleteBuilder = questionDao.deleteBuilder();
            // request initialization
            deleteBuilder.where().eq("id_question", question.getId_question());
            // request execution
            deleteBuilder.delete();
        } finally {
            connectionSource.close();
        }
    }

    // update question
    public static void updateQuestion(int id, String newValue) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, String> questionUpdate = DaoManager.createDao(connectionSource, Question.class);

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<Question, String> updateBuilder = questionUpdate.updateBuilder();
            // set the criteria
            updateBuilder.where().eq("id_question", id);
            // update the value of the target fields
            updateBuilder.updateColumnValue("question_content", newValue);
            // update execution
            updateBuilder.update();
        } finally {
            connectionSource.close();
        }
    }

    public static List<Question> getQuestionList(long id_classroom) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Question, Long> questionDao = DaoManager.createDao(connectionSource, Question.class);

            return questionDao.queryBuilder().where().eq("id_classroom", id_classroom).query();

        } finally {
            connectionSource.close();
        }
    }
}
