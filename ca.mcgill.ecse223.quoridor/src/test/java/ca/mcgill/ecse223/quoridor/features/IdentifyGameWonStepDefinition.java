package ca.mcgill.ecse223.quoridor.features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.PlayerPosition;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.utilities.ControllerUtilities;
import cucumber.api.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Given;

public class IdentifyGameWonStepDefinition {

	private Player player;
	private int currRow;
	private int currCol;
	
	@Given("Player {string} has just completed his move")
	public void player_has_just_completed_his_move(String string) {
		
		Quoridor q = QuoridorApplication.getQuoridor();
		
	    if(string.equals("black")) {
	    	player = q.getCurrentGame().getBlackPlayer();
	    }
	    else{
	    	player = q.getCurrentGame().getWhitePlayer();
	    }
	    
	}
	
	@Given("The new position of {string} is {int}:{int}")
	public void the_new_position_of_is(String string, Integer int1, Integer int2) {
		Quoridor q = QuoridorApplication.getQuoridor();
		Tile aTile = new Tile(int1, int2, q.getBoard());
		PlayerPosition pos = new PlayerPosition(player,aTile);
		
		if(string.equals("black")) {
			q.getCurrentGame().getCurrentPosition().setBlackPosition(pos);
		}
		else {
			q.getCurrentGame().getCurrentPosition().setWhitePosition(pos);
		}
		
		
	}
	
	@Given("The clock of {string} is more than zero")
	public void the_clock_of_is_more_than_zero(String string) {
	    
	}
	
	@When("Checking of game result is initated")
	public void checking_of_game_result_is_initated() {
		Quoridor q = QuoridorApplication.getQuoridor();
		if(player.equals(q.getCurrentGame().getBlackPlayer())) {
			currRow = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow();
			currCol = q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn();
			//stategame = QuoridorController.checkIfWon(player,row, col);
		}
		else {
			currRow = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow();
			currCol = q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn();
			//stategame = QuoridorController.checkIfWon(player,row, col);
		}
	    
	}
	
	@Then("Game result shall be {string}")
	public void game_result_shall_be(String string) {
		Quoridor q = QuoridorApplication.getQuoridor();
		boolean status = QuoridorController.checkIfWon(player,currRow, currCol);
	    if(status == true && player.equals(q.getCurrentGame().getBlackPlayer())) {
	    	assertEquals(string, "blackWon");
	    }
	    if(status == true && player.equals(q.getCurrentGame().getWhitePlayer())) {
	    	assertEquals(string, "whiteWon");
	    }
	    if(status == false){
	    	assertEquals(string, "pending");
	    }
	}
	
	@Then("The game shall no longer be running")
	public void the_game_shall_no_longer_be_running() {
	   
	}
	
	@When("The clock of {string} counts down to zero")
	public void the_clock_of_counts_down_to_zero(String string) {
	   
	}
}
