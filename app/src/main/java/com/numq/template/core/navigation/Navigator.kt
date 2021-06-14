package com.numq.template.core.navigation

import android.os.Bundle
import androidx.navigation.NavController
import com.numq.template.R
import javax.inject.Inject

class Navigator @Inject constructor() {

    @Inject
    lateinit var navController: NavController

    fun showCards(bundle: Bundle? = null) = navController.navigate(R.id.cardsFragment, bundle)

    fun showCardDetails(bundle: Bundle? = null) =
        navController.navigate(R.id.detailFragment, bundle)
}