package com.monteiro.virtualclassroom.virtualclassroom.model.dao;

// Import required packages

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.UpdateBuilder;
import com.j256.ormlite.stmt.Where;
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.User;

import java.io.IOException;
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

            Dao<User, String> clashUserDao = DaoManager.createDao(connectionSource, User.class);

            clashUserDao.createOrUpdate(user);
        } finally {
            connectionSource.close();
        }
    }

    public static User getUser(String email, String password) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        User gotUserMail = null;
        User gotUserPsw = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> clashUserDao = DaoManager.createDao(connectionSource, User.class); //creates a new dao object
            gotUserMail = clashUserDao.queryBuilder().where().eq("user_email", email).queryForFirst();
            gotUserPsw = clashUserDao.queryBuilder().where().eq("user_password", password).queryForFirst();
            System.out.println(gotUserMail);
            if (gotUserMail == gotUserPsw) {
                return gotUserMail;
            } else if (gotUserMail == null) {
                return null;
            }
        } finally {
            connectionSource.close();
        }
        return gotUserMail;
    }
    public static User  getUserByMail(String email) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        User gotUserMail = null;
        try {
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> clashUserDao = DaoManager.createDao(connectionSource, User.class); //creates a new dao object
            gotUserMail = clashUserDao.queryBuilder().where().eq("user_email", email).queryForFirst();
            System.out.println(gotUserMail);
            if(gotUserMail == null){
                return null;
            }
            else {
                return gotUserMail;
            }
        }  finally {
            connectionSource.close();
        }
    }


    public static List<User> readAll() throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> readTable = DaoManager.createDao(connectionSource, User.class);
            return readTable.queryForAll();
        } finally {

            connectionSource.close();
        }
    }

    // delete user
    public static void deleteUser(int id) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the dao with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> test = DaoManager.createDao(connectionSource, User.class);

            /*                    ----delete call----              */
            // DAO setting
            DeleteBuilder<User, String> deleteBuilder = test.deleteBuilder();
            // request initialization
            deleteBuilder.where().eq("id_user", id);
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
            System.out.println("in the update");
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> update = DaoManager.createDao(connectionSource, User.class);

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<User,String > updateBuilder = update.updateBuilder();
            // set the criteria
            updateBuilder.where().eq(column, oldValue);
            System.out.println("column update research");
            // update the value of the target fields
            updateBuilder.updateColumnValue(column, newValue);
            System.out.println("column update done");
            // update execution
            updateBuilder.update();
        } finally {

            connectionSource.close();
        }
    }

    public static void updateUserEmail(String oldValue, String newValue) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;
        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> update = DaoManager.createDao(connectionSource, User.class);

            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<User, String> updateBuilder = update.updateBuilder();
            // set the criteria
            updateBuilder.where().eq("user_email", oldValue);
            // update the value of the target fields
            updateBuilder.updateColumnValue(oldValue, newValue);
            // update execution
            updateBuilder.update();
        } finally {

            connectionSource.close();
        }
    }

    // update password user
    public void updatePwdUser(String user_email, String newPassword) throws SQLException, IOException {
        JdbcConnectionSource connectionSource = null;

        try {
            // initiate the DAO with the connection source
            connectionSource = new JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW);
            Dao<User, String> update = DaoManager.createDao(connectionSource, User.class);
            System.out.println("db_connection ok");
            System.out.println("entry_param1 : " + user_email);
            System.out.println("entry_param2 : " + newPassword);
            /*                      ----update call----                 */
            // DAO setting
            UpdateBuilder<User, String> updateBuilder = update.updateBuilder();

            // set the criteria like i would a QueryBuilder
            updateBuilder.where().eq("user_email", user_email);

            // print the query
//            System.out.println(updateBuilder.where().eq("user_email", user_email));

            // update the value of the target fields
            updateBuilder.updateColumnValue("user_password", newPassword);

            // print of targeted field
//            System.out.println(updateBuilder.updateColumnValue("user_password", newPassword));

            // query exec
            updateBuilder.update();

            //print the result of update
//            System.out.println(updateBuilder.update());

        } finally {
            connectionSource.close();
        }
    }
}
