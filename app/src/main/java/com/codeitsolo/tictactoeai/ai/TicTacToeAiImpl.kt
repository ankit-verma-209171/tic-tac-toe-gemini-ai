package com.codeitsolo.tictactoeai.ai

import com.codeitsolo.tictactoeai.data.model.Player
import javax.inject.Inject

/**
 * Concrete implementation of [TicTacToeAi]
 * This uses Gemini AI to generate a move
 */
class TicTacToeAiImpl @Inject constructor(): TicTacToeAi {
    override suspend fun generateMove(gameMatrix: String, player: Player): Int? {
        return null
    }
}
