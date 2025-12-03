package com.example.animalbattle.util

object ScoreManager {
    var playerScore: Int = 0
    var aiScore: Int = 0

    // This is the target score I use to decide when the whole game is over.
    const val WIN_SCORE = 3

    // I call this whenever I want to start a fresh game with clean scores.
    fun reset() {
        playerScore = 0
        aiScore = 0
    }
}
