package com.codeitsolo.tictactoeai.data.model

import androidx.compose.runtime.Immutable

/**
 * Represents current Game State
 */
@Immutable
sealed interface GameState {
    data object Playing : GameState
    data object Draw : GameState
    data class Won(val winner: Player) : GameState
}
