package com.brawijaya.proyekpbpu.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.brawijaya.proyekpbpu.BuildConfig
import com.brawijaya.proyekpbpu.data.UserRepository
import com.brawijaya.proyekpbpu.data.local.entity.UserEntity
import com.brawijaya.proyekpbpu.data.local.room.UserDao
import com.brawijaya.proyekpbpu.data.local.room.UserDatabase
import com.brawijaya.proyekpbpu.utils.ResponseStatus
import com.example.githubusertest.response.DetailUserResponse
import com.example.testlibrarygit.retrofit.ApiGithubConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel(application: Application)  : AndroidViewModel(application){
    private val mUserRepository: UserRepository = UserRepository(application)

    private var userDatabase: UserDatabase? = UserDatabase.getDatabase(application)
    private var userDao: UserDao? = userDatabase?.userDao()

    var userData = MutableLiveData<DetailUserResponse>()
    val isLoading = MutableLiveData(true)
    val stringError = MutableLiveData<String>()

    private fun setDetailUser(user: DetailUserResponse) {
        userData.postValue(user)
    }

    fun getDetail(query: String) {
        isLoading.postValue(true)
        val client = ApiGithubConfig.getApiService().getDetailUser(BuildConfig.API_TOKEN, query)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                isLoading.postValue(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setDetailUser(responseBody)
                    }
                } else {
                    val errorMessage = when (val statusCode = response.code()) {
                        ResponseStatus.BAD_REQUEST.stat -> "$statusCode : Bad Request"
                        ResponseStatus.FORBIDDEN.stat -> "$statusCode : Forbidden"
                        ResponseStatus.NOT_FOUND.stat -> "$statusCode : Not Found"
                        else -> "$statusCode"
                    }
                    Log.e(MainViewModel.TAG, errorMessage)
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                isLoading.postValue(false)
                stringError.postValue(t.message)
                Log.e(MainViewModel.TAG, t.message.toString())
                t.printStackTrace()
            }
        })
    }

    fun insert(user: UserEntity) {
        mUserRepository.insert(UserEntity(user.id, user.username, user.urlToImage))
    }
    fun update(user: UserEntity) {
        mUserRepository.update(user)
    }
    fun delete(user: UserEntity) {
        mUserRepository.delete(user)
    }
    suspend fun isFavorite(id: String) = userDao?.isUserFavorite(id)
}