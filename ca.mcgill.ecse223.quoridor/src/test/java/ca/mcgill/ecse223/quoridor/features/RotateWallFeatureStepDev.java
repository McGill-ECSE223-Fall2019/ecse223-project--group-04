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

public class RotateWallFeatureStepDev {
	/**
	 * Class used to define the step definitions related to the rotate wall feature
	 * @throws Exception
	 * @author Iyatan Atchoro
	 */
	
	/**
     * Method to check that the rotate wall method works
     * @param 
     */
	
	@When("I try to flip the wall")
	public void i_try_to_flip_the_wall() throws Exception {
		QuoridorController.rotateWall();
	}
	 /**
     * Method asserting whether it it properly rotated
     * @param string
     */

	@Then("The wall shall be rotated over the board to {string}")
	public void the_wall_shall_be_rotated_over_the_board_to(String string) {
		// Checking if the board wall is rotated over the board
		Quoridor q = QuoridorApplication.getQuoridor();
		assertEquals(string, q.getCurrentGame().getBlackPlayer().getWall(11).getMove().getWallDirection());	
	}
}
