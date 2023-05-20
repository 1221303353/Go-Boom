import java.util.*;

public class Game {
    //Variable declaration
    private Deck deck;
    private Player player;
    private ArrayList<String> center;
    private int currentPlayer;
    private ArrayList<Integer> playerList;

    //Variable initialisation
    public Game() {
        this.deck = new Deck();
        this.player = new Player();
        this.center = new ArrayList<>();
        this.playerList = new ArrayList<>();

        // for (int i=1; i<=player.getNoOfPlayer(); i++) {
        //     this.playerList.add("player"+i);
        // }
    }
    
    //To display the game board
    public void displayBoard() {
        //Display Trick #
        System.out.println();
        System.out.printf("Trick #%d \n", player.getTrickCount());

        //Display Player Hand
        for (int i=0; i<player.getNoOfPlayer(); i++) {
            System.out.printf("Player%d: [", i+1);
            for (int j=0; j<player.getNoOfPlayerCard(i); j++) {
                System.out.printf(player.getHand(i, j));
                if (j<player.getNoOfPlayerCard(i)-1) {
                    System.out.print(", ");
                }
            }
            System.out.print("] \n");
        }

        //Display Center
        System.out.printf("%-7s: [", "Center");
        for (int i=0; i<center.size(); i++) {
            System.out.printf(center.get(i));
            if (i<center.size()-1) {
                System.out.print(", ");
            }
        }
        System.out.print("] \n");
        
        //Display Deck
        System.out.printf("%-7s: [", "Deck");
        for (int i=0; i<deck.cardInDeckSize(); i++)
        {
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

    //To check wheather the card played is playable
    public boolean isPlayableCard(String card) {
        String leadSuit = deck.getSuit(this.center.get(0));
        String leadRank = deck.getRank(this.center.get(0));
        String cardSuit = deck.getSuit(card);
        String cardRank = deck.getRank(card);

        return leadSuit.equals(cardSuit) || leadRank.equals(cardRank);
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
    
    public String userInput() {
        //To ask the current user input
        Scanner scanner = new Scanner(System.in);
        String command;
        
        System.out.print(">  ");
        command = scanner.nextLine().toLowerCase();
        //scanner.close();
        return command;
    }

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

    public int getWinPlayer() {
        String leadSuit = deck.getSuit(this.center.get(0));
        String cardSuit;
        int[] cardRank = new int[4];

        if (this.center.size() == 5) {
            this.center.remove(0); 
        }
        
        for (int i=0; i<this.center.size(); i++) {
            cardRank[i] = deck.getRankValue(this.center.get(i));
        }

        int maxNumber = cardRank[0];
        int maxIndex = 0;

        for (int i=0; i<this.center.size(); i++) {
            cardSuit = deck.getSuit(this.center.get(i));
            
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

    public void turn() {
        boolean endTurn = false;
        String cmd;

        while (!endTurn) {
            displayBoard();
            
            //To display the current player turn
            System.out.printf("%-7s: Player%d \n", "Turn", currentPlayer+1);
            cmd = userInput();           

            //To exit the game
            if (cmd.equals("x")) {
                System.out.println("Exiting the game...");
                endTurn = true;
                //break;
            }
            //To draw a card 
            else if (cmd.equals("d")) {
                int count=0;
                boolean foundPlayableCard = false;
                boolean handPlayable = false;

                if (this.center.size() == 0) {
                    System.out.println("You must play a card to lead the trick.");
                    endTurn = false;
                    continue;
                }

                for (int i=0; i<player.getHandRowSize(currentPlayer); i++) {
                    if (isPlayableCard(player.getHand(currentPlayer, i))) {
                        handPlayable = true;
                        break;
                    }
                }

                //To check if the deck has a playable card
                for (int j=0; j<deck.cardInDeckSize(); j++) {
                    //If there is a playable card
                    if (isPlayableCard(deck.cardInDeck(j))) {
                        foundPlayableCard = true;
                        count = j+1;
                        break;
                    }
                }
                
                //If there is a playable card in the deck
                if (foundPlayableCard && !handPlayable) {
                    //Drew a card until the playable card is found
                    System.out.printf("You drew the card: ");
                    for (int j=0; j<count; j++) {
                        String drawnCard = deck.drawCard();
                        player.setHand(currentPlayer, drawnCard);
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
                    //break;
                }
                else if (handPlayable) {
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

                Boolean validHand = false;
                for (int j=0; j<player.getHandRowSize(currentPlayer); j++) {
                    if (cmd.equals(player.getHand(currentPlayer, j))) {
                        validHand = true;
                    }
                }

                if (validHand) {
                    //Play the playable card to the center
                    this.center.add(cmd);
                    this.playerList.add(currentPlayer+1);
                    player.dealCard(currentPlayer, cmd);
                    
                    //End the current player turn
                    endTurn = true;  
                    //break; 
                }
                // If the card is not playable
                else {
                    System.out.println("Invalid input. Please try again.");
                    endTurn = false;
                }
            }
        }
        getNextPlayer();
    }

    public void trick() {
        boolean endTrick = false;
        int winPlayer = 0;
        deck.shuffle();
        player.setTrickCount(0);

        while (!endTrick) {
            this.center.clear();
            this.playerList.clear();
            player.upTrickCount();

            if (player.getTrickCount() == 1) {
                this.center.add(0, deck.drawCard());
                setFirstPlayer();
                for (int i=0; i<player.getNoOfPlayer(); i++) {
                    for (int j=0; j<player.getNoOfCard(); j++) {
                        player.setHand(i, deck.drawCard());
                    }
                }
            }
            
            for (int i=0; i<player.getNoOfPlayer(); i++) {
                turn();
                
                for (int j=0; j<player.getNoOfPlayer(); j++) {
                    if (player.getHandRowSize(j) == 0) {
                        System.out.println("\n\nGO BOOM!!");
                        System.out.printf("Player%d Wins The Game \n", j+1);
                        endTrick = true;
                        break;
                    }
                }

                if (endTrick) {
                    break;
                }
            }
            
            if (endTrick) {
                break;
            }

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

            //Score
        }
    }
}
