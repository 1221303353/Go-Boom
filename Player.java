import java.util.ArrayList;
import java.util.List;

public class Player {
    //Variable declaratiom and initialisation
    private int trickCount = 0;
    private int noOfPlayer = 4;
    private int noOfCard = 7;
    private ArrayList<List<String>> hand;
    private int[] score;
    
    //To generate space for player hand
    public Player() {
        this.hand = new ArrayList<List<String>>();
        this.score = new int[4];

        for (int i=0; i<this.noOfPlayer; i++) {
            this.hand.add(new ArrayList<String>());
        }
    }

    //To return the trick count
    public int getTrickCount() {
        return this.trickCount;
    }

    //To set the trick count
    public void setTrickCount(int i) {
        this.trickCount = i;
    }

    //To increase the trick count
    public void upTrickCount() {
        this.trickCount++; 
    }

    //To return number of players
    public int getNoOfPlayer() {
        return this.noOfPlayer;
    }

    //To return original number of cards on the player
    public int getNoOfCard() {
        return this.noOfCard;
    }

    //To add a card on the player
    public void setHand(int i, String card) {
        this.hand.get(i).add(card);
    }

    //To return the card on the player
    public String getHand(int i, int j) {
        return this.hand.get(i).get(j);
    } 

    //To return number of cards on the player
    public int getHandRowSize(int i) {
        return this.hand.get(i).size();
    }
    
    //To remove a card from the player
    public void playCard(int i, String cmd) {
        this.hand.get(i).remove(cmd);
    }
    

    //Part 2
    //To return the player score
    public int getScore(int i) {
        return this.score[i];
    }

    //To increase the player score
    public void setScore(int i, int j) {
        this.score[i] += j; 
    }

}
