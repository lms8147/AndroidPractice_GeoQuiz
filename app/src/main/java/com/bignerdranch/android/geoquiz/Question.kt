package com.bignerdranch.android.geoquiz

import androidx.annotation.StringRes

data class Question(@StringRes val textResId: Int, val answer: Boolean) {
    fun check(answer: Boolean): Boolean {
        return this.answer == answer
    }
}
