package ca.mcgill.ecse223.quoridor.features;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


/**
 * Class that encapsulates all the MoveWall Gherkin features
 * @author Alexander
 *
 */
public class MoveWallStepDefinition {

	private int Prow;
	private int Pcol;
	private String Pdir;
	private WallMove aMove;
	private Wall aWall;
	/**
	 * Method that verifies if a wall candidate exists with direction and coordinates
	 * @author Alexander Legouverneur
	 */
	@Given("A wall move candidate exists with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_exists_with_at_position(String string, int int1, int int2) {
		
		//access the system
		Quoridor q = QuoridorApplication.getQuoridor();

		// Find tile at position
		Tile aTile = null;
		for(Tile t : q.getBoard().getTiles()){
			if(t.getRow() == int1 && t.getColumn() == int2) aTile = t;
			else continue;
		}
		if(aTile==null) fail("ERROR NO TILE WITH THESE COORDINATES");

		// Get some wall for the black player.
		aWall = q.getCurrentGame().getWhitePlayer().getWall(0);

		if(string.equals("vertical")) {
			aMove = new WallMove( 1, 1, q.getCurrentGame().getWhitePlayer(), aTile, q.getCurrentGame(), Direction.Vertical, aWall);
		}
		if(string.equals("horizontal")) {
			aMove = new WallMove( 1, 1, q.getCurrentGame().getWhitePlayer(), aTile, q.getCurrentGame(), Direction.Horizontal, aWall);
		}
		Prow = aTile.getRow();
		Pcol = aTile.getColumn();
		Pdir = string;
	}

	/**
	 * Method that verifies that the wall is not at the side edge of the board
	 * @author Alexander Legouverneur
	 */
	@And("The wall candidate is not at the {string} edge of the board")
	public void theWallCandidateIsNotAtTheSideEdgeOfTheBoard(String side) {
		//access the system
		Quoridor q = QuoridorApplication.getQuoridor();	

		assertEquals(false,QuoridorController.checkWallSideEdge( q.getCurrentGame().getWhitePlayer().getWall(0), side));




	}

	/**
	 * Method that initiates the trial for the move of the wall on the side
	 * @author Alexander Legouverneur
	 */
	@When("I try to move the wall {string}")
	public void iTryToMoveTheWallSide(String side) {
		//access system
		QuoridorController.verifyMoveWallOnSide(aWall, side, 0);
	}

	/**
	 * Method that initiates the movement of the wall to the new position
	 * @param row
	 * @param col
	 * @author Alexander Legouverneur
	 */
	@Then("The wall shall be moved over the board to position \\({int}, {int})")
	public void the_wall_shall_be_moved_over_the_board_to_position(int int1, int int2) {
		Quoridor q = QuoridorApplication.getQuoridor();	

		assertEquals(int1, q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getTargetTile().getRow());
		assertEquals(int2, q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getTargetTile().getColumn());

	}
	/**
	 * Method that checks if the wall was correctly moved over the board position by
	 * checking the coordinates
	 * @author Alexander Legouverneur
	 */
	@And("A wall move candidate shall exist with {string} at position \\({int}, {int})")
	public void a_wall_move_candidate_shall_exist_with_at_position(String string, int int1, int int2) {

		Quoridor q = QuoridorApplication.getQuoridor();	
		assertEquals(int1,q.getCurrentGame().getWallMoveCandidate().getWallPlaced().getMove().getTargetTile().getRow());
		assertEquals(int2, q.getCurrentGame().getWallMoveCandidate().getWallPlaced().getMove().getTargetTile().getColumn());
		if(string.equals("vertical")) {
			assertEquals(Direction.Vertical, q.getCurrentGame().getWallMoveCandidate().getWallPlaced().getMove().getWallDirection());
		}
		else {
			assertEquals(Direction.Horizontal, q.getCurrentGame().getWallMoveCandidate().getWallPlaced().getMove().getWallDirection());
		}
	}

	/**
	 * Method that verifies that the wall is at the side edge of the board
	 * @author Alexander Legouverneur
	 */
	@And("The wall candidate is at the {string} edge of the board")
	public void theWallCandidateIsAtTheEdge1(String side) {
		Quoridor q = QuoridorApplication.getQuoridor();	

		QuoridorController.checkWallSideEdge(q.getCurrentGame().getWhitePlayer().getWall(0), side);




	}

	/**
	 * Method that verifies if user is notified when trying to do an illegal move
	 * @author Alexander Legouverneur
	 */
	@Then("I shall be notified that my move is illegal")
	public void iShallBeNotifiedThatMyMoveIsIllegal() {
		//access system
		String aString = new String("Illegal");
		assertEquals(aString, QuoridorController.illegalWallMove());			
	}
}
