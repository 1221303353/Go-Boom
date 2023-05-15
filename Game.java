//import java.util.*;

public class Game {
    private Deck deck;
    private Player player;

    int trickCount;

    public Game() {
        this.deck = new Deck();
        this.player = new Player();

    }

    //Set the game
    public void setGame() {
        //trickCount = player.getTrickCount(); 
        deck.shuffle();
    }

    //Start the game
    public void start() {
        player.upTrickCount();
        for (int i=0; i<player.getNoOfPlayer(); i++) {
            for (int j=0; j<player.getNoOfCard(); j++) {
                player.setHand(i, j, deck.drawCard());
            }
        }
        
        System.out.println();
        System.out.printf("Trick #%d \n", player.getTrickCount());
        for (int i=0; i<player.getNoOfPlayer(); i++) {
            System.out.printf("Player%d: [", i+1);
            for (int j=0; j<player.getNoOfCard(); j++) {
                System.out.printf(player.getHand(i, j));
                if (j<player.getNoOfCard()-1) {
                    System.out.print(", ");
                }
            }
            System.out.print("] \n");
        }
        System.out.printf("%-7s: [%s] \n", "Center", deck.drawCard());
        deck.cardInDeck();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.setGame();
        game.start();
    }
}
