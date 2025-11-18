🐾 Animal Battle

Ett enkelt men unikt kortspel för Android utvecklat i Kotlin. Spelet är turbaserat och bygger på snabba dueller mellan spelaren och en AI, där varje runda avgörs av vilket djurkort man drar och vilken handling man väljer.

🎯 Spelidé

Spelaren och AI:n får varsitt djurkort. Varje kort har tre egenskaper:

• Namn
• Styrka (en siffra som påverkar duellen)
• Personlighet (styr hur AI:n beter sig)

Spelaren väljer sedan en av två handlingar:

• Attack
• Defend

Rundan avgörs genom enkel jämförelse av styrka och valda handlingar. Först till ett visst antal poäng vinner matchen.

🤖 AI – Personality System

I stället för att AI:n alltid gör det optimala valet använder spelet ett enkelt personlighetssystem.
Det gör AI:n mer varierad och spelet mer levande.

AGGRESSIVE
– Föredrar att Attackera
– Passar djur som är starka eller offensiva

DEFENSIVE
– Föredrar att Försvara
– Passar djur som är långsamma eller försiktiga

RANDOM
– Gör helt slumpmässiga val
– Passar oförutsägbara djur

AI:n väljer handling baserat på djurets personlighet och inte bara styrkan.
Det gör spelet både balanserat och roligt, utan att AI:n känns “perfekt”.

🐗 Exempel på djurkort (koncept)

Här är exempel på hur djuren i spelet kan vara utformade:

Björn
– Styrka: Hög
– Personlighet: Aggressive

Hare
– Styrka: Låg
– Personlighet: Defensive

Tvättbjörn
– Styrka: Medel
– Personlighet: Random

Djurens bilder (om de används) läggs i res/drawable/.

📱 UI – Skiss / Struktur

Startskärm
– Knapp: “Start Game”
– Titel och enkel grafik

Spelskärm
– Spelarens kort: namn, styrka, personlighet, bild
– AI:s kort (dolt tills rundan avslutas)
– Knappar: Attack / Defend
– Poängställning
– Resultattext (“You won the round”, etc.)

Game Over
– Vinnare visas
– Knapp: “Play Again”