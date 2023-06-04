import java.util.ArrayList;
import java.util.LinkedHashMap;

//Data that needs to be stored (use in save/load feature)
public class GameData {
    //Variable declaration
    private ArrayList<String> card;                 
    private int currentPlayerIndex;
    private int trickCount;
    private ArrayList<String> center;
    private ArrayList<Integer> playerInputList;
    private ArrayList<ArrayList<String>> hand;
    private LinkedHashMap<String, Integer> playerScore;
    
    //Receive data that needs to be stored and store it in this class variables
    public GameData (ArrayList<String> card, ArrayList<ArrayList<String>> hand, LinkedHashMap<String, Integer> playerScore,
    int currentPlayerIndex, int trickCount, ArrayList<String> center, ArrayList<Integer> playerInputList) {
        
        this.card = card;
        this.currentPlayerIndex = currentPlayerIndex;
        this.trickCount = trickCount;
        this.center = center;
        this.playerInputList = playerInputList;
        this.hand = hand;
        this.playerScore = playerScore;
    }


    //To return the data in this class
    
    public ArrayList<String> getCard() {
        return this.card;
    }

    public int getCurrentPlayerIndex() {
        return this.currentPlayerIndex;
    }

    public int getTrickCount() {
        return this.trickCount;
    }

    public ArrayList<String> getCenter() {
        return this.center;
    }

    public ArrayList<Integer> getPlayerInputList() {
        return this.playerInputList;
    }

    public ArrayList<ArrayList<String>> getHand() {
        return this.hand;
    }

    public LinkedHashMap<String, Integer> getPlayerScore() {
        return this.playerScore;
    }
}
