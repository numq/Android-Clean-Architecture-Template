package com.numq.template.core.navigation

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.numq.template.R
import com.numq.template.databinding.ActivityRouteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RouteActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityRouteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRouteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController = findNavController(R.id.navHostFragment)
        setupToolbar()
    }

    fun showProgress() = progressStatus(View.VISIBLE)

    fun hideProgress() = progressStatus(View.GONE)

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbarLayout.toolbar)
    }

    private fun progressStatus(viewStatus: Int) {
        binding.toolbarLayout.toolbarProgressBar.visibility = viewStatus
    }

    fun hideSoftKeyboard() {
        currentFocus?.let {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}