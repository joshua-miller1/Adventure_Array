/*
Joshua Miller
Final Project -- World Exporation 
In this game you have to manuver across a ten by ten grid to find the helipad to escape. There are two obstacles 
to this game, a lake and a cliff which the player can not travel through. You can score points in various ways but they
are undisclosed for design purposes. 
Dec. 3 2018 
 */

package joshuamillerfinalprojectjava;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

/**
 *
 * @author jt.miller
 */
public class JoshuaMillerFinalProjectJava {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        Random randomNumber = new Random();
        Scanner Keyboard = new Scanner(System.in);
        
        //booleans which control the game being turned on 
        boolean gameOn = true;
        boolean menuOn = true;
        
        //main part of logic 
        String locationStatus = null;

        //booleans and integers here allow me to create a score 
        int score = 0;
        int moveCounter = 0;
        boolean achievementMap = false;
        boolean achievementLake = false;
        boolean achievementCliff = false;
        boolean achievementHeli = false; //this is important to the exit method beceause it also tracks if the person actually played the game or not 
        
        // these strings allow me to compare the value inside the array in the logic of the game
        String H = "H";
        String N = "N";
        String L = "L";
        String C = "C";
        String initials;
        String[][] map = new String[10][10];

        //Fill the Map with value 'N' for nothing, can not let the array be empty because you can't call on an empty array
        //Null gives an error which the N can avoid 
        for (String[] map1 : map) {
            for (int playerLocationY = 0; playerLocationY < map1.length; playerLocationY++) {
                map1[playerLocationY] = "N";
            }
        }

        //The Game Menu 
        System.out.println("Welcome to World Exploration!");
        do {
            System.out.println("Selection an option: 1-4.\n\nMessage from the creator: 'I highly encourage you to read the game description! It is full of information!'\n");
            System.out.println("1. Play Game\n2. Discription\n3. Credits\n4. Exit");
            int menuChoice = Keyboard.nextInt();
            switch (menuChoice) {
                case 1:
                    menuOn = false;
                    System.out.println("Lets get started!");
                    break;
                case 2:
                    System.out.println("This is a text game where you naviagate your player across the terrain.\n"
                            + "You will move by selecting WASD for the movement directions followed by enter.\n(W is up, A is left, S is down, D is right). There "
                            + "are multiple obstacles \nthat you will not be able to move through and will have to find a way around.\n"
                            + "Be careful out there and good luck!\n");
                    break;
                case 3:
                    System.out.println("This game was made by Josh Miller. I appreciate you for looking at this section.\nJust for you, because you looked at the credits, here is the trick to winning this game.\nUse your map by selecting the M option when it prompts you for movement. Good Luck!\n");
                    System.out.println("On the map you are the P and obstacles are C, and L, H is the escape route.");
                    break;
                case 4:
                    menuOn = false;
                    gameOn = false;
                    break;

            }
        } while (menuOn);
        
//===================================================================================================
//This section of code deals with creating the various location in the map, the try catch is important here to 
//keep the program from stopping when parts of areas are generated outside the map 
        
        //create the lake obstacle
        int lakeSpawnX, lakeSpawnY;
        lakeSpawnX = randomNumber.nextInt(8);
        lakeSpawnY = randomNumber.nextInt(8);
        try {
            map[lakeSpawnX][lakeSpawnY] = "L";
            map[lakeSpawnX][lakeSpawnY + 1] = "L";
            map[lakeSpawnX][lakeSpawnY + 2] = "L";
            map[lakeSpawnX + 1][lakeSpawnY] = "L";
            map[lakeSpawnX + 1][lakeSpawnY + 1] = "L";
            map[lakeSpawnX + 1][lakeSpawnY + 2] = "L";
            map[lakeSpawnX + 2][lakeSpawnY] = "L";
            map[lakeSpawnX + 2][lakeSpawnY + 1] = "L";
            map[lakeSpawnX + 2][lakeSpawnY + 2] = "L";
        } catch (ArrayIndexOutOfBoundsException exception) {}

