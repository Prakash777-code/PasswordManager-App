package com.example.vaultx.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vaultx.Models.PasswordItem

@Database(entities = [PasswordItem::class], version = 1)
abstract class PasswordDatabse : RoomDatabase(){

    abstract fun passwordDao(): PasswordDao

    companion object {

        @Volatile
        private var INSTANCE: PasswordDatabse? = null

        fun getInstance(context: Context): PasswordDatabse {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PasswordDatabse::class.java,
                    "password_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}