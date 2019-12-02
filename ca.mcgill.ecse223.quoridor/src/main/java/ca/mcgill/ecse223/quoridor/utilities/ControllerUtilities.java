package ca.mcgill.ecse223.quoridor.utilities;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;

import java.util.List;

/**
 * Class used to group together common utilities and constants used by the application controller methods.
 * @author Tristan Bouchard
 *
 */
public class ControllerUtilities {
	
	
	// ***************************************
	// Constants
	public static final int TOTAL_NUMBER_OF_TILES = 81;
	public static final int TOTAL_NUMBER_OF_ROWS = 9;
	public static final int TOTAL_NUMBER_OF_COLS = 9;
	public static final int WHITE_TILE_INDEX = 4;
	public static final int BLACK_TILE_INDEX = 76;
	public static final int WHITE_INITIAL_ROW = 9;
	public static final int WHITE_INITIAL_COL = 5;
	public static final int BLACK_INITIAL_ROW = 1;
	public static final int BLACK_INITIAL_COL = 5;
	
	public static final int TOTAL_WALL_STOCK_AT_START = 10;
	public static final int TOTAL_NUMBER_OF_WALLS_PER_PLAYER = 10;
	public static final String CURRENT_BLACK_TIMER_THREAD_NAME = "CurrentBlackPlayerTimerThread";
	public static final String CURRENT_WHITE_TIMER_THREAD_NAME = "CurrentWhitePlayerTimerThread";
	
	/**
	 * Enumeration describing the potential diagonal direction a player
	 * may move in.
	 * @author Tristan Bouchard
	 *
	 */
	public enum DiagonalDirections{
		upleft,
		upright,
		downleft,
		downright
	}
	
	/**
	 * Enumeration describing the possible standard moves of a player
	 * @author Tristan Bouchard
	 *
	 */
	public enum MoveDirections{
		up,
		down,
		left,
		right
	}
	
	/**
	 * Enumeration describing which players can reach their victory zone
	 * @author Tristan Bouchard
	 */
	public enum PathAvailableToPlayers{
		none,
		white,
		black,
		both
	}
	
	// ***************************************
	
	// ***************************************
	// Helper methods
	// ***************************************
	
	/**
	 * Method to verify the validity of a selectedUsername.
	 * 
	 * @param userName - Name to verify
	 * @author Tristan Bouchard
	 */
	public static Boolean isUserNameValid(String userName) {
		userName = userName.trim();
		return (userName != null && !userName.isEmpty() && !userName.equals(""));
	}
	/**
	 * Method used to clear all existing moves in a game object
	 * @param currentGame
	 * @author Tristan Bouchard
	 */
	public static void clearExistingMoves(Game currentGame) {
		Move tempMove;
		while(currentGame.getMoves().size() > 0) {
			tempMove = currentGame.getMove(0);
			tempMove.delete();
			currentGame.removeMove(tempMove);
		}
	}

	/**
	 * Method used to clear all existing positions in a game object
	 * @param currentGame
	 * @author Tristan Bouchard
	 */
	public static void clearExistingPositions(Game currentGame) {
		while (currentGame.getPositions().size() > 0) {
			GamePosition tempPos = currentGame.getPosition(0);
			tempPos.delete();
			currentGame.removePosition(tempPos);
		}
	}

	/**
	 * Method used to initialize all wall in stock for both players
	 * @param initialGamePosition
	 * @param currentWhitePlayer
	 * @param currentBlackPlayer
	 */
	public static void initializeWallsInStock(GamePosition initialGamePosition, Player currentWhitePlayer, Player currentBlackPlayer) {
		clearPlayerWalls(currentWhitePlayer, currentBlackPlayer);
		for(int i = 0; i < ControllerUtilities.TOTAL_NUMBER_OF_WALLS_PER_PLAYER; i++) {
			Wall newWhiteWall = new Wall(i, currentWhitePlayer);
			Wall newBlackWall = new Wall(i + ControllerUtilities.TOTAL_NUMBER_OF_WALLS_PER_PLAYER, currentBlackPlayer);
			initialGamePosition.addWhiteWallsInStock(newWhiteWall);
			initialGamePosition.addBlackWallsInStock(newBlackWall);
			currentWhitePlayer.addWall(newWhiteWall);
			currentBlackPlayer.addWall(newBlackWall);
		}
	}
	
	/**
	 * Method used to clear the walls of each player in stock 
	 * @param currentWhitePlayer
	 * @param currentBlackPlayer
	 */
	private static void clearPlayerWalls(Player currentWhitePlayer, Player currentBlackPlayer) {
		if(currentWhitePlayer.equals(null) || currentBlackPlayer.equals(null)) {
			throw new IllegalArgumentException("Players cannot be null!");
		}
		for(int i = 0; i < ControllerUtilities.TOTAL_NUMBER_OF_WALLS_PER_PLAYER * 2; i ++) {
			if(Wall.hasWithId(i)) {
				Wall tempWall = Wall.getWithId(i);
				tempWall.delete();	
			}
		}
		Wall currWall;
		while(currentWhitePlayer.hasWalls()) {
			currWall = currentWhitePlayer.getWall(0);
			currWall.delete();
			currentWhitePlayer.removeWall(currWall);
		}
		while(currentBlackPlayer.hasWalls()) {
			currWall = currentBlackPlayer.getWall(0);
			currWall.delete();
			currentBlackPlayer.removeWall(currWall);
		}
	}
	/**
	 * Method used to remove all player's walls on the board, when initializing the game
	 * @param gamePosition
	 * @author Tristan Bouchard
	 */
	public static void emptyWallsOnBoard(GamePosition gamePosition) {
		Wall tempWall;
		while(gamePosition.getBlackWallsOnBoard().size() > 0) {
			tempWall = gamePosition.getBlackWallsOnBoard(0);
			tempWall.delete();
			gamePosition.removeBlackWallsOnBoard(tempWall);
		}
		while(gamePosition.getWhiteWallsOnBoard().size() > 0) {
			tempWall = gamePosition.getWhiteWallsOnBoard(0);
			tempWall.delete();
			gamePosition.removeWhiteWallsOnBoard(tempWall);
		}
	}

	public static void initTilesForNewBoard(Board newBoard) {
		// Creating tiles by rows, i.e., the column index changes with every tile
		// creation
		for (int i = 1; i <= 9; i++) { // rows
			for (int j = 1; j <= 9; j++) { // columns
				newBoard.addTile(i, j);
			}
		}

	}
	/**
	 * Method used to check if username already exists within the list of users
	 * @param username - String value for checking the given username
	 * @author Nayem Alam
	 */
	public static boolean isDuplicate(String username) {
		List<User> userList = QuoridorApplication.getQuoridor().getUsers();
		for (User user : userList) {
			if (user.getName().equals(username)) {
				return true;
			}
		}
		return false;
	}
}
