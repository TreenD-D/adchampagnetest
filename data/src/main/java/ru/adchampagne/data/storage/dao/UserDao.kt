package ru.adchampagne.data.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ru.adchampagne.data.storage.entity.UserEntity

@Dao
interface UserDao {
    @Query("SELECT * FROM users WHERE id=:id LIMIT 1")
    suspend fun getEntity(id: Long): UserEntity

    @Query("SELECT * FROM users WHERE mail=:mail LIMIT 1")
    suspend fun getEntityByMail(mail: String): UserEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(user: UserEntity): Long

    @Query("DELETE FROM users")
    suspend fun drop()

    @Query("DELETE FROM users WHERE id=:id")
    fun deleteSingleUser(id: Long)
}