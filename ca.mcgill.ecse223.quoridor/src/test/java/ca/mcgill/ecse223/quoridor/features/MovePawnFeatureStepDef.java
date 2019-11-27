package ca.mcgill.ecse223.quoridor.features;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import ca.mcgill.ecse223.quoridor.*;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.*;


/**
 * Class used to encapsulate the step definitions related to LoadPosition
 * feature
 * 
 * @author Nicolas Buisson
 * 
 */

public class MovePawnFeatureStepDef {

	private QuoridorController QC = new QuoridorController();
	private Quoridor q = QuoridorApplication.getQuoridor();
	private String statusOfMove = "";
	private boolean legalMove = false;

	@Given("The player is located at {int}:{int}")
	public void the_player_is_located_at(Integer int1, Integer int2) {
		
		// If current player is black player, set position
		if(QuoridorController.getCurrentPlayer().equals(QuoridorController.getBlackPlayer())) {
			Tile playerTile = QuoridorController.getTileAtRowCol(int1, int2);
			PlayerPosition playerPos = new PlayerPosition(q.getCurrentGame().getBlackPlayer(), playerTile);
			q.getCurrentGame().getCurrentPosition().setBlackPosition(playerPos);
		}else {
			Tile playerTile = QuoridorController.getTileAtRowCol(int1, int2);
			PlayerPosition playerPos = new PlayerPosition(q.getCurrentGame().getBlackPlayer(), playerTile);
			q.getCurrentGame().getCurrentPosition().setWhitePosition(playerPos);
		}
	}

	@Given("The opponent is located at {int}:{int}")
	public void the_opponent_is_located_at(Integer int1, Integer int2) {

		// If current player is white player, set position of black player
		if(QuoridorController.getCurrentPlayer().equals(QuoridorController.getWhitePlayer())) {
			Tile playerTile = QuoridorController.getTileAtRowCol(int1, int2);
			PlayerPosition opponentPos = new PlayerPosition(q.getCurrentGame().getBlackPlayer(), playerTile);
			q.getCurrentGame().getCurrentPosition().setBlackPosition(opponentPos);
		}else {
			Tile playerTile = QuoridorController.getTileAtRowCol(int1, int2);
			PlayerPosition opponentPos = new PlayerPosition(q.getCurrentGame().getWhitePlayer(), playerTile);
			q.getCurrentGame().getCurrentPosition().setWhitePosition(opponentPos);
		}
	}

	@Given("There are no {string} walls {string} from the player")
	public void there_are_no_walls_from_the_player(String string, String string2) {
		//do not place any walls
	}

	@Given("The opponent is not {string} from the player")
	public void The_opponent_is_not_from_the_player(String string) {

	}


	@When("Player {string} initiates to move {string}")
	public void player_initiates_to_move(String string, String string2) {
		Player player;
		if(string.equals("black")) {
			player = q.getCurrentGame().getBlackPlayer();
		}else {
			player = q.getCurrentGame().getWhitePlayer();
		}
		try {
			legalMove = QuoridorController.movePawn(player, string2);
		}catch(IllegalArgumentException e){
			legalMove = false;
		}
	}

	@Then("The move {string} shall be {string}")
	public void the_move_shall_be(String string, String string2) {
		if(legalMove) {
			statusOfMove = "success";
		}else {
			statusOfMove = "illegal";
		}
		assertEquals(string2, statusOfMove);
	}

	@Then("Player's new position shall be {int}:{int}")
	public void player_s_new_position_shall_be(Integer int1, Integer int2) {
		
		// Player has switched after a valid move, will not switch after an invalid move
		if(QuoridorController.getCurrentPlayer().equals(QuoridorController.getBlackPlayer())) {
			assertEquals(int1, (Integer)QuoridorController.getBlackPlayer().getGameAsBlack().getCurrentPosition().getBlackPosition().getTile().getRow());
			assertEquals(int2, (Integer)QuoridorController.getBlackPlayer().getGameAsBlack().getCurrentPosition().getBlackPosition().getTile().getColumn());
		}else {
			assertEquals(int1, (Integer)QuoridorController.getWhitePlayer().getGameAsWhite().getCurrentPosition().getWhitePosition().getTile().getRow());
			assertEquals(int2, (Integer)QuoridorController.getWhitePlayer().getGameAsWhite().getCurrentPosition().getWhitePosition().getTile().getColumn());
		}

	}

	@Then("The next player to move shall become {string}")
	public void the_next_player_to_move_shall_become(String string) {
		String nextPlayerToMove = "";
		if(QuoridorController.getCurrentPlayer().equals(QuoridorController.getBlackPlayer())) {
			if(legalMove) {
				nextPlayerToMove = "white";
			} else {
				nextPlayerToMove = "black";
			}
		}else {
			if(legalMove) {
				nextPlayerToMove = "black";
			}else {
				nextPlayerToMove = "white";
			}
		}
		QuoridorController.switchCurrentPlayer();
		assertEquals(string, nextPlayerToMove);
		
	}

	@Given("There is a {string} wall at {int}:{int}")
	public void there_is_a_wall_at(String string, Integer int1, Integer int2) {

		if(string.equals("vertical")) {
			Wall wall = q.getCurrentGame().getCurrentPosition().getBlackWallsInStock(0);
			q.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
			Tile wallTile = q.getBoard().getTile((int1 -1)*9 + (int2-1));
			WallMove wallMove = new WallMove(0, 0, q.getCurrentGame().getBlackPlayer(), wallTile, q.getCurrentGame(), Direction.Vertical, wall);
			wall.setMove(wallMove);
			q.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);
		}
		else {
			Wall wall = q.getCurrentGame().getCurrentPosition().getBlackWallsInStock(0);
			q.getCurrentGame().getCurrentPosition().removeBlackWallsInStock(wall);
			Tile wallTile = q.getBoard().getTile((int1 -1)*9 + (int2-1));
			WallMove wallMove = new WallMove(0, 0, q.getCurrentGame().getBlackPlayer(), wallTile, q.getCurrentGame(), Direction.Horizontal, wall);
			wall.setMove(wallMove);
			q.getCurrentGame().getCurrentPosition().addBlackWallsOnBoard(wall);

		}
	}

}
