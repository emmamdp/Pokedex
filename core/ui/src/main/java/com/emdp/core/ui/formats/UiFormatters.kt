package com.emdp.core.ui.formats

import java.util.Locale

object UiFormatters {

    fun formatPokedexId(id: Int, digits: Int = 4): String =
        "#${id.toString().padStart(digits, '0')}"

    fun formatWeightToKg(weightHg: Int?): String =
        weightHg?.let { hg ->
            val kg = hg / 10.0
            "${formatOneDecimal(kg)} Kg"
        } ?: "-"

    fun formatHeightToM(heightDm: Int?): String =
        heightDm?.let { dm ->
            val m = dm / 10.0
            "${formatOneDecimal(m)} m"
        } ?: "-"

    private fun formatOneDecimal(value: Double): String =
        String.format(Locale.getDefault(), "%.1f", value)
}