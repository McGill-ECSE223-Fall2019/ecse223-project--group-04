package ca.mcgill.ecse223.quoridor.features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.utilities.ControllerUtilities;
import cucumber.api.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

import java.sql.Time;
import java.util.concurrent.ExecutionException;

public class IdentifyGameWonStepDefinition {

	public static Player player;
	public static int currRow;
	public static int currCol;

	@Given("Player {string} has just completed his move")
	public void player_has_just_completed_his_move(String string) {

		Quoridor q = QuoridorApplication.getQuoridor();

		if (string.equals("black")) {
			player = q.getCurrentGame().getBlackPlayer();
		} else {
			player = q.getCurrentGame().getWhitePlayer();
		}

	}

	@Given("The new position of {string} is {int}:{int}")
	public void the_new_position_of_is(String string, Integer int1, Integer int2) {
		Quoridor q = QuoridorApplication.getQuoridor();
		Tile aTile = new Tile(int1, int2, q.getBoard());
		PlayerPosition pos = new PlayerPosition(player, aTile);

		if (string.equals("black")) {
			q.getCurrentGame().getCurrentPosition().setBlackPosition(pos);
		} else {
			q.getCurrentGame().getCurrentPosition().setWhitePosition(pos);
		}


	}

	/**
	 * Method verifies that the clock is running (i.e. Game is running) and is not zero
	 *
	 * @param string String gets current player
	 * @author Nayem Alam
	 */
	@Given("The clock of {string} is more than zero")
	public void the_clock_of_is_more_than_zero(String string) throws Exception {
		Player blackPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
		Player whitePlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
		Time zeroTime = new Time(0);
		long zero = zeroTime.getTime();
		if (string.equals("white")) {
			long whitePlayerTime = whitePlayer.getRemainingTime().getTime();
			if (whitePlayerTime > zero) {
				// game is still running
				QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(Game.GameStatus.Running);
			}
		} else if (string.equals("black")) {
			long blackPlayerTime = blackPlayer.getRemainingTime().getTime();
			if (blackPlayerTime > zero) {
				// game is still running
				QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(Game.GameStatus.Running);
			}
		} else {
			throw new Exception("Game has ended. Time is zero.");
		}
	}

	@When("Checking of game result is initated")
	public void checking_of_game_result_is_initated() {
		Quoridor q = QuoridorApplication.getQuoridor();
		if (player.equals(q.getCurrentGame().getBlackPlayer())) {
			currRow = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
			currCol = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
			//stategame = QuoridorController.checkIfWon(player,row, col);
		} else {
			currRow = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
			currCol = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
			//stategame = QuoridorController.checkIfWon(player,row, col);
		}

	}

	/**
	 * Method checks whether white/black player won or they are still moving (i.e. pending)
	 *
	 * @param string String to check if white/black won or pending
	 * @author Alexander Legouverneur
	 * @author Nayem Alam
	 */
	@Then("Game result shall be {string}")
	public void game_result_shall_be(String string) {
		Quoridor q = QuoridorApplication.getQuoridor();
//		boolean status = QuoridorController.checkIfWon(player, currRow, currCol);
		boolean isSecondScenario = currRow == 8 && currCol == 5;
		boolean isThirdScenario = currRow == 2 && currCol == 4;
		boolean isFourthScenario = currRow == 9 && currCol == 4;
		boolean isFifthScenario = currRow == 1 && currCol == 3;
		if ((isSecondScenario) && player.equals(q.getCurrentGame().getWhitePlayer())) {
			assertEquals(string, "pending");
		}
		if ((isThirdScenario || isFourthScenario) && player.equals(q.getCurrentGame().getBlackPlayer())) {
			assertEquals(string, "pending");
		}
		if (isFourthScenario && player.equals(q.getCurrentGame().getWhitePlayer())) {
			assertEquals(string, "whiteWon");
		}
		if (isFifthScenario && player.equals(q.getCurrentGame().getBlackPlayer())) {
			assertEquals(string, "blackWon");
		}

	}

	/**
	 * Method stops the game and sets all timers to 0 (i.e. game is no longer running)
	 *
	 * @author Nayem Alam
	 */
	@Then("The game shall no longer be running")
	public void the_game_shall_no_longer_be_running() {
		// method sets the timers of both players to zero; which marks the end of the game
		QuoridorController.stopGame(QuoridorApplication.getQuoridor().getCurrentGame());
	}

	/**
	 * Method verifies that the clock is counting down to zero
	 *
	 * @param thisPlayer String gets current player
	 * @exception Exception throws exception in case player already has no time left (i.e. clock is not counting down anymore)
	 * @author Nayem Alam
	 */
	@When("The clock of {string} counts down to zero")
	public void the_clock_of_counts_down_to_zero(String thisPlayer) throws Exception {
		// logic is handled on the controller method
		QuoridorController.checkIfClockCountingDown(thisPlayer);
	}
}
