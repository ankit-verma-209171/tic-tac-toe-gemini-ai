package com.codeitsolo.tictactoeai.ai

import com.codeitsolo.tictactoeai.BuildConfig
import com.codeitsolo.tictactoeai.data.model.Player
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.GoogleGenerativeAIException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Concrete implementation of [TicTacToeAi]
 * This uses Gemini AI to generate a move
 */
class TicTacToeAiImpl @Inject constructor() : TicTacToeAi {
    private val generativeModel = GenerativeModel(
        // The Gemini 1.5 models are versatile and work with most use cases
        modelName = "gemini-1.5-flash",
        // Access your API key as a Build Configuration variable (see "Set up your API key" above)
        apiKey = BuildConfig.AI_API_KEY
    )

    override suspend fun generateMove(
        gameMatrix: String,
        player: Player,
        availablePositions: List<Int>
    ): Int? =
        withContext(Dispatchers.IO) {
            val prompt = """
                You are pro at playing TicTacToe.
                Currently you are playing as $player in a 3x3 TicTacToe and it's your turn
                Current game state is $gameMatrix 
                where O means boxes marked by O, X means boxes marked by X and - means it is empty
                and can be filled
                also note the positions start with 0 from top-left and goes to 8 in bottom-right
                Give the best immediate move for $player strictly only integer position as reply
                Positions should be from $availablePositions
            """.trimIndent()
            val response = try {
                generativeModel.generateContent(prompt)
            } catch (e: GoogleGenerativeAIException) {
                null
            }
            response?.text?.trim()?.split(" ")?.last()?.toIntOrNull()
        }
}
