/*
 * Created by Ayaan on 02/02/23, 10:30 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 01/02/23, 10:27 pm
 */

package com.atech.tts_engine.utils

import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat


const val DEF_SPEECH_AND_PITCH = 0.8f

fun getErrorText(errorCode: Int): String = when (errorCode) {
    TextToSpeech.ERROR -> "ERROR"
    TextToSpeech.ERROR_INVALID_REQUEST -> "ERROR_INVALID_REQUEST"
    TextToSpeech.ERROR_NETWORK -> "ERROR_NETWORK"
    TextToSpeech.ERROR_NETWORK_TIMEOUT -> "ERROR_NETWORK_TIMEOUT"
    TextToSpeech.ERROR_SERVICE -> "ERROR_SERVICE"
    TextToSpeech.ERROR_SYNTHESIS -> "ERROR_SYNTHESIS"
    TextToSpeech.ERROR_NOT_INSTALLED_YET -> "ERROR_NOT_INSTALLED_YET"
    else -> "UNKNOWN"
}

inline fun TextToSpeech.setListener(
    crossinline onStart: (String?) -> Unit = {},
    crossinline onError: (String?) -> Unit = {},
    crossinline onRange: (Int, Int) -> Unit = { _, _ -> },
    crossinline onDone: (String?) -> Unit,
) = this.apply {
    setOnUtteranceProgressListener(object : UtteranceProgressListener() {
        override fun onStart(p0: String?) {
            onStart.invoke(p0)
        }

        override fun onDone(p0: String?) {
            onDone.invoke(p0)
        }

        @Deprecated("Deprecated in Java", ReplaceWith("onError.invoke(p0)"))
        override fun onError(p0: String?) {
            onError.invoke(p0)
        }

        override fun onRangeStart(utteranceId: String?, start: Int, end: Int, frame: Int) {
            super.onRangeStart(utteranceId, start, end, frame)
            onRange.invoke(start, end)
        }
    })
}

fun TextView.highlightText(
    start: Int,
    end: Int,
    @ColorRes color: Int
) = this.apply {
    val text = this.text.toString()
    if (text.isBlank()) return@apply
    val textWithHighlights: Spannable = SpannableString(text)
    textWithHighlights.setSpan(
        ForegroundColorSpan(
            ContextCompat.getColor(
                context, color
            )
        ), start, end, Spanned.SPAN_INCLUSIVE_INCLUSIVE
    )
    this.text = textWithHighlights
}