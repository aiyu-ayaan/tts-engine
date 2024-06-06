/*
 * Created by Ayaan on 02/02/23, 10:30 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 02/02/23, 9:56 pm
 */

package com.atech.tts_engine.tts.engine

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import com.atech.tts_engine.utils.DEF_SPEECH_AND_PITCH
import com.atech.tts_engine.utils.getErrorText
import com.atech.tts_engine.utils.setListener
import java.util.Locale

class TextToSpeechEngine private constructor() {
    private var tts: TextToSpeech? = null

    private var defaultPitch = 0.8f
    private var defaultSpeed = 0.8f
    private var defLanguage = Locale.getDefault()
    private var onStartListener: (() -> Unit)? = null
    private var onDoneListener: (() -> Unit)? = null
    private var onErrorListener: ((String) -> Unit)? = null
    private var onHighlightListener: ((Int, Int) -> Unit)? = null
    private var message: String? = null


    companion object {
        private var instance: TextToSpeechEngine? = null
        fun getInstance(): TextToSpeechEngine {
            if (instance == null) {
                instance = TextToSpeechEngine()
            }
            return instance!!
        }
    }

    fun initTTS(context: Context, message: String) {
        tts = TextToSpeech(context) {
            if (it == TextToSpeech.SUCCESS) {
                tts!!.language = defLanguage
                tts!!.setPitch(defaultPitch)
                tts!!.setSpeechRate(defaultSpeed)
                tts!!.setListener(
                    onStart = {
                        onStartListener?.invoke()
                    },
                    onError = { e ->
                        e?.let { error ->
                            onErrorListener?.invoke(error)
                        }
                    },
                    onRange = { start, end ->
                        if (this@TextToSpeechEngine.message != null)
                            onHighlightListener?.invoke(start, end)
                    },
                    onDone = {
                        onDoneListener?.invoke()
                    }
                )
                speak(message)
            } else {
                Log.d("AAA", "initTTS: $it")
                onErrorListener?.invoke(getErrorText(it))
            }
        }
    }

    private fun speak(message: String): TextToSpeechEngine {
        tts!!.speak(
            message,
            TextToSpeech.QUEUE_FLUSH,
            null,
            TextToSpeech.ACTION_TTS_QUEUE_PROCESSING_COMPLETED
        )
        return this
    }

    fun setPitchAndSpeed(pitch: Float, speed: Float) {
        defaultPitch = pitch
        defaultSpeed = speed
    }

    fun resetPitchAndSpeed() {
        defaultPitch = DEF_SPEECH_AND_PITCH
        defaultSpeed = DEF_SPEECH_AND_PITCH
    }

    fun setLanguage(local: Locale): TextToSpeechEngine {
        this.defLanguage = local
        return this
    }

    fun setHighlightedMessage(message: String) {
        this.message = message
    }

    fun setOnStartListener(onStartListener: (() -> Unit)): TextToSpeechEngine {
        this.onStartListener = onStartListener
        return this
    }

    fun setOnCompletionListener(onDoneListener: () -> Unit): TextToSpeechEngine {
        this.onDoneListener = onDoneListener
        return this
    }

    fun setOnErrorListener(onErrorListener: (String) -> Unit): TextToSpeechEngine {
        this.onErrorListener = onErrorListener
        return this
    }

    fun setOnHighlightListener(onHighlightListener: (Int, Int) -> Unit): TextToSpeechEngine {
        this.onHighlightListener = onHighlightListener
        return this
    }


    fun destroy() {
        tts?.stop()
        tts?.shutdown()
        tts = null
        instance = null
    }


}