package com.brawijaya.proyekpbpu.data.local.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user")
@Parcelize
data class UserEntity(
    @ColumnInfo(name = "id")
    @PrimaryKey
    var id: String,

    @ColumnInfo(name = "username")
    var username: String,

    @ColumnInfo(name = "urlToImage")
    var urlToImage: String,

    ) : Parcelable