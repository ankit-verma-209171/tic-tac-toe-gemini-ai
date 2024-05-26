package com.codeitsolo.tictactoeai.presentation

import androidx.compose.runtime.Stable
import com.codeitsolo.tictactoeai.data.model.GameState
import com.codeitsolo.tictactoeai.data.model.Player

/**
 * UiState for [TicTacToeAiRoute]
 */
@Stable
data class TicTacToeUiState(
    val gameBoard: Map<Int, Player?> = List(9) { it }.associateWith { null },
    val player: Player = Player.X,
    val gameState: GameState = GameState.Playing,
    val isInProgress: Boolean = false,
) {
    val nextPlayer: Player
        get() = when (player) {
            Player.X -> Player.O
            Player.O -> Player.X
        }

    companion object {
        // All winning possibilities in the game
        val winningCombinations = listOf(
            setOf(0, 1, 2),
            setOf(3, 4, 5),
            setOf(6, 7, 8),
            setOf(0, 3, 6),
            setOf(1, 4, 7),
            setOf(2, 5, 8),
            setOf(0, 4, 8),
            setOf(2, 4, 6)
        )
    }
}
