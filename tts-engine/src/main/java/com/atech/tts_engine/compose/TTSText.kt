/*
 * Created by Ayaan on 02/02/23, 10:30 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 02/02/23, 10:17 pm
 */

package com.atech.tts_engine.compose

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp


/**
 * This is a custom text that can highlight the text
 * @param textHighlightBuilder: TextHighlightBuilder
 *
 * @see TextHighlightBuilder
 * @author Ayaan
 */
@Composable
fun TTSText(
    textHighlightBuilder: TextHighlightBuilder,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current

) {
    Text(
        modifier = modifier,
        text = textHighlightBuilder.annotatedString,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

/**
 * This is a builder class that can highlight the text
 * @param text: String
 * @param startEnd: Pair<Int, Int>
 * @author Ayaan
 */
data class TextHighlightBuilder(
    val text: String,
    val startEnd: Pair<Int, Int>,
    val style: SpanStyle = SpanStyle(
        color = Color.Black,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp
    )
) {
    val annotatedString = buildAnnotatedString {
        // Append the text and highlight the start and end with old text to black
        append(text)
        addStyle(
            style = style,
            start = startEnd.first,
            end = startEnd.second
        )
    }
}
