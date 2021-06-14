package com.numq.template.core.extension

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textview.MaterialTextView
import com.numq.template.R

fun Activity.showToolBar() {
    val toolbar = this.findViewById(R.id.toolbarLayout) as? AppBarLayout
    toolbar?.visibility = View.VISIBLE
}

fun Activity.hideToolBar() {
    val toolbar = this.findViewById(R.id.toolbarLayout) as? AppBarLayout
    toolbar?.visibility = View.GONE
}

fun Activity.showProgressBar() {
    val progressBar = this.findViewById<ProgressBar>(R.id.progress_bar)
    progressBar.visibility = View.VISIBLE
}

fun Activity.hideProgressBar() {
    val progressBar = this.findViewById<ProgressBar>(R.id.progress_bar)
    progressBar.visibility = View.GONE
}

fun Activity.showPlaceholder(
    text: String? = null,
    image: Drawable? = null,
    animated: Boolean = false
) {
    val placeholder = this.findViewById<LinearLayout>(R.id.placeholderLayout)
    val placeholderText = this.findViewById<MaterialTextView>(R.id.placeholderText)
    text?.let { placeholderText.text = it }
    val placeholderImage = this.findViewById<ImageView>(R.id.placeholderImage)
    image?.let { placeholderImage.setImageDrawable(it) }
    if (animated) placeholder.fadeInAnimation()
    placeholder.visibility = View.VISIBLE
}

fun Activity.hidePlaceholder() {
    val placeholder = this.findViewById<LinearLayout>(R.id.placeholderLayout)
    placeholder.visibility = View.GONE
}

val Activity.placeholderShowing
    get() = this.findViewById<LinearLayout>(R.id.placeholderLayout).visibility == View.VISIBLE

val Activity.isKeyboardShowing
    get(): Boolean {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.isActive
    }

val Activity.hideKeyboard
    get(): Unit {
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

fun Activity.drawable(id: Int) = ContextCompat.getDrawable(this, id)

fun Activity.showToast(text: String, shortLength: Boolean = true) {
    val length = if (shortLength) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    Toast.makeText(this, text, length).show()
}

fun Activity.createYesNoDialog(
    text: String = "",
    yes: () -> Unit = {},
    no: () -> Unit = {}
): AlertDialog {
    val dialog = MaterialAlertDialogBuilder(this)
    with(dialog) {
        setTitle(text)
        setPositiveButton(null) { alert, _ ->
            yes()
            alert.dismiss()
        }
        setPositiveButtonIcon(drawable(R.drawable.outline_done_24))
        setNegativeButton(null) { alert, _ ->
            no()
            alert.dismiss()
        }
        setNegativeButtonIcon(drawable(R.drawable.outline_close_24))
    }
    return dialog.create()
}

fun Activity.createOkDialog(text: String = "", ok: () -> Unit = {}): AlertDialog {
    val dialog = MaterialAlertDialogBuilder(this)
    with(dialog) {
        setTitle(text)
        setNeutralButton(null) { _, _ ->
            ok()
        }
        setPositiveButtonIcon(drawable(R.drawable.outline_done_24))
    }
    return dialog.create()
}