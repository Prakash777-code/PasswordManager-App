package com.example.vaultx.Models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "password_table")
data class PasswordItem(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val email: String,
    val password: String,

    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long = System.currentTimeMillis()

) : Parcelable