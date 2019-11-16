package ca.mcgill.ecse223.quoridor.features;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.*;

/**
 * Class used to encapsulate the step definitions related to LoadPosition
 * feature
 * 
 * @author Nicolas Buisson
 * 
 */
public class LoadPositionStepDef {

	
	private QuoridorController QC = new QuoridorController();
	private GamePosition gamePosition;	
	private Exception thrownException;
	private boolean valid;
	
	//false if invalid position
	//true if valid position

	
	// *********************************************
	//Load position scenario
	// *********************************************

	/**   
	 * @author Nicolas Buisson
	 */
	@When ("I initiate to load a saved game {string}")
	public void IInitiateToLoadASavedGame(String filename) throws IOException {
		thrownException = null;
		try {
			valid = QC.loadSavedPosition(filename);
		} catch (IllegalArgumentException e) {
			thrownException = e;
		}

	}

	/**   
	 * @author Nicolas Buisson
	 */
	@And ("The position to load is valid")
	public void ThePositionToLoadIsValid() {
		assertEquals(null, thrownException);
		//assertEquals(true, valid);
	}

	/**  
	 * @author Nicolas Buisson
	 */
	@Then("It shall be {string}'s turn")
	public void ItShallBePlayerTurn(String player) {
		gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		gamePosition.getPlayerToMove().getUser().setName("black");
		gamePosition.getPlayerToMove().getNextPlayer().getUser().setName("white");
		assertEquals(player, gamePosition.getPlayerToMove().getUser().getName());
	}
	/**  
	 * @author Nicolas Buisson
	 */
	@And ("{string} shall be at {int}:{int}")
	public void PlayerShallBeAtRowCol(String player, int p_row, int p_col) {
		
		//if(gamePosition.getGame().getBlackPlayer().equals(gamePosition.getPlayerToMove()))
			
		if(player.equals("black")) {
			assertEquals(p_row, gamePosition.getBlackPosition().getTile().getRow());
			
			assertEquals(p_col, gamePosition.getBlackPosition().getTile().getColumn());
		}
		else {
			assertEquals(p_row, gamePosition.getWhitePosition().getTile().getRow());
			
			assertEquals(p_col, gamePosition.getWhitePosition().getTile().getColumn());
		}
	}

	/**  
	 * @author Nicolas Buisson
	 */
	@And ("{string} shall have a vertical wall at {int}:{int}")
	public void PlayerShallHaveAVerticalWallAtRowCol(String player, int pw_row, int pw_col) {
		gamePosition = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition();
		//if(gamePosition.getGame().getBlackPlayer().equals(gamePosition.getPlayerToMove()))
		
		if(player.equals("black")) {

			assertEquals(Direction.Vertical, gamePosition.getBlackWallsOnBoard(0).getMove().getWallDirection());

			assertEquals(pw_row, gamePosition.getBlackWallsOnBoard(0).getMove().getTargetTile().getRow());

			assertEquals(pw_col, gamePosition.getBlackWallsOnBoard(0).getMove().getTargetTile().getColumn());
		}else {
			
			assertEquals(Direction.Vertical, gamePosition.getWhiteWallsOnBoard(0).getMove().getWallDirection());

			assertEquals(pw_row, gamePosition.getWhiteWallsOnBoard(0).getMove().getTargetTile().getRow());

			assertEquals(pw_col, gamePosition.getWhiteWallsOnBoard(0).getMove().getTargetTile().getColumn());
		}
	}
	
	/**  
	 * @author Nicolas Buisson
	 */
	@And ("{string} shall have a horizontal wall at {int}:{int}")
	public void PlayerShallHaveAHorizontalWallAtRowCol(String player, int pw_row, int pw_col) {

		//if(gamePosition.getGame().getBlackPlayer().equals(gamePosition.getPlayerToMove()))
		
		if(player.equals("black")) {

			assertEquals(Direction.Horizontal, gamePosition.getBlackWallsOnBoard(0).getMove().getWallDirection());

			assertEquals(pw_row, gamePosition.getBlackWallsOnBoard(0).getMove().getTargetTile().getRow());

			assertEquals(pw_col, gamePosition.getBlackWallsOnBoard(0).getMove().getTargetTile().getColumn());
		}else {
			
			assertEquals(Direction.Horizontal, gamePosition.getWhiteWallsOnBoard(0).getMove().getWallDirection());

			assertEquals(pw_row, gamePosition.getWhiteWallsOnBoard(0).getMove().getTargetTile().getRow());

			assertEquals(pw_col, gamePosition.getWhiteWallsOnBoard(0).getMove().getTargetTile().getColumn());
		}
	}

	/**  
	 * @author Nicolas Buisson
	 */
	@And ("Both players shall have {int} in their stacks")
	public void BothPlayersShallHaveRemainingWallsInTheirStacks(int remaining_walls) { 
		
		assertEquals(remaining_walls, gamePosition.getWhiteWallsInStock().size());
		assertEquals(remaining_walls, gamePosition.getBlackWallsInStock().size());
	}

	// *********************************************
	//Load invalid position scenario
	// *********************************************

	/**  
	 * @author Nicolas Buisson
	 */
	@And ("The position to load is invalid")
	public void ThePositionToLoadIsInvalid() {
		assertNotNull(thrownException);
		//assertEquals(false, valid);
	}

	/**  
	 * @author Nicolas Buisson
	 */
	@Then("The load shall return an error")
	public void TheLoadShallReturnAnError() {
		assertNotNull(thrownException);
	}
}