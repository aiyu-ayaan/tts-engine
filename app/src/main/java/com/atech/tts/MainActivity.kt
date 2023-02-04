/*
 * Created by Ayaan on 02/02/23, 10:30 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 02/02/23, 10:23 pm
 */

package com.atech.tts

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import com.atech.tts.ui.theme.TTSLibraryTheme
import com.atech.tts_engine.compose.TTSText
import com.atech.tts_engine.compose.TextHighlightBuilder
import com.atech.tts_engine.tts.builder.TextToSpeechHelper

private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TTSLibraryTheme {
                val viewModel = MainViewModel()
                val resource = stringResource(id = R.string.des)
                val text = remember { resource }

//                screen
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Screen(
                        state = text,
                        viewModel = viewModel,
                    ) {
                        speak(
                            this,
                            this as LifecycleOwner,
                            text,
                            viewModel
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun Screen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    state: String,
    onButtonClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val s = viewModel.getState().value
        TTSText(
            textAlign = TextAlign.Center,
            textHighlightBuilder = TextHighlightBuilder(
                text = state,
                s
            ),
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(onClick = onButtonClick) {
            Text(text = stringResource(id = R.string.speak))
        }
    }
}


private fun speak(
    activity: Activity,
    owner: LifecycleOwner,
    message: String,
    viewModel: MainViewModel
) {
    TextToSpeechHelper
        .getInstance(activity)
        .registerLifecycle(owner)
        .speak(message)
        .highlight()
        .onHighlight { pair ->
            viewModel.updateState(pair)
        }
        .onDone {
            Log.d(TAG, "speak: done")
        }
        .onError {
            Log.d(TAG, "speak: $it")
        }
}

