package com.numq.template.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.numq.template.core.model.Card
import com.numq.template.data.cards.CardsDao

@Database(entities = [Card::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val cardsDao: CardsDao
}