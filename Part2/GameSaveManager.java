import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

//Class to write and access data in a txt file (use in save/load feature)
public class GameSaveManager {

    //To write data in a txt file
    public static void saveGame(String filename, ArrayList<String> card, ArrayList<ArrayList<String>> hand, LinkedHashMap<String, Integer> playerScore,
     int currentPlayerIndex, int trickCount, ArrayList<String> center, ArrayList<Integer> playerInputList) {
        
        try {
            //To set the txt file location
            String directory = "C:Part2";
            String filePath = directory + "/" + filename;
            FileWriter writer = new FileWriter(filePath);
            
            writer.write("card:");
            for (String data : card) {
                writer.write(data + ",");
            }
            writer.write("\n");

            writer.write("hand:");
            for (ArrayList<String> player : hand) {
                writer.write("[");
                for (String item : player) {
                    writer.write(item + ",");
                }
                writer.write("],");
            }
            writer.write("\n");

            writer.write("playerScore:");
            for (String player : playerScore.keySet()) {
                writer.write(player + "=" + playerScore.get(player) + ",");
            }
            writer.write("\n");

            writer.write("currentPlayerIndex:" + currentPlayerIndex + "\n");
            writer.write("trickCount:" + trickCount + "\n");

            writer.write("center:");
            for (String item : center) {
                writer.write(item + ",");
            }
            writer.write("\n");

            writer.write("playerInputList:");
            for (int input : playerInputList) {
                writer.write(input + ",");
            }
            writer.write("\n");

            writer.close();
            System.out.println("Game saved successfully.");

        } catch (IOException e) {
            System.out.println("Failed to save the game: " + e.getMessage());
        }
    }

    //To access the data in a txt file
    public static GameData loadGame(String filename) {
        
        try {
            //To load the txt file at the location
            String directory = "C:Part2";
            String filePath = directory + "/" + filename;
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            //Variable declaration and initialisation
            String line;
            ArrayList<String> card = new ArrayList<>();
            ArrayList<ArrayList<String>> hand = new ArrayList<>();
            LinkedHashMap<String, Integer> playerScore = new LinkedHashMap<>();
            int currentPlayerIndex = 0;
            int trickCount = 0;
            ArrayList<String> center = new ArrayList<>();
            ArrayList<Integer> playerInputList = new ArrayList<>();

            //Loop while txt file has a line
            while ((line = reader.readLine()) != null) {
                
                //Split the line into two parts seperated by ":" into an array
                String[] parts = line.split(":");
                
                //If a line in a txt file has data
                if (parts.length == 2) {
                    //Seperate the data into two parts, key and value
                    String key = parts[0];
                    String value = parts[1];

                    //Access the value of a data by their key and add it into this class variable
                    
                    if (key.equals("card")) {
                        String[] cardArray = value.split(",");
                        for (String item : cardArray) {
                            card.add(item);
                        }

                    } 
                    else if (key.equals("hand")) {
                        String[] handArray = value.split("],");
                        for (String player : handArray) {
                            ArrayList<String> handList = new ArrayList<>();
                            String[] cardArray = player.replace("[", "").replace("]", "").split(",");
                            for (String item : cardArray) {
                                handList.add(item);
                            }
                            hand.add(handList);
                        }

                    } 
                    else if (key.equals("playerScore")) {
                        String[] scoreArray = value.split(",");
                        for (String score : scoreArray) {
                            String[] keyValue = score.split("=");
                            if (keyValue.length == 2) {
                                String player = keyValue[0];
                                int scoreValue = Integer.parseInt(keyValue[1]);
                                playerScore.put(player, scoreValue);
                            }
                        }
                    
                    }
                    else if (key.equals("currentPlayerIndex")) {
                        currentPlayerIndex = Integer.parseInt(value);
                    }
                    else if (key.equals("trickCount")) {
                        trickCount = Integer.parseInt(value);
                    }
                    else if (key.equals("center")) {
                        String[] centerArray = value.split(",");
                        for (String item : centerArray) {
                            center.add(item);
                        }
                    }
                    else if (key.equals("playerInputList")) {
                        String[] inputArray = value.split(",");
                        for (String input : inputArray) {
                            playerInputList.add(Integer.parseInt(input));
                        }
                    }
                }
            }

            reader.close();
            return new GameData(card, hand, playerScore, currentPlayerIndex, trickCount, center, playerInputList);

        } catch (IOException e) {
            System.out.println("Failed to load the game: " + e.getMessage());
        }

        return null;
    }
}
