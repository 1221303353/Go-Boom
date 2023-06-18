import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.*;
import java.util.*;

public class Game2 extends Application{
    //Variable declaration
    private Deck2 deck;
    private Player2 player;
    private int currentPlayerIndex;
    private int trickCount = 0;
    private ArrayList<String> center;
    private ArrayList<Integer> playerInputList;

    //Variable initialisation
    public Game2() {
        this.deck = new Deck2();
        this.player = new Player2();

        this.center = new ArrayList<>();
        this.playerInputList = new ArrayList<>();
    }

    //To display the game board
    public void displayBoard() {
        //Display Trick #
        System.out.println();
        System.out.printf("Trick #%d \n", this.trickCount);

        //Display Player Hand
        for (int i=0; i<player.getNoOfPlayer(); i++) {
            System.out.printf("%-7s: " + player.getHand(i) + "\n", player.getPlayerName(i), i+1);
        }
        
        //Display Center
        System.out.printf("%-7s: " + this.center + "\n", "Center");
        
        //Display Deck
        System.out.printf("%-7s: " + deck.cardInDeck() + "\n", "Deck");

        //Display Score
        System.out.printf("%-7s: ", "Score");
        for (int i=0; i<player.getNoOfPlayer(); i++) {
            System.out.printf(player.getPlayerName(i) + " = %-3d", player.getPlayerScore(player.getPlayerName(i)));
            if (i<player.getNoOfPlayer()-1) {
                System.out.print(" | ");
            }
        }
        System.out.print("\n");
    }
    
    //To set the first player
    public void setFirstPlayer() {
        switch(deck.getRank(this.center.get(0))) {
            case "A": case "5": case "9": case "K":
                this.currentPlayerIndex = 0;
                break;
            case "2": case "6": case "10":
                this.currentPlayerIndex = 1;
                break;
            case "3": case "7": case "J":
                this.currentPlayerIndex = 2;
                break;
            case "4": case "8": case "Q":
                this.currentPlayerIndex = 3;
                break;
            default:
                this.currentPlayerIndex = 0;
                break;
        }
    }

    //To end a player turn to the next
    public void getNextPlayer() {
        if (this.currentPlayerIndex == 0) {
            this.currentPlayerIndex = 1;
        } else if (this.currentPlayerIndex == 1) {
            this.currentPlayerIndex = 2;
        } else if (this.currentPlayerIndex == 2) {
            this.currentPlayerIndex = 3;
        } else if (this.currentPlayerIndex == 3) {
            this.currentPlayerIndex = 0;
        }
    }

    //To ask the user for input
    public String userInput(Scanner scanner) {
        //To ask the current user input
        String command;
        
        System.out.print(">  ");
        command = scanner.nextLine().toLowerCase(); //toLowerCase for case insensitive command
        return command;
    }

    //To check wheather the card played is playable
    public boolean isPlayableCard(String card) {
        String leadSuit = deck.getSuit(this.center.get(0));
        String leadRank = deck.getRank(this.center.get(0));
        String cardSuit = deck.getSuit(card);
        String cardRank = deck.getRank(card);

        //Comparing the lead suit with card suit and lead rank with card rank
        return leadSuit.equals(cardSuit) || leadRank.equals(cardRank);
    }
    
    //To find the player who wins the trick
    public int getWinPlayer() {
        String leadSuit = deck.getSuit(this.center.get(0));
        String cardSuit;
        int[] cardRank = new int[4];

        //To avoid comparing the lead card in the first trick
        if (this.center.size() == 5) {
            this.center.remove(0); 
        }
        
        //To get the card rank value
        for (int i=0; i<this.center.size(); i++) {
            cardRank[i] = deck.getRankValue(this.center.get(i));
        }

        int maxNumber = 0;
        int maxIndex = 0;

        //To compare the card rank value and find the index in an array for the highest rank value
        for (int i=0; i<this.center.size(); i++) {
            cardSuit = deck.getSuit(this.center.get(i));
            
            //To skip the comparing a card with different suit than the lead card
            if (!cardSuit.equals(leadSuit)) {
                continue;
            }
            
            if (cardRank[i] > maxNumber) {
                maxNumber = cardRank[i];
                maxIndex = i;
            }
        }
        
        return maxIndex;
    }

