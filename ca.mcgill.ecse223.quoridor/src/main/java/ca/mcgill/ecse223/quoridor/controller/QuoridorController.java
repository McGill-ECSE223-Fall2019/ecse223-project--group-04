package ca.mcgill.ecse223.quoridor.controller;
import java.util.*;

import javax.swing.text.Utilities;
import java.io.*;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import ca.mcgill.ecse223.quoridor.utilities.*;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

public class QuoridorController {

	private static int moveNumber;
	 Quoridor quoridor;


	/**
	 * Method to capture the time at which the clock is stopped
	 * Used as a helper method
	 * @param
	 * @author Ousmane Baricisse
	 * @return time in seconds
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
		Player player = quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove();
		return player.hasWalls();
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
	public static boolean grabWall(Quoridor quoridor) {
		// TODO Auto-generated method stub

		Player playerToMove = quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove();

		List<Wall> list = playerToMove.getWalls();



		boolean wasRemoved = false;
		System.out.println("sizeee: " + list.size());
		if(list.size() != 0) {
//			System.out.println("Do uuuuuu?  " + playerToMove.equals(quoridor.getCurrentGame().getWhitePlayer()));
			Wall wall = list.get(list.size()-1);
			System.out.println("CHECKKKK: " + playerToMove.equals(quoridor.getCurrentGame().getWhitePlayer()));
			if(playerToMove.equals(quoridor.getCurrentGame().getBlackPlayer())) {
				wasRemoved = quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
			} else {
				wasRemoved = quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
			}


			Tile tile = new Tile(1, 1, quoridor.getBoard());
			List<Move> listOfMoves = quoridor.getCurrentGame().getMoves();
			WallMove wallMove;
			if(listOfMoves.size()==0) {
				wallMove = new WallMove(1, 1, playerToMove, tile, quoridor.getCurrentGame(), Direction.Horizontal, wall);
			} else {
				Move move = listOfMoves.get(listOfMoves.size()-1);
				wallMove = new WallMove(move.getMoveNumber(), move.getRoundNumber(), playerToMove, tile, quoridor.getCurrentGame(), Direction.Horizontal, wall);
			}

			quoridor.getCurrentGame().setWallMoveCandidate(wallMove);

		}

		return wasRemoved;



	}

	/**
	 * Method: initializeNewGame(Quoridor quoridor)
	 * @param quoridor - Quoridor object within which to create new game
	 * @return {true} if game correctly initialized, an exception otherwise
	 * @throws IllegalArgumentException - If the supplied quoridor is null or already has a game
	 * @throws RuntimeException - If the amount of users is insufficient
	 */
	public static Boolean initializeNewGame(Quoridor quoridor) throws Exception {
		Boolean quoridorIsValid = !quoridor.equals(null);
		
		if(!quoridorIsValid) {
			throw new IllegalArgumentException("This Quoridor already contains a game, or the Quoridor is null");
		}
		Game newGame = new Game(GameStatus.Initializing, MoveMode.PlayerMove, quoridor);

		// Verify that there are at least 2 users for this game
		if(quoridor.getUsers().size() < 2) {
			throw new RuntimeException("Not enough users to start a game! There must be at least 2 users.");
		}

		// TODO: Talk to Imad about this?
		// Logic behind this is that the white player wants to get to the black players' tile
		// and vice versa
		Player whitePlayer = new Player(new Time(0), quoridor.getUser(0), ControllerUtilities.BLACK_TILE_INDEX, Direction.Horizontal);
		Player blackPlayer = new Player(new Time(0), quoridor.getUser(1), ControllerUtilities.WHITE_TILE_INDEX, Direction.Horizontal);
		whitePlayer.setGameAsWhite(newGame);
		blackPlayer.setGameAsBlack(newGame);

		newGame.setWhitePlayer(whitePlayer);
		newGame.setBlackPlayer(blackPlayer);
		// Set the game to the quoridor object
		quoridor.setCurrentGame(newGame);

		return true;
	}

