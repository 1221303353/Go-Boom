import java.util.*;

public class Game {
    //Variable declaration
    private Deck deck;
    private Player player;
    private int currentPlayer;
    private ArrayList<String> center;
    private ArrayList<Integer> playerList;

    //Variable initialisation
    public Game() {
        this.deck = new Deck();
        this.player = new Player();

        this.center = new ArrayList<>();
        this.playerList = new ArrayList<>();
    }
    
    //To display the game board
    public void displayBoard() {
        //Display Trick #
        System.out.println();
        System.out.printf("Trick #%d \n", player.getTrickCount());

        //Display Player Hand
        for (int i=0; i<player.getNoOfPlayer(); i++) {
            System.out.printf("Player%d: [", i+1);
            for (int j=0; j<player.getHandRowSize(i); j++) {
                System.out.printf(player.getHand(i, j));
                if (j<player.getHandRowSize(i)-1) {
                    System.out.print(", ");
                }
            }
            System.out.print("] \n");
        }

        //Display Center
        System.out.printf("%-7s: [", "Center");
        for (int i=0; i<this.center.size(); i++) {
            System.out.printf(this.center.get(i));
            if (i<this.center.size()-1) {
                System.out.print(", ");
            }
        }
        System.out.print("] \n");
        
        //Display Deck
        System.out.printf("%-7s: [", "Deck");
        for (int i=0; i<deck.cardInDeckSize(); i++) {
            System.out.print(deck.cardInDeck(i));
            if (i<deck.cardInDeckSize()-1) {
                System.out.print(", ");
            }
        }
        System.out.print("] \n");

        //Display Score
        System.out.printf("%-7s: ", "Score");
        for (int i=0; i<player.getNoOfPlayer(); i++) {
            System.out.printf("Player%d: = %-3d", i+1, player.getScore(i));
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
                this.currentPlayer = 0;
                break;
            case "2": case "6": case "10":
                this.currentPlayer = 1;
                break;
            case "3": case "7": case "J":
                this.currentPlayer = 2;
                break;
            case "4": case "8": case "Q":
                this.currentPlayer = 3;
                break;
            default:
                this.currentPlayer = 0;
                break;
        }
    }

    //To end a player turn to the next
    public void getNextPlayer() {
        if (this.currentPlayer == 0) {
            this.currentPlayer = 1;
        } else if (this.currentPlayer == 1) {
            this.currentPlayer = 2;
        } else if (this.currentPlayer == 2) {
            this.currentPlayer = 3;
        } else if (this.currentPlayer == 3) {
            this.currentPlayer = 0;
        }
    }

    //To ask the user for input
    public String userInput() {
        //To ask the current user input
        Scanner scanner = new Scanner(System.in);
        String command;
        
        System.out.print(">  ");
        command = scanner.nextLine().toLowerCase(); //toLowerCase for case insensitive command
        //scanner.close();
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

    //The flow of the player turn
    public void turn() {
        boolean endTurn = false;
        String cmd;

        while (!endTurn) {
            displayBoard();
            
            //To display the current player turn
            System.out.printf("%-7s: Player%d \n", "Turn", this.currentPlayer+1);
            cmd = userInput();           

            //To exit the game
            if (cmd.equals("x")) {
                System.out.println("Exiting the game...");
                endTurn = true;
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
                for (int i=0; i<player.getHandRowSize(this.currentPlayer); i++) {
                    if (isPlayableCard(player.getHand(this.currentPlayer, i))) {
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
                        
                        player.setHand(this.currentPlayer, drawnCard);
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
                    //Skip the player turn
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
                for (int j=0; j<player.getHandRowSize(this.currentPlayer); j++) {
                    if (cmd.equals(player.getHand(this.currentPlayer, j))) {
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
                        this.playerList.add(this.currentPlayer+1);
                        player.playCard(this.currentPlayer, cmd);
                        
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
        //Get the next player turn
        getNextPlayer();
    }

    //The flow of the trick
    public void trick() {
        boolean endTrick = false;
        int winPlayer = 0;
        
        //Shuffle the deck and set the trick count to 0 for every game
        deck.shuffle();
        player.setTrickCount(0);

        while (!endTrick) {
            //Clear the center, playerList and up the trick count for every trick
            this.center.clear();
            this.playerList.clear();
            player.upTrickCount();

            //If it's the first trick
            if (player.getTrickCount() == 1) {
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
            for (int i=0; i<player.getNoOfPlayer(); i++) {
                turn();
                
                //Check if the player has no cards left in their hand,
                for (int j=0; j<player.getNoOfPlayer(); j++) {
                    //If yes, that player won the game
                    if (player.getHandRowSize(j) == 0) {
                        System.out.println("\n\nGO BOOM!!");
                        System.out.printf("Player%d Wins The Game \n", j+1);
                        endTrick = true;
                        break;
                    }
                }

                //To break out of the nested for loops
                if (endTrick) {
                    break;
                }
            }
            //To break out of the nested loops
            if (endTrick) {
                break;
            }

            //Display the game board and display the player who won the trick.
            displayBoard();
            winPlayer = getWinPlayer();
            currentPlayer = this.playerList.get(winPlayer) - 1;
            System.out.printf("\n\n*** Player" + this.playerList.get(winPlayer) + " wins Trick #%d *** \n", player.getTrickCount());
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        Boolean endGame = false;
        
        while (!endGame) {
            game.trick();

            //Part 2
            //Score
        }
    }

}
