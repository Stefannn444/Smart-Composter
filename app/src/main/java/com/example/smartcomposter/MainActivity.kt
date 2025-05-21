package com.example.smartcomposter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.smartcomposter.ui.screens.ComposterScreen
import com.example.smartcomposter.ui.screens.ComposterTopAppBar
import com.example.smartcomposter.ui.theme.SmartComposterTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SmartComposterTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    topBar = {ComposterTopAppBar()}
                    ) {paddingValues->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        ComposterScreen(modifier = Modifier)
                    }
                }
            }
        }
    }
}
