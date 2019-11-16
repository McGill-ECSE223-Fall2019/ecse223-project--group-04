package ca.mcgill.ecse223.quoridor.features;

import static org.junit.Assert.assertEquals;

import java.util.List;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.model.Wall;
import ca.mcgill.ecse223.quoridor.model.WallMove;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Class that encapsulates the ValidatePosition Gherkin feature
 * @author Alexander Legouverneur
 *
 */
public class ValidatePositionStepDefinition {



	private int providedRow;
	private int providedColumn;
	private String providedDirection;
	private int providedRow1;
	private int providedColumn1;
	private String providedDirection1;


	/**
	 * Methods that assigns the values of the position to the black player
	 * @param row row of the tile where the pawn is
	 * @param col Column of the tile where the pawn is
	 * @author Alexander Legouverneur
	 */
	@Given("A game position is supplied with pawn coordinate {int}:{int}")
	public void a_game_position_is_supplied_with_pawn_coordinate(int int1, int int2) {
		providedRow = int1;
		providedColumn = int2;
	}

	/**
	 * Methods that initiates the validation of the position
	 * @author Alexander Legouverneur
	 */
	@When("Validation of the position is initiated")
	public void ValidationOfThePositionIsInitiated() {
		Quoridor q = QuoridorApplication.getQuoridor();
		//if there are no walls placed on the board then call the  method initialize validation for pawns
		//else call the method initialize validation for wall

		if(q.getCurrentGame().getCurrentPosition().getBlackPosition()== null) {
			if(q.getCurrentGame().getCurrentPosition().numberOfWhiteWallsOnBoard()>1) {
				QuoridorController.initiatePosValidation(providedRow,providedColumn, providedDirection,0);
				QuoridorController.initiatePosValidation(providedRow1,providedColumn1, providedDirection1,1);
			}
			else {
				QuoridorController.initiatePosValidation(providedRow,providedColumn, providedDirection,0);
			}

		}
		else {

			QuoridorController.initializeValidatePosition(providedRow,providedColumn);
		}

	}

	/**
	 * Verifies if the result is equal to what it should be
	 * @param result  string to compare to the result of the method called
	 */
	@Then("The result is {string}")
	public void validatePositionResult(String result) {
		assertEquals(result, QuoridorController.validatePawnPosition(providedRow, providedColumn));
	}

	/**
	 * Method that gets the coordinate and direction of wall
	 * @author Alexander Legouverneur
	 */
	@Given("A game position is supplied with wall coordinate {int}:{int}-{string}")
	public void aGamePositionIsSuppliedWithWallCoordinateAndDir(Integer row, Integer col, String dir) {



		//Assign the position and direction to the wall
		providedRow = row;
		providedColumn = col;
		providedDirection = dir;
	}

	/**
	 * Method that verifies if the position of the wall is returned in result
	 * @author Alexander Legouverneur
	 */
	@Then("The position shall be {string}")
	public void TheWallPositionShallBeResult(String result) {
		Quoridor q = QuoridorApplication.getQuoridor();
		if(q.getCurrentGame().getCurrentPosition().getBlackPosition()== null) {
			assertEquals(result,QuoridorController.validateWall(providedRow, providedColumn, providedDirection));
		}

	}

	/**
	 * Method that verifies if the position of the wall is valid
	 * @author Alexander Legouverneur
	 */
	@Then("The position shall be valid")
	public void ThePositionShallBeValid() {

		Quoridor q = QuoridorApplication.getQuoridor();
		providedRow = q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getTargetTile().getRow();
		providedColumn = q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getTargetTile().getColumn();
		if(q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getWallDirection() == Direction.Horizontal) {
			providedDirection = "horizontal";
		}
		if(q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getWallDirection() == Direction.Vertical) {
			providedDirection = "vertical";
		}
		providedRow1 = q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getTargetTile().getRow();
		providedColumn1 = q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getTargetTile().getColumn();
		if(q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getWallDirection() == Direction.Horizontal) {
			providedDirection1 = "horizontal";
		}
		if(q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getWallDirection() == Direction.Vertical) {
			providedDirection1 = "vertical";
		}
		//QuoridorController.checkWallValid(providedRow,providedColumn, providedDirection,q.getCurrentGame().getWhitePlayer().getWall(0));
		assertEquals(true,QuoridorController.checkWallValid(providedRow,providedColumn, providedDirection,q.getCurrentGame().getWhitePlayer().getWall(0)));
		assertEquals(true,QuoridorController.checkWallValid(providedRow1,providedColumn1, providedDirection1,q.getCurrentGame().getBlackPlayer().getWall(0)));
	}

	/**
	 * Method that verifies if the position is invalid
	 * @author Alexander Legouverneur
	 */
	@Then("The position shall be invalid")
	public void ThePositionShallBeInvalid2() {
		Quoridor q = QuoridorApplication.getQuoridor();
		providedRow = q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getTargetTile().getRow();
		providedColumn = q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getTargetTile().getColumn();
		if(q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getWallDirection() == Direction.Horizontal) {
			providedDirection = "horizontal";
		}
		if(q.getCurrentGame().getWhitePlayer().getWall(0).getMove().getWallDirection() == Direction.Vertical) {
			providedDirection = "vertical";
		}
		providedRow1 = q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getTargetTile().getRow();
		providedColumn1 = q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getTargetTile().getColumn();
		if(q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getWallDirection() == Direction.Horizontal) {
			providedDirection1 = "horizontal";
		}
		if(q.getCurrentGame().getBlackPlayer().getWall(0).getMove().getWallDirection() == Direction.Vertical) {
			providedDirection1 = "vertical";
		}
		assertEquals(false,QuoridorController.checkWallValid(providedRow,providedColumn, providedDirection,q.getCurrentGame().getBlackPlayer().getWall(0)));
		assertEquals(false,QuoridorController.checkWallValid(providedRow1,providedColumn1, providedDirection1,q.getCurrentGame().getBlackPlayer().getWall(1)));
	}
}