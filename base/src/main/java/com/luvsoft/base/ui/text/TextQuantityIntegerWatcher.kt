package com.luvsoft.base.ui.text

import android.text.Editable
import com.google.android.material.internal.TextWatcherAdapter

const val DEFAULT_VALUE = '0'
const val DEFAULT_VALUE_Pass = ""
const val FIRST_CHARACTER = 0
const val SECOND_CHARACTER = 1

class TextQuantityIntegerWatcher : com.luvsoft.base.ui.adapters.TextWatcherAdapter() {

    override fun afterTextChanged(editable: Editable) {
        if (editable.isNotBlank()) handleNotAllowMoreThanSpecified(editable)
        else editable.append(DEFAULT_VALUE)
    }

    private fun handleNotAllowMoreThanSpecified(editable: Editable) {
        val newUnits = editable.toString()
        if (newUnits.length > SECOND_CHARACTER) {
            if (newUnits.first() == DEFAULT_VALUE && newUnits[SECOND_CHARACTER] != DEFAULT_VALUE) {
                editable.delete(FIRST_CHARACTER, SECOND_CHARACTER)
            }
        }
    }
}


class TextQuantityStringWatcher : com.luvsoft.base.ui.adapters.TextWatcherAdapter() {

    override fun afterTextChanged(editable: Editable) {
        if (editable.isNotBlank()) handleNotAllowMoreThanSpecified(editable)
        else editable.append(DEFAULT_VALUE_Pass)
    }

    private fun handleNotAllowMoreThanSpecified(editable: Editable) {

    }
}