    //To calculate the player score based on the cards at hand
    public int cardScore(int j) {
        int score=0;

        for (int i=0; i<player.getHandRowSize(j); i++) {
            
            switch (deck.getRank(player.getHand(j, i))) {
                case "A":
                    score += 1;
                    break;
                case "2": 
                    score += 2;
                    break;
                case "3":
                    score += 3;
                    break;
                case "4":
                    score += 4;
                    break;
                case "5":
                    score += 5;
                    break;
                case "6": 
                    score += 6;
                    break;
                case "7":
                    score += 7;
                    break;
                case "8":
                    score += 8;
                    break;
                case "9":
                    score += 9;
                    break;
                case "10":
                case "J":
                case "Q":
                case "K":
                    score += 10;
                    break;
                default :
                    score = 0;
                    break;
            }
        }

        return score;
    }

    //The flow of the player turn
    public int turn() {
        boolean endTurn = false;
        String cmd;
        Scanner scanner = new Scanner(System.in);

        while (!endTurn) {
            displayBoard();
            
            //To display the current player turn
            System.out.printf("%-7s: %s \n", "Turn", player.getPlayerName(this.currentPlayerIndex));
            cmd = userInput(scanner);           

            //To save the game
            if (cmd.equals("save")) {
                GameSaveManager.save("save.txt", deck.cardInDeck(), player.getHand(), player.getPlayerScore(), this.currentPlayerIndex, 
                    this.trickCount, this.center, this.playerInputList);
                
                endTurn = false;
            }
            //To load a saved game
            else if (cmd.equals("load")) {
                GameData savedData = GameSaveManager.load("save.txt");
        
                if (savedData != null) {
                    deck.setCardInDeck(savedData.getCard());
                    player.setHand(savedData.getHand());
                    player.setPlayerScore(savedData.getPlayerScore());
                    this.currentPlayerIndex = savedData.getCurrentPlayerIndex();
                    this.trickCount = savedData.getTrickCount();
                    this.center = savedData.getCenter();
                    this.playerInputList = savedData.getPlayerInputList();
                }
                else {
                    System.out.println("There are no games saved in this program.");
                }
                endTurn = false;
            }
            //To start a new game
            else if (cmd.equals("s")) {
                System.out.println("Starting a new game...");
                endTurn = true;
                return 2;
            }
            //To exit the game
            else if (cmd.equals("x")) {
                System.out.println("Exiting the game...");
                endTurn = true;
                return 1;
            }
            //To draw a card 
            else if (cmd.equals("d")) {
                int count=0;
                boolean foundPlayableCard = false;
                boolean handPlayable = false;

                //In the case after trick #1, the player must play a card first
                if (this.center.size() == 0) {
                    System.out.println("You must play a card to lead the trick.");
                    endTurn = false;
                    continue;
                }

                //To check if the player hand has a playable card
                for (int i=0; i<player.getHandRowSize(this.currentPlayerIndex); i++) {
                    if (isPlayableCard(player.getHand(this.currentPlayerIndex, i))) {
                        handPlayable = true;
                        break;
                    }
                }

                //To check if the deck has a playable card
                for (int j=0; j<deck.cardInDeckSize(); j++) {
                    if (isPlayableCard(deck.cardInDeck(j))) {
                        foundPlayableCard = true;
                        count = j+1;
                        break;
                    }
                }
                
                //If there is a playable card in the deck and no playable card in the hand
                if (foundPlayableCard && !handPlayable) {
                    //Drew a card until the playable card is found
                    System.out.printf("You drew the card: ");
                    for (int j=0; j<count; j++) {
                        String drawnCard = deck.drawCard();
                        
                        player.setHand(this.currentPlayerIndex, drawnCard);
                        System.out.printf("%s", drawnCard);
                        if (j<count-1) {
                            System.out.print(", ");
                        }
                    }
                    System.out.println();
                    endTurn = false;
                }
                //If there is no playable card in the deck
                else if (!foundPlayableCard) {
                    //Skip the player turn
                    System.out.println("No playable card found in the deck. Your turn is skipped.");
                    endTurn = true;
                }
                //If there is a playable card in the hand
                else if (handPlayable) {
                    //Repeat the player turn
                    System.out.println("Your hand has a playable card. \nYou cannot draw a card from the deck.");
                    endTurn = false;
                }
            } 
            //To play a card         
            else {
                //To ensure the card suit is lowercase and the card rank is uppercase 
                String cardSuit = deck.getSuit(cmd);
                String cardRank = deck.getRank(cmd);
                cmd = cardSuit + cardRank;

                //To check if the command entered is the same as the cards in the player hand
                Boolean validHand = false;
                for (int j=0; j<player.getHandRowSize(this.currentPlayerIndex); j++) {
                    if (cmd.equals(player.getHand(this.currentPlayerIndex, j))) {
                        validHand = true;
                    }
                }

                //Check if both case has a valid card in hand.
                //If yes, check if there is a lead card in the center
                int value = 0;
                if (this.center.size() == 0 && validHand) {
                    value = 1;
                }
                else if (validHand && isPlayableCard(cmd)) {
                    value = 2; 
                }

                //Case 1: turn requires player to play a lead card
                //Case 2: a lead card has already been played
                //Default: invalid input
                switch (value) {
                    case 1:
                    case 2:
                        //Play the card
                        this.center.add(cmd);
                        this.playerInputList.add(this.currentPlayerIndex);
                        player.playCard(this.currentPlayerIndex, cmd);
                        
                        //End the current player turn
                        endTurn = true;  
                        break;
                    
                    default:
                        //Invalid input
                        System.out.println("Invalid input. Please try again.");
                        endTurn = false;
                        break;
                }
            }
        }

        return 0;
    }

