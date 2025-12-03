package com.example.animalbattle.data

// This is the basic model I use for each animal card in the game.
data class AnimalCard(
    val name: String,
    val strength: Int,
    val personality: Personality,
    val imageRes: Int
)
