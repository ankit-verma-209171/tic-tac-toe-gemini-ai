package com.codeitsolo.tictactoeai.di

import com.codeitsolo.tictactoeai.ai.TicTacToeAi
import com.codeitsolo.tictactoeai.ai.TicTacToeAiImpl
import com.codeitsolo.tictactoeai.data.repository.TicTacToeAiRepository
import com.codeitsolo.tictactoeai.data.repository.TicTacToeAiRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * App module for Dagger Hilt
 */
@Module
@InstallIn(SingletonComponent::class)
interface AppModule {

    @Binds
    fun bindTicTacToeAi(impl: TicTacToeAiImpl): TicTacToeAi

    @Binds
    fun bindTicTacToeAiRepository(impl: TicTacToeAiRepositoryImpl): TicTacToeAiRepository
}