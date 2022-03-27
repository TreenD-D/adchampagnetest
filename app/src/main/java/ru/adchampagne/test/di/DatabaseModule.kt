package ru.adchampagne.test.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import ru.adchampagne.data.storage.AdChampagneTestDb

internal val databaseModule = module {
    single {
        Room
            .databaseBuilder(
                androidContext(),
                AdChampagneTestDb::class.java,
                "adchampagnetestusers.db"
            )
            .fallbackToDestructiveMigration()
            .build()
    }

    factory { get<AdChampagneTestDb>().usersDao() }
}