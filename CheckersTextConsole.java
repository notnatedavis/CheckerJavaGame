/**
 * Program that runs a fully functional Checkers Game within the console of the user
 * 
 * packages file under 'ui'.
 */
package ui;

import core.Checkers;
import core.CheckersComputerPlayer;

import java.util.Scanner; // scanner

/**
 * This is a CheckersTextConsole class within the ui package making use 
 * of Checkers class within the core package.
 * 
 * @author Nathaniel Davis-Perez
 * @version Build Jan 29, 2024
 * package ui
 */
public class CheckersTextConsole {
	
    /**
     * This main method instantiates a Checkers game followed by a while
     * loop that is active until the isGameOver() method from the core package 
     * reads true.
     */
    public static void main(String[] args) {
    	
    	/**
        * instantiates new object of Checkers to game
        * instantiates new object of Scanner to scanner 
        */
       Checkers game = new Checkers();
       Scanner scanner = new Scanner(System.in);
        
        /**
         * This while loop will remain true until the method isGameOver() 
         * returns true from the Checkers class within the core package.
         */
        while (!game.isGameOver()) {
        	// Displays copy of a original state 'Checkers board'
            game.printBoard();
            
            char choice; // Variable for Player's game mode
            while (true) {
            	System.out.println("Begin Game. Enter 'P' to play against another player; enter 'C' to play against computer.");
                choice = scanner.next().charAt(0);
                
                if (choice == 'P' || choice == 'C') {
                	break;
                } else { // If not valid choice
                    System.out.println("Invalid choice. Please enter 'P' or 'C'.");
                }
            }
            if (choice == 'P') {
            	System.out.println("Start game against player.");
            	playAgainstPlayer(); // Begins game; player vs player
            } else if (choice == 'C') {
            	System.out.println("Start game against computer.");
            	playAgainstComputer(); // Begins game; player vs computer
            }
        }
    }
    /**
     * This method exists to loop (player vs player) through logic for a Checkers game.
     */
    private static void playAgainstPlayer() {
    	
    	/**
         * instantiates new object of Checkers to game
         * instantiates new object of Scanner to scanner 
         */
        Checkers game = new Checkers();
        Scanner scanner = new Scanner(System.in);
    	
    	while (!game.isGameOver()) {
    		// Print current player + read move
    		System.out.println("You are Player " + game.getCurrentPlayer() + ". It is your turn.");
            System.out.println("Choose a cell position of piece to be moved and the new position. e.g., 3a-4b");
    		String move = scanner.nextLine();
    		
    		// Validate move format
    		if (isValidMoveFormat(move)) { // Executes if true
    			// Split 'move' into source and destination
    			String[] coordinates = move.split("-");
            	
    			// Stored integer values for source and destination
                int sourceRow = 8 - Integer.parseInt(coordinates[0].substring(0, 1));
                int sourceColumn = coordinates[0].charAt(1) - 'a';
                int destinationRow = 8 - Integer.parseInt(coordinates[1].substring(0, 1));
                int destinationColumn = coordinates[1].charAt(1) - 'a';
                
                // Checks if passed move is a valid move on board
    			if (game.isValidMove(sourceRow, sourceColumn, destinationRow, destinationColumn)) {
    				
    				// Moves piece if valid
    				game.movePiece(sourceRow, sourceColumn, destinationRow, destinationColumn);
    				
    				game.switchPlayer(); // Only switches once a valid move has been made
    				System.out.println(); // Spacing
    				game.printBoard(); // Only prints once a valid move has been made
    			}
    		} else {
    			System.out.println("Invalid move format");
    			System.out.println();
    		}
    	}
    	/**
         * Prints Final version of the updated board followed by assigning the winner
         * based on which player has 0 pieces remaining, and then displaying appropriate winner.
         */ 
        game.printBoard();
        char winner = (game.countPieces() == 0) ? 'o' : 'x';
        System.out.println("Player " + winner + " Won the Game");
    }
    /**
     * This method exists to loop (player vs computer) through logic for a Checkers game.
     */
    private static void playAgainstComputer() {
    	
    	/**
         * instantiates new object of Checkers to game
         * instantiates new object of Scanner to scanner 
         * instantiates new object of CheckersComputerPlayer to computer
         */
        Checkers game = new Checkers();
        Scanner scanner = new Scanner(System.in);
    	CheckersComputerPlayer computer = new CheckersComputerPlayer(game);
    	
    	while (!game.isGameOver()) {
    		// Print current player + read move
    		System.out.println("You are Player " + game.getCurrentPlayer() + ". It is your turn.");
            System.out.println("Choose a cell position of piece to be moved and the new position. e.g., 3a-4b");
    		String move = scanner.nextLine();
    		
    		// Validate move format
    		if (isValidMoveFormat(move)) { // Executes if true
    			// Split 'move' into source and destination
    			String[] coordinates = move.split("-");
            	
    			// Stored integer values for source and destination
                int sourceRow = 8 - Integer.parseInt(coordinates[0].substring(0, 1));
                int sourceColumn = coordinates[0].charAt(1) - 'a';
                int destinationRow = 8 - Integer.parseInt(coordinates[1].substring(0, 1));
                int destinationColumn = coordinates[1].charAt(1) - 'a';
                
                // Checks if passed move is a valid move on board
    			if (game.isValidMove(sourceRow, sourceColumn, destinationRow, destinationColumn)) {
    				// Moves piece if valid
    				game.movePiece(sourceRow, sourceColumn, destinationRow, destinationColumn);
    				
    				game.switchPlayer(); // Only switches once a valid move has been made
    				System.out.println(); // Spacing
    				game.printBoard(); // Only  prints once a valid move has been made
    			}
    		} else {
    			System.out.println("Invalid move format");
    			System.out.println();
    		}
    		if (game.getCurrentPlayer() == 'o') {
    			// Indicates computer's turn
    			computer.makeMove();
    			game.switchPlayer();
    		}
    	}
    }
    /**
     * This method exists to return a boolean value to validate that the
     * move string is formated as a valid move.
     * 
     * @param move
     * 
     * @return true; if format
     * @return false; if not format
     */
    private static boolean isValidMoveFormat(String move) {
    	if(!move.matches("[1-8][a-h]-[1-8][a-h]")) {
    		return false; // Will print invalid move
    	}
    	return true;
    }
}