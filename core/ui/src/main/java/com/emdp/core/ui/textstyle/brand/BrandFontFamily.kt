package com.emdp.core.ui.textstyle.brand

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.emdp.core.ui.R

object BrandFontFamily {
    val defaultBrandFont = FontFamily(
        Font(resId = R.font.bangers_regular,
            weight = FontWeight.Bold
        )
    )
}