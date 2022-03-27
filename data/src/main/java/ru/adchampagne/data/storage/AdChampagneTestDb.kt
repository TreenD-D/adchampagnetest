package ru.adchampagne.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.adchampagne.data.storage.dao.UserDao
import ru.adchampagne.data.storage.entity.UserEntity

@Database(
    entities = [
        UserEntity::class,
    ],
    version = 1,
    exportSchema = false
)

abstract class AdChampagneTestDb : RoomDatabase() {
    abstract fun usersDao(): UserDao
}