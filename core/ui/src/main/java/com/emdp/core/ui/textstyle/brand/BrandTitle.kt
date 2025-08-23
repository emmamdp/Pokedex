package com.emdp.core.ui.textstyle.brand

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun BrandTitle(
    text: String,
    size: BrandTitleSize = BrandTitleSize.M,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onBackground,
    maxLines: Int = 1,
    textAlign: TextAlign? = null
) {
    val fontSize: TextUnit = size.sp.sp
    val lineHeight: TextUnit = when (size) {
        BrandTitleSize.S -> 24.sp
        BrandTitleSize.M -> 34.sp
        BrandTitleSize.L -> 42.sp
    }
    val letterSpacing = when (size) {
        BrandTitleSize.S -> 0.1.sp
        BrandTitleSize.M -> 0.25.sp
        BrandTitleSize.L -> 0.3.sp
    }

    Text(
        text = text,
        modifier = modifier,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign,
        style = TextStyle(
            fontFamily = BrandFontFamily.defaultBrandFont,
            fontWeight = FontWeight.Bold,
            fontSize = fontSize,
            lineHeight = lineHeight,
            letterSpacing = letterSpacing
        )
    )
}