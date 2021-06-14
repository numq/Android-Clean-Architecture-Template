package com.numq.template.presentation.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.numq.template.core.interactor.None
import com.numq.template.core.model.Card
import com.numq.template.presentation.usecases.DeleteCard
import com.numq.template.presentation.usecases.GetCards
import com.numq.template.presentation.usecases.InsertCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CardsViewModel
@Inject constructor(
    private val getCards: GetCards,
    private val insertCard: InsertCard,
    private val deleteCard: DeleteCard
) : ViewModel() {

    private val _cards = MutableLiveData<List<Card>>()
    val cards: LiveData<List<Card>> = _cards

    init {
        getCards()
    }

    fun getCards() = viewModelScope.launch(Dispatchers.IO) {
        val data = getCards.run(None()).data
        withContext(Dispatchers.Main) {
            _cards.value = data ?: listOf()
        }
    }

    fun insertCard(card: Card) = viewModelScope.async {
        insertCard.run(card)
    }

    fun deleteCard(card: Card) = viewModelScope.async {
        deleteCard.run(card)
    }
}
