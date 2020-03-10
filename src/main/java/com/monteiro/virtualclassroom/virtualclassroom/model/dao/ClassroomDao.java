package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.monteiro.virtualclassroom.virtualclassroom.ConstantsKt.*;

public class ClassroomDao {

    public ClassroomDao() {
    }

    public static void saveClassroom(Classroom classroom) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        // instantiate the dao with the connection source
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Classroom, String> classroomDao = DaoManager.createDao(connectionSource, Classroom.class); //creates a new dao object
            classroomDao.create(classroom);
        } finally {
            connectionSource.close();
        }
    }

    public static Classroom getClassroom(long firstId) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Classroom, String> clashClassroomDao = DaoManager.createDao(connectionSource, Classroom.class);//creates a new dao object

            return clashClassroomDao.queryBuilder().where().eq("id_classroom", firstId).queryForFirst();

        } finally {
            connectionSource.close();
        }
    }

    public Classroom getClassroomByName(String className) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Classroom, String> classroomDao = DaoManager.createDao(connectionSource, Classroom.class);

            return classroomDao.queryBuilder().where().eq("classroom_name", className).queryForFirst();

        } finally {
            connectionSource.close();
        }
    }

    public static List<Classroom> getClassroomRowsList(long offset, long end) throws IOException, SQLException {
        List<Classroom> classroomRowList;
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Classroom, Long> dao = DaoManager.createDao(connectionSource, Classroom.class);
            QueryBuilder<Classroom, Long> queryBuilder = dao.queryBuilder();
            queryBuilder.offset(offset).limit(end);
            PreparedQuery<Classroom> preparedQuery = queryBuilder.prepare();
            classroomRowList = dao.query(preparedQuery);
            return classroomRowList;

        } finally {
            connectionSource.close();
        }
    }

    public static List<Classroom> getClassroomList() throws SQLException, IOException {
        List<Classroom> classroomRowList;
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Classroom, Long> listDao = DaoManager.createDao(connectionSource, Classroom.class);
            classroomRowList = listDao.queryForAll();
            return classroomRowList;

        } finally {
            connectionSource.close();
        }
    }

    public static long getClassroomCount() throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            Dao<Classroom, String> ClassConnection = DaoManager.createDao(connectionSource, Classroom.class);//creates a new dao object
            return ClassConnection.queryBuilder().countOf();

        } finally {
            connectionSource.close();
        }
    }

    public static void deleteClassroom(Classroom classroom) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Classroom, String> classroomDao = DaoManager.createDao(connectionSource, Classroom.class);

            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<Classroom, String> deleteBuilder = classroomDao.deleteBuilder();
            // request initialization
            deleteBuilder.where().eq("id_classroom", classroom.getId_classroom());
            // request execution
            deleteBuilder.delete();
        } finally {
            connectionSource.close();
        }
    }

    public static void updateClassroomName(String oldValue, String newValue) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<Classroom, String> classroomUpdate = DaoManager.createDao(connectionSource, Classroom.class);
            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<Classroom, String> updateBuilder = classroomUpdate.updateBuilder();
            // set the criteria
            updateBuilder.where().eq("classroom_name", oldValue);
            // update the value of the target fields
            updateBuilder.updateColumnValue("classroom_name", newValue);
            // update execution
            updateBuilder.update();
        } finally {
            connectionSource.close();
        }
    }
}