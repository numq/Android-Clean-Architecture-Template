package com.numq.template.presentation.cards

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.numq.template.R
import com.numq.template.core.base.BaseFragment
import com.numq.template.core.extension.*
import com.numq.template.core.model.Card
import com.numq.template.core.navigation.Router
import com.numq.template.databinding.CardsFragmentBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CardsFragment : BaseFragment<CardsFragmentBinding>(CardsFragmentBinding::inflate) {

    private val cardsViewModel: CardsViewModel by lazy {
        ViewModelProvider(this.requireActivity()).get(CardsViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.requireActivity().showToolBar()

        val navController = this.findNavController()

        val rvCards = binding.rvCards
        val cardsAdapter = CardsAdapter()
        with(rvCards) {
            layoutManager = GridLayoutManager(this.context, 2)
            adapter = cardsAdapter
        }

        cardsAdapter.onClick = { _, card ->
            navController.navigate(
                Router.HOME_TO_DETAIL,
                bundleOf(Router.HOME_TO_DETAIL.toString() to card)
            )
        }

        cardsAdapter.onLongClick = { _, card ->
            val delete: () -> Unit = {
                val wait: () -> Unit = { this.requireActivity().showProgressBar() }
                val then: () -> Unit = {
                    this.requireActivity().hideProgressBar()
                    cardsViewModel.getCards()
                }
                cardsViewModel.deleteCard(card).waitThen(wait, then)
            }
            this.requireActivity()
                .createYesNoDialog(getString(R.string.dialog_delete_item), delete).show()
        }

        val btnAddCard = binding.btnAddCard
        val addCardDialog = addNewCard()
        with(btnAddCard) {
            btnAddCard.setOnClickListener {
                addCardDialog.show()
            }
            addCardDialog.setOnShowListener {
                visibility = View.GONE
            }
            addCardDialog.setOnDismissListener {
                fadeInAnimation()
            }
        }

        val cardsObserver = Observer<List<Card>> {
            if (it.isEmpty()) this.requireActivity().showPlaceholder(animated = true)
            else this.requireActivity().hidePlaceholder()
            cardsAdapter.updateData(it)
        }

        cardsViewModel.cards.observe(this.requireActivity(), cardsObserver)
    }

    private fun addNewCard(): AlertDialog {
        val dialog = MaterialAlertDialogBuilder(this.requireContext())
        dialog.setView(R.layout.dialog_add_card)
        dialog.setPositiveButton(null) { alert, _ ->
            val alertDialog = (alert as AlertDialog?)
            alertDialog?.let {
                val front = it.findViewById<TextInputEditText>(R.id.alertFront)
                val back = it.findViewById<TextInputEditText>(R.id.alertBack)
                val card =
                    Card(frontText = front?.text.toString(), backText = back?.text.toString())
                if (card.frontText.trim() != "" && card.backText.trim() != "") {
                    val wait: () -> Unit = { this.requireActivity().showProgressBar() }
                    val then: () -> Unit = {
                        this.requireActivity().hideProgressBar()
                        cardsViewModel.getCards()
                        front?.setText("")
                        back?.setText("")
                    }
                    cardsViewModel.insertCard(card).waitThen(wait, then)
                } else {
                    this.requireActivity()
                        .showToast(getString(R.string.dialog_fields_should_be_filled))
                }
            }
        }.setPositiveButtonIcon(
            ContextCompat.getDrawable(
                this.requireContext(),
                R.drawable.outline_done_24
            )
        )
        return dialog.create()
    }
}