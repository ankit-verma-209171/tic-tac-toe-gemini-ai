package com.codeitsolo.tictactoeai.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codeitsolo.tictactoeai.data.model.GameState
import com.codeitsolo.tictactoeai.data.model.Player
import com.codeitsolo.tictactoeai.data.repository.TicTacToeAiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

/**
 * View Model for [TicTacToeAiRoute]
 */
@HiltViewModel
class TicTacToeViewModel @Inject constructor(
    private val ticTacToeAiRepository: TicTacToeAiRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(TicTacToeUiState())
    val uiState = _uiState.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = TicTacToeUiState()
    )

    fun onBoxClick(box: Player?, position: Int, player: Player) {
        updateIsInProgress(true)
        updateBoard(box, position, player)
        viewModelScope.launch(Dispatchers.IO) {
            delay(300.milliseconds)
            aiMove()
            updateIsInProgress(false)
        }
    }

    fun onRestartClick() {
        resetGame()
    }

    private fun updateBoard(box: Player?, position: Int, player: Player): Boolean {
        if (box != null) return false
        val updatedGameBoard = _uiState.value.gameBoard + (position to player)

        _uiState.update {
            when {
                player.hasWon(gameBoard = updatedGameBoard)  -> {
                    it.copy(
                        gameState = GameState.Won(winner = player),
                        gameBoard = updatedGameBoard,
                    )
                }

                updatedGameBoard.isDraw() -> {
                    it.copy(
                        gameState = GameState.Draw,
                        gameBoard = updatedGameBoard,
                    )
                }

                else -> {
                    it.copy(
                        player = it.nextPlayer,
                        gameBoard = updatedGameBoard,
                    )
                }
            }
        }

        return true
    }

    private suspend fun aiMove() {
        if (_uiState.value.gameState != GameState.Playing) return

        val aiPosition = ticTacToeAiRepository.getAiMoveForGameBoard(
            gameBoard = _uiState.value.gameBoard,
            player = _uiState.value.player
        ) ?: run {
            resetGame()
            return
        }

        val aiBox = _uiState.value.gameBoard[aiPosition]
        val isBoardUpdated = updateBoard(aiBox, aiPosition, _uiState.value.player)
        if (!isBoardUpdated) {
            resetGame()
        }
    }

    private fun Player.hasWon(gameBoard: Map<Int, Player?>): Boolean {
        val combination = gameBoard
            .filter { it.value == this }
            .map { it.key }
        return TicTacToeUiState.winningCombinations.any { combination.containsAll(it) }
    }

    private fun Map<Int, Player?>.isDraw(): Boolean = values.all { it != null }

    private fun updateIsInProgress(isInProgress: Boolean) {
        _uiState.update {
            it.copy(isInProgress = isInProgress)
        }
    }

    private fun resetGame() {
        _uiState.update { TicTacToeUiState() }
    }
}
