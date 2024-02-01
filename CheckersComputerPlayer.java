/** 
 * Logic for fully functional Checkers Computer Player
 * 
 * packages file under 'core'.
 */
package core;

import core.Checkers;
import java.util.Random;

/**
 * This class contains the logic for an individual computer turn
 */
public class CheckersComputerPlayer {
	private char[][] board;
	
	/**
	 * This method exists to create a CheckersComputerPlayer of object checkers with the passed board.
	 * 
	 * @param checkers
	 */
	public CheckersComputerPlayer(Checkers checkers) {
		this.board = checkers.getBoard();
	}
	
	/**
	 * This method exists for the computer to execute a move on the board.
	 */
	public void makeMove() {//makes a random move
		// Instantiate ints
		int sourceRow = -1, sourceColumn = -1, destinationRow, destinationColumn;
		
		// Search for a valid 'o' piece
		for (int row = 0; row < 8; row++) {
			for (int col = 0; col < 8; col++) {
				// Checks if valid piece has a valid move
				if (isValidPiece(row, col) && checkValidMoves(row, col)) {
					// If true store values
					sourceRow = row;
					sourceColumn = col;
					break;
				}
			}
		}
		
		// Find valid destination for 'o' with stored values
		do {
			destinationRow = new Random().nextInt(8);
			destinationColumn = new Random().nextInt(8);
		} while (!isValidMove(sourceRow, sourceColumn, destinationRow, destinationColumn, 'o'));
		
		// Execute move
		board[destinationRow][destinationColumn] = 'o';
		board[sourceRow][sourceColumn] = '_';
		System.out.println(); // spacing
		
		// Print board
		printBoard();
	}
	
	/**
	 * This method exists to return a boolean value (T/F) if the passed position of piece has any
	 * valid moves.
	 * 
	 * @param row
	 * @param col
	 * 
	 * @return true; if has potential valid move
	 * @return false; if no potential valid move
	 */
	private boolean checkValidMoves(int row, int col) {//checks if the piece has any valid moves
		char currentPlayer = 'o';
		// Checks surrounding diagonals (distance 1)
		if (isValidMove(row, col, row + 1, col - 1, currentPlayer)) {
			return true;
		}
		if (isValidMove(row, col, row + 1, col + 1, currentPlayer)) {
			return true;
		}
		
		// Checks surrounding diagonals (distance 2)
		if (isValidCapture(row, col, row + 2, col - 2, row + 1, col - 1, currentPlayer)) {
			return true;
		}
		if (isValidCapture(row, col, row + 2, col + 2, row + 1, col + 1, currentPlayer)) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method exists to return a boolean value (T/F) if the passed source and destination are valid
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param destinationRow
	 * @param destinationColumn
	 * @param currentPlayer
	 * 
	 * @return true; if move is valid
	 * @return false; if move is not valid
	 */
	private boolean isValidMove(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn, char currentPlayer) {
		// Checks if the destination is within bounds
		if (destinationRow >= 0 && destinationRow < 8 && destinationColumn >= 0 && destinationColumn < 8) {
			// Checks if destination is empty
			if (board[destinationRow][destinationColumn] == '_') {
				// Checks if moving in correct direction
				if ((currentPlayer == 'x' && destinationRow > sourceRow) || (currentPlayer == 'o' && destinationRow < sourceRow)) {
		    		return false;
		    	}
				// Checks if diagonal of distance 1
				if (Math.abs(destinationRow - sourceRow) == 1 && Math.abs(destinationColumn - sourceColumn) == 1) {
					return true;
				} else if (Math.abs(destinationRow - sourceRow) == 2 && Math.abs(destinationColumn - sourceColumn) == 2) {
					// Checks if diagonal of distance 2 for capturing
					int capturedRow = (destinationRow + sourceRow) / 2;
					int capturedColumn = (destinationColumn + sourceColumn) / 2;
					
					// If piece capturing is not own piece, capture
					if (board[capturedRow][capturedColumn] == 'x') {
						board[capturedRow][capturedColumn] = '_';
						return true;
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * This method exists to return a boolean value (T/F) if the passed source, destination, and capture pieces are valid
	 * 
	 * @param sourceRow
	 * @param sourceColumn
	 * @param captureRow
	 * @param captureColumn
	 * @param destinationRow
	 * @param destinationColumn
	 * @param currentPlayer
	 * 
	 * @return true; if valid capture
	 * @return false; if not valid capture
	 */
	private boolean isValidCapture(int sourceRow, int sourceColumn, int captureRow, int captureColumn, int destinationRow, int destinationColumn, char currentPlayer) {
		int capturedRow = (sourceRow + destinationRow) / 2;
    	int capturedColumn = (sourceColumn + destinationColumn) / 2;
    	
    	char capturedPiece = board[capturedRow][capturedColumn];
    	
		// Checks if destination is within bounds
		if (destinationRow >= 0 && destinationRow < 8 && destinationColumn >= 0 && destinationColumn < 8) {
			// Checks if destination is open
			if (board[destinationRow][destinationColumn] == '_' && capturedPiece != currentPlayer) {
				//
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This method exists to return a boolean value (T/F) if passed piece is computer's piece
	 * 
	 * @param row
	 * @param col
	 * 
	 * @return true; if 'o'
	 * @return false; if not 'o'
	 */
	private boolean isValidPiece(int row, int col) {
		return board[row][col] == 'o';
	}
	
	/**
     * This method exists to print out the current state of the board.
     */
	private void printBoard() {
		for (int i = 0; i < 8; i++) {
			System.out.print((8 - i) + "|"); //prints row # here
			for (int j = 0; j < 8; j++) {
				System.out.print(board[i][j] + "|");
			}
			System.out.println();
		}
	    System.out.println("  a b c d e f g h"); //prints column here
	}
}