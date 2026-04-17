package com.example.vaultx.Repository

import androidx.lifecycle.LiveData
import com.example.vaultx.Local.PasswordDao
import com.example.vaultx.Models.PasswordItem
import kotlinx.coroutines.flow.Flow

class PasswordRepository(private val dao: PasswordDao) {

    val passwords: LiveData<List<PasswordItem>> = dao.getPasswords()
    suspend fun insert(item: PasswordItem) {
        dao.insert(
            item.copy(
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun update(item: PasswordItem) {
        dao.update(
            item.copy(
                updatedAt = System.currentTimeMillis()
            )
        )
    }

    suspend fun delete(item: PasswordItem) {
        dao.delete(item)
    }

    fun searchPasswords(query: String):LiveData<List<PasswordItem>>{
        return dao.searchPasswords(query)
    }
}