package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

// Import required packages

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.Classroom;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;

import static com.monteiro.virtualclassroom.virtualclassroom.ConstantsKt.*;


public class UserDao {

    public UserDao() {

    }

    public static void saveUser(User user) throws Exception {
        JdbcConnectionSource connectionSource = null;
        // instantiate the dao with the connection source
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);

            user.setUser_email(user.getUser_email().toLowerCase()); //email en minuscule
            user.setUser_name(StringUtils.capitalize(user.getUser_name().toLowerCase())); //Nom 1 er lettre en maj

            // initiate  user
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);
            // initiate classroom
            Dao<Classroom, String> classDao = DaoManager.createDao(connectionSource, Classroom.class);

            if (user.getIsAdmin() == false) {
                // refresh mandatory to get the FK
                classDao.refresh(user.getClassroom());
            }

            userDao.createOrUpdate(user);
        } finally {
            connectionSource.close();
        }
    }

    public static User getUserLogin(String email, String password) throws SQLException, IOException, NoSuchAlgorithmException {
        JdbcConnectionSource connectionSource = null;
        User gotUser;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class); //creates a new dao object
            gotUser = userDao.queryBuilder().where().eq("user_email", email.toLowerCase()).and().eq("user_password", User.hashPassword(password)).queryForFirst();
            return gotUser;
        } finally {
            connectionSource.close();
        }
    }

    public static User getUser(int userId) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        System.out.println("I am in the getUser method ");
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class); //creates a new dao object
            return userDao.queryBuilder().where().eq("id_user", userId).queryForFirst();
        } finally {
            connectionSource.close();
        }
    }


    public static List<User> getStudentsList(Classroom classroom) throws IOException, SQLException {
        List<User> studentList;
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, Long> userDao = DaoManager.createDao(connectionSource, User.class);
            studentList = userDao.queryBuilder().where().eq("id_classroom", classroom.getId_classroom()).query();
            return studentList;

        } finally {
            connectionSource.close();
        }
    }

    // delete user
    public static void deleteUser(User user) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);

            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<User, String> deleteBuilder = userDao.deleteBuilder();
            // request initialization
            deleteBuilder.where().eq("id_user", user.getUser_id());
            // request execution
            deleteBuilder.delete();
        } finally {

            connectionSource.close();
        }
    }

    // update user
    public static void updateUser(String column, String oldValue, String newValue) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<User, String> updateBuilder = userDao.updateBuilder();
            // set the criteria
            updateBuilder.where().eq(column, oldValue);
            // update the value of the target fields
            updateBuilder.updateColumnValue(column, newValue);
            System.out.println(column + newValue);
            System.out.println("column update done");
            // update execution
            updateBuilder.update();
        } finally {
            connectionSource.close();
        }
    }

    // update user
    public static boolean isAdmin() throws Exception {
        JdbcConnectionSource connectionSource = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class); //creates a new dao object
            return userDao.queryBuilder().where().eq("isAdmin", true).countOf() > 0;
        } finally {
            connectionSource.close();
        }
    }

    // update password user
    public void updatePwdUser(String user_email, String currentPassword, String newPassword) throws SQLException, IOException, NoSuchAlgorithmException {
        JdbcConnectionSource connectionSource = null;

        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> userDao = DaoManager.createDao(connectionSource, User.class);
            System.out.println("db_connection ok");

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<User, String> updateBuilder = userDao.updateBuilder();

            // set the criteria like i would a QueryBuilder
            updateBuilder.where().eq("user_email", user_email).and().eq("user_password", User.hashPassword(currentPassword));

            // update the value of the target fields
            updateBuilder.updateColumnValue("user_password", User.hashPassword(newPassword));

            // query exec
            updateBuilder.update();

        } finally {
            connectionSource.close();
        }
    }
}
