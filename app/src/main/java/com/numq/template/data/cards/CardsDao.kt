package com.numq.template.data.cards

import androidx.room.*
import com.numq.template.core.model.Card

@Dao
interface CardsDao {

    @Query("SELECT * FROM cards")
    fun getAll(): List<Card>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCard(card: Card)

    @Update
    suspend fun updateCard(card: Card)

    @Delete
    suspend fun deleteCard(card: Card)
}