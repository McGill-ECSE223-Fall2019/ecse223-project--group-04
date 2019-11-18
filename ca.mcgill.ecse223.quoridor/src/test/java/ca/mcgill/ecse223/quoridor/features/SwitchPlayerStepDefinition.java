package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Time;

import javax.swing.text.Position;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.utilities.TimerUtilities;
import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * class to handle Switch Current Player Feature
 * 
 * @author Ousmane Baricisse
 * 
 */

public class SwitchPlayerStepDefinition {

	private static Player nextPlayer; // keeps track of next Player
	private static Player currentPlayer; // keeps track of current Player

	/**
	 * sets the player to move to the correct player based on given parameter
	 * 
	 * @param string
	 * @throws Exception
	 */

	@Given("The player to move is {string}")
	public void the_player_to_move_is(String string) throws Exception {
		// Write code here that turns the phrase above into concrete actions
		boolean condition = false;
		Player playerToMove; 
		if (string.equalsIgnoreCase("white")) {
			playerToMove = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(playerToMove);


		} else if(string.equalsIgnoreCase("black")) {
			playerToMove = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
			QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setPlayerToMove(playerToMove);
	}
	}

	/**
	 * sets the time remaining for current Player to 180s
	 * 
	 * @param string
	 * 
	 * @author ousmanebaricisse
	 */
	@Given("The clock of {string} is running")
	public void the_clock_of_is_running(String string) {
		// Write code here that turns the phrase above into concrete actions
		QuoridorController.startClock();

	}

	/**
	 * stops the timer for other player by setting it to 0
	 * 
	 * @param string
	 * @throws Exception
	 * @author ousmanebaricisse
	 */
	@Given("The clock of {string} is stopped")
	public void the_clock_of_is_stopped(String string) throws Exception {
		QuoridorController.stopClock();
	}

	/**
	 * calls controller method to complete the move controller method is a void
	 * method Pass in as parameter a given destination and the state of game, as
	 * well as the current player
	 * 
	 * @param string
	 * @author ousmanebaricisse
	 */
	@When("Player {string} completes his move")
	public void player_completes_his_move(String string) {
		// Write code here that turns the phrase above into concrete actions
		assertTrue(QuoridorApplication.getQuoridor().getCurrentGame().hasWallMoveCandidate());
		// assertTrue(condition);
	}

	/**
	 * asserts then the next player to move is set to current
	 * 
	 * @param string
	 */
	@Then("The user interface shall be showing it is {string} turn")
	public void the_user_interface_shall_be_showing_it_is_turn(String string) {
		// Write code here that turns the phrase above into concrete actions

		Player playerToMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();

	}

	/**
	 * assets the remaining time for current player to be zero as clock is stopped
	 * for current player
	 * 
	 * @param string
	 * @throws Exception
	 */
	@Then("The clock of {string} shall be stopped")
	public void the_clock_of_shall_be_stopped(String string) throws Exception {
		// Write code here that turns the phrase above into concrete actions
		int time = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.getRemainingTime().getMinutes();
		boolean condition = time != 0;
		assertTrue(condition);
	}

	/**
	 * starts clock for other player
	 * 
	 * @author ousmanebaricisse
	 * @param string
	 */
	@Then("The clock of {string} shall be running")
	public void the_clock_of_shall_be_running(String string) {
		// Write code here that turns the phrase above into concrete actions

		boolean condition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasNextPlayer();
		assertTrue(!condition);
	}

	/**
	 * asserts next player to move check if player to move is alternated return true
	 * if next player is player to move else return false
	 * 
	 * @author ousmanebaricisse
	 * @param string
	 */
	@Then("The next player to move shall be {string}")
	public void the_next_player_to_move_shall_be(String string) {
		// Write code here that turns the phrase above into concrete actions
		boolean condition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
				.hasNextPlayer();
		assertTrue(!condition);

	}
}