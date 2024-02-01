/** 
 * Logic for fully functional Checkers Game
 * 
 * packages file under 'core'.
 */
package core;

/**
 * This is a Checkers class within the core package making use 
 * of CheckersTextConsole class within the 'ui' package.
 * 
 * @author Nathaniel Davis-Perez
 * @version Build Jan 21, 2024
 * package core
 */
public class Checkers {
	
    private char[][] board;
    private char currentPlayer;

    /**
     * This class exists to hold a new version of a blank board
     * and currentPlayers value; x : o
     */
    public Checkers() {
        // Initialize board + set current player
        board = new char[][] {
            {'_', 'o', '_', 'o', '_', 'o', '_', 'o'},
            {'o', '_', 'o', '_', 'o', '_', 'o', '_'},
            {'_', 'o', '_', 'o', '_', 'o', '_', 'o'},
            {'_', '_', '_', '_', '_', '_', '_', '_'},
            {'_', '_', '_', '_', '_', '_', '_', '_'},
            {'x', '_', 'x', '_', 'x', '_', 'x', '_'},
            {'_', 'x', '_', 'x', '_', 'x', '_', 'x'},
            {'x', '_', 'x', '_', 'x', '_', 'x', '_'}
        };
        currentPlayer = 'x';
    }
    
    /**
     * This method exists to return the current state of the board.
     * 
     * @return board
     */
    public char[][] getBoard() {
    	return board;
    }
    
    /**
     * This method exists to print out the current state of the board.
     */
    public void printBoard() {
        for (int i = 0; i < 8; i++) {
        	System.out.print((8 - i) + "|"); //prints row # here
            for (int j = 0; j < 8; j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.println();
        }
        System.out.println("  a b c d e f g h"); //prints column here
    }

    /**
     * This method exists to switch current player
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == 'o') ? 'x' : 'o';
    }

    /**
     * This method exists to return the current player.
     * 
     * @return currentPlayer;
     */
    public char getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * This method exists to move a selected source piece to a selected destination.
     * 
     * @param sourceRow
     * @param sourceColumn
     * @param destinationRow
     * @param destinationColumn
     */
    public void movePiece(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
    	if (Math.abs(destinationRow - sourceRow) == 2 && Math.abs(destinationColumn - sourceColumn) == 2) {
    		// Captures piece in between if valid
    		capturePiece(sourceRow, sourceColumn, destinationRow, destinationColumn);;
    	} else {
    		char piece = getPieceAt(sourceRow, sourceColumn);
        	board[sourceRow][sourceColumn] = '_';
        	board[destinationRow][destinationColumn] = piece;
    	}
    }
    
    /**
     * This method exists to return a boolean value (T/F) of whether or not the passed move
     * is a valid move on the board.
     * 
     * @param sourceRow
     * @param sourceColumn
     * @param destinationRow
     * @param destinationColumn
     * 
     * @return true; if valid move
     * @return false: if not a valid move
     */
    public boolean isValidMove(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
    	
    	// Saves value of source and destination piece
    	char sourcePiece = getPieceAt(sourceRow, sourceColumn);
    	char destinationPiece = getPieceAt(destinationRow, destinationColumn);
    	
    	// Checks if destination piece is within bounds of the board
    	if (destinationRow < 0 || destinationRow > 8 || destinationColumn < 0 || destinationColumn > 8) {
    		return false;
    	}
    	
    	// Checks if the destination piece is occupied
    	if (destinationPiece != '_') {
    		return false;
    	}
    
    	// Checks if the appropriate piece is moving in the correct direction along the board
    	if ((sourcePiece == 'x' && destinationRow > sourceRow) || (sourcePiece == 'o' && destinationRow < sourceRow)) {
    		return false;
    	}
    	
    	// If statement reads whether or not the value is a diagonal (distance 1)
    	if (Math.abs(destinationRow - sourceRow) != 1 || Math.abs(destinationColumn - sourceColumn) != 1) {
    		// If statement reads whether or not the value is diagonal (distance 2)
    		if (Math.abs(destinationRow - sourceRow) == 2 && Math.abs(destinationColumn - sourceColumn) == 2) {
    			return true;
    		}
    		return false;
    	}
    	return true;
    }
    
    /**
     * This method exists to return the current piece on board at selected position.
     * 
     * @param row
     * @param column
     * 
     * @return board[row][column]
     */
    public char getPieceAt(int row, int column) {
    	return board[row][column];
    }
    
    /**
     * This method exists to execute a 'capture' move on the board.
     * 
     * @param sourceRow
     * @param sourceColumn
     * @param destinationRow
     * @param destinationColumn
     */
    private void capturePiece(int sourceRow, int sourceColumn, int destinationRow, int destinationColumn) {
    	// Position of captured piece
    	int capturedRow = (sourceRow + destinationRow) / 2;
    	int capturedColumn = (sourceColumn + destinationColumn) / 2;
    	
    	// Values of source and captured piece
    	char sourcePiece = getPieceAt(sourceRow, sourceColumn);
    	char capturedPiece = getPieceAt(capturedRow, capturedColumn);
    	
    	// If statement reads if captured piece is not blank or same piece, update board capturing piece
    	if (capturedPiece != '_'&& capturedPiece != sourcePiece) {
    		board[destinationRow][destinationColumn] = sourcePiece;
    		board[sourceRow][sourceColumn] = '_';
    		board[capturedRow][capturedColumn] = '_';
    	}
    }
 
    /**
     * This method exists to read when the game is over based on total pieces.
     * 
     * @return true; if total pieces = 0
     * @return false; if total pieces > 0
     */
    public boolean isGameOver() {
        return countPieces() <= 0;
    }
    
    /**
     * This method exists to count total pieces currentPlayer has on the board.
     * 
     * @return count
     */
    public int countPieces() {
    	char currentPlayer = getCurrentPlayer();
    	int count = 0;
    	
    	// Goes through board and counts total
    	for (int i = 0; i < 8; i ++) {
    		for (int j = 0; j < 8; j++) {
    			if (board[i][j] == currentPlayer) {
    				count++;
    			}
    		}
    	}
    	return count;
    }
}