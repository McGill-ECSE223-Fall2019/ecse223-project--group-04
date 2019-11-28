package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import ca.mcgill.ecse223.quoridor.utilities.ControllerUtilities.PathAvailableToPlayers;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;

public class CheckIfPathExistsStepDef {
	
	// Private variables for passing values over between tests
	private PathAvailableToPlayers paths;
	
	// Methods:
	
	/**
	 * Method used to ensure that a wall move exists at a certain position
	 * @param wallDir - Direction of the wall move candidate
	 * @param row - row of the wall move candidate
	 * @param col - column of the wall move candidate
	 * @author Tristan Bouchard
	 */
	@Given("A {string} wall move candidate exists at position {int}:{int}")
	public void givenHorizontalWallMoveCandidateExistsAtPosition(String wallDir, Integer row, Integer col) {
		Game currentGame = QuoridorApplication.getQuoridor().getCurrentGame();
		// Create a new move?
		Direction dir;
		if(wallDir == "horizontal") {
			dir = Direction.Horizontal;
		} else {
			dir = Direction.Vertical;
		}
		// Get next wall available for the current player
		//QuoridorController.getCurrentPlayer().
		//QuoridorController.
		//WallMove moveCandidate = new WallMove(row, col, QuoridorController.getCurrentPlayer(), dir,)
		//currentGame.addMove(aMove)
	}
	
	/**
	 * Method used to ensure that the black player is located at a specified position
	 * @param brow - Black player position row
	 * @param bcol - Black player position column
	 * @author Tristan Bouchard
	 */
	@Given("The black player is located at {int}:{int}")
	public void givenBlackPlayerIsLocated(Integer brow, Integer bcol) {
		Tile playerTile = QuoridorController.getTileAtRowCol(brow, bcol);
		PlayerPosition pos = new PlayerPosition(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer(), playerTile);
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setBlackPosition(pos);
	}
	
	/**
	 * Method used to ensure that the white player is located at a specified position
	 * @param wrow - White player position row
	 * @param wcol - White player position column
	 * @author Tristan Bouchard
	 */
	@Given("The white player is located at {int}:{int}")
	public void givenWhitePlayerIsLocated(Integer wrow, Integer wcol) {
		Tile playerTile = QuoridorController.getTileAtRowCol(wrow, wcol);
		PlayerPosition pos = new PlayerPosition(QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer(), playerTile);
		QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().setWhitePosition(pos);
	}
	
	/**
	 * Method used to initiate the path checking algorithm
	 * @author Tristan Bouchard
	 */
	@When("Check path existence is initiated")
	public void initiateCheckPathExistence() {
		this.paths = QuoridorController.checkIfPathExists();
	}
	
	/**
	 * Method used to verify that the path is available for the expected players
	 * @param result - Path available to players
	 * @author Tristan Bouchard
	 */
	@Then("Path is available for {string} player")
	public void pathIsAvailableForBothPlayers(String result) {
		assertEquals(this.paths.toString(), result);
	}

	
}
