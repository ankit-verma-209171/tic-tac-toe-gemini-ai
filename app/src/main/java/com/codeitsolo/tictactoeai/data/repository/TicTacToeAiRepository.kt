package com.codeitsolo.tictactoeai.data.repository

import com.codeitsolo.tictactoeai.data.model.Player

/**
 * Represents Tic-Tac-Toe AI repository.
 */
interface TicTacToeAiRepository {

    /**
     * Get AI move for given game board.
     *
     * @param gameBoard current game board
     * @param player current player
     *
     * @return AI move representing the position desired by the AI.
     */
    suspend fun getAiMoveForGameBoard(
        gameBoard: Map<Int, Player?>,
        player: Player
    ): Int?
}
