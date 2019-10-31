package ca.mcgill.ecse223.quoridor.controller;
import javax.swing.text.Utilities;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.utilities.*;
import sun.util.resources.ext.CurrencyNames_da_DK;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class QuoridorController {



	/**
	 * Method to capture the time at which the clock is stopped
	 * Used as a helper method
	 * @param
	 * @author Ousmane Baricisse
	 * @return time in seconds
	 */
	public static long stopClock() throws Exception {
		try {
			return TimerUtilities.getCurrentTime();
		} catch (Exception e) {
			throw new UnsupportedOperationException();
		}

	}

	/**
	 * This method, according to the Gherkin definition, should Complete 
	 * a player move from the given position to the next tile.
	 * returns true if the move was completed successfully.
	 * @param quoridor
	 * @author Ousmane Baricisse
	 * @param quoridor
	 * @param destination
	 * @return boolean
	 * @throws
	 */
	public static boolean completeMove(Quoridor quoridor) {
		throw new UnsupportedOperationException();
	}

	/**
	 * @author Ousmane Baricisse
	 * @return
	 */
	public static String showPlayerTurn() {
		throw new UnsupportedOperationException();
	}



	/**
	 * This method, according to the Gherkin definition, should grab wall
	 * from the stock and add it to the board
	 * Need to know the player and the board as parameters
	 * @param board, player
	 * @author Ousmane Baricisse
	 * @return
	 */
	public static void grabWall(Board board, Player player) {
		// TODO Auto-generated method stub
	}
	/**
	 * Method - initializeNewGame()
	 * 
	 * This method, according to the Gherkin definition, should initialize a new
	 * game in the Quoridor object It should perform the following: 1. Set a name to
	 * White and Black Players 2. Set the total thinking time to both players
	 * 
	 * @param quoridor - Quoridor in which to start new game
	 * @author Tristan Bouchard
	 */
	public static void initializeNewGame(Quoridor quoridor) throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method: startClock(Game game) 
	 * This method, according to the Gherkin
	 * specification files, should: 
	 * 1. Run the game 
	 * 2. Initialize the board
	 * 
	 * @param No parameters - The current player's clock will start counting down
	 * @throws Exception
	 * @author Tristan Bouchard
	 */
	public static void startClock() throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method - initializeBoard(Quoridor quoridor)
	 * 
	 * This method, based on the Gherkin definition, should initialize the game
	 * board for the specified quoridor. It should: 
	 * 1. Set the current player to white player 
	 * 2. Set both pawns to their initial position 
	 * 3. Set all of the players walls to their stock 
	 * 4. Start the white player's clock
	 * 
	 * @throws Exception
	 * @author Tristan Bouchard
	 */

	public static void initializeBoard() throws Exception {
		throw new UnsupportedOperationException();
	}

	/**
	 * Method - setThinkingTime(int min, int sec)
	 *
	 * This method, according to the Gherkin definition, should set the total
	 * thinking time for both players in the game, before the game begins
	 *
	 * @param min - Integer sets the number of minutes
	 * @param sec - Integer sets the number of seconds
	 * @author Nayem Alam
	 */
	public static void setThinkingTime(Integer min, Integer sec) {
		Player bPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		Player wPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		// converts min and sec to long type (unix epoch time)
		Time thinkingTime = new Time(min* 60L *1000 + sec* 1000L);
		// set same thinking time for both players
		bPlayer.setRemainingTime(thinkingTime);
		wPlayer.setRemainingTime(thinkingTime);
	}
	/**
	 * Method - selectExistingUserName(String username)
	 *
	 * This method, according to the Gherkin definition, should allow a player
	 * starting the game to select an existing username
	 *
	 * @param username - String username exists within the list of users
	 * @param currentPlayer - Player can either be whitePlayer or blackPlayer
	 * @param quoridor - Quoridor contains given list of users (if any)
	 * @throws Exception - throws exception if users list is empty
	 * @author Nayem Alam
	 */
	public static void selectExistingUserName(String username, Player currentPlayer, Quoridor quoridor) throws Exception {
		List<User> users = quoridor.getUsers();
		if(!users.isEmpty()) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getName().equals(username)) {
					// currentPlayer's User would contain the existing username
					currentPlayer.setUser(users.get(i));
				}
			}
		} else {
			throw new Exception("There are no existing users in the list.");
		}
	}

	/**
	 * Method - selectNewUserName(String username)
	 *
	 * This method, according to the Gherkin definition, should allow a player
	 * starting the game to select a new username
	 *
	 * @param username - String username is new and does not exist in list of users yet
	 * @param currentPlayer - Player can either be whitePlayer or blackPlayer
	 * @param quoridor - Quoridor contains given list of users (if any)
	 * @author Nayem Alam
	 */
	public static void selectNewUserName(String username, Player currentPlayer, Quoridor quoridor) {
		List<User> users = quoridor.getUsers();
		for (int i = 0; i < users.size(); i++) {
			// currentPlayer would be able to set a new username
			users.get(i).setName(username);
			currentPlayer.setUser(users.get(i));
		}
	}


	/**
	 * Modifier method used to set the name of the white player
	 * 
	 * @param name
	 * @return true if name is set correctly, false otherwise
	 * @author Tristan Bouchard
	 */
	public static Boolean setWhitePlayerUserName(String name) {
		if (!ControllerUtilities.isUserNameValid(name)) {
			return false;
		}
		Player p = getWhitePlayer();
		p.getUser().setName(name);

		return true;
	}

	/**
	 * Modifier method used to set the name of the black player
	 * 
	 * @param name
	 * @return true if name is set correctly, false otherwise
	 * @author Tristan Bouchard
	 */
	public static Boolean setBlackPlayerUserName(String name) {
		if (!ControllerUtilities.isUserNameValid(name)) {
			return false;
		}
		Player p = getBlackPlayer();
		p.getUser().setName(name);
		return true;

	}

	/**
	 * Query method to obtain the white player from the current game
	 * 
	 * @return
	 * @author Tristan Bouchard
	 */
	public static Player getWhitePlayer() {
		return QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
	}

	/**
	 * Query method to obtain the black player from the current game
	 * 
	 * @return
	 * @author Tristan Bouchard
	 */
	public static Player getBlackPlayer() {
		return QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
	}

	/**
	 * Modifier method used to set the thinking time for both players
	 * 
	 * @param thinkingTime
	 * @return
	 * @author Tristan Bouchard
	 */
	public static Boolean setTotalThinkingTime(long thinkingTime) {
		if (thinkingTime < 0) {
			return false;
		}
		Boolean successWhite = setWhitePlayerThinkingTime(thinkingTime);
		Boolean successBlack = setBlackPlayerThinkingTime(thinkingTime);
		return successBlack && successWhite;
	}

	/**
	 * Modifier method used to set the thinking time for black player
	 * 
	 * @param thinkingTime
	 * @return
	 * @author Tristan Bouchard
	 */
	public static Boolean setBlackPlayerThinkingTime(long thinkingTime) {
		Player playerBlack = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		if (thinkingTime < 0) {
			return false;
		}
		Boolean success = playerBlack.setRemainingTime(new Time(thinkingTime));
		return success;
	}

	/**
	 * Modifier method used to set the thinking time for white player
	 * 
	 * @param thinkingTime
	 * @return
	 * @author Tristan Bouchard
	 */
	public static Boolean setWhitePlayerThinkingTime(long thinkingTime) {
		Player playerWhite = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		if (thinkingTime < 0) {
			return false;
		}
		Boolean success = playerWhite.setRemainingTime(new Time(thinkingTime));
		return success;
	}

	/**
	 * Query method used to verify the correct initialization of the board
	 * 
	 * @return
	 * @author Tristan Bouchard
	 */
	public static Boolean verifyBoardInitialization() {
		Board board = QuoridorApplication.getQuoridor().getBoard();
		// Verify number of tiles
		Boolean correctNumberOfTiles = (board.getTiles().size() == ControllerUtilities.TOTAL_NUMBER_OF_TILES);

		// Verify the indices of the tiles only if the total size is correct
		Boolean correctTileIndexing = true;
		for (int row = 0; row < ControllerUtilities.TOTAL_NUMBER_OF_ROWS; row++) {
			for (int col = 0; col < ControllerUtilities.TOTAL_NUMBER_OF_COLS; col++) {
				// Obtain tile in the list and verify that the indices are correct
				int index = ((ControllerUtilities.TOTAL_NUMBER_OF_COLS) * (row) + (col));
				Tile currentTile = board.getTile(index);
				correctTileIndexing = correctTileIndexing && (row == currentTile.getRow());
				correctTileIndexing = correctTileIndexing && (col == currentTile.getColumn());
			}
		}
		return correctNumberOfTiles && correctTileIndexing;
	}

	/**
	 * Query method used to obtain the current position of the white player, as a
	 * PlayerPosition
	 * 
	 * @return current PlayerPosition
	 * @author Tristan Bouchard
	 */
	public static PlayerPosition getWhitePlayerPosition() {
		PlayerPosition whitePos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getWhitePosition();
		return whitePos;
	}

	/**
	 * Query method used to obtain the current position of the black player, as a
	 * PlayerPosition
	 * 
	 * @return current PlayerPosition
	 * @author Tristan Bouchard
	 */
	public static PlayerPosition getBlackPlayerPlayerPosition() {
		PlayerPosition blackPos = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getBlackPosition();
		return blackPos;
	}


	
	/**
	 * Method that initializes the Validation of the position, 
	 * @param PawnPosition
	 * @throws UnsupportedOperationException
	 * @returns true/false
	 * @author Alexander Legouverneur
	 */
	public static boolean InitializeValidatePosition(int row, int col) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method that returns if the position is valid by calling InitializeVaidatePostion()
	 * @param pawn position
	 * @throws UnsupportedOperationException
	 * @returns ok/error strings
	 * @author Alexander Legouverneur
	 */
	public static String ValidatePawnPosition(int row, int col) throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}
	/**
	 * Method that initiates the validation of the position
	 * @param wallPosition
	 * @param Walldir
	 * @return true/false true for initialized validation
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static boolean InitiatePosValidation(int row, int col, String Walldir) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method that returns the direction of the wall after validation
	 * @param WallPosition
	 * @param Wall Direction
	 * @return direction -of the wall
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static String ValidateWall(int row, int col, String dir ) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method that checks if wall Position is valid
	 * @param WallPosition
	 * @param Wall Direction
	 * @return true/false
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static boolean CheckWallValid(int row, int col, String dir) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
//end of validate position
	/**
	 * Checks if the wall is on the side edge of the board
	 * @param aWall
	 * @return true/false -true if on side edge
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static boolean CheckWallSideEdge(Wall aWall) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Methods that checks if the move on the specified side is possible, with if statements to see if the move is 
	 * legal, if yes proceed to the move otherwise, call the method IlllegalWallMove()
	 * @param aWall
	 * @param side
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static void VerifyMoveWallOnSide(Wall aWall, String side) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
	
	/**
	 * This method is called by VerifyMoveWallOnSide if the move is illegal. Returns a string "illegal", and makes
	 * sure the coordinates of the wall remain the same as before
	 * @param aWall
	 * @return String
	 * @throws UnsupportedOperationException
	 *@author Alexander Legouverneur
	 */
	public static String IllegalWallMove(Wall aWall) throws UnsupportedOperationException{
		throw new UnsupportedOperationException();
	}
  	/**
	 * Method - saveGameFile(String filename, Game game)
	 * 
	 * Controller method used to save the game as a text file
	 * This file can later be loaded to keep playing 
	 * this instance of the game
	 * 
	 * @param filename - String name of file
	 * @return String - contents of the file
	 * @author Nicolas Buisson
	 * 
	 */
	public static String saveGameFile(String filename) {
		
		throw new UnsupportedOperationException();
	}
	/**
	 * Method - overWriteFile()
	 * 
	 * Controller method used to overwrite a saved game file
	 * with the current game
	 * 
	 * @param filename - String name of file
	 * @return String - contains the content of the overwritten file
	 * @author Nicolas Buisson
	 * 
	 */
	public static String overWriteFile(String filename) {
		
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method - cancelOverWriteFile()
	 * 
	 * Controller method used to cancel overwriting a file
	 * 
	 * @return String - content of file
	 * @author Nicolas Buisson
	 * 
	 */
	public static String cancelOverWriteFile() {
		throw new UnsupportedOperationException();
		//if user cancels overwriting the file
		//then nothing happens, file and model are unchanged
		//only change is in UI
		//might not have to be a controller method
	}
	/**
	 * Method - loadSavedGame()
	 * 
	 * Controller method used to load a file containing the game state
	 * of a previous game that the user wishes to continue playing
	 * 
	 * @param filename - name of file
	 * @return Game - the game to be continued is returned
	 * @author Nicolas Buisson
	 * 
	 */
	public static Game loadSavedGame(String filename) {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Method used to rotate a wall
	 * @author Iyatan Atchoro
	 */
	public static void rotateWall() throws Exception{
		
		throw new UnsupportedOperationException();
		
	}
	/**
	 * Method used to drop a wall
	 * @author Iyatan Atchoro
	 */

	public static void dropWall() {	
		throw new UnsupportedOperationException();
	}
}
