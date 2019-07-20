package ru.skillbranch.devintensive.extensions

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.inputmethod.InputMethodManager

fun Activity.hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
}

fun Activity.isKeyboardOpen(): Boolean {
    val rect = Rect()
    val rootView: View = getWindow().getDecorView().findViewById(android.R.id.content)
    rootView.getWindowVisibleDisplayFrame(rect)

    val screenHeight = rootView.height
    val keypadHeight = screenHeight - rect.bottom
    return keypadHeight > screenHeight * 0.15
}

fun Activity.isKeyboardClosed(): Boolean {
    return !isKeyboardOpen()
}