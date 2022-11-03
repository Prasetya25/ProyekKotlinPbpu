package com.brawijaya.data.remote.response

import com.google.gson.annotations.SerializedName

data class FindUserResponse(

    @field:SerializedName("items")
    val items: List<UserResponse>

)
