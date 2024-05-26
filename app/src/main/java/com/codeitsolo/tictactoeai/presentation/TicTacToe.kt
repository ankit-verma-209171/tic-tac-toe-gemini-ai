package com.codeitsolo.tictactoeai.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codeitsolo.tictactoeai.R
import com.codeitsolo.tictactoeai.data.model.GameState
import com.codeitsolo.tictactoeai.data.model.Player
import com.codeitsolo.tictactoeai.ui.theme.TicTacToeAITheme

/**
 * Represents route for TicTacToeAi Game Screen
 */
@Composable
internal fun TicTacToeAiRoute(
    modifier: Modifier = Modifier,
    viewModel: TicTacToeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TicTacToeAi(
        modifier = modifier,
        uiState = uiState,
        onBoxClick = viewModel::onBoxClick,
        onRestartClick = viewModel::onRestartClick,
    )
}

/**
 * Represents stateless TicTacToeAi Game Screen
 */
@Composable
private fun TicTacToeAi(
    modifier: Modifier = Modifier,
    uiState: TicTacToeUiState,
    onBoxClick: (box: Player?, position: Int, player: Player) -> Unit,
    onRestartClick: () -> Unit = {},
) {
    Scaffold(
        modifier = modifier
            .fillMaxSize()
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = when (uiState.gameState) {
                    GameState.Draw -> stringResource(R.string.it_s_a_draw)
                    GameState.Playing -> stringResource(R.string.player, uiState.player)
                    is GameState.Won -> stringResource(R.string.player_won, uiState.player)
                },
                style = MaterialTheme.typography.displayMedium,
            )

            Box(contentAlignment = Alignment.Center) {
                LazyVerticalGrid(
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.game_grid_padding)),
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.game_grid_vertical_spacing)),
                    horizontalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.game_grid_horizontal_spacing)),
                ) {

                    with(uiState.gameBoard) {
                        items(keys.toList()) {
                            BoxCell(
                                state = get(it),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .aspectRatio(1f),
                                allowInteraction = uiState.gameState == GameState.Playing && !uiState.isInProgress,
                                isInProgress = uiState.isInProgress,
                                onBoxClick = { onBoxClick(get(it), it, uiState.player) }
                            )
                        }
                    }
                }

                if (uiState.gameState != GameState.Playing) {
                    Button(
                        modifier = Modifier,
                        onClick = onRestartClick,
                        colors = ButtonDefaults.buttonColors().copy(
                            containerColor = MaterialTheme.colorScheme.surface,
                        )
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(dimensionResource(id = R.dimen.game_restart_button_text_padding)),
                            text = stringResource(R.string.restart),
                            style = MaterialTheme.typography.displayMedium,
                            color = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                }
            }
        }
    }
}

/**
 * Represents stateless BoxCell
 */
@Composable
private fun BoxCell(
    modifier: Modifier = Modifier,
    state: Player?,
    allowInteraction: Boolean = true,
    isInProgress: Boolean = false,
    onBoxClick: () -> Unit
) {
    val painter = when (state) {
        Player.O -> painterResource(R.drawable.circle)
        Player.X -> rememberVectorPainter(image = Icons.Default.Close)
        else -> null
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.game_box_corner_radius_size)))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .clickable(onClick = onBoxClick, enabled = allowInteraction),
        contentAlignment = Alignment.Center
    ) {
        if (painter != null) {
            Icon(
                modifier = Modifier
                    .size(dimensionResource(id = R.dimen.game_box_player_icon_size)),
                painter = painter,
                contentDescription = null,
                tint = when (state) {
                    is Player.O -> MaterialTheme.colorScheme.primary
                    is Player.X -> MaterialTheme.colorScheme.error
                    null -> MaterialTheme.colorScheme.onSurface
                }.copy(if (allowInteraction || isInProgress) 1f else 0.4f)
            )
        }
    }
}

// region Preview

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(showBackground = true)
@Composable
private fun BoxCellPreview() {
    TicTacToeAITheme {
        BoxCell(
            modifier = Modifier
                .size(124.dp),
            state = Player.X,
            onBoxClick = {}
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Preview(showBackground = true)
@Composable
private fun TicTacToeAiPreview() {
    TicTacToeAITheme {
        TicTacToeAi(
            uiState = TicTacToeUiState(
                gameBoard = List(9) { it }
                    .associateWith { null } + mapOf(0 to Player.X, 5 to Player.O)
            ),
            onBoxClick = { _, _, _ -> }
        )
    }
}

// endregion