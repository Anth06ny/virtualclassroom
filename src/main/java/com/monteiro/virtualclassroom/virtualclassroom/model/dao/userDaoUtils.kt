package com.monteiro.virtualclassroom.virtualclassroom.model.dao

import com.j256.ormlite.dao.DaoManager
import com.j256.ormlite.jdbc.JdbcConnectionSource
import com.monteiro.virtualclassroom.virtualclassroom.BDD_ADMIN
import com.monteiro.virtualclassroom.virtualclassroom.BDD_PSW
import com.monteiro.virtualclassroom.virtualclassroom.BDD_URL
import com.monteiro.virtualclassroom.virtualclassroom.model.bean.UserBean
import java.util.*

fun daoGetUser(name: String): UserBean? {

    var connectionSource: JdbcConnectionSource? = null
    // instantiate the dao with the connection source
    try {
        connectionSource = JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW)
        val userDao = DaoManager.createDao(connectionSource, UserBean::class.java)

        return userDao.queryBuilder().where().eq("name", name).queryForFirst()

    } finally {
        connectionSource?.close()
    }
}


fun daoSaveUser(user: UserBean) {
    var connectionSource: JdbcConnectionSource? = null
    // instantiate the dao with the connection source
    try {
        connectionSource = JdbcConnectionSource(BDD_URL, BDD_ADMIN, BDD_PSW)

        val clashUserDao = DaoManager.createDao(connectionSource, UserBean::class.java)

        clashUserDao.createOrUpdate(user)

    } finally {
        connectionSource?.close()
    }
}