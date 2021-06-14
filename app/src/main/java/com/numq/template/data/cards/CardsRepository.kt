package com.numq.template.data.cards

import com.numq.template.core.model.Card
import com.numq.template.core.type.Resource
import javax.inject.Inject

interface CardsRepository {

    fun getAll(): Resource<List<Card>>
    suspend fun insertCard(card: Card)
    suspend fun updateCard(card: Card)
    suspend fun deleteCard(card: Card)

    class Cache
    @Inject constructor(
        private val cardsDao: CardsDao
    ) : CardsRepository {

        override fun getAll() = Resource(Resource.Status.SUCCESS, cardsDao.getAll(), "")
        override suspend fun insertCard(card: Card) = cardsDao.insertCard(card)
        override suspend fun updateCard(card: Card) = cardsDao.updateCard(card)
        override suspend fun deleteCard(card: Card) = cardsDao.deleteCard(card)
    }

    class Network

}