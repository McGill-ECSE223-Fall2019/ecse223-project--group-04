
package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.Time;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Board;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.utilities.ControllerUtilities;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Class used to encapsulate the StartNewGame Gherkin feature
 * 
 * @author Tristan Bouchard
 *
 */
public class StartNewGameFeatureStepDef {

	// *********************************************
	// Initiate new game scenario
	// *********************************************

	/**
	 * Method used to create a new game
	 * 
	 * @throws Exception
	 * @author Tristan Bouchard
	 */
	@When("A new game is being initialized")
	public void aNewGameIsBeingInitialized() throws Exception {
		QuoridorController.initializeNewGame(QuoridorApplication.getQuoridor());
	}

	/**
	 * Method used to set a name for the white player
	 * 
	 * @author Tristan Bouchard
	 */
	@And("White player chooses a username")
	public void whitePlayerChoosesUserName() {
		Boolean nameSetCorrectly = QuoridorController.setWhitePlayerUserName(TestingUtilities.WHITE_PLAYER_NAME);
		if (!nameSetCorrectly) {
			fail();
		}
	}

	/**
	 * Method used to set a name for the black player
	 * 
	 * @author Tristan Bouchard
	 */
	@And("Black player chooses a username")
	public void blackPlayerChoosesUserName() {
		Boolean nameSetCorrectly = QuoridorController.setBlackPlayerUserName(TestingUtilities.BLACK_PLAYER_NAME);
		if (!nameSetCorrectly) {
			fail();
		}
	}

	/**
	 * Method used to set the total thinking time for each player
	 * 
	 * @author Tristan Bouchard
	 */
	@And("Total thinking time is set")
	public void totalThinkingTimeIsSet() {
		QuoridorController.setTotalThinkingTime(TestingUtilities.THINKING_TIME_MS);
	}

	/**
	 * Method used to verify that the game is ready to begin
	 * 
	 * @author Tristan Bouchard
	 */
	@Then("The game shall become ready to start")
	public void theGameShallBecomeReadyToStart() {
		assertEquals(Game.GameStatus.Initializing, QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus());
	}

	// *********************************************
	// Start clock scenario
	// *********************************************

	/**
	 * Given method that ensures that the system is in a state such that the game is
	 * ready to start
	 * 
	 * @throws Exception
	 * @author Tristan Bouchard
	 */
	@Given("The game is ready to start")
	public void theGameIsReadyToStart() throws Exception {
		// Here, I would call my controller method to start a game and put it in a
		// known state where the clock is ready to be started, such as:
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		quoridor.addUser("TestUser1");
		quoridor.addUser("TestUser2");
		QuoridorController.initializeNewGame(quoridor);
		// Maybe other steps, dunno?
	}

	/**
	 * Method used to initiate the action of starting the clock
	 * 
	 * @throws Exception
	 * @author Tristan Bouchard
	 */
	@When("I start the clock")
	public void iStartTheClock() throws Exception {
		QuoridorController.startClock();
	}

	/**
	 * Query method to verify that the game is in a running state
	 * 
	 * @author Tristan Bouchard
	 */
	@Then("The game shall be running")
	public void theGameShallBeRunning() {
		assertEquals(Game.GameStatus.Running, QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus());
	}

	/**
	 * Verify here that the board is properly initialized
	 * 
	 * @author Tristan Bouchard
	 */
	@And("The board shall be initialized")
	public void theBoardShallBeInitialized() {
		Boolean success = QuoridorController.verifyBoardInitialization();
		if (!success) {
			fail();
		}
	}
}