        //create the cliff obstacle
        int cliffSpawnX, cliffSpawnY;
        cliffSpawnX = randomNumber.nextInt(9);
        cliffSpawnY = randomNumber.nextInt(9);
        try {
            map[cliffSpawnX][cliffSpawnY] = "C";
            map[cliffSpawnX + 1][cliffSpawnY] = "C";
            map[cliffSpawnX][cliffSpawnY + 1] = "C";
            map[cliffSpawnX + 1][cliffSpawnY + 1] = "C";
        } catch (ArrayIndexOutOfBoundsException exception) {}
        
        //create the helipad
        int helipadX, helipadY;
        helipadX = randomNumber.nextInt(9);
        helipadY = randomNumber.nextInt(9);
        try {
            map[helipadX][helipadY] = "H";
            map[helipadX][helipadY + 1] = "H";
            map[helipadX + 1][helipadY] = "H";
            map[helipadX + 1][helipadY + 1] = "H";
        } catch (ArrayIndexOutOfBoundsException exception) {}
        
        //Create the player last so it can not be overlapped  
        int playerLocationX, playerLocationY;
        playerLocationX = randomNumber.nextInt(9);
        playerLocationY = randomNumber.nextInt(9);
        //place the player 
        map[playerLocationX][playerLocationY] = "P";

        //====================================================================================
        /*This is the game loop that tracks where the player is and where the player intends to go. It also tracks the
        number of moves that the player makes and it contains the logic for checking the next spot in the array
        Key points in this section are moveCounter and locationStatus and the switch case 
        
        */
        while (gameOn) {
            moveCounter++;
            System.out.println("What would you like to do?");
            System.out.println("Your options are: w, a, s, d.");
            char movementChoice = Keyboard.next().charAt(0);

            switch (movementChoice) {
                case 'w':
                    try {
                        locationStatus = map[playerLocationX][playerLocationY - 1];
                    } catch (ArrayIndexOutOfBoundsException exception) {
                    }
                    if (playerLocationY == 0) {
                        System.out.println("You cannot go that way.");
                        break;
                    } else if (locationStatus.equals(N)) {
                        System.out.println("You have moved north.");
                        map[playerLocationX][playerLocationY] = "N";
                        playerLocationY -= 1;
                        map[playerLocationX][playerLocationY] = "P";
                        break;
                    } else if (locationStatus.equals(H)) {
                        System.out.println("Congradualations you have completed the game!");
                        achievementHeli = true;
                        gameOn = false;
                        break;
                    } else if (locationStatus.equals(L)) {
                        System.out.println("There is a lake in the way, you can't risk swimming through it.");
                        achievementLake = true;
                        break;
                    } else if (locationStatus.equals(C)) {
                        achievementCliff = true;
                        System.out.println("There is an cliff in the way, you can't go any further.");
                        break;
                    } else {
                        System.out.println("You can not go any further or the demon lords will kill you out of spite.");
                        break;
                    }
                case 'a':
                    try {
                        locationStatus = map[playerLocationX - 1][playerLocationY];
                    } catch (ArrayIndexOutOfBoundsException exception) {
                    }
                    if (playerLocationX == 0) {
                        System.out.println("You cannot go that way.");
                        break;
                    } else if (locationStatus.equals(N)) {
                        System.out.println("You have moved west.");
                        map[playerLocationX][playerLocationY] = "N";
                        playerLocationX -= 1;
                        map[playerLocationX][playerLocationY] = "P";
                        break;
                    } else if (locationStatus.equals(H)) {
                        System.out.println("Congradualations you have completed the game!");
                        achievementHeli = true;
                        gameOn = false;
                        break;
                    } else if (locationStatus.equals(L)) {
                        System.out.println("There is a lake in the way, you can't risk swimming through it.");
                        achievementLake = true;
                        break;
                    } else if (locationStatus.equals(C)) {
                        achievementCliff = true;
                        System.out.println("There is an cliff in the way, you can't go any further.");
                        break;
                    } else {
                        System.out.println("You can not go any further or you risk the life of your first son.");
                        break;
                    }
                case 's':
                    try {
                        locationStatus = map[playerLocationX][playerLocationY + 1];
                    } catch (ArrayIndexOutOfBoundsException exception) {
                    }

                    if (playerLocationY == 9) {
                        System.out.println("You cannot go that way.");
                        break;
                    } else if (locationStatus.equals(N)) {
                        System.out.println("You have moved south.");
                        map[playerLocationX][playerLocationY] = "N";
                        playerLocationY += 1;
                        map[playerLocationX][playerLocationY] = "P";
                        break;
                    } else if (locationStatus.equals(H)) {
                        System.out.println("Congradualations you have completed the game!");
                        achievementHeli = true;
                        gameOn = false;
                        break;
                    } else if (locationStatus.equals(L)) {
                        System.out.println("There is a lake in the way, you can't risk swimming through it.");
                        achievementLake = true;
                        break;
                    } else if (locationStatus.equals(C)) {
                        achievementCliff = true;
                        System.out.println("There is an cliff in the way, you can't go any further.");
                        break;
                    } else {
                        System.out.println("If you proceed you will die, try another route. .");
                        break;
                    }
                case 'd':
                    try {
                        locationStatus = map[playerLocationX + 1][playerLocationY];
                    } catch (ArrayIndexOutOfBoundsException exception) {
                    }
                    if (playerLocationX == 9) {
                        System.out.println("You cannot go that way.");
                        break;
                    } else if (locationStatus.equals(N)) {
                        System.out.println("You have moved east.");
                        map[playerLocationX][playerLocationY] = "N";
                        playerLocationX += 1;
                        map[playerLocationX][playerLocationY] = "P";
                        break;
                    } else if (locationStatus.equals(H)) {
                        System.out.println("Congradualations you have completed the game!");
                        achievementHeli = true;
                        gameOn = false;
                        break;
                    } else if (locationStatus.equals(L)) {
                        System.out.println("There is a lake in the way, you can't risk swimming through it.");
                        achievementLake = true;
                        break;
                    } else if (locationStatus.equals(C)) {
                        achievementCliff = true;
                        System.out.println("There is an cliff in the way, you can't go any further.");
                        break;
                    } else {
                        System.out.println("If you're seeing this code in game that means I made a mistake.. sorry about that.");
                        break;
                    }
                case 'm':
                    achievementMap = true;
                    //print map -- intital
                    for (int y = 0; y < 10; y++) {
                        for (int x = 0; x < 10; x++) {
                            System.out.printf("%10s", map[x][y] + " ");
                        }
                        System.out.println();
                    }
                    break;
                case 'q':
                    gameOn = false;
                    break;
                default:
                    break;
            }

        }

