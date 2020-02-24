package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Option;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Question;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.monteiro.virtualclassroom.virtualclassroom.ConstantsKt.*;


public class OptionDao {

    // constructor
    public OptionDao() {
    }

    // save classroom method
    public static void saveOption(Option option) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        // instantiate the dao with the connection source
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Option, String> clashOptionDao = DaoManager.createDao(connectionSource, Option.class);

            // initiate classroom
            Dao<Question, String> questionDao = DaoManager.createDao(connectionSource, Question.class);

            questionDao.refresh(option.getQuestion());
            clashOptionDao.createOrUpdate(option);
        } finally {
            connectionSource.close();
        }
    }

    /**
     * @return row count of the target table
     * @throws SQLException
     * @throws IOException
     */
    public static long getOptionCount() throws Exception {
        JdbcConnectionSource connectionSource = null;

        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Option, String> optionDao = DaoManager.createDao(connectionSource, Option.class);
            return optionDao.queryBuilder().countOf();
        } finally {
            connectionSource.close();
        }
    }

    /**
     * @param idQuestion id de la question
     * @return les options de reponse de la question
     */
    public static List<Option> getAllOptionsFromQuestion(int idQuestion, long startRow, long endRow) throws Exception {

        // prepare connectionSource
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Option, Long> OptionDao = DaoManager.createDao(connectionSource, Option.class);
            return OptionDao.queryBuilder().offset(startRow).limit(endRow).where().eq("id_question", idQuestion).query();
        } finally {
            connectionSource.close();
        }
    }

    /**
     * @param option_id
     * @return an option
     * @throws SQLException
     * @throws IOException
     */
    // retrieve classroom method
    public static Option getOption(int option_id) throws Exception {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Option, String> clashOptionDao = DaoManager.createDao(connectionSource, Option.class);//creates a new dao object

            return clashOptionDao.queryBuilder().where().eq("id_option", option_id).queryForFirst();

        } finally {
            connectionSource.close();
        }
    }

    // delete information method
    public static void deleteOption(int id) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Option, String> option = DaoManager.createDao(connectionSource, Option.class);

            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<Option, String> deleteBuilder = option.deleteBuilder();
            // request initialization
            deleteBuilder.where().eq("id_option", id);
            // request execution
            deleteBuilder.delete();
        } finally {
            connectionSource.close();
        }
    }

    // update Option
    public static void updateOption(int id, String newValue) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Option, String> optionUpdate = DaoManager.createDao(connectionSource, Option.class);

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<Option, String> updateBuilder = optionUpdate.updateBuilder();
            // set the criteria
            updateBuilder.where().eq("id_option", id);
            // update the value of the target fields
            updateBuilder.updateColumnValue("option_content", newValue);
            // update execution
            updateBuilder.update();
        } finally {
            connectionSource.close();
        }
    }

    public static List<Option> getOptionList(int idQuestion) throws IOException, SQLException {
        List<Option> optionList;
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Option, Integer> optionDao = DaoManager.createDao(connectionSource, Option.class);
            optionList = optionDao.queryBuilder().where().eq("id_question", idQuestion).query();
            return optionList;

        } finally {
            connectionSource.close();
        }
    }

}
