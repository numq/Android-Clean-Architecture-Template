package com.numq.template.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.numq.template.core.model.Card
import com.numq.template.data.cards.CardsDao

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val cardsDao: CardsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            var instance = INSTANCE

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
            }

            return instance
        }
    }
}