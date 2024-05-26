package com.codeitsolo.tictactoeai.ai

import com.codeitsolo.tictactoeai.data.model.Player

/**
 * Represents an AI that can play Tic-Tac-Toe.
 */
interface TicTacToeAi {

    /**
     * Generates a move for the given game board.
     *
     * @param gameMatrix The current state of the game board.
     * @param player The current player.
     */
    suspend fun generateMove(
        gameMatrix: String,
        player: Player,
        availablePositions: List<Int>
    ): Int?
}
