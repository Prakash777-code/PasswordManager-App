package com.example.vaultx.Local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vaultx.Models.PasswordItem
import com.example.vaultx.Util.AppConstants.DbConstants

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
                    DbConstants.DATABASE_NAME
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}