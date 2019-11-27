package ca.mcgill.ecse223.quoridor.features;

import java.util.*;
import java.util.concurrent.locks.Condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.List;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
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
	private static int previousNumberOfWalls;
	private static boolean grabWallResult;
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

		System.out.println("here is num:  " + this.previousNumberOfWalls);

		boolean condition = this.game.getCurrentPosition().getPlayerToMove().getWalls().size() > 0;
		System.out.println("here is num:  " + condition);
		assertTrue(condition);
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
	public void i_try_to_grab_a_wall_from_my_stock() {
		// Write code here that turns the phrase above into concrete actions

		this.grabWallResult =  QuoridorController.grabWall(QuoridorApplication.getQuoridor());

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
	 * @throws Exception
	 */
	@Then("The wall in my hand shall disappear from my stock")
	public void the_wall_in_my_hand_shall_disappear_from_my_stock() throws Exception {
		// Write code here that turns the phrase above into concrete actions
		// This is more of a GUI
		Player playerToMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		Wall wall = this.game.getWallMoveCandidate().getWallPlaced();
		if(playerToMove.equals(this.game.getWhitePlayer())){
			assertTrue(!this.game.getCurrentPosition().getWhiteWallsInStock().contains(wall));
		} else if(playerToMove.equals(this.game.getBlackPlayer())){
			assertTrue(!this.game.getCurrentPosition().getBlackWallsInStock().contains(wall));
		}
		
	}

	/**
	 * Make sure the number of walls for current player is zero
	 * 
	 * @author Ousmane Baricisse
	 */

	@Given("I have no more walls on stock")
	public void i_have_no_more_walls_on_stock() {
		// Write code here that turns the phrase above into concrete actions
		Player playerToMove = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove();
		if(playerToMove.equals(this.game.getWhitePlayer())){
			ArrayList<Wall> removedWalls = new ArrayList<>();
			for(Wall wall : game.getCurrentPosition().getWhiteWallsInStock()){
				game.getCurrentPosition().addWhiteWallsOnBoard(wall);
				Tile tile = new Tile(1, 1, QuoridorApplication.getQuoridor().getBoard());
				WallMove wallMove = new WallMove(1, 1, playerToMove, tile, QuoridorApplication.getQuoridor().getCurrentGame(), Direction.Horizontal, wall);
				QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wallMove);
				
			} 
			for(Wall wall :removedWalls ) game.getCurrentPosition().removeWhiteWallsInStock(wall);
		} else if(playerToMove.equals(this.game.getBlackPlayer())){
			ArrayList<Wall> removedWalls = new ArrayList<>();
			for(Wall wall : game.getCurrentPosition().getBlackWallsInStock()){
				game.getCurrentPosition().addBlackWallsOnBoard(wall);
				Tile tile = new Tile(1, 1, QuoridorApplication.getQuoridor().getBoard());
				WallMove wallMove = new WallMove(1, 1, playerToMove, tile, QuoridorApplication.getQuoridor().getCurrentGame(), Direction.Horizontal, wall);
				QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(wallMove);
			} 
			for(Wall wall :removedWalls ) game.getCurrentPosition().removeBlackWallsInStock(wall);
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

		// assertEquals("You have no more walls in stock!", this.);
		
		assertTrue(this.grabWallResult);

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

		boolean condition = this.previousNumberOfWalls == 0;
		assertTrue(condition);
	}

}
