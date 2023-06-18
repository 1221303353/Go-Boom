import java.util.ArrayList;
import java.util.Collections;

public class Deck2 {
    //Variable declaration and initialisation
    private ArrayList<String> card;                 //cards in the deck
    private ArrayList<String> wholeDeck;            //cards in the deck (without any removal or insertion)
    private String[] suits = {"d", "c", "h", "s"};
    private String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    
    // To generate a deck of 52 cards
    public Deck2() {
        this.card = new ArrayList<>();
        this.wholeDeck = new ArrayList<>();

        for (String suit: suits) {
            for (String rank: ranks) {
                this.card.add(suit + rank);
                this.wholeDeck.add(suit + rank);        
            }
        }
    }

    //To shuffle the deck
    public void shuffle() {
        Collections.shuffle(this.card);
        //https://stackoverflow.com/questions/39557701/shuffle-a-deck-of-cards-in-java
    }
    
    // To get a card suit
    public String getSuit(String suit) {
        //toLowerCase() to ensure the suit remains lowercase despite the input
        suit = suit.substring(0, 1).toLowerCase(); 
        return suit;
    }

    //To get a card rank
    public String getRank(String rank) {
        //toUpperCase() to ensure the rank remains uppercase despite the input
        rank = rank.substring(1).toUpperCase();
        return rank;
    }

    //To get the value of rank
    public int getRankValue(String rank) {
        rank = getRank(rank);
        switch (rank) {
            case "A":
                return 14;
            case "K":
                return 13;
            case "Q":
                return 12;
            case "J":
                return 11;
            case "10":
                return 10;
            case "9":
                return 9;
            case "8":
                return 8;
            case "7":
                return 7;
            case "6":
                return 6;
            case "5":
                return 5;
            case "4":
                return 4;
            case "3":
                return 3;
            case "2":
                return 2;
            default:
                return 0;
        }
    }
    
    //To draw a card from the deck
    public String drawCard() {
        return this.card.remove(0);
    }

    //To return the cards in the deck
    public ArrayList<String> cardInDeck() {
        return this.card;
    }

    //To return a card in the deck
    public String cardInDeck(int i) {
        return this.card.get(i);
    }

    //To return the size of the deck
    public int cardInDeckSize() {
        return this.card.size();
    }

    //To return a card in the deck (without adjustment)
    public String cardWholeDeck(int i) {
        return this.wholeDeck.get(i);
    }

    //To return the size of the deck (without adjustment)
    public int cardWholeDeckSize() {
        return this.wholeDeck.size();
    }

    //To set the card arrary (use in save/load feature)
    public void setCardInDeck(ArrayList<String> card) {
        this.card = card;
    }

    public void createNewDeck() {
        this.card.clear();
        this.wholeDeck.clear();

        for (String suit: suits) {
            for (String rank: ranks) {
                this.card.add(suit + rank);
                this.wholeDeck.add(suit + rank);        
            }
        }
    }
}
