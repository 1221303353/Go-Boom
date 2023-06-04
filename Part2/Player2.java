import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Player2 {
    //Variable declaration and initialisation
    private int noOfCard = 3;

    private String[] playerName = {"Player1", "Player2", "Player3", "Player4"};
    private ArrayList<ArrayList<String>> hand;
    private LinkedHashMap<String, Integer> playerScore;
    
    //To generate space for player hand
    public Player2() {
        this.hand = new ArrayList<ArrayList<String>>();
        this.playerScore = new LinkedHashMap<>();

        for (int i=0; i<this.playerName.length; i++) {
            this.hand.add(new ArrayList<String>());
        }

        for (int i=0; i<this.playerName.length; i++) {
            this.playerScore.put(this.playerName[i], 0);
        }
    }

    //To get the player name
    public String getPlayerName(int i) {
        return this.playerName[i];
    }

    //To return number of players
    public int getNoOfPlayer() {
        return this.playerName.length;
    }

    //To return original number of cards on the player
    public int getNoOfCard() {
        return this.noOfCard;
    }

    //To add a card on the player
    public void setHand(int i, String card) {
        this.hand.get(i).add(card);
    }

    //To return a card on a player
    public String getHand(int i, int j) {
        return this.hand.get(i).get(j);
    } 

    //To return the cards on a player
    public ArrayList<String> getHand(int i) {
        return this.hand.get(i);
    }

    //To return the hand 2D ArrayList
    public ArrayList<ArrayList<String>> getHand() {
        return this.hand;
    }
    
    //To return number of cards on the player
    public int getHandRowSize(int i) {
        return this.hand.get(i).size();
    }

    //To remove a card from the player
    public void playCard(int i, String cmd) {
        this.hand.get(i).remove(cmd);
    }

    //To return a player score
    public int getPlayerScore(String player) {
        return this.playerScore.get(player);
    }

    //To return the playerScore LinkedHaskMap
    public LinkedHashMap<String, Integer> getPlayerScore() {
        return this.playerScore;
    }
    
    //To increase a player score
    public void setScore(String player, int i) {
        int score = this.playerScore.get(player) + i;
        this.playerScore.put(player, score);
    }

    //To set the hand 2D ArrayList (use in save/load feature)
    public void setHand(ArrayList<ArrayList<String>> hand) {
        this.hand = hand;
    }

    //To set the playerScore LinkedHashMap (use in save/load feature)
    public void setPlayerScore(LinkedHashMap<String, Integer> playerScore) {
        this.playerScore = playerScore;
    }
}
