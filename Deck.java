import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    //Variable declaration and initialisation
    private ArrayList<String> card;
    private ArrayList<String> wholeDeck;
    private String[] suits = {"d", "c", "h", "s"};
    private String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    
    // To generate a deck of 52 cards
    public Deck() {
        this.card = new ArrayList<>();
        this.wholeDeck = new ArrayList<>();

        for (String suit: suits) {
            for (String rank: ranks) {
                this.card.add(suit + rank);
                this.wholeDeck.add(suit + rank);        
            }
        }
    }

    // To get a card suit
    public String getSuit(String i) {
        //toLowerCase() to ensure the suit remains lowercase despite the input
        i = i.substring(0, 1).toLowerCase(); 
        return i;
    }

    //To get a card rank
    public String getRank(String i) {
        i = i.substring(1).toUpperCase();
        return i;
    }

    public int getRankValue(String i) {
        String j = getRank(i);
        switch (j) {
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
    
    //To shuffle the deck
    public void shuffle() {
        Collections.shuffle(this.card);
        //https://stackoverflow.com/questions/39557701/shuffle-a-deck-of-cards-in-java
    }

    //To draw a card from the deck
    public String drawCard() {
        return this.card.remove(0);
    }

    //To return the type of card in the deck
    public String cardInDeck(int i) {
        return this.card.get(i);
    }

    //To return the size of the deck
    public int cardInDeckSize() {
        return this.card.size();
    }

    public String cardWholeDeck(int i) {
        return this.wholeDeck.get(i);
    }

    public int cardWholeDeckSize() {
        return this.wholeDeck.size();
    }


    public static void main(String[] args)
    {
        //Deck deck = new Deck();
        // deck.printTest();
        // deck.shuffle();
        // deck.printTest();

    }
}
