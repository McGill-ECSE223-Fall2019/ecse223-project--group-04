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

	private static Quoridor quoridor;

	private static List<Tile> availableTiles = new ArrayList<Tile>();
	
	private static Timer currentBlackPlayerTimer = null;
	private static Timer currentWhitePlayerTimer = null;

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
	 * @author Ousmane Baricisse
	 * @return 
	 * 
	 */
	public static void switchCurrentPlayer(){
		stopCurrentPlayerClock();
		stopNonCurrentPlayerClock();
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = getCurrentPlayer();
		if(curPlayer.equals(game.getWhitePlayer())){
			game.getCurrentPosition().setPlayerToMove(game.getBlackPlayer());
			game.getBlackPlayer().setNextPlayer(game.getWhitePlayer());
			setCurrentPlayer(getBlackPlayer());
		} else {
			game.getCurrentPosition().setPlayerToMove(game.getWhitePlayer());
			game.getWhitePlayer().setNextPlayer(game.getBlackPlayer());
			setCurrentPlayer(getWhitePlayer());
		}
		startClock();
		
	}

	/**
	 * @author Ousmane Baricisse
	 * @return 
	 * 
	 */
	public static String getCurPlayerToString(){
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = game.getCurrentPosition().getPlayerToMove();
		if(curPlayer.equals(game.getWhitePlayer())){
			return "white";
		} else {
			return "black";
		}
	}

	/**
	 * This method, according to the Gherkin definition, should grab wall from the
	 * stock and add it to the board Need to know the player and the board as
	 * parameters
	 * 
	 * @param board, player
	 * @author Ousmane Baricisse
	 * @return
	 */
	public static boolean grabWall(Quoridor quoridor) {
		// TODO Auto-generated method stub

		Player playerToMove = quoridor.getCurrentGame().getCurrentPosition().getPlayerToMove();

		Game game = quoridor.getCurrentGame();

		List<Wall> list = new ArrayList<>();
		if(playerToMove.equals(game.getWhitePlayer())){
			list = game.getCurrentPosition().getWhiteWallsInStock();
		} else if(playerToMove.equals(game.getBlackPlayer())){
			list = game.getCurrentPosition().getBlackWallsInStock();
		}

		boolean wasRemoved = false;
		System.out.println("sizeee: " + list.size());
		if (list.size() != 0) {
			// System.out.println("Do uuuuuu? " +
			// playerToMove.equals(quoridor.getCurrentGame().getWhitePlayer()));
			Wall wall = list.get(list.size() - 1);
			System.out.println("CHECKKKK: " + playerToMove.equals(quoridor.getCurrentGame().getWhitePlayer()));
			if (playerToMove.equals(quoridor.getCurrentGame().getBlackPlayer())) {
				wasRemoved = quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
			} else {
				wasRemoved = quoridor.getCurrentGame().getCurrentPosition().removeWhiteWallsInStock(wall);
			}

			Tile tile = new Tile(1, 1, quoridor.getBoard());
			List<Move> listOfMoves = quoridor.getCurrentGame().getMoves();
			WallMove wallMove;
			if (listOfMoves.size() == 0) {
				wallMove = new WallMove(1, 1, playerToMove, tile, quoridor.getCurrentGame(), Direction.Horizontal,
						wall);
			} else {
				Move move = listOfMoves.get(listOfMoves.size() - 1);
				wallMove = new WallMove(move.getMoveNumber(), move.getRoundNumber(), playerToMove, tile,
						quoridor.getCurrentGame(), Direction.Horizontal, wall);
			}

			quoridor.getCurrentGame().setWallMoveCandidate(wallMove);

		}

		return wasRemoved;

	}

	/**
	 * Method: initializeNewGame(Quoridor quoridor)
	 * 
	 * @param quoridor - Quoridor object within which to create new game
	 * @return {true} if game correctly initialized, an exception otherwise
	 * @throws IllegalArgumentException - If the supplied quoridor is null or
	 *                                  already has a game
	 * @throws RuntimeException         - If the amount of users is insufficient
	 */
	public static Boolean initializeNewGame(Quoridor quoridor, Player whitePlayer, Player blackPlayer)
			throws Exception {
		Boolean quoridorIsValid = !quoridor.equals(null);

		if (!quoridorIsValid) {
			throw new IllegalArgumentException("This Quoridor already contains a game, or the Quoridor is null");
		}
		Game newGame = new Game(GameStatus.Initializing, MoveMode.PlayerMove, quoridor);

		// Verify that there are at least 2 users for this game
		if (quoridor.getUsers().size() < 2) {
			throw new RuntimeException("Not enough users to start a game! There must be at least 2 users.");
		}

		// TODO: Talk to Imad about this?
		// Logic behind this is that the white player wants to get to the black players'
		// tile
		// and vice versa
		whitePlayer.setGameAsWhite(newGame);
		blackPlayer.setGameAsBlack(newGame);

		newGame.setWhitePlayer(whitePlayer);
		newGame.setBlackPlayer(blackPlayer);
		// Set the game to the quoridor object
		quoridor.setCurrentGame(newGame);

		initializeBoard(quoridor);

		return true;
	}

	/**
	 * Method: startClock(Game game) This method, according to the Gherkin
	 * specification files, should: 1. Run the game 2. Initialize the board
	 * 
	 * @param No parameters - The current player's clock will start counting down
	 * @throws RuntimeException
	 * @author Tristan Bouchard
	 */
	public static Boolean startClock() throws RuntimeException {
		Quoridor currentQuor = QuoridorApplication.getQuoridor();
		if (!currentQuor.hasCurrentGame()) {
			throw new RuntimeException("There is no current game to start! Please create a game first.");
		}
		Game currentGame = currentQuor.getCurrentGame();

		if (!currentGame.hasBlackPlayer() || !currentGame.hasWhitePlayer()) {
			throw new RuntimeException("Game has incorrect amount of players. Please verify the players.");
		}
		Player currentPlayer = getCurrentPlayer();
		
		if(currentPlayer.equals(getBlackPlayer())) {
			currentBlackPlayerTimer = new Timer(ControllerUtilities.CURRENT_BLACK_TIMER_THREAD_NAME);
			currentBlackPlayerTimer.schedule(new ThreadTimer(currentPlayer), 0, 1000);
		} else {
			currentBlackPlayerTimer = new Timer(ControllerUtilities.CURRENT_WHITE_TIMER_THREAD_NAME);
			currentBlackPlayerTimer.schedule(new ThreadTimer(currentPlayer), 0, 1000);
		}
		// Set game status to running
		currentGame.setGameStatus(GameStatus.Running);

		return true;
	}
	
	public static void stopCurrentPlayerClock() {
		Player currentPlayer = getCurrentPlayer();
		
		if(currentPlayer.equals(getBlackPlayer()) && !(currentBlackPlayerTimer == null)) {
			currentBlackPlayerTimer.cancel();
			currentBlackPlayerTimer = null;
		} else if(!(currentWhitePlayerTimer == null)){
			currentWhitePlayerTimer.cancel();
			currentWhitePlayerTimer = null;
		} else {
		}
		
	}
	public static void stopNonCurrentPlayerClock() {
		Player currentPlayer = getCurrentPlayer();
		
		if(currentPlayer.equals(getBlackPlayer()) && !(currentWhitePlayerTimer == null)) {
			currentWhitePlayerTimer.cancel();
			currentWhitePlayerTimer = null;
		} else if(!(currentBlackPlayerTimer == null)){
			currentBlackPlayerTimer.cancel();
			currentBlackPlayerTimer = null;
		} else {
		}
	}

	/**
	 * Method - initializeBoard(Quoridor quoridor)
	 * 
	 * This method, based on the Gherkin definition, should initialize the game
	 * board for the specified quoridor. It should: 1. Set the current player to
	 * white player 2. Set both pawns to their initial position 3. Set all of the
	 * players walls to their stock 4. Start the white player's clock
	 * 
	 * @throws Exception
	 * @author Tristan Bouchard
	 */
	public static void initializeBoard(Quoridor quoridor) throws Exception {

		Boolean quoridorIsValid = !quoridor.equals(null);
		Boolean gameIsValid = quoridor.hasCurrentGame() && !quoridor.getCurrentGame().equals(null);

		if (!quoridorIsValid) {
			throw new IllegalArgumentException("This Quoridor already contains a game, or the Quoridor is null");
		}
		if (!gameIsValid) {
			throw new RuntimeException("Game is not properly initialized");
		}
		// This method will only create a board if no board has been previously created
		if (!quoridor.hasBoard()) {
			Board newBoard = new Board(quoridor);
			ControllerUtilities.initTilesForNewBoard(newBoard);
			quoridor.setBoard(newBoard);
		}

		// Set white player to be current player
		Game currentGame = quoridor.getCurrentGame();
		Player currentWhitePlayer = currentGame.hasWhitePlayer() ? currentGame.getWhitePlayer() : null;
		Player currentBlackPlayer = currentGame.hasBlackPlayer() ? currentGame.getBlackPlayer() : null;
		if (currentWhitePlayer.equals(null) || currentBlackPlayer.equals(null)) {
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
	 * 
	 * @param currentGame        - Current game in which to set the initial position
	 * @param quoridor           - Current instance of the board
	 * @param currentWhitePlayer - Current white player to initiate position
	 * @param currentBlackPlayer - Current black player to initiate position
	 */
	private static void setInitialGamePosition(Game currentGame, Board board, Player currentWhitePlayer,
			Player currentBlackPlayer) {
		// Get initial tiles
		Tile whitePlayerInitialTile = getTileAtRowCol(ControllerUtilities.WHITE_INITIAL_ROW, ControllerUtilities.WHITE_INITIAL_COL);
		Tile blackPlayerInitialTile = getTileAtRowCol(ControllerUtilities.BLACK_INITIAL_ROW, ControllerUtilities.BLACK_INITIAL_COL);
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
	 * Method used to set the next player to play. Requires currentPlayer and next
	 * player
	 * 
	 * @param currentPlayer - Player to set nextPlayer in
	 * @param nextPlayer    - Player to be set as next player
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
		Time thinkingTime = new Time(min * 60L * 1000 + sec * 1000L);
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
	 * @param username      - String username exists within the list of users
	 * @param currentPlayer - Player can either be whitePlayer or blackPlayer
	 * @param quoridor      - Quoridor contains given list of users (if any)
	 * @author Nayem Alam
	 */
	public static void selectExistingUserName(String username, Player currentPlayer, Quoridor quoridor) {
		List<User> users = quoridor.getUsers();
		if (!users.isEmpty()) {
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
	 * This method interacts with the GUI, it checks for specific conditions and
	 * then runs the corresponding controller class
	 *
	 * @param username - String username can either exist in the list or can create
	 *                 new username
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

	/**
	 * GUI related Method - createNewUsernamePlayerOneGUI(String username)
	 *
	 * This method interacts with the GUI, it creates a new user with username
	 * for player 1
	 * View handles warning if duplicate user
	 *
	 * @param username - String username to create
	 * @author Nayem Alam
	 */
	public static void createNewUsernamePlayerOneGUI(String username) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// add a new user
		quoridor.addUser(username);

	}
	/**
	 * GUI related Method - createNewUsernamePlayerTwoGUI(String username)
	 *
	 * This method interacts with the GUI, it creates a new user with username
	 * for player 2
	 * View handles warning if duplicate user
	 *
	 * @param username - String username to create
	 * @author Nayem Alam
	 */
	public static void createNewUsernamePlayerTwoGUI(String username) {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// add a new user
		quoridor.addUser(username);
	}

	/**
	 * GUI related Method - selectAnExistingUsernameGUI(String username)
	 *
	 * This method interacts with the GUI, it checks if the username is already
	 * created if so, the user then selects that username
	 *
	 * @param username - String username can either exist in the list or can create new username
	 * @author Nayem Alam
	 */
	public static void selectAnExistingUsernameGUI(String username) {
		List<User> userList = QuoridorApplication.getQuoridor().getUsers();
		for (User user : userList) {
			if (user.getName().equals(username)) {
				user.setName(username);
			}
		}
	}
	/**
	 * GUI related Method - createWhitePlayer()
	 *
	 * This method creates a new WhitePlayer, to interact with the view
	 *
	 * @author Nayem Alam
	 */
	public static Player createWhitePlayer() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		return new Player(new Time(0), quoridor.getUser(0), ControllerUtilities.BLACK_TILE_INDEX, Direction.Horizontal);
	}
	/**
	 * GUI related Method - createBlackPlayer()
	 *
	 * This method creates a new WhitePlayer, to interact with the view
	 *
	 * @author Nayem Alam
	 */
	public static Player createBlackPlayer() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		return new Player(new Time(0), quoridor.getUser(1), ControllerUtilities.WHITE_TILE_INDEX, Direction.Vertical);
	}
	/**
	 * GUI related Method - setThinkingTimeGUI(Player wPlayer, Player bPlayer, Integer min, Integer sec)
	 *
	 * This method interacts with the GUI, it sets the thinking time for player1 and 2
	 *
	 * @param wPlayer - Player sets the whitePlayer
	 * @param bPlayer - Player sets the blackPlayer
	 * @param min - Integer sets the number of minutes
	 * @param sec - Integer sets the number of seconds
	 * @author Nayem Alam
	 */
	public static void setThinkingTimeGUI(Player wPlayer, Player bPlayer, Integer min, Integer sec) {
		// converts min and sec to long type (unix epoch time)
		Time thinkingTime = new Time(min * 60L * 1000 + sec * 1000L);
		// set same thinking time for both players
		bPlayer.setRemainingTime(thinkingTime);
		wPlayer.setRemainingTime(thinkingTime);
	}

	// public static List<String> myUsers() {
	// List<String> userList = new ArrayList<>();
	// userList.add("Daniel");
	// userList.add("Hyacinth");
	// return userList;
	// }

	/**
	 * Method - selectNewUserName(String username)
	 *
	 * This method, according to the Gherkin definition, should allow a player
	 * starting the game to select a new username
	 *
	 * @param username      - String username is new and does not exist in list of
	 *                      users yet
	 * @param currentPlayer - Player can either be whitePlayer or blackPlayer
	 * @param quoridor      - Quoridor contains given list of users (if any)
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
				int index = ((ControllerUtilities.TOTAL_NUMBER_OF_COLS) * (row - 1) + (col - 1));
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
	 * 
	 * @param row , the row of the position of the pawn
	 * @param col , the column of the pawn
	 * @returns true/false , true if the position is valid else false
	 * @author Alexander Legouverneur
	 */
	public static boolean initializeValidatePosition(int row, int col) {
		if (row >= 1 && row <= 9 && col >= 1 && col <= 9) {
			return true;
		} else
			return false;
	}

	/**
	 * Method that returns if the position is valid by calling
	 * InitializeVaidatePostion()
	 * 
	 * @param row of the pawn position
	 * @param col of th pawn position
	 * @returns ok/error strings
	 * @author Alexander Legouverneur
	 */
	public static String validatePawnPosition(int row, int col) {
		if (initializeValidatePosition(row, col) == true) {
			return "ok";
		} else
			return "error";
	}

	/**
	 * Method that initiates the validation of the position by checking all cases of
	 * incorrect position for all placed walls.
	 * 
	 * @param row     , is the row of the target tile of the WallMove associated to
	 *                the wall
	 * @param col     , the column of the target tile of the wall move associated to
	 *                the wall
	 * @param Walldir , the direction of the WallMove associated with the wall
	 * @param id      , the id of the wall currently checked for validity
	 * @return true/false true for valid position, false for invalid
	 * @author Alexander Legouverneur
	 */
	public static boolean initiatePosValidation(int row, int col, String Walldir, int id) {
		Quoridor q = QuoridorApplication.getQuoridor();
		if (row >= 1 && row <= 8 && col >= 1 && col <= 8) {

			Direction dir1;
			int col1;
			int row1;

			for (int i = 0; i <= 19; i++) {
				boolean wallPassed = false;
				if (id == i) {
					continue; // don t want to compare the wall with itself
				}
				//System.out.println("Iteration Id"+ i);
				if (i > 9) {

					if (q.getCurrentGame().getBlackPlayer().getWall(i - 10).hasMove() == false) {
						continue;
					}
					row1 = q.getCurrentGame().getBlackPlayer().getWall(i - 10).getMove().getTargetTile().getRow();
					col1 = q.getCurrentGame().getBlackPlayer().getWall(i - 10).getMove().getTargetTile().getColumn();
					dir1 = q.getCurrentGame().getBlackPlayer().getWall(i - 10).getMove().getWallDirection();
					wallPassed = true;
				} else {
					if (q.getCurrentGame().getWhitePlayer().getWall(i).hasMove() == false) {
						continue;
					}
					row1 = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getTargetTile().getRow();
					col1 = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getTargetTile().getColumn();
					dir1 = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getWallDirection();
					wallPassed = true;
				}

				if (wallPassed && dir1 == Direction.Horizontal) {
					if (Walldir.equals("horizontal")) {
						if (col1 == col && row1 == row) {
							return false;
						}
						if (col1 == col + 1 && row1 == row) {
							return false;
						}
						if (col1 == col - 1 && row1 == row) {
							return false;
						}
					} else {
						if (col1 == col && row1 == row) {
							return false;
						}
					}
				} else if(wallPassed && dir1 == Direction.Vertical) {
					if (Walldir.equals("vertical")) {
						if (row1 == row + 1 && col1 == col) {
							return false;
						}
						if (row1 == row - 1 && col1 == col) {
							return false;
						}
						if (col1 == col && row1 == row) {
							return false;
						}
					} else {
						if (col1 == col && row1 == row) {
							return false;
						}
					}
				}
			}
			return true;
		} 

		else {
			System.out.println("this");
			return false;
		}
	}

	/**
	 * Method that returns if the wall can be placed at the indicated position by
	 * calling InitiatePosValidation()
	 * 
	 * @param row , the row of the tile of the WallMove associated with to the wall
	 * @param dir , direction of the wall
	 * @param col , column of the tile of the WallMove associated to the wall
	 * @return ok/error , strings, ok if wall can be placed, else error.
	 * @author Alexander Legouverneur
	 */
	public static String validateWall(int row, int col, String dir) {
		if (initiatePosValidation(row, col, dir, 0) == true) {
			return "ok";
		} else
			return "error";
	}

	/**
	 * Method that checks if wall Position is valid
	 * 
	 * @param row  , row of the tile of the WallMove associated with the wall
	 * @param col  , column of the tile of the WallMove associated with the wall
	 * @param dir  , direction of the wall
	 * @param wall , the wall which the position is checked
	 * @return true/false , true if position is valid, false if it is not
	 * @author Alexander Legouverneur
	 */
	public static boolean checkWallValid(int row, int col, String dir, Wall wall) {

		if (initiatePosValidation(row, col, dir, wall.getId()-1) == true) {
			return true;
		} else
			return false;
	}

	// end of validate position
	/**
	 * Checks if the wall is on the side edge of the board
	 * 
	 * @param aWall
	 * @return true/false -true if on side edge
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static boolean checkWallSideEdge(Wall aWall, String side) {

		int row = aWall.getMove().getTargetTile().getRow();
		int col = aWall.getMove().getTargetTile().getColumn();
		Direction dir = aWall.getMove().getWallDirection();
		// System.out.println("Coordinates: "+row+","+col);

		if (col == 1 && side.equals("left") && dir == Direction.Vertical) {
			return true;
		}
		if (col == 8 && side.equals("right") && dir == Direction.Horizontal) {
			return true;
		}
		if (col == 1 && side.equals("left") && dir == Direction.Horizontal) {
			return true;
		}
		if (col == 8 && side.equals("right") && dir == Direction.Vertical) {
			return true;
		}
		if (row == 8 && side.equals("down") && dir == Direction.Vertical) {
			return true;
		}
		if (row == 1 && side.equals("up") && dir == Direction.Vertical) {
			return true;
		}
		if (row == 1 && side.equals("up") && dir == Direction.Horizontal) {
			return true;
		}
		if (row == 8 && side.equals("down") && dir == Direction.Horizontal) {
			return true;
		}
		return false;
	}

	/**
	 * Methods that checks if the move on the specified side is possible, with if
	 * statements to see if the move is legal, if yes proceed to the move otherwise,
	 * call the method IlllegalWallMove()
	 * 
	 * @param aWall
	 * @param side
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static void verifyMoveWallOnSide(Wall aWall, String side, int index) {
		Quoridor q = QuoridorApplication.getQuoridor();

		try {
			aWall.getOwner().getWall(index).getMove().getTargetTile().getRow();
			aWall.getOwner().getWall(index).getMove().getTargetTile().getColumn();
			aWall.getOwner().getWall(index).getMove().getWallDirection();
		} catch (Exception e) {
			System.out.println("THE WALL IS NOT PLACED: ");
		}

		int row = aWall.getOwner().getWall(index).getMove().getTargetTile().getRow();
		int col = aWall.getOwner().getWall(index).getMove().getTargetTile().getColumn();

		if (checkWallSideEdge(aWall, side) == true) {
			illegalWallMove();
		}

		if (checkWallSideEdge(aWall, side) == false) {

			if (side.equals("left")) {
				Tile aTile = new Tile(row, col - 1, q.getBoard());
				aWall.getMove().setTargetTile(aTile);
			}
			if (side.equals("right")) {
				Tile aTile = new Tile(row, col + 1, q.getBoard());
				aWall.getMove().setTargetTile(aTile);

			}
			if (side.equals("up")) {
				Tile aTile = new Tile(row - 1, col, q.getBoard());
				aWall.getMove().setTargetTile(aTile);
			}
			if (side.equals("down")) {
				Tile aTile = new Tile(row + 1, col, q.getBoard());
				aWall.getMove().setTargetTile(aTile);
			}
		}

	}

	/**
	 * This method is called by VerifyMoveWallOnSide if the move is illegal. Returns
	 * a string "illegal", and makes sure the coordinates of the wall remain the
	 * same as before
	 * 
	 * @param aWall
	 * @return String
	 * @throws UnsupportedOperationException
	 * @author Alexander Legouverneur
	 */
	public static String illegalWallMove() {
		return "Illegal";
	}

	/**
	 * This method generalizes the wall move. It will go through all the possible
	 * errors by calling other methods. To be sure that the wall move is possible,
	 * and if yes, execute the wall move.
	 * 
	 * @param row    row of the target tile for the move
	 * @param col    column of the target tile for the move
	 * @param dir    direction of the move
	 * @param aWall  wall to be moved
	 * @param player player to whom the wall belongs
	 */
	public static boolean wallMove(int row, int col, String dir, Wall aWall, Player player) {
		System.out.println("wall move not working " + row + ","+ col + " id "+ aWall.getId());

		Quoridor q = QuoridorApplication.getQuoridor();
		boolean pos;
		Tile aTile = new Tile(row, col, q.getBoard());

		if (dir.toLowerCase().equals("vertical")) {
			pos = initiatePosValidation(row, col, "vertical", aWall.getId());
			System.out.println("THIS IS IN MOVE WALL "+pos);
			System.out.println("Wall Has move "+aWall.hasMove());
			if (aWall.hasMove() && pos) {
				moveNumber++;
				aWall.getMove().setWallDirection(Direction.Vertical);
				aWall.getMove().setTargetTile(aTile);
				if(player.equals(q.getCurrentGame().getWhitePlayer())) {
					q.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(aWall);
				}
				else {
					//quoridor.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(aWall);
					q.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(aWall);
				}
				return true;
			} else {
				return false;
			}
		} else {
			pos = initiatePosValidation(row, col, "horizontal", aWall.getId());
			if (aWall.hasMove() && pos == true) {
				moveNumber++;
				aWall.getMove().setWallDirection(Direction.Horizontal);
				aWall.getMove().setTargetTile(aTile);
				if(player.equals(q.getCurrentGame().getWhitePlayer())) {
					q.getCurrentGame().getCurrentPosition().addWhiteWallsOnBoard(aWall);
				}
				else {
					q.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(aWall);

				}

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
	 * Controller method used to save the game as a text file This file can later be
	 * loaded to keep playing this instance of the game first checks if the file
	 * exists or not
	 * 
	 * @param filename - String name of file
	 * @return String - contents of the file that was just written by calling
	 *         overwriteGamePosition
	 * @author Nicolas Buisson
	 * 
	 */
	public static boolean saveGamePosition(String filename) throws IllegalArgumentException, IOException {
		File file = new File(filename);

		boolean fileExists = file.createNewFile();
		// false if it already exists
		// true if it doesn't, gets created

		// if(!fileExists) {
		// throw new IllegalArgumentException("File already exists!");
		// }
		if (fileExists = true) {
			overwriteGamePosition(filename);
		}
		return fileExists;

	}

	/**
	 * Method - overwriteGameFile(String filename, Game game)
	 * 
	 * Controller method used to save the game as a text file This file can later be
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
	public static boolean loadSavedPosition(String filename, Player whitePlayer, Player blackPlayer)
			throws IOException {
		Quoridor quoridor = QuoridorApplication.getQuoridor();

		try {
			QuoridorController.initializeNewGame(quoridor, whitePlayer, blackPlayer);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		GamePosition gamePositionToLoad = game.getCurrentPosition();

		File file = new File(filename);
		String content = "";
		BufferedReader BF = new BufferedReader(new FileReader(file));
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
		boolean whiteFirst = positions[0].startsWith("W");
		whiteData = (whiteFirst ? positions[0] : positions[1]);
		blackData = (whiteFirst ? positions[1] : positions[0]);
		gamePositionToLoad.setPlayerToMove((whiteFirst ? whitePlayer : blackPlayer));
		whitePlayer.setNextPlayer(blackPlayer);
		blackPlayer.setNextPlayer(whitePlayer);

		String whitePositions[] = whiteData.split(",");
		String blackPositions[] = blackData.split(",");

		// use ASCII values
		// 'a' = 97, will correspond to the column numbers
		int whitePawnColumn = ((int) whitePositions[0].charAt(3)) - 96;
		int whitePawnRow = whitePositions[0].charAt(4) - 48;
		boolean whitePawnValid = initializeValidatePosition(whitePawnRow, whitePawnColumn);
		if (whitePawnValid) {
			Tile whitePawnTile = quoridor.getBoard().getTile((whitePawnRow - 1) * 9 + whitePawnColumn - 1);
			gamePositionToLoad.getWhitePosition().setTile(whitePawnTile);
		} else {
			quoridor.setCurrentGame(null);
			throw new IllegalArgumentException("Invalid Positions loaded!");
		}
		int blackPawnColumn = ((int) blackPositions[0].charAt(3)) - 96;
		int blackPawnRow = blackPositions[0].charAt(4) - 48;

		//check if the pawns are placed on the same tile
		if(blackPawnColumn == whitePawnColumn && blackPawnRow == whitePawnRow) {
			throw new IllegalArgumentException("Invalid Positions loaded!");
		}


		boolean blackPawnValid = initializeValidatePosition(blackPawnRow, blackPawnColumn);
		if (blackPawnValid = true) {
			Tile blackPawnTile = quoridor.getBoard().getTile((blackPawnRow - 1) * 9 + blackPawnColumn - 1);
			gamePositionToLoad.getBlackPosition().setTile(blackPawnTile);
		} else {
			quoridor.setCurrentGame(null);
			throw new IllegalArgumentException("Invalid Positions loaded!");
		}
		for (int i = 1; i < whitePositions.length; i++) {

			whitePositions[i] = whitePositions[i].trim(); 

			int whiteWallColumn = whitePositions[i].charAt(0) - 96;
			int whiteWallRow = whitePositions[i].charAt(1) - 48; // ASCII value of char representing row, not actual
			// row number
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

			boolean whiteWallsValid = initiatePosValidation(whiteWallRow, whiteWallColumn, wallOrientation, i-1);
			if (whiteWallsValid) {
				Wall whiteWall = gamePositionToLoad.getWhiteWallsInStock(0);
				gamePositionToLoad.removeWhiteWallsInStock(whiteWall);
				gamePositionToLoad.addWhiteWallsOnBoard(whiteWall);
				Tile whiteWallTile = quoridor.getBoard().getTile((whiteWallRow - 1) * 9 + (whiteWallColumn - 1));
				// TODO Double check round and move stuff.
				WallMove whiteWallMove = new WallMove(i, i, getWhitePlayer(), whiteWallTile, game,
						whiteWallDirection, whiteWall);
				whiteWall.setMove(whiteWallMove);
			} else {
				quoridor.setCurrentGame(null);
				throw new IllegalArgumentException("Invalid Positions loaded!");
			}
		}

		for (int i = 1; i < blackPositions.length; i++) {

			blackPositions[i] = blackPositions[i].trim();

			int blackWallColumn = blackPositions[i].charAt(0) - 96;
			int blackWallRow = blackPositions[i].charAt(1) - 48; // unicode value of char representing row, not actual
			// row number
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

			boolean blackWallsValid = initiatePosValidation(blackWallRow, blackWallColumn, wallOrientation, 10 + i - 1);
			if (blackWallsValid) {
				Wall blackWall = gamePositionToLoad.getBlackWallsInStock(0);
				gamePositionToLoad.removeBlackWallsInStock(blackWall);
				gamePositionToLoad.addBlackWallsOnBoard(blackWall);
				Tile blackWallTile = quoridor.getBoard().getTile((blackWallRow - 1) * 9 + (blackWallColumn - 1));
				WallMove blackWallMove = new WallMove(i, i, getBlackPlayer(), blackWallTile, game,
						blackWallDirection, blackWall);
				blackWall.setMove(blackWallMove);
			} else {
				quoridor.setCurrentGame(null);
				throw new IllegalArgumentException("Invalid Positions!");
			}
		}
		return true;
	}

	/**
	 * Method used to rotate a wall
	 * 
	 * @author Iyatan Atchoro
	 */
	public static void rotateWall() throws Exception {
		Game curGame = QuoridorApplication.getQuoridor().getCurrentGame();
		if (curGame.getWallMoveCandidate() == null) {
			throw new Exception("No wall Selected");
		}
		Direction wallDir = curGame.getWallMoveCandidate().getWallDirection();
		if (wallDir == Direction.Horizontal) {
			curGame.getWallMoveCandidate().setWallDirection(Direction.Vertical);
		} else {
			curGame.getWallMoveCandidate().setWallDirection(Direction.Horizontal);
		}

	}
	/**
	 * Method used to drop a wall
	 * 
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

		if (initiatePosValidation(x, y, dir, id)) {
			q.getCurrentGame().addMove(wallMoveCandidate);
			List<Move> curList = new ArrayList<>(q.getCurrentGame().getMoves());
			Move lastMoveInTheList = curList.get(curList.size() - 1);
			lastMoveInTheList.setNextMove(wallMoveCandidate);
			wallMoveCandidate.setPrevMove(lastMoveInTheList);
		} else {
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
		if(playerWhite.hasNextPlayer() && !playerWhite.getNextPlayer().equals(null)){
			return playerWhite;
		}
		else {
			return QuoridorController.getBlackPlayer();
		}
	}

	/**
	 * Method used to set the current player
	 * @param currentPlayer
	 * @author Tristan Bouchard
	 */
	public static void setCurrentPlayer(Player currentPlayer) {
		Player playerWhite = QuoridorController.getWhitePlayer();
		if(currentPlayer.equals(playerWhite)){
			playerWhite.setNextPlayer(getBlackPlayer());
			getBlackPlayer().setNextPlayer(null);
		}
		else {
			getBlackPlayer().setNextPlayer(playerWhite);
			getWhitePlayer().setNextPlayer(null);
		}
	}

	/**
	 * This method uses getCurrentPlayer method to transform the current player into
	 * an int so that it can be used into the view
	 * 
	 * @return 0/1 where 0 is white player and 1 is dark player
	 * @author Alexander Legouverneur
	 */
	public static int currentPlayerInt() {

		Quoridor q = QuoridorApplication.getQuoridor();
		if (getCurrentPlayer().equals(q.getCurrentGame().getWhitePlayer())) {

			return 0;

		} else
			return 1;
	}

	/**
	 * This methods is a getter for the next wall to be placed
	 * 
	 * @author Alexander Legouverneur
	 * @return the next wall to be placed
	 */
	public Wall getNextWall() {
		Quoridor q = QuoridorApplication.getQuoridor();
		if (currentPlayerInt() == 1) {

			int size = q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard().size();
			int index = size; // index of next wall is index-1+1+9
			if (index > 9) {
				return null;
			} else
				return q.getCurrentGame().getBlackPlayer().getWall(index);
		} else {

			int size = q.getCurrentGame().getCurrentPosition().getBlackWallsOnBoard().size();
			int index = size + 10; // index of next wall is index-1+1+9
			if (index > 19) {
				return null;
			} else
				return q.getCurrentGame().getBlackPlayer().getWall(index);
		}

	}

	/**
	 * Method used to notify invalid Wall move
	 * 
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

	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Wall getWallMoveCandidate(){
		return QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().getWallPlaced();
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public static Direction getWallDirection(boolean isRotated){
		return isRotated ? Direction.Vertical : Direction.Horizontal;
	}
	public static Wall getWall(int id) {
		Quoridor q = QuoridorApplication.getQuoridor();
		if (id > 9) {

			return q.getCurrentGame().getBlackPlayer().getWall(id - 10);

		} else {
			return q.getCurrentGame().getBlackPlayer().getWall(id);
		}
	}

	/**
	 * Method used to move a pawn based on a selection of possible tiles
	 * @param player - Current player to move
	 * @param side - Direction of move
	 * @return true if successful move 
	 * @throws IllegalArgumentException on error
	 * @author Tristan Bouchard, Nicolas Buisson
	 */
	public static boolean movePawn(Player player, String side) throws IllegalArgumentException {

		Tile playerTile;
		Tile newPlayerTile = null;

		if(player.equals(getBlackPlayer())) {
			playerTile = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().getTile();

		}else {
			playerTile = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().getTile();
		}

		int currentPlayerRow = playerTile.getRow();
		int currentPlayerColumn = playerTile.getColumn();

		boolean leftIsAllowed = currentPlayerColumn > 1;
		boolean rightIsAllowed = currentPlayerColumn < 9;
		boolean upIsAllowed = currentPlayerRow > 1;
		boolean downIsAllowed = currentPlayerRow < 9;

		boolean leftJumpIsAllowed = currentPlayerColumn > 2;
		boolean rightJumpIsAllowed = currentPlayerColumn < 8;
		boolean upJumpIsAllowed = currentPlayerRow > 2;
		boolean downJumpIsAllowed = currentPlayerRow < 8;

		// Begin by getting all possible tiles this player can move to
		mainValidateMovePawn(player);

		int i = 0;
		for (Tile tile: availableTiles) {

			if(side.equals("left")) {
				// Take care of left and left jump
				if(leftIsAllowed) {
					Tile closeLeftTile = getTileAtRowCol(currentPlayerRow, currentPlayerColumn - 1);
					if(tile.equals(closeLeftTile)) {
						newPlayerTile = closeLeftTile;
						break;
					} else if (leftJumpIsAllowed) {
						Tile farLeftTile = getTileAtRowCol(currentPlayerRow, currentPlayerColumn - 2);
						if(tile.equals(farLeftTile)) {
							newPlayerTile = farLeftTile;
							break;
						}
					}
				}

			} else if(side.equals("right")) {
				// Take care of right and right jump
				if(rightIsAllowed) {
					Tile closeRightTile = getTileAtRowCol(currentPlayerRow, currentPlayerColumn + 1);
					if(tile.equals(closeRightTile)) {
						newPlayerTile = closeRightTile;
						break;
					} else if (rightJumpIsAllowed) {
						Tile farRightTile = getTileAtRowCol(currentPlayerRow, currentPlayerColumn + 2);
						if(tile.equals(farRightTile)) {
							newPlayerTile = farRightTile;
							break;
						}
					}
				}
			} else if(side.equals("up")) {
				// Take care of up and up jump
				if(upIsAllowed) {
					Tile closeUpTile = getTileAtRowCol(currentPlayerRow - 1, currentPlayerColumn);
					if(tile.equals(closeUpTile)) {
						newPlayerTile = closeUpTile;
						break;
					} else if (upJumpIsAllowed) {
						Tile farUpTile = getTileAtRowCol(currentPlayerRow - 2, currentPlayerColumn);
						if(tile.equals(farUpTile)) {
							newPlayerTile = farUpTile;
							break;
						}
					}
				}
			} else if(side.equals("down")) {
				// Take care of down and down jump
				if(downIsAllowed) {
					Tile closeDownTile = getTileAtRowCol(currentPlayerRow + 1, currentPlayerColumn);
					if(tile.equals(closeDownTile)) {
						newPlayerTile = closeDownTile;
						break;
					} else if (downJumpIsAllowed) {
						Tile farDownTile = getTileAtRowCol(currentPlayerRow + 2, currentPlayerColumn);
						if(tile.equals(farDownTile)) {
							newPlayerTile = farDownTile;
							break;
						}
					}
				}
			} else if(side.equals(ControllerUtilities.DiagonalDirections.upleft.toString())){
				// UpLeft
				if(upIsAllowed && leftIsAllowed) {
					Tile upLeft = getTileAtRowCol(currentPlayerRow - 1, currentPlayerColumn - 1);
					if(tile.equals(upLeft)) {
						newPlayerTile = upLeft;
						break;
					}
				}
			} else if(side.equals(ControllerUtilities.DiagonalDirections.upright.toString())){
				// UpRight
				if(upIsAllowed && rightIsAllowed) {
					Tile upRight = getTileAtRowCol(currentPlayerRow - 1, currentPlayerColumn + 1);
					if(tile.equals(upRight)) {
						newPlayerTile = upRight;
						break;
					}
				}
			} else if(side.equals(ControllerUtilities.DiagonalDirections.downleft.toString())){
				// downLeft
				if(downIsAllowed && leftIsAllowed) {
					Tile downLeft = getTileAtRowCol(currentPlayerRow + 1, currentPlayerColumn - 1);
					if(tile.equals(downLeft)) {
						newPlayerTile = downLeft;
						break;
					}
				}
			} else if(side.equals(ControllerUtilities.DiagonalDirections.downright.toString())){
				// downRight
				if(downIsAllowed && rightIsAllowed) {
					Tile downRight = getTileAtRowCol(currentPlayerRow + 1, currentPlayerColumn + 1);
					if(tile.equals(downRight)) {
						newPlayerTile = downRight;
						break;
					}
				}
			}
		}

		if(player.equals(getBlackPlayer()) && !(newPlayerTile == null)) {
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition().setTile(newPlayerTile);
		}else if(player.equals(getWhitePlayer()) && !(newPlayerTile == null)){
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition().setTile(newPlayerTile);
		}
		else {
			throw new IllegalArgumentException("Illegal move!");
		}
		//switchCurrentPlayer();
		return true;
	} 

	/**
	 * helper method made to access a Tile
	 * using its row and column coordinates
	 * @author Nicolas Buisson
	 * 
	 */
	public static Tile getTileAtRowCol(int row, int column) {
		return QuoridorApplication.getQuoridor().getBoard().getTile((row-1)*9 + column-1);
	}

	//VALIDATE MOVE PAWN LOGIC

	/**
	 * helper method to get the player position
	 * @author Alexander Legouverneur
	 * @param player to move 
	 * @return the tile of the position of the player
	 */
	public static Tile getPlayerPosition(Player player) {

		Quoridor q = QuoridorApplication.getQuoridor();
		if(player.equals(q.getCurrentGame().getBlackPlayer())) {

			return q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile();

		}
		else {
			return q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile();
		}
	}

	/**
	 * This method is the main method to get all the available tiles for a pawn move for a given player
	 * It calls other methods that have the logic behind them
	 * @author Alexander Legouverneur, Tristan Bouchard
	 * @param player the current player
	 */
	public static void mainValidateMovePawn(Player player) {
		availableTiles = new ArrayList<Tile>();

		Quoridor q = QuoridorApplication.getQuoridor();
		int currentRow;
		int currentCol;
		if(player.equals(q.getCurrentGame().getBlackPlayer())) {
			currentRow = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
			currentCol = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
		}
		else {
			currentRow = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
			currentCol = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
		}

		// Add checks to verify if on side of board
		boolean leftIsAllowed = (currentCol > 1);
		boolean rightIsAllowed = (currentCol < 9);
		boolean upIsAllowed = (currentRow > 1);
		boolean downIsAllowed = (currentRow < 9);

		// Regular moves, no jumps
		if(leftIsAllowed && !checkWallOnWay(currentRow, currentCol-1, "left") && !pawnOnWay(player, false)) {
			availableTiles.add(getTileAtRowCol(currentRow,currentCol-1));
		}
		if(rightIsAllowed && !checkWallOnWay(currentRow, currentCol+1, "right") && !pawnOnWay(player, false)) {
			availableTiles.add(getTileAtRowCol(currentRow,currentCol+1));
		}
		if(upIsAllowed && !checkWallOnWay(currentRow-1, currentCol, "up") && !pawnOnWay(player, false) ) {
			availableTiles.add(getTileAtRowCol(currentRow-1,currentCol));
		}
		if(downIsAllowed && !checkWallOnWay(currentRow+1, currentCol, "down") && !pawnOnWay(player, false) ) {
			availableTiles.add(getTileAtRowCol(currentRow+1,currentCol));
		}

		//Jump pawn getting tiles
		if(!checkWallOnWay(currentRow, currentCol-1, "left") && pawnOnWay(player, false)) {
			pawnOnWay(player, true);
		}
		if(!checkWallOnWay(currentRow, currentCol+1, "right") && pawnOnWay(player, false)) {
			pawnOnWay(player, true);
		}
		if(!checkWallOnWay(currentRow-1,currentCol, "up") && pawnOnWay(player, false)) {
			pawnOnWay(player, true);
		}
		if(!checkWallOnWay(currentRow+1, currentCol, "down") && pawnOnWay(player, false)) {
			pawnOnWay(player, true);
		}
		int i = 0;
	}

	/**
	 * Method to check if there is a wall on the way of the possible move, returns false if yes, else returns true
	 * @param targetRow of the tile after the move is done
	 * @param targetCol of the tile after the move is done
	 * @param side of the move, the side the player went to arrive to the (row,col) tile
	 * @return true if wall on the way, false otherwise
	 * @author Alexander Legouverneur
	 */
	public static boolean checkWallOnWay(int targetRow, int targetCol, String side) {//tile where the move is not the player tile
		Quoridor q = QuoridorApplication.getQuoridor();
		int wallRow;
		int wallCol;
		Direction dir;
		for(int i=0; i<20; i++) {

			if(i<10) {
				if(q.getCurrentGame().getWhitePlayer().getWall(i).hasMove() == true) {
					wallRow = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getTargetTile().getRow();
					wallCol = q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getTargetTile().getColumn();
					dir =  q.getCurrentGame().getWhitePlayer().getWall(i).getMove().getWallDirection();
				}
				else continue;
			}
			else {
				if(q.getCurrentGame().getBlackPlayer().getWall(i - 10).hasMove() == true) {
					wallRow = q.getCurrentGame().getBlackPlayer().getWall(i - 10).getMove().getTargetTile().getRow();
					wallCol = q.getCurrentGame().getBlackPlayer().getWall(i - 10).getMove().getTargetTile().getColumn();
					dir =  q.getCurrentGame().getBlackPlayer().getWall(i - 10).getMove().getWallDirection();
				}
				else continue;
			}
			if(side.equals("left") && targetRow == wallRow && targetCol == wallCol && dir.equals(Direction.Vertical)) {
				return true;
			}
			if(side.equals("left") && targetRow == wallRow + 1 && targetCol == wallCol && dir.equals(Direction.Vertical)) {
				return true;
			}
			if(side.equals("right") && targetRow == wallRow && targetCol == wallCol + 1 && dir.equals(Direction.Vertical)) {
				return true;
			}
			if(side.equals("right") && targetRow == wallRow + 1 && targetCol == wallCol + 1 && dir.equals(Direction.Vertical)) {
				return true;
			}
			if(side.equals("up") && targetRow == wallRow && targetCol == wallCol + 1 && dir.equals(Direction.Horizontal)) {
				return true;
			}
			if(side.equals("up") && targetRow == wallRow && targetCol == wallCol && dir.equals(Direction.Horizontal)) {
				return true;
			}
			if(side.equals("down") && targetRow == wallRow + 1 && targetCol == wallCol + 1 && dir.equals(Direction.Horizontal)) {
				return true;
			}
			if(side.equals("down") && targetRow == wallRow + 1 && targetCol == wallCol && dir.equals(Direction.Horizontal)) {
				return true;
			}

		}
		return false;

	}

	/**
	 * Method that checks if there is a pawn in an adjacent tile of the current player position. If yes return true else return false.
	 * This method calls getJumpPawnTiles if the condition is set to true.
	 * Cond is here because sometimes pawnOnWay is called just to check and not add the tiles to the array
	 * @param player current player
	 * @param cond  boolean to know if it has to call the other method
	 * @return true if there is a pawn in an adjacent tile to the current player, else false
	 * @author Alexander Legouverneur
	 */
	public static boolean pawnOnWay(Player player, boolean cond) { //sometimes I call the method and don t want to call pawnJump with it 
		Quoridor q = QuoridorApplication.getQuoridor();				//thats why I add a condition boolean
		int currentPlayerRow;
		int currentPlayerCol;
		int opponentRow;
		int opponentCol;
		if(player.equals(q.getCurrentGame().getBlackPlayer())) {
			currentPlayerRow = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
			currentPlayerCol = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
			opponentRow = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
			opponentCol = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();

		}
		else {
			currentPlayerRow = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
			currentPlayerCol = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
			opponentRow = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
			opponentCol = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();

		}
		// If opponent below
		if((currentPlayerRow + 1 == opponentRow) && (currentPlayerCol == opponentCol)) {
			if(cond == true) {
				getJumpPawnTiles(opponentRow, opponentCol, currentPlayerRow,currentPlayerCol);
			}
			return true;
		}
		// If opponent above
		if(currentPlayerRow-1 ==opponentRow && currentPlayerCol == opponentCol) {
			if(cond == true) {
				getJumpPawnTiles(opponentRow, opponentCol, currentPlayerRow,currentPlayerCol);
			}
			return true;
		}
		// If opponent left
		if(currentPlayerRow == opponentRow && currentPlayerCol-1 == currentPlayerCol) {
			if(cond == true) {
				getJumpPawnTiles(opponentRow, opponentCol, currentPlayerRow,currentPlayerCol);
			}
			return true;
		}
		//if opponent right
		if (currentPlayerRow == opponentRow && currentPlayerCol+1 == opponentCol) {
			if(cond == true) {
				getJumpPawnTiles(opponentRow, opponentCol, currentPlayerRow,currentPlayerCol);
			}
			return true;
		}
		else {
			return false;
		}


	}

	/**
	 * This method gets all the available tiles for a jump pawn move and adds them into the array of available tiles to be
	 * sent into the view. It also makes sure, the current player position is not added to the list.
	 * @param opponentRow  row of the tile of the pawn of the opponent
	 * @param opponentCol col of the tile of the pawn of the opponent
	 * @param currentPlayerRow  row of the current player position
	 * @param currentPlayerCol  col of the current player position
	 * @author Alexander Legouverneur
	 */
	public static void getJumpPawnTiles(int opponentRow, int opponentCol, int currentPlayerRow, int currentPlayerCol) {//row and col are the coordinates of the player we want to jump
		//ActualRow and ActualCol are the coordinates of the current player

		// Booleans for player jumps, aka "Double tile jumps"
		boolean leftJumpOverIsAllowed = currentPlayerCol > 2;
		boolean rightJumpOverIsAllowed = currentPlayerCol < 8;
		boolean upJumpOverIsAllowed = currentPlayerRow > 2;
		boolean downJumpOverIsAllowed = currentPlayerRow < 8;

		boolean opponentRowIsValid = opponentRow > 0 && opponentRow <= 9;
		boolean opponentColIsValid = opponentCol > 0 && opponentRow <= 9;

		if(!opponentColIsValid || !opponentRowIsValid) {
			throw new IllegalArgumentException("Illegal opponent position");
		}

		// Verify if opponent has walls around him

		// Down jump
		if(initializeValidatePosition(opponentRow - 1, opponentCol) && !checkWallOnWay(opponentRow-1,opponentCol,"up")) {
			// If not our current player tile
			if( (opponentRow-1 != currentPlayerRow || opponentCol != currentPlayerCol)) {
				availableTiles.add(getTileAtRowCol(opponentRow-1,opponentCol));
			}
		}
		if(initializeValidatePosition(opponentRow + 1, opponentCol) && !checkWallOnWay(opponentRow+1,opponentCol,"down")) {
			if((opponentRow+1 != currentPlayerRow || opponentCol != currentPlayerCol)) {
				availableTiles.add(getTileAtRowCol(opponentRow + 1,opponentCol));
			}
		}
		if(initializeValidatePosition(opponentRow, opponentCol - 1) && !checkWallOnWay(opponentRow,opponentCol-1,"left")) {
			if((opponentRow != currentPlayerRow || opponentCol - 1 != currentPlayerCol)) {
				availableTiles.add(getTileAtRowCol(opponentRow,opponentCol - 1 ));
			}
		}
		if(initializeValidatePosition(opponentRow, opponentCol + 1) && !checkWallOnWay(opponentRow,opponentCol+1,"right")) {
			if((opponentRow != currentPlayerRow || opponentCol + 1 != currentPlayerCol)) {
				availableTiles.add(getTileAtRowCol(opponentRow,opponentCol + 1));
			}
		}
	}
	/**
	 * This method compares the coordinates entered as arguments to the tiles into the list
	 * @param row of tile coordinate
	 * @param col of tile coordinate
	 * @return true if there is a match, false if there is no match
	 * @author Alexander Legouverneur
	 */
	public static boolean compareAvailableTiles(int row, int col){
		for(Tile tile : availableTiles) {
			if(tile.getRow() == row && tile.getColumn() == col) {
				return true;
			}
			
		}
			return false;
	}

	public static void InitTwoUsers() {
		QuoridorApplication.getQuoridor().addUser("U1");
		QuoridorApplication.getQuoridor().addUser("U2");
	}
	
	public static List<Tile> getAvailableTiles(){
		return availableTiles; // for view
	}
	
	/**
	 * This method checks if the player reached the other side of the board (thus if he won).
	 * @param player  the player we are checking for position
	 * @param row   the row of the tile of the position of the player we are checking the position for
	 * @param col   the column of the tile of the position of the player we are checking the position for
	 * @return true if he reached the other side, false if he did not
	 * @author Alexander Legouverneur
	 */
	public static boolean checkIfWon(Player player, int row, int col) {
		Quoridor q = QuoridorApplication.getQuoridor();
		if(player.equals(q.getCurrentGame().getBlackPlayer())) {
			if(row == 1 ) {
				//stopGame(q.getCurrentGame());
				return true;
			}
			
		}
		else {
			if(row == 9) {
				//stopGame(q.getCurrentGame());
				return true;
			}
		}
		return false;
	}
	public static void stopGame(Game game) {
		game.delete();
	}
	
	/**
	 * This method is used in the replay mode to jump to the start of the game to be able to replay
	 * and monitor what happened from the start.
	 * @param moveNum the moveNumber currently monitored
	 * @author Alexander Legouverneur
	 */
	public static void jumpToStart(int moveNum, int roundNum) {
		Quoridor q = QuoridorApplication.getQuoridor();
		
		Tile aTile = new Tile(1,5,q.getBoard());
		PlayerPosition Bpos = new PlayerPosition(q.getCurrentGame().getBlackPlayer(), aTile);
		q.getCurrentGame().getCurrentPosition().setBlackPosition(Bpos);
		
		Tile aTile1 = new Tile(9,5,q.getBoard());
		PlayerPosition Wpos = new PlayerPosition(q.getCurrentGame().getWhitePlayer(), aTile1);
		q.getCurrentGame().getCurrentPosition().setBlackPosition(Wpos);
		if(roundNum==2) {
			int thresh = 2*(moveNum-1)+1;
			for(int i = 0; i<thresh; i++) {
				stepBack();
			}
		}
		else {
			int thresh = 2*(moveNum-1);
			for(int i = 0; i<thresh;i++) {
				stepBack();
			}
		}
		
		
	}
	public static Move stepBack() {
		
	}
}


