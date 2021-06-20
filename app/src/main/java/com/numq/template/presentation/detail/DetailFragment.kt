package com.numq.template.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.numq.template.core.base.BaseFragment
import com.numq.template.core.constants.AppConstants
import com.numq.template.core.extension.hideToolBar
import com.numq.template.core.model.Card
import com.numq.template.core.navigation.Router
import com.numq.template.databinding.DetailFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<DetailFragmentBinding>(DetailFragmentBinding::inflate) {

    private val detailViewModel: DetailViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.requireActivity().hideToolBar()

        val navController = this.findNavController()

        val card = arguments?.getParcelable(AppConstants.ARGUMENT_CARD) as Card?
        if (card != null) {
            binding.frontText.text = card.frontText
            binding.backText.text = card.backText
        } else {
            this.requireActivity().onBackPressed()
        }

        this.requireActivity().onBackPressedDispatcher.addCallback {
            navController.navigate(Router.DETAIL_TO_CARDS)
        }
    }
}