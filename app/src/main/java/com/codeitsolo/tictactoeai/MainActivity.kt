package com.codeitsolo.tictactoeai

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.ui.Modifier
import com.codeitsolo.tictactoeai.presentation.TicTacToeAiRoute
import com.codeitsolo.tictactoeai.ui.theme.TicTacToeAITheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Represents Entry Point for the Tic Tac Toe App
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TicTacToeAITheme {
                TicTacToeAiRoute(
                    modifier = Modifier
                )
            }
        }
    }
}
