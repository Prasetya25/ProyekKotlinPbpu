package com.brawijaya.proyekpbpu.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.brawijaya.proyekpbpu.data.UserRepository
import com.brawijaya.proyekpbpu.data.local.entity.UserEntity

class FavoriteViewModel (application: Application) : AndroidViewModel(application) {
    private val mUserRepository: UserRepository = UserRepository(application)
    fun getUserFavorite(): LiveData<List<UserEntity>> = mUserRepository.getUserFavorite()
}