        ///Creating the scores and stating the achievements
        if (achievementMap) {
            score += 10;
            System.out.println("Achievement Earned: Map");
        }
        if (achievementLake) {
            score += 10;
            System.out.println("Achievement Earned: Lake");
        }
        if (achievementCliff) {
            score += 10;
            System.out.println("Achievement Earned: Cliff");
        }
        if (moveCounter < 15) {
            score += 15;
        } else if (moveCounter > 15) {
            score += 10;
        }
        if (achievementHeli == true) {
            System.out.println("Achievement Earned: Helipad");
            score += 5;
        }
        if (achievementHeli == false) {
            score = 0;
        }

        //This section deals with leaving the program without starting the game, and writing the score to the scoreboard page 
        try {
            if (achievementHeli == true) {
                System.out.println("Please enter your initials for the score board.");
                initials = Keyboard.next();
                System.out.println("You have made the scoreboard with a score of " + score + "!\nPlease check your desktop for the file.");

                FileWriter fw = new FileWriter("/Users/jt.miller/Desktop/Scoreboard.txt", true);
                PrintWriter outputFile = new PrintWriter(fw);

                outputFile.write(initials + ": " + score);
                outputFile.write("\n");
                outputFile.close();
            } else {
                System.out.println("Play again soon!");
            }
        } catch (FileNotFoundException | EOFException e) {
            System.out.println("Error message: " + e.getMessage());
        }

    }
}
