package com.brawijaya.proyekpbpu.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.brawijaya.proyekpbpu.data.local.entity.UserEntity
import com.brawijaya.proyekpbpu.data.local.room.UserDao
import com.brawijaya.proyekpbpu.data.local.room.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val mUserDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = UserDatabase.getDatabase(application)
        mUserDao = db.userDao()
    }
    fun getUserFavorite(): LiveData<List<UserEntity>> = mUserDao.getUserFavorite()
    fun insert(user: UserEntity) {
        executorService.execute { mUserDao.insertUser(user) }
    }
    fun delete(user: UserEntity) {
        executorService.execute { mUserDao.delete(user) }
    }
    fun update(user: UserEntity) {
        executorService.execute { mUserDao.updateUser(user) }
    }
}