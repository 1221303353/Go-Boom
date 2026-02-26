# 🃏 Go Boom Game

This project is a Java-based object-oriented implementation of the Go Boom card game.

The system is divided into:
- Part 1 – Console-based game implementation
- Part 2 – Extended features including GUI, save/load functionality, and full round completion

This project emphasizes:
- Object-Oriented Programming (OOP)
- Proper class design (inheritance & composition)
- Use of appropriate data structures (List, Set, Map)
- GUI development (JavaFX / Swing / etc.)
- File handling / persistence

# Game Rules
1. 52 cards are randomized.
2. The first card in the deck becomes the lead card and is placed at the center.
3. The first lead card determines the first player (Refer Table).
4. Each of the 4 players receives 7 cards.

| Card Rank | First Player |
|---|---|
| A, 5, 9, K | Player 1 |
| 2, 6, 10 | Player 2 |
| 3, 7, J | Player 3 |
| 4, 8, Q | Player 4 |

5. Players must follow suit or rank of the lead card.
6. The highest rank card of the same suit wins the trick.
7. The winner of the trick leads the next trick.
8. If a player cannot follow suit or rank:
- Must draw from the deck until a playable card is obtained.
- If deck is empty → player skips.