	/**
	 * Method: startClock(Game game) 
	 * This method, according to the Gherkin
	 * specification files, should: 
	 * 1. Run the game 
	 * 2. Initialize the board
	 * 
	 * @param No parameters - The current player's clock will start counting down
	 * @throws RuntimeException
	 * @author Tristan Bouchard
	 */
	public static Boolean startClock() throws RuntimeException {
		Quoridor currentQuor = QuoridorApplication.getQuoridor();
		if(!currentQuor.hasCurrentGame()) {
			throw new RuntimeException("There is no current game to start! Please create a game first.");
		}
		Game currentGame = currentQuor.getCurrentGame();

		if(!currentGame.hasBlackPlayer() || !currentGame.hasWhitePlayer()) {
			throw new RuntimeException("Game has incorrect amount of players. Please verify the players.");
		}
		Player currentPlayer = getCurrentPlayer();

		// TODO: how do I start the time???
		Timer timer = new Timer("MyTimer");
		timer.schedule(new ThreadTimer(currentPlayer), 0, 1000);
		// Set game status to running
		currentGame.setGameStatus(GameStatus.Running);

		return true;
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
	public static void initializeBoard(Quoridor quoridor) throws Exception {

		Boolean quoridorIsValid = !quoridor.equals(null);
		Boolean gameIsValid = quoridor.hasCurrentGame() && !quoridor.getCurrentGame().equals(null);

		if(!quoridorIsValid) {
			throw new IllegalArgumentException("This Quoridor already contains a game, or the Quoridor is null");
		}
		if(!gameIsValid) {
			throw new RuntimeException("Game is not properly initialized");
		}
		// This method will only create a board if no board has been previously created
		if(!quoridor.hasBoard()) {
			Board newBoard = new Board(quoridor);
			ControllerUtilities.initTilesForNewBoard(newBoard);
			quoridor.setBoard(newBoard);
		}

		// Set white player to be current player
		Game currentGame = quoridor.getCurrentGame();
		Player currentWhitePlayer = currentGame.hasWhitePlayer() ? currentGame.getWhitePlayer() : null;
		Player currentBlackPlayer = currentGame.hasBlackPlayer() ? currentGame.getBlackPlayer() : null;
		if(currentWhitePlayer.equals(null) || currentBlackPlayer.equals(null)) {
			throw new RuntimeException("Players of the current game are invalid");
		}

		setNextPlayer(currentWhitePlayer, currentBlackPlayer);

		// Clear existing positions and moves in the Game
		ControllerUtilities.clearExistingPositions(currentGame);
		ControllerUtilities.clearExistingMoves(currentGame);

		// Set white + black pawn to their initial positions
		setInitialGamePosition(currentGame, quoridor.getBoard(), currentWhitePlayer, currentBlackPlayer);

		startClock();

	}


	/**
	 * Method used to set the initial game position and walls in the new game object
	 * @param currentGame - Current game in which to set the initial position
	 * @param quoridor - Current instance of the board
	 * @param currentWhitePlayer - Current white player to initiate position
	 * @param currentBlackPlayer - Current black player to initiate position
	 */
	private static void setInitialGamePosition(Game currentGame, Board board, Player currentWhitePlayer, Player currentBlackPlayer) {
		// Get initial tiles
		Tile whitePlayerInitialTile = board.getTile(ControllerUtilities.WHITE_TILE_INDEX);
		Tile blackPlayerInitialTile = board.getTile(ControllerUtilities.BLACK_TILE_INDEX);
		// Set initial player positions
		PlayerPosition whitePlayerPosition = new PlayerPosition(currentWhitePlayer, whitePlayerInitialTile);
		PlayerPosition blackPlayerPosition = new PlayerPosition(currentBlackPlayer, blackPlayerInitialTile);
		// Create game position
		GamePosition initialGamePosition = new GamePosition(0, whitePlayerPosition, blackPlayerPosition,
				currentWhitePlayer, currentGame);
		currentGame.addPosition(initialGamePosition);
		currentGame.setCurrentPosition(initialGamePosition);
		// Clear potential existing walls on board, moves and game positions
		ControllerUtilities.emptyWallsOnBoard(initialGamePosition);

		// initialize walls in stock
		ControllerUtilities.initializeWallsInStock(initialGamePosition, currentWhitePlayer, currentBlackPlayer);

	}

	/**
	 * Method used to set the next player to play. Requires currentPlayer and next player 
	 * @param currentPlayer - Player to set nextPlayer in
	 * @param nextPlayer - Player to be set as next player
	 */
	private static void setNextPlayer(Player currentPlayer, Player nextPlayer) {
		// TODO: Is this valid?
		// Set white players' next player to be the black player and set
		// black players next player to null
		currentPlayer.setNextPlayer(nextPlayer);
		nextPlayer.setNextPlayer(null);
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

	public static String testMethod() {
		return "Hello world!";
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
	 * @author Nayem Alam
	 */
	public static void selectExistingUserName(String username, Player currentPlayer, Quoridor quoridor) {
		List<User> users = quoridor.getUsers();
		if(!users.isEmpty()) {
			for (int i = 0; i < users.size(); i++) {
				if (users.get(i).getName().equals(username)) {
					// currentPlayer's User would contain the existing username
					currentPlayer.setUser(users.get(i));
				}
			}
		}
	}

	/**
	 * GUI related Method - selectExistingUserName(String username)
	 *
	 * This method interacts with the GUI, it checks for specific conditions and then runs
	 * the corresponding controller class
	 *
	 * @param username - String username can either exist in the list or can create new username
	 * @author Nayem Alam
	 */
	public static List<User> getUsers(String username) {
		List<User> userList = QuoridorApplication.getQuoridor().getUsers();
		for (User user : userList) {
			if (!user.getName().contains(username)) {
				selectNewUserName(username, getCurrentPlayer(), QuoridorApplication.getQuoridor());
			} else {
				selectExistingUserName(username, getCurrentPlayer(), QuoridorApplication.getQuoridor());
			}
		}
		return userList;
	}

	//	public static List<String> myUsers() {
	//		List<String> userList = new ArrayList<>();
	//		userList.add("Daniel");
	//		userList.add("Hyacinth");
	//		return userList;
	//	}

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
		for (int row = 1; row <= ControllerUtilities.TOTAL_NUMBER_OF_ROWS; row++) {
			for (int col = 1; col <= ControllerUtilities.TOTAL_NUMBER_OF_COLS; col++) {
				// Obtain tile in the list and verify that the indices are correct
				int index = ((ControllerUtilities.TOTAL_NUMBER_OF_COLS) * (row-1) + (col-1));
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
	 * @param row , the row of the position of the pawn 
	 * @param col , the column of the pawn
	 * @returns true/false , true if the position is valid else false
	 * @author Alexander Legouverneur
	 */
	public static boolean initializeValidatePosition(int row, int col) {
		if(row>=1 && row<=9 && col>=1 && col<=9) {
			return true;
		}
		else return false;
	}

	/**
	 * Method that returns if the position is valid by calling InitializeVaidatePostion()
	 * @param row of the pawn position
	 * @param col of th pawn position
	 * @returns ok/error strings
	 * @author Alexander Legouverneur
	 */
	public static String validatePawnPosition(int row, int col) {
		if(initializeValidatePosition(row, col) == true) {
			return "ok";
		}
		else return "error";
	}
	/**
	 * Method that initiates the validation of the position by checking all cases of incorrect position 
	 * for all placed walls.
	 * @param row , is the row of the target tile of the WallMove associated to the wall
	 * @param col , the column of the target tile of the wall move associated to the wall
	 * @param Walldir , the direction of the WallMove associated with the wall
	 * @param id , the id of the wall currently checked for validity
	 * @return true/false true for valid position, false for invalid
	 * @author Alexander Legouverneur
	 */
	public static boolean initiatePosValidation(int row, int col, String Walldir, int id) {
		Quoridor q = QuoridorApplication.getQuoridor();
		if(row>=1 && row<=8 && col>=1 && col<=8) {

			Direction dir1;
			int col1;
			int row1;
			for(int i = 0; i<=19; i++){
				if(id == i) {
					continue; //don t want to compare the wall with itself
				}


				if(i>9) {

					if(q.getCurrentGame().getBlackPlayer().getWall(i-10).hasMove() == false) {
						continue;
					}
					row1 = q.getCurrentGame().getBlackPlayer().getWall(i-10).getMove().getTargetTile().getRow();
					col1 = q.getCurrentGame().getBlackPlayer().getWall(i-10).getMove().getTargetTile().getColumn();
					dir1 = q.getCurrentGame().getBlackPlayer().getWall(i-10).getMove().getWallDirection();
				}
				else {
					if(q.getCurrentGame().getWhitePlayer().getWall(i).hasMove() == false) {
						continue;
					}
					row1 = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getTargetTile().getRow();
					col1 = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getTargetTile().getColumn();
					dir1 = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getWallDirection();
				}


				if(dir1 == Direction.Horizontal) {
					if(Walldir.equals("horizontal")) {
						if(col1 == col && row1 == row) {
							return false;
						}
						if(col1 ==col+1 && row1 == row) {
							return false;
						}
						if(col1 == col-1 && row1 == row) {
							return false;
						}
					}
					else {
						if(col1 == col && row1 == row) {
							return false;
						}
					}
				}
				else {
					if(Walldir.equals("vertical")) {
						if(row1 == row+1 && col1 == col) {
							return false;
						}
						if( row1 == row-1 && col1 == col) {
							return false;
						}
						if(col1==col && row1 == row) {
							return false;
						}
					}
					else {
						if(col1 == col && row1 == row) {
							return false;
						}
					}
				}
			}
			return true;
		}
		else return false;
	}

	/**
	 * Method that returns if the wall can be placed at the indicated position by calling InitiatePosValidation()
	 * @param row , the row of the tile of the WallMove associated with to the wall
	 * @param dir , direction of the wall
	 * @param col , column of the tile of the WallMove associated to the wall
	 * @return ok/error , strings, ok if wall can be placed, else error.
	 * @author Alexander Legouverneur
	 */
	public static String validateWall(int row, int col, String dir) {
		if(initiatePosValidation(row, col, dir, 0) == true) {
			return "ok";
		}
		else return "error";
	}

	/**
	 * Method that checks if wall Position is valid
	 * @param row , row of the tile of the WallMove associated with the wall
	 * @param col , column of the tile of the WallMove associated with the wall
	 * @param dir , direction of the wall
	 * @param wall , the wall which the position is checked
	 * @return true/false , true if position is valid, false if it is not
	 * @author Alexander Legouverneur
	 */
	public static boolean checkWallValid(int row, int col, String dir, Wall wall) {
		if(initiatePosValidation(row, col, dir, wall.getId()) == true) {
			return true;
		}
		else return false;
	}

	//end of validate position
	/**
	 * Checks if the wall is on the side edge of the board
	 * @param aWall
	 * @return true/false -true if on side edge
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static boolean checkWallSideEdge(Wall aWall, String side) {

		int row = aWall.getMove().getTargetTile().getRow();
		int col = aWall.getMove().getTargetTile().getColumn();
		Direction dir = aWall.getMove().getWallDirection();
		//System.out.println("Coordinates: "+row+","+col);

		if(col == 1 && side.equals("left") && dir == Direction.Vertical) {
			return true;
		}
		if(col == 8 && side.equals("right") && dir == Direction.Horizontal) {
			return true;
		}
		if(col == 1 && side.equals("left") && dir == Direction.Horizontal) {
			return true;
		}
		if(col == 8 && side.equals("right") && dir == Direction.Vertical) {
			return true;
		}
		if(row == 8 && side.equals("down") && dir == Direction.Vertical) {
			return true;
		}
		if(row == 1 && side.equals("up") && dir == Direction.Vertical) {
			return true;
		}
		if (row == 1 && side.equals("up") && dir == Direction.Horizontal) {
			return true;
		}
		if(row == 8 && side.equals("down") && dir == Direction.Horizontal) {
			return true;
		}
		return false;
	}

	/**
	 * Methods that checks if the move on the specified side is possible, with if statements to see if the move is 
	 * legal, if yes proceed to the move otherwise, call the method IlllegalWallMove()
	 * @param aWall
	 * @param side
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static void verifyMoveWallOnSide(Wall aWall, String side, int index){
		Quoridor q = QuoridorApplication.getQuoridor();

		try {
			aWall.getOwner().getWall(index).getMove().getTargetTile().getRow();
			aWall.getOwner().getWall(index).getMove().getTargetTile().getColumn();
			aWall.getOwner().getWall(index).getMove().getWallDirection();
		} catch(Exception e) {
			System.out.println("THE WALL IS NOT PLACED: ");
		}

		int row =aWall.getOwner().getWall(index).getMove().getTargetTile().getRow();
		int col = aWall.getOwner().getWall(index).getMove().getTargetTile().getColumn();

		if(checkWallSideEdge(aWall,side) == true) {
			illegalWallMove();
		}

		if(checkWallSideEdge(aWall,side) == false) {



			if(side.equals("left")) {
				Tile aTile = new Tile(row, col-1,q.getBoard());
				aWall.getMove().setTargetTile(aTile);
			}
			if(side.equals("right")) {
				Tile aTile = new Tile(row, col+1,q.getBoard());
				aWall.getMove().setTargetTile(aTile);

			}
			if(side.equals("up")) {
				Tile aTile = new Tile(row-1, col,q.getBoard());
				aWall.getMove().setTargetTile(aTile);
			}
			if(side.equals("down")) {
				Tile aTile = new Tile(row+1, col,q.getBoard());
				aWall.getMove().setTargetTile(aTile);
			}
		}

	}

	/**
	 * This method is called by VerifyMoveWallOnSide if the move is illegal. Returns a string "illegal", and makes
	 * sure the coordinates of the wall remain the same as before
	 * @param aWall
	 * @return String
	 * @throws UnsupportedOperationException
	 *@author Alexander Legouverneur
	 */
	public static String illegalWallMove() {
		return "Illegal";
	}

	/**
	 * This method generalizes the wall move. It will go through all the possible errors by calling other methods.
	 * To be sure that the wall move is possible, and if yes, execute the wall move. 
	 * 
	 * @param row  		row of the target tile for the move
	 * @param col  		column of the target tile for the move
	 * @param dir  		direction of the move
	 * @param aWall 	wall to be moved 
	 * @param player	player to whom the wall belongs
	 */
	public static boolean wallMove(int row, int col, String dir, Wall aWall,Player player) {

		Quoridor q = QuoridorApplication.getQuoridor();
		boolean pos;
		Tile aTile = new Tile(row, col, q.getBoard());

		if(dir.equals("vertical")) {
			pos = initiatePosValidation(row, col, "vertical", aWall.getId());

			if(aWall.hasMove() == false && pos == true) {
				moveNumber++;
				new WallMove(moveNumber,1,player,aTile,q.getCurrentGame(), Direction.Vertical, aWall);
				return true;
			}
			else {
				return false;
			}
		}
		else {
			pos = initiatePosValidation(row, col, "horizontal", aWall.getId());

			if(aWall.hasMove() == false && pos == true) {
				moveNumber++;
				new WallMove(moveNumber,1,player,aTile,q.getCurrentGame(), Direction.Horizontal, aWall);
				return true;
			}
			else {
				return false;
			}
		}

	}
	/**
	 * Method - saveGamePosition(String filename, Game game)
	 * 
	 * Controller method used to save the game as a text file 
	 * This file can later be
	 * loaded to keep playing this instance of the game
	 * first checks if the file exists or not
	 * 
	 * @param filename - String name of file
	 * @return String - contents of the file that was just written by calling overwriteGamePosition
	 * @author Nicolas Buisson
	 * 
	 */
	public static boolean saveGamePosition(String filename) throws IllegalArgumentException, IOException {
		File file = new File(filename);

		boolean fileExists = file.createNewFile();
		//false if it already exists
		//true if it doesn't, gets created

//		if(!fileExists) {
//			throw new IllegalArgumentException("File already exists!");
//		}
		if (fileExists = true) {
			overwriteGamePosition(filename);
		}
		return fileExists;

	}

	/**
	 * Method - overwriteGameFile(String filename, Game game)
	 * 
	 * Controller method used to save the game as a text file 
	 * This file can later be
	 * loaded to keep playing this instance of the game
	 * 
	 * @param filename - String name of file
	 * @return String - contents of the file that was just written
	 * @author Nicolas Buisson
	 * 
	 */
	public static String overwriteGamePosition(String filename) throws IOException {

		String gameData = "";
		String whiteData = "W: ";
		String blackData = "B: ";
		String seperator = ", ";
		String[] columnArray = new String[] { "no 0 column", "a", "b", "c", "d", "e", "f", "g", "h", "i" };
		int whitePawnColumn = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition()
				.getTile().getColumn();
		int whitePawnRow = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition()
				.getTile().getRow();
		int blackPawnColumn = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition()
				.getTile().getColumn();
		int blackPawnRow = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition()
				.getTile().getRow();

		whiteData = whiteData + columnArray[whitePawnColumn] + String.valueOf(whitePawnRow);
		blackData = blackData + columnArray[blackPawnColumn] + String.valueOf(blackPawnRow);

		List<Wall> whiteWallsOnBoard = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getWhiteWallsOnBoard();
		List<Wall> blackWallsOnBoard = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getBlackWallsOnBoard();

		for (int i = 0; i < whiteWallsOnBoard.size(); i++) {
			int wallColumn = whiteWallsOnBoard.get(i).getMove().getTargetTile().getColumn();
			int wallRow = whiteWallsOnBoard.get(i).getMove().getTargetTile().getRow();
			String wallOrientation = whiteWallsOnBoard.get(i).getMove().getWallDirection().name();
			if (wallOrientation.equals("Vertical")) {
				wallOrientation = "v";
			} else {
				wallOrientation = "h";
			}
			whiteData = whiteData + seperator + columnArray[wallColumn] + String.valueOf(wallRow) + wallOrientation;
		}

		for (int i = 0; i < blackWallsOnBoard.size(); i++) {
			int wallColumn = blackWallsOnBoard.get(i).getMove().getTargetTile().getColumn();
			int wallRow = blackWallsOnBoard.get(i).getMove().getTargetTile().getRow();
			String wallOrientation = blackWallsOnBoard.get(i).getMove().getWallDirection().name();
			if (wallOrientation.equals("Vertical")) {
				wallOrientation = "v";
			} else {
				wallOrientation = "h";
			}
			blackData = blackData + seperator + columnArray[wallColumn] + String.valueOf(wallRow) + wallOrientation;
		}

		// need to figure out whose turn it is
		// to find order of data saved
		boolean playerToPlay = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().hasNextPlayer();
		// true for white
		// false for black
		if (playerToPlay = true) {
			gameData = whiteData + "\n" + blackData;
		} else {
			gameData = blackData + "\n" + whiteData;
		}

		File file = new File(filename);
		BufferedWriter bW = new BufferedWriter(new FileWriter(file));

		bW.write(gameData);

		if (bW != null) {
			bW.close();
		}

		return gameData;

	}

	/**
	 * Method - overWriteFile()
	 * 
	 * Controller method used to overwrite a saved game file with the current game
	 * 
	 * @param filename - String name of file
	 * @return String - contains the content of the overwritten file
	 * @author Nicolas Buisson
	 * 
	 */

	public static String overWriteFile(String filename) throws IOException {
		// overwriting taken care of by saveGamePosition method
		return overwriteGamePosition(filename);
	}

	/**
	 * Method - cancelOverWriteFile()
	 * 
	 * Controller method used to cancel overwriting a file
	 * 
	 * @return void
	 * @author Nicolas Buisson
	 * 
	 */
	public static void cancelOverWriteFile() {

		// if user cancels overwriting the file
		// then nothing happens, file and model are unchanged
		// only change is in UI
		// might not have to be a controller method
	}

	/**
	 * Method - loadSavedPosition()
	 * 
	 * Controller method used to load a file containing the game state of a previous
	 * game that the user wishes to continue playing
	 * 
	 * @param filename - name of file
	 * @return GamePosition - the game position is returned
	 * @author Nicolas Buisson
	 * 
	 */
	public static boolean loadSavedPosition(String filename) throws IOException {

		boolean validPosition = true;
		int j = 0; //iterator value used to save how many white walls were placed on board
		int k = 0; //iterator value used to save how many black walls were placed on board
		//will later be used to determine how many walls should be placed in the stock of each player

		//NEED TO CREATE A GAME
		Quoridor quoridor =  QuoridorApplication.getQuoridor();
		int thinkingTime = 180;
		User user1 = quoridor.getUser(0);
		User user2 = quoridor.getUser(1);
		Player player1 = new Player(new Time(thinkingTime), user1, 9, Direction.Horizontal);
		Player player2 = new Player(new Time(thinkingTime), user2, 1, Direction.Horizontal);

		Player[] players = { player1, player2 };

		Tile player1StartPos = quoridor.getBoard().getTile(36);
		Tile player2StartPos = quoridor.getBoard().getTile(44);

		try {
			QuoridorController.initializeNewGame(quoridor);
			QuoridorController.initializeBoard(quoridor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		game.setWhitePlayer(players[0]);
		game.setBlackPlayer(players[1]);

		PlayerPosition player1Position = new PlayerPosition(quoridor.getCurrentGame().getWhitePlayer(), player1StartPos);
		PlayerPosition player2Position = new PlayerPosition(quoridor.getCurrentGame().getBlackPlayer(), player2StartPos);

		GamePosition gamePosition = new GamePosition(1, player1Position, player2Position, players[0], game);
		game.setCurrentPosition(gamePosition);
		//take initial position and modify it to match the file specifications
		GamePosition gamePositionToLoad = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();

		//PERHAPS USE THE STRINGBUILDER FROM SAVEPOSITIONSTEPDEF
		File file = new File(filename);
		String content = "";
		FileReader reader = new FileReader(file);
		BufferedReader BF = new BufferedReader(reader);
		String line = BF.readLine();
		while (line != null) {
			content = content + line + "\n";
			line = BF.readLine();
		}

		if (BF != null) {
			BF.close();
		}
		// data of game saved in string content
		// need to decompose it to access all the individual positions
		// of the pawns and the walls
		// separates white data and black data
		String whiteData = "";
		String blackData = "";
		String positions[] = content.split("\n");
		if (positions[0].startsWith("W")) {
			whiteData = positions[0];
			blackData = positions[1];
			gamePositionToLoad.setPlayerToMove(QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer());
			getWhitePlayer().setNextPlayer(getBlackPlayer());
			// white player's turn to play
		} else {
			blackData = positions[0];
			whiteData = positions[1];
			gamePositionToLoad.setPlayerToMove(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer());
			getBlackPlayer().setNextPlayer(getWhitePlayer());
			// black player's turn to play
		}
		String whitePositions[] = whiteData.split(" ");
		String blackPositions[] = blackData.split(" ");

		// use unicode values
		// 'a' = 97, will correspond to the column numbers
		int whitePawnColumn = ((int) whitePositions[1].charAt(0)) - 96;
		int whitePawnRow = whitePositions[1].charAt(1) - 48;
		boolean whitePawnValid = initializeValidatePosition(whitePawnRow, whitePawnColumn);
		if (whitePawnValid = true) {
			Tile whitePawnTile = new Tile(whitePawnRow, whitePawnColumn, QuoridorApplication.getQuoridor().getBoard());
			PlayerPosition whitePosition = new PlayerPosition(
					QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer(), whitePawnTile);
			gamePositionToLoad.setWhitePosition(whitePosition);
		}
		int blackPawnColumn = ((int) blackPositions[1].charAt(0)) - 96;
		int blackPawnRow = blackPositions[1].charAt(1) - 48;

		boolean blackPawnValid = initializeValidatePosition(blackPawnRow, blackPawnColumn);
		if (blackPawnValid = true) {
			Tile blackPawnTile = new Tile(blackPawnRow, blackPawnColumn, QuoridorApplication.getQuoridor().getBoard());
			PlayerPosition blackPosition = new PlayerPosition(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer(), blackPawnTile);
			gamePositionToLoad.setBlackPosition(blackPosition);
		}

		boolean whiteWallsValid = true;
		boolean blackWallsValid = true;

		for (int i = 2; i < whitePositions.length; i++) {

			int whiteWallColumn = whitePositions[i].charAt(0) - 96;
			int whiteWallRow = whitePositions[i].charAt(1) - 48; //unicode value of char representing row, not actual row number
			// '0' = 48
			char whiteWallO = whitePositions[i].charAt(2);
			Direction whiteWallDirection;
			String wallOrientation = "";
			if (whiteWallO == 'v') {
				wallOrientation = "vertical";
				whiteWallDirection = Direction.Vertical;
			} else {
				wallOrientation = "horizontal";
				whiteWallDirection = Direction.Horizontal;
			}

			whiteWallsValid = whiteWallsValid & initiatePosValidation(whiteWallRow, whiteWallColumn, wallOrientation, i - 2);
			if (whiteWallsValid = true) {
//				Wall whiteWall = new Wall(i - 2, getWhitePlayer());
				Wall whiteWall = getWhitePlayer().getWall(i-2);
				gamePositionToLoad.addWhiteWallsOnBoard(whiteWall);
				Tile whiteWallTile = new Tile(whiteWallColumn, whiteWallRow, QuoridorApplication.getQuoridor().getBoard());
				WallMove whiteWallMove = new WallMove(0, 0, getWhitePlayer(), whiteWallTile, game, whiteWallDirection, whiteWall);
				gamePositionToLoad.getWhiteWallsOnBoard(j).setMove(whiteWallMove);
				j++;
			}
		}
		//NEED TO ADD THE WALLS ON STOCK, means need to create all of them
//		while(j < 10) {
//			Wall whiteWall = new Wall( j, getWhitePlayer());
//			gamePositionToLoad.addWhiteWallsInStock(whiteWall);
//			j++;
//		}
		
		for (int i = 2; i < blackPositions.length; i++) {

			int blackWallColumn = blackPositions[i].charAt(0) - 96;
			int blackWallRow = blackPositions[i].charAt(1) - 48; //unicode value of char representing row, not actual row number
			char blackWallO = blackPositions[i].charAt(2);
			String wallOrientation = "";
			Direction blackWallDirection;
			if (blackWallO == 'v') {
				wallOrientation = "vertical";
				blackWallDirection = Direction.Vertical;
			} else {
				wallOrientation = "horizontal";
				blackWallDirection = Direction.Horizontal;
			}

			blackWallsValid = blackWallsValid & initiatePosValidation(blackWallRow, blackWallColumn, wallOrientation, 10 + i - 2 );
			if (blackWallsValid = true) {
				Tile blackWallTile = new Tile(blackWallColumn, blackWallRow, QuoridorApplication.getQuoridor().getBoard());
				//Wall blackWall = new Wall(10 + i - 2, getBlackPlayer());
				Wall blackWall = getBlackPlayer().getWall(i-2);
				gamePositionToLoad.addBlackWallsOnBoard(blackWall);
				WallMove blackWallMove = new WallMove(0, 0, getBlackPlayer(), blackWallTile, game, blackWallDirection, blackWall);
				gamePositionToLoad.getBlackWallsOnBoard(k).setMove(blackWallMove);
				k++;
			}
		}
//		while(k < 10) {
//			Wall blackWall = new Wall( k + 10, getBlackPlayer());
//			gamePositionToLoad.addBlackWallsInStock(blackWall);
//			k++;
//		}
		validPosition = whitePawnValid & blackPawnValid & whiteWallsValid & blackWallsValid;
		if(validPosition  = true) {
			QuoridorApplication.getQuoridor().getCurrentGame().setCurrentPosition(gamePositionToLoad);
		}


		return validPosition;
	}

	/**
	 * Method used to rotate a wall
	 * @author Iyatan Atchoro
	 */
	public static void rotateWall() throws Exception{
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();

		if(curGame.getWallMoveCandidate()==null) {
			throw new Exception("No wall Selected");
		}
		Direction wallDir = curGame.getWallMoveCandidate().getWallDirection();
		if(wallDir==Direction.Horizontal) {
			curGame.getWallMoveCandidate().setWallDirection(Direction.Vertical);
		}else {
			curGame.getWallMoveCandidate().setWallDirection(Direction.Horizontal);
		}

	}
	/**
	 * Method used to drop a wall
	 * @author Iyatan Atchoro
	 */
	public static void dropWall() throws Exception {
		// check wall in hand

		  Quoridor q = QuoridorApplication.getQuoridor();
		   WallMove wallMoveCandidate = q.getCurrentGame().getWallMoveCandidate();
		   int x = wallMoveCandidate.getTargetTile().getRow();
		   int y = wallMoveCandidate.getTargetTile().getColumn();
		   String dir = wallMoveCandidate.getWallDirection().toString();
		   int id = wallMoveCandidate.getWallPlaced().getId();

		   if(initiatePosValidation(x,y,dir,id)){
			   q.getCurrentGame().addMove(wallMoveCandidate);
		       List<Move> curList = new ArrayList<>(q.getCurrentGame().getMoves());
		       Move lastMoveInTheList = curList.get(curList.size()-1);
		       lastMoveInTheList.setNextMove(wallMoveCandidate);
		       wallMoveCandidate.setPrevMove(lastMoveInTheList);
		   }else {
			   throw new Exception("Not a valid position");
		   }

		   q.getCurrentGame().setGameStatus(GameStatus.Replay);
	}

	/**
	 * Method used to get currentPlayer
	 * @return player the current player
	 */
	public static Player getCurrentPlayer() {
		Player playerWhite = QuoridorController.getWhitePlayer();
		if(playerWhite.hasNextPlayer()){
			return playerWhite;
		}
		return QuoridorController.getBlackPlayer();
	}

	/**
	 * This method uses getCurrentPlayer method to transform the current player into an int
	 * so that it can be used into the view
	 * @return 0/1 where 0 is white player and 1 is dark player
	 * @author Alexander Legouverneur
	 */
	public static int currentPlayerInt() {

		Quoridor q = QuoridorApplication.getQuoridor();
		if(getCurrentPlayer().equals(q.getCurrentGame().getWhitePlayer())) {

			return 0;

		}
		else return 1;
	}

	/**
	 * This methods is a getter for the next wall to be placed
	 *  @author Alexander Legouverneur
	 * @return the next wall to be placed
	 */
	public Wall getNextWall() {
		Quoridor q = QuoridorApplication.getQuoridor();
		if(currentPlayerInt()== 1) {

			int size =  q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard().size();
			int index = size; //index of next wall is index-1+1+9
			if(index>9) {
				return null;
			}
			else return q.getCurrentGame().getBlackPlayer().getWall(index);
		}
		else {

			int size =  q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard().size();
			int index = size+10; //index of next wall is index-1+1+9
			if(index>19) {
				return null;
			}
			else return q.getCurrentGame().getBlackPlayer().getWall(index);
		}

	}

	/**
	 * Method used to notify invalid Wall move
	 * @author Iyatan Atchoro
	 */

	public static String invalidWallMove() {
		return "Invalid";
	}


	/**
	 * @author ousmanebaricisse
	 *
	 */
	public static boolean isWhitePlayer(Quoridor quoridor, Player player) {
		return player.equals(quoridor.getCurrentGame().getWhitePlayer());
	}

	public static Wall getWall(int id) {
		Quoridor q = QuoridorApplication.getQuoridor();
		if(id>9) {

			return q.getCurrentGame().getBlackPlayer().getWall(id-10);


		}
		else {
			return q.getCurrentGame().getBlackPlayer().getWall(id);
		}
	}
}
