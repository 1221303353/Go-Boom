import java.util.ArrayList;
import java.util.List;

public class Player {
    //private Deck deck;

    private int trickCount=0;
    //private int[] noPlayer;
    private int noOfPlayer = 4;
    private int noOfCard = 7;
    private ArrayList<List<String>> hand;
    
    public Player() {
        //this.noPlayer = new int[4];
        this.hand = new ArrayList<List<String>>();
        for (int i=0; i<this.noOfPlayer; i++)
        {
            this.hand.add(new ArrayList<String>());
        }
    }

    public int getTrickCount() {
        return this.trickCount;
    }
    public void upTrickCount() {
        this.trickCount++; 
    }

    public int getNoOfPlayer() {
        return this.noOfPlayer;
    }

    public int getNoOfCard() {
        return this.noOfCard;
    }

    // public ArrayList<List<String>> getHand() {
    //     return this.hand;
    // }

    public void setHand(int i, int j, String card) {
        this.hand.get(i).add(j, card);
    }

    public String getHand(int i, int j) {
        return this.hand.get(i).get(j);
    } 
    
    public static void main(String[] args) {
        Player player = new Player();
        Deck deck = new Deck();

        for (int i=0; i<player.getNoOfPlayer(); i++) {
            for (int j=0; j<player.getNoOfCard(); j++) {
                player.setHand(i, j, deck.drawCard());
            }
        }

        for (int i=0; i<player.getNoOfPlayer(); i++) {
            System.out.printf("Player%d: [", i+1);
            for (int j=0; j<player.getNoOfCard(); j++) {
                System.out.printf(player.getHand(i, j));
                System.out.print(", ");
            }
            System.out.print("] \n");
            
        }
        
    }

}
