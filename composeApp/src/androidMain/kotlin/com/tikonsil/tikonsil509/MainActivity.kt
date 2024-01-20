package com.tikonsil.tikonsil509

import App
import BaseViewModel
import PlatformInitializer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        PlatformInitializer.initialize(this)
        setContent {
            App()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        BaseViewModel().clearViewModel()
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}