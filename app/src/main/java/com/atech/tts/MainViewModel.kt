/*
 * Created by Ayaan on 02/02/23, 10:30 pm
 * Copyright (c) 2023 . All rights reserved.
 * Last modified 02/02/23, 10:23 pm
 */

package com.atech.tts

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private val state = mutableStateOf(Pair(0, 0))

    fun updateState(pair: Pair<Int, Int>) {
        state.value = pair
    }

    fun getState(): State<Pair<Int, Int>> = state
}