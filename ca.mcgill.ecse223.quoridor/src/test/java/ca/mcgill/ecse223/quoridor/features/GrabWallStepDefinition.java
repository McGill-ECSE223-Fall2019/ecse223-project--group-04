package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Wall;
import cucumber.api.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * This class implements all steps definition for grab wall feature.
 * 
 * @author Ousmane Baricisse
 * @throws Exception
 */

public class GrabWallStepDefinition {

	private static Player currentPlayer; // keeps track of current player
	private static Player nextPlayer; // next player
	private static int curPlayerWallCount; // the number of walls in stock for current player
	private static Game game = QuoridorApplication.getQuoridor().getCurrentGame(); // current game state

	/**
	 * Step definition to make sure that I have enough walls on my stack. If I have
	 * no walls, then add 10 walls in stock
	 * 
	 * @author Ousmane Baricisse
	 */
	@Given("I have more walls on stock")
	public void i_have_more_walls_on_stock() {
		// Write code here that turns the phrase above into concrete actions
		this.currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove(); // set
		// currentPlayer
		this.nextPlayer = currentPlayer.getNextPlayer(); // set next player as the other player
		this.curPlayerWallCount = this.currentPlayer.numberOfWalls();

		if (this.curPlayerWallCount == 0) {
			if (this.currentPlayer.toString().equalsIgnoreCase("black")) {
				for (int j = 0; j < 10; j++) {
					this.game.getCurrentPosition().addBlackWallsInStock(new Wall(j, this.currentPlayer));
				}

			} else {
				for (int j = 0; j < 10; j++) {
					this.game.getCurrentPosition().addBlackWallsInStock(new Wall(j, this.currentPlayer));
				}
			}

		}
	}

	/**
	 * Step definition to call controller methods for grab wall controller method is
	 * void
	 * 
	 * @return void
	 * @author Ousmane Baricisse
	 * @throws Exception
	 */
	
	@When("I try to grab a wall from my stock")
	public void i_try_to_grab_a_wall_from_my_stock(){
		// Write code here that turns the phrase above into concrete actions
		QuoridorController.grabWall(QuoridorApplication.getQuoridor().getBoard(), this.currentPlayer);

	}

	/**
	 * check if game has a wall move candidate
	 * 
	 * @author Ousmane Baricisse
	 */
	@Then("A wall move candidate shall be created at initial position")
	public void a_wall_move_candidate_shall_be_created_at_initial_position() {
		// Write code here that turns the phrase above into concrete actions
		boolean condition = this.game.hasWallMoveCandidate();
		assertTrue(condition);
	}

	/**
	 * did not implement this as it is related to the GUI
	 * 
	 * @author ousmanebaricisse
	 */
	@Then("The wall in my hand shall disappear from my stock")
	public void the_wall_in_my_hand_shall_disappear_from_my_stock() {
		// Write code here that turns the phrase above into concrete actions
		// This is more of a GUI
		throw new cucumber.api.PendingException();
	}

	/**
	 * Make sure the number of walls for current player is zero
	 * 
	 * @author Ousmane Baricisse
	 */

	@Given("I have no more walls on stock")
	public void i_have_no_more_walls_on_stock() {
		// Write code here that turns the phrase above into concrete actions
		this.currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove(); // set
		// currentPlayer
		this.nextPlayer = currentPlayer.getNextPlayer(); // set next player as the other player
		this.curPlayerWallCount = this.currentPlayer.numberOfWalls();

		if (this.curPlayerWallCount != 0) {
			// remove all walls in stock
			if (this.currentPlayer.toString().equalsIgnoreCase("white")) {
				if(this.game.getCurrentPosition().getBlackWallsInStock() != null) {
					this.game.getCurrentPosition().getBlackWallsInStock().clear();
				}
				
			} else {
				if(this.game.getCurrentPosition().getBlackWallsInStock() != null) {
					this.game.getCurrentPosition().getWhiteWallsInStock().clear();
				}
			}

		}
	}

	/**
	 * Assert if the number of player-walls is zero
	 * 
	 * @author ousmanebaricisse
	 */
	@Then("I shall be notified that I have no more walls")
	public void i_shall_be_notified_that_I_have_no_more_walls() {
		// Write code here that turns the phrase above into concrete actions
		Game game = QuoridorApplication.getQuoridor().getCurrentGame();
		Player curPlayer = game.getCurrentPosition().getPlayerToMove();

		if (curPlayer.equals(game.getWhitePlayer())) {
			int wall = game.getCurrentPosition().numberOfWhiteWallsInStock();
			assertEquals("You have no more walls in stock!", 0, wall);
		}
	}

	/**
	 * 
	 * Not implemented
	 * 
	 * @author ousmanebaricisse
	 */

	@Then("I shall have no walls in my hand")
	public void i_shall_have_no_walls_in_my_hand() {
		// Write code here that turns the phrase above into concrete actions
		// perhaps GUI

		throw new cucumber.api.PendingException();
	}

}
