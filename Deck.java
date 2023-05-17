import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    //Variable declaration and initialisation
    private ArrayList<String> card;
    private String[] suits = {"d", "c", "h", "s"};
    private String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
    
    // To generate a deck of 52 cards
    public Deck() {
        this.card = new ArrayList<>();

        for (String suit: suits) {
            for (String rank: ranks) {
                this.card.add(suit + rank);           
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

    public static void main(String[] args)
    {
        //Deck deck = new Deck();
        // deck.printTest();
        // deck.shuffle();
        // deck.printTest();

    }
}
