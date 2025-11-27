package com.example.animalbattle.data

import com.example.animalbattle.R

class Deck {

    private val cards = listOf(
        AnimalCard("Bear", 90, Personality.AGGRESSIVE, R.drawable.ic_launcher_foreground),
        AnimalCard("Rabbit", 30, Personality.DEFENSIVE, R.drawable.ic_launcher_foreground),
        AnimalCard("Raccoon", 55, Personality.RANDOM, R.drawable.ic_launcher_foreground)
    )

    fun drawRandomCard(): AnimalCard {
        return cards.random()
    }
}
