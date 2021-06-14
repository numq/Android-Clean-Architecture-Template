package com.numq.template.presentation.usecases

import com.numq.template.data.cards.CardsRepository
import com.numq.template.core.interactor.UseCase
import com.numq.template.core.model.Card
import javax.inject.Inject

class DeleteCard
@Inject constructor(private val cardsRepository: CardsRepository) : UseCase<Card, Unit>() {

    override suspend fun run(params: Card) = cardsRepository.deleteCard(params)
}