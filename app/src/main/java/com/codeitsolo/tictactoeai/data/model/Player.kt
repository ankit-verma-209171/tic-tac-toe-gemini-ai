package com.codeitsolo.tictactoeai.data.model

import androidx.compose.runtime.Immutable

/**
 * Represents Player
 */
@Immutable
sealed interface Player {
    data object X : Player
    data object O : Player
}
