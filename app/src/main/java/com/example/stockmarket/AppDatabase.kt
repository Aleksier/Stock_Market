package com.example.stockmarket

/*
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoritesCompanies::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoritesCompaniesDao(): FavoritesCompaniesDao

    companion object {
        private const val DATABASE_NAME = "FavoritesCompanies.db"
        fun getInstance(applicationContext: Context): AppDatabase =
           Room.databaseBuilder(
               applicationContext,
               AppDatabase::class.java,
               DATABASE_NAME
           ).allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

    }
}
 */