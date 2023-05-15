import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<String> card;

    // To generate a deck of 52 cards
    public Deck() {
        this.card = new ArrayList<>();

        String[] suits = {"d", "c", "h", "s"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};

        for (String suit: suits) {
            for (String rank: ranks) {
                //String cardid = suit + rank;
                this.card.add(suit + rank);           
            }
        }
    }

    //To shuffle the deck
    public void shuffle() {
        Collections.shuffle(this.card);
        //https://stackoverflow.com/questions/39557701/shuffle-a-deck-of-cards-in-java
    }

    public String drawCard() {
        return this.card.remove(this.card.size() - 1);
    }

    public void cardInDeck() {
        System.out.printf("%-7s: [", "Deck");
        for (int i=0; i<this.card.size(); i++)
        {
            System.out.print(this.card.get(i));
            if (i<this.card.size()-1) {
                System.out.print(", ");
            }
        }
        System.out.print("] \n");

        System.out.println(this.card.size());
    }

    public static void main(String[] args)
    {
        //Deck deck = new Deck();
        // deck.printTest();
        // deck.shuffle();
        // deck.printTest();

    }
}
