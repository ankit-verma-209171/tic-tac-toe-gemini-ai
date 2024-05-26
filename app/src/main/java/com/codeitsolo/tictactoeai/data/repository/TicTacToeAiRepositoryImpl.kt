package com.codeitsolo.tictactoeai.data.repository

import com.codeitsolo.tictactoeai.ai.TicTacToeAi
import com.codeitsolo.tictactoeai.data.model.Player
import javax.inject.Inject

/**
 * Concrete implementation of [TicTacToeAiRepository]
 */
class TicTacToeAiRepositoryImpl @Inject constructor(
    private val ai: TicTacToeAi
) : TicTacToeAiRepository {

    override suspend fun getAiMoveForGameBoard(
        gameBoard: Map<Int, Player?>,
        player: Player
    ): Int? = ai.generateMove(
        gameMatrix = gameBoard.getGameMatrix(),
        player = player
    )

    /**
     * Create game matrix from the game board
     */
    private fun Map<Int, Player?>.getGameMatrix(): String = buildString {
        this@getGameMatrix.forEach { (index, player) ->
            append("[$index] -> ${player.toString()}\n")
        }
    }

    /**
     * Converts [Player] to String
     */
    private fun Player?.toString() = when (this) {
        Player.O -> "O"
        Player.X -> "X"
        null -> "-"
    }
}
