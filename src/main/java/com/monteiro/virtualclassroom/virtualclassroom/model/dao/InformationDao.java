package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Information;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Question;

import java.io.IOException;
import java.sql.SQLException;

import static com.monteiro.virtualclassroom.virtualclassroom.ConstantsKt.*;

public class InformationDao {

    // constructor
    public InformationDao() {}

    // save information method
    public static void saveInformation(Information information) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        // instantiate the dao with the connection source
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Information, String> clashInformationDao = DaoManager.createDao(connectionSource, Information.class); //creates a new dao object
            clashInformationDao.createOrUpdate(information);
        } finally {
            connectionSource.close();
        }
    }

    // retrieve information method
    public static Information getClassroom(int information) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Information, String> clashInformationDao = DaoManager.createDao(connectionSource, Information.class);//creates a new dao object

            return clashInformationDao.queryBuilder().where().eq("id_classroom", information).queryForFirst();

        }  finally {
            connectionSource.close();
        }
    }

    // delete information method
    public static void deleteInformation(int id) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Information, String> information = DaoManager.createDao(connectionSource, Information.class);

            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<Information, String> deleteBuilder = information.deleteBuilder();
            // request initialization
            deleteBuilder.where().eq("id_information", id);
            // request execution
            deleteBuilder.delete();
        } finally {
            connectionSource.close();
        }
    }

    // update information
    public static void updateQuestion(int id,String targetColumn, String newValue) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Information, String>  informationUpdate = DaoManager.createDao(connectionSource, Information.class);

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<Information,String > updateBuilder = informationUpdate.updateBuilder();
            // set the criteria
            updateBuilder.where().eq("id_information", id);
            // update the value of the target fields
            updateBuilder.updateColumnValue(targetColumn, newValue);
            // update execution
            updateBuilder.update();
        } finally {
            connectionSource.close();
        }
    }
}
