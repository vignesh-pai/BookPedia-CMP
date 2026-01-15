@file:OptIn(ExperimentalWasmJsInterop::class)
package com.plcoding.bookpedia

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlin.js.ExperimentalWasmJsInterop
import kotlin.js.js
@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    ComposeViewport {
        App(
            onCounterChange = { counter ->
                if(counter == 10) {
                    alert()
                }
            }
        )
    }
}

private fun alert() {
    js("{ alert(\"You've cracked it!\") }")
}