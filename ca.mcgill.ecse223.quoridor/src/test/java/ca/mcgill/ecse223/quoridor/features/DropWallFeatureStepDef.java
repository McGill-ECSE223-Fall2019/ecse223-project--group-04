package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DropWallFeatureStepDef {
	/**
	 * Class used to define the step definitions related to the drop wall feature
	 * 
	 * @throws Exception
	 * @author Iyatan Atchoro
	 */
	private CucumberStepDefinitions csd = new CucumberStepDefinitions();
	
	
	/**
     * Method to see if the wall move candidate is with the right direction , row and column
     * @param string, int, int
     */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is valid")
	public void the_wall_move_candidate_with_at_position_is_valid(String string, int int1, int int2) {
		Quoridor q = QuoridorApplication.getQuoridor();

		// Find tile at position
		Tile aTile = null;
		for (Tile t : q.getBoard().getTiles()) {
			if (t.getRow() == int1 && t.getColumn() == int2)
				aTile = t;
		}
		if (aTile == null)
			fail("Error initializing board. No tile found with coordinates.");

		// Get some wall for the black player.
		Wall aWall = q.getCurrentGame().getBlackPlayer().getWall(0);

		if (string == "vertical") {
			WallMove aMove = new WallMove(1, 1, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame(),
					Direction.Vertical, aWall);
			aWall.setMove(aMove);
		}
		if (string == "horizontal") {
			WallMove aMove = new WallMove(2, 1, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame(),
					Direction.Horizontal, aWall);
			aWall.setMove(aMove);
		}

	}
	
	/**
     * Testing the dropWall method
     * @param
	 * @throws Exception 
     */

	@When("I release the wall in my hand")
	public void i_release_the_wall_in_my_hand() throws Exception {
		QuoridorController.dropWall();
	}
	
	/**
     * Method to see if the wall move is registered with the right direction , row and column
     * @param string, int, int
     */

	@Then("A wall move shall be registered with {string} at position \\({int}, {int})")
	public void a_wall_move_shall_be_registered_with_at_position(String string, int int1, int int2) {
		// Checking if the wall was properly dropped at the right place
		Quoridor q = QuoridorApplication.getQuoridor();
		assertEquals(string, q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getWallDirection().toString().toLowerCase());
//		assertEquals(q.getCurrentGame().getGameStatus(),"Running");

	}


	/**
     * Method to assert if my moves are completed
     * @param 
     */
	
	@Then("My move shall be completed")
	public void my_move_shall_be_completed() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		assertEquals(quoridor.getCurrentGame().getGameStatus(),"completed");

	}
	/**
     * Method to scheck if it is not my turn to move
     * @param 
     */

	@Then("It shall not be my turn to move")
	public void it_shall_not_be_my_turn_to_move() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		assertNotEquals(currentPlayer, quoridor.getCurrentGame().getMove(11).getPlayer());
	}
	/**
     * Method to the invalidity of the wall move candidate
     * @param string, int, int
     */

	@Given("The wall move candidate with {string} at position \\({int}, {int}) is invalid")
	public void the_wall_move_candidate_with_at_position_is_invalid(String string, Integer int1, Integer int2) {
Quoridor q = QuoridorApplication.getQuoridor();
		
		// Find tile at position
		Tile aTile = null;
		for(Tile t : q.getBoard().getTiles()){
			if(t.getRow() == int1 && t.getColumn() == int2) aTile = t;
		}
		if(aTile==null) fail("Error initializing board. No tile found with coordinates.");
		
		Wall aWall = q.getCurrentGame().getBlackPlayer().getWall(0);
		
		if(string == "horizontal") {
			WallMove aMove = new WallMove( 1, 1, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame(), Direction.Vertical, aWall);
			aWall.setMove(aMove);
		}
		if(string == "vertical") {
			WallMove aMove = new WallMove( 2, 1, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame(), Direction.Horizontal, aWall);
			aWall.setMove(aMove);
		}

	}
	/**
     * Method to assert if we are notivied of the previous method
     * @param string, int, int
     */

	@Then("I shall be notified that my wall move is invalid")
	public void i_shall_be_notified_that_my_wall_move_is_invalid() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		assertEquals(quoridor.getCurrentGame().getMove(11),"invalid");

	}
	
	/**
     * Method to check if we have more wall in ones hand
     * @param 
     */

	@Then("I shall have a wall in my hand over the board")
	public void i_shall_have_a_wall_in_my_hand_over_the_board() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		assertNotEquals(currentPlayer.getWalls(), 0);

	}
	
	/**
     * Method to check if it is my turn to move
     * @param string, int, int
     */

	@Then("It shall be my turn to move")
	public void it_shall_be_my_turn_to_move() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		Player currentPlayer = quoridor.getCurrentGame().getWhitePlayer();
		assertEquals(currentPlayer, quoridor.getCurrentGame().getPosition(11).getPlayerToMove());
	}
	/**
     * Method to see that the wall move if not registered with the given
     * @param string, int, int
     */

	@Then("No wall move shall be registered with {string} at position \\({int}, {int})")
	public void no_wall_move_shall_be_registered_with_at_position(String string, Integer int1, Integer int2) {
		Quoridor q = QuoridorApplication.getQuoridor();
		assertEquals(string, q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getWallDirection());
		

	}
	@Then("I shall not have a wall in my hand")
	public void i_shall_not_have_a_wall_in_my_hand() {
		Quoridor q = QuoridorApplication.getQuoridor();
		assertNotEquals(q.getCurrentGame().getBlackPlayer().getWalls().size(), 0);
	    
	}

}
