package com.numq.template.presentation.usecases

import com.numq.template.data.cards.CardsRepository
import com.numq.template.core.interactor.None
import com.numq.template.core.interactor.UseCase
import com.numq.template.core.model.Card
import com.numq.template.core.type.Resource
import javax.inject.Inject

class GetCards
@Inject constructor(private val cardsRepository: CardsRepository) :
    UseCase<None, Resource<List<Card>>>() {

    override suspend fun run(params: None) = cardsRepository.getAll()
}