    public boolean play() {
        boolean endPlay = false;
        player.clearPlayerScore();
        

        while (!endPlay) {        
            //Shuffle the deck and set the trick count to 0 for every game round
            boolean endTrick = false;
            
            deck.shuffle();
            this.trickCount = 0;
            int winPlayer = 0;
            
            while (!endTrick) {
                //Clear the center, playerList and up the trick count for every trick
                this.center.clear();
                this.playerInputList.clear();
                this.trickCount++;
                
                //If it's the first trick
                if (this.trickCount == 1) {
                    //Draw a card from the deck to the center as the lead card
                    //Set the first player based on the lead card
                    //Draw 7 cards for each player to the player hand
                    this.center.add(0, deck.drawCard());
                    setFirstPlayer();
                    for (int i=0; i<player.getNoOfPlayer(); i++) {
                        for (int j=0; j<player.getNoOfCard(); j++) {
                            player.setHand(i, deck.drawCard());
                        }
                    }
                }
                
                //4 player take turns in playing
                while (playerInputList.size() < 4) {
                    int caseValue = turn();

                    if (caseValue==1) {
                        endPlay = true;
                        endTrick = true;
                        return true;
                    }
                    else if (caseValue==2) {
                        deck.createNewDeck();
                        player.clearHand();
                        endTrick = true;
                        endPlay = true;
                        return false;
                    }

                    //Check if the player has no cards left in their hand,
                    //If yes, that player won the game
                    if (player.getHandRowSize(this.currentPlayerIndex) == 0) {
                        System.out.println("\n\nGO BOOM!!");
                        System.out.printf("%s Wins The Game \n", player.getPlayerName(this.currentPlayerIndex));
                        
                        for (int j=0; j<player.getNoOfPlayer(); j++) {
                            player.setScore(player.getPlayerName(j), cardScore(j));
                        }
                        deck.createNewDeck();
                        player.clearHand();
                        endTrick = true;
                        break;
                    }

                    //Get the next player turn
                    getNextPlayer();
                }

                if (this.playerInputList.size() == 4) {
                    //Display the game board and display the player who won the trick.
                    displayBoard();
                    winPlayer = getWinPlayer();
                    currentPlayerIndex = this.playerInputList.get(winPlayer);
                    System.out.printf("\n\n*** %s wins Trick #%d *** \n", player.getPlayerName(currentPlayerIndex), this.trickCount);
                }
            }
        }

        return false;
    }
 
    public static void main(String[] args) {
        //Variable declaration and initialisation
        Game2 game = new Game2();
        Boolean endProgram = false;
        Scanner scanner = new Scanner(System.in);
        
        Application.launch(args);

        //Loop for data validation
        while (!endProgram) {
            System.out.println("Welcome to Go Boom Card Game \n");
            System.out.println("s - to start the game");
            System.out.println("x - to exit the game");
            
            String cmd = game.userInput(scanner);
            
            if (cmd.equals("s")) {
                Boolean endGame = false;
                
                while (!endGame) {
                    endGame = game.play();
                }
                endProgram = true;
            }
            else if (cmd.equals("x")) {
                System.out.println("Exiting the game...");
                endProgram = true;
            }
            else {
                System.out.println("Invalid Input. Please try again \n");
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("GUI/scenes/Main.fxml")); 
        Scene scene = new Scene(root, Color.GREEN);
        Image icon = new Image("GUI/images/icon_1.2.jpg");
        stage.getIcons().add(icon);
        stage.setTitle("Go-Boom!! Card Game");
        
        stage.setScene(scene); 
        stage.show();
        stage.setOnCloseRequest(event -> {
            event.consume();
            exit(stage);
        });
    }

    public void exit(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit!");
        alert.setContentText("Are you sure you want to exit?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
        
    }
}
