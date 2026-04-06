package com.example.vaultx.Local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.vaultx.Models.PasswordItem
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Insert
    suspend fun insert(item: PasswordItem)

    @Update
    suspend fun update(item: PasswordItem)

    @Delete
    suspend fun delete(item: PasswordItem)

    @Query("SELECT * FROM password_table ORDER BY updatedAt DESC")
    fun getPasswords(): LiveData<List<PasswordItem>>

    @Query("""
    SELECT * FROM password_table 
    WHERE title LIKE '%' || :query || '%' 
    OR email LIKE '%' || :query || '%' 
    ORDER BY updatedAt DESC
    """)
    fun searchPasswords(query: String):  LiveData<List<PasswordItem>>


}