package com.example.animalbattle.data

import com.example.animalbattle.R

class Deck {

    private val cards = listOf(
        // I grouped the aggressive animals together since they represent the strong, high-power cards.
        AnimalCard("Bear", 90, Personality.AGGRESSIVE, R.drawable.ic_bear),
        AnimalCard("Tiger", 95, Personality.AGGRESSIVE, R.drawable.ic_tiger),
        AnimalCard("Lion", 92, Personality.AGGRESSIVE, R.drawable.ic_lion),
        AnimalCard("Wolf", 85, Personality.AGGRESSIVE, R.drawable.ic_wolf),
        AnimalCard("Eagle", 80, Personality.AGGRESSIVE, R.drawable.ic_eagle),
        AnimalCard("Boar", 78, Personality.AGGRESSIVE, R.drawable.ic_boar),

        // These ones are meant to feel more defensive or cautious, so I placed the gentler animals here.
        AnimalCard("Rabbit", 30, Personality.DEFENSIVE, R.drawable.ic_rabbit),
        AnimalCard("Hedgehog", 35, Personality.DEFENSIVE, R.drawable.ic_hedgehog),
        AnimalCard("Turtle", 40, Personality.DEFENSIVE, R.drawable.ic_turtle),
        AnimalCard("Deer", 45, Personality.DEFENSIVE, R.drawable.ic_deer),
        AnimalCard("Koala", 25, Personality.DEFENSIVE, R.drawable.ic_koala),
        AnimalCard("Sheep", 28, Personality.DEFENSIVE, R.drawable.ic_sheep),

        // And here I added a mix of animals where the personality is intentionally unpredictable.
        AnimalCard("Raccoon", 55, Personality.RANDOM, R.drawable.ic_raccoon),
        AnimalCard("Fox", 60, Personality.RANDOM, R.drawable.ic_fox),
        AnimalCard("Otter", 50, Personality.RANDOM, R.drawable.ic_otter),
        AnimalCard("Monkey", 65, Personality.RANDOM, R.drawable.ic_monkey),
        AnimalCard("Penguin", 48, Personality.RANDOM, R.drawable.ic_penguin),
        AnimalCard("Goat", 52, Personality.RANDOM, R.drawable.ic_goat),
        AnimalCard("Cat", 58, Personality.RANDOM, R.drawable.ic_cat),
        AnimalCard("Dog", 62, Personality.RANDOM, R.drawable.ic_dog)
    )

    // Just return a random card from the list when I need to draw one.
    fun drawRandomCard(): AnimalCard {
        return cards.random()
    }
}
