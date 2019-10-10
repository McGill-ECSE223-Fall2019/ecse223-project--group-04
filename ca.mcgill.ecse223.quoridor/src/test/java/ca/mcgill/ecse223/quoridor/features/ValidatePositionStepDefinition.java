package ca.mcgill.ecse223.quoridor.features;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;
import cucumber.api.PendingException;
import ca.mcgill.ecse223.quoridor.controller.*;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * Class that encapsulates the ValidatePosition Gherkin feature
 * @author Alexander Legouverneur
 *
 */
public class ValidatePositionStepDefinition {
	private CucumberStepDefinitions csd = new CucumberStepDefinitions();

	//@Given - construct objects to match the scenario in the string.
	//@When - do what it says in the string (e.g. Move the pawn etc.) BY CONTROLLER METHOD 
	//@Then - get result of what I did before (the pawn) and make sure its at the right position.
	//Either use the QuoridorApplication.getQuoridor() to get the system and then call a whole bunch of methods until you arrive at the pawn
	// OR make a query method in your controler getPawn(Player) - getBlackPlayer()

	/**
	 * Methods that assigns the values of the position to the black player
	 * @param row
	 * @param col
	 * @author Alexander Legouverneur
	 */
	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void aGamePositionIsSuppliedWithPawnCoordinate(int row, int col) {
		// Reinitialize board and system
		csd.theGameIsRunning();

		// Access the system
		Quoridor q = QuoridorApplication.getQuoridor();
		Tile myTile = new Tile(row, col, q.getBoard());


		//set the position
		q.getCurrentGame().getCurrentPosition().getBlackPosition().setTile(myTile);
	}

	/**
	 * Methods that initiates the validation of the position
	 * @author Alexander Legouverneur
	 */
	@When("Validation of the position is initiated")
	public void ValidationOfThePositionIsInitiated() {
		Quoridor q = QuoridorApplication.getQuoridor();
		QuoridorController.InitializeValidatePosition(q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow(),q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn()); 
	}

	/**
	 * 
	 * @param result
	 */
	@Then("The result is {string}")
	public void validatePositionResult(String result) {
		Quoridor q = QuoridorApplication.getQuoridor();
		assertEquals(result, QuoridorController.ValidatePawnPosition(q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow(), q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn()));
		assertEquals(result, QuoridorController.ValidatePawnPosition(q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow(), q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow()));
	}

	/**
	 * Method to check that position of wall is supplied with direction and coordinates
	 * @author Alexander Legouverneur
	 */
	@Given("A game position is supplied with wall coordinate {int}:{int}-{dir}")
	public void aGamePositionIsSuppliedWithWallCoordinateAndDir(int row, int col, Direction dir) {
		// Reinitialize board and system
		csd.theGameIsRunning();

		// Access the system
		Quoridor q = QuoridorApplication.getQuoridor();
		Tile myTile = new Tile(row, col, q.getBoard());

		//Assign the position and direction to the wall
		Tile aTile = new Tile(row, col, q.getBoard());
		Wall aWall = new Wall(11, q.getCurrentGame().getBlackPlayer());
		WallMove Move = new WallMove( 1, 1, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame(), dir, aWall);
		aWall.setMove(Move);
	}

	/**
	 * Method that initiates the validation of the position
	 * @author Alexander Legouverneur
	 */
	@When("Validation of the position is initiated (Wall)")
	public void ValidationOfWallPositionInitiated() {
		Quoridor q = QuoridorApplication.getQuoridor();
		QuoridorController.InitiatePosValidation(q.getCurrentGame().getBlackPlayer().getWall(11).getMove().getTargetTile(), q.getCurrentGame().getBlackPlayer().getWall(11).getMove().getWallDirection());
		
	}

	/**
	 * Method that verifies if the position of the wall is returned in result
	 * @author Alexander Legouverneur
	 */
	@Then("The position shall be \"<result>\"(Wall)")
	public void TheWallPositionShallBeResult(int row, int col, Direction result) {
		Quoridor q = QuoridorApplication.getQuoridor();
		assertEquals(result,QuoridorController.ValidateWallDirection(q.getCurrentGame().getBlackPlayer().getWall(11)));
		Tile r = QuoridorController.ValidateWallTile(row,col);
		assertEquals(row, r.getRow());
		assertEquals(row, r.getColumn());
	}

	/**
	 * Method that checks if the wall exist
	 * @author Alexander Legouverneur
	 */
	@Given("The following walls exist:")
	public void ThefollowingWallsExist() {
		//call controller methods
	}

	/**
	 * Method that verifies if the position of the wall is valid
	 * @author Alexander Legouverneur
	 */
	@Then("The position shall be valid")
	public void ThePositionShallBeValid() {
		//call controller methods
	}

	/**
	 * Method that verifies if the position is invalid
	 * @author Alexander Legouverneur
	 */
	@Then("The position shall be invalid")
	public void ThePositionShallBeInvalid() {
		///call controller methods
	}
}