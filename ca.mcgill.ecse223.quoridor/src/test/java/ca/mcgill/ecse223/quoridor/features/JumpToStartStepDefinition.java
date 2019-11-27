package ca.mcgill.ecse223.quoridor.features;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;

import ca.mcgill.ecse223.quoridor.model.*;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;
import static org.junit.Assert.assertEquals;

public class JumpToStartStepDefinition {


	private int moveNumber;
	private int RoundNumber;

	@Given("The following moves have been played in game:")
	public void the_following_moves_have_been_played_in_game(io.cucumber.datatable.DataTable dataTable) {
		// Write code here that turns the phrase above into concrete actions
		// For automatic transformation, change DataTable to one of
		// E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
		// Map<K, List<V>>. E,K,V must be a String, Integer, Float,
		// Double, Byte, Short, Long, BigInteger or BigDecimal.
		Quoridor q = QuoridorApplication.getQuoridor();
		List<Map<String, String>> valueMaps = dataTable.asMaps();
		for (Map<String, String> map : valueMaps) {
			Integer moveN = Integer.decode(map.get("mv"));
			Integer round = Integer.decode(map.get("rnd"));
			String move = map.get("move");

			int col = ((int) move.charAt(0)) - 96;
			int row = move.charAt(1) - 48;
			Tile aTile = new Tile(row, col,q.getBoard());
			if(move.length() == 2) {
				if(round == 1) {
					StepMove mv = new StepMove( moveN, 1, q.getCurrentGame().getWhitePlayer(), aTile, q.getCurrentGame());
				}
				else {
					StepMove mv = new StepMove( moveN, 1, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame());
				}

			}
			else {
				int indexW = 0;
				int indexB = 0;
				if(move.charAt(2) == 'h') {
					if(round == 1) {
						new WallMove(moveN, round, q.getCurrentGame().getWhitePlayer(), aTile, q.getCurrentGame(), Direction.Horizontal,q.getCurrentGame().getWhitePlayer().getWall(indexW));
						indexW++;
					}
					if( round == 2) {
						new WallMove(moveN, round, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame(), Direction.Horizontal,q.getCurrentGame().getBlackPlayer().getWall(indexB));
						indexB++;
					}
				}
				if(move.charAt(2) == 'v') {
					if(round == 1) {
						new WallMove(moveN, round, q.getCurrentGame().getWhitePlayer(), aTile, q.getCurrentGame(), Direction.Vertical,q.getCurrentGame().getWhitePlayer().getWall(indexW));
						indexW++;
					}
					if( round == 2) {
						new WallMove(moveN, round, q.getCurrentGame().getBlackPlayer(), aTile, q.getCurrentGame(), Direction.Vertical,q.getCurrentGame().getBlackPlayer().getWall(indexB));
						indexB++;
					}
				}
			}

		}
	}

	@Given("The next move is {double}")
	public void the_next_move_is(Double double1) {
		int moveN = double1.intValue();
		Double roundD = (double1%1)*10;
		int round = roundD.intValue();

		moveNumber = moveN;
		RoundNumber = round;
	}

	@When("Jump to start position is initiated")
	public void jump_to_start_position_is_initiated() {

		QuoridorController.jumpToStart(moveNumber, RoundNumber);
	}

	@Then("The next move shall be {double}")
	public void the_next_move_shall_be(Double double1) {
		int moveN = double1.intValue();
		Double roundD = (double1%1)*10;
		int round = roundD.intValue();

	}

	@Then("White player's position shall be \\({double})")
	public void white_player_s_position_shall_be(Double double1) {
		Quoridor q = QuoridorApplication.getQuoridor();
		int row = double1.intValue();
		Double colD = (double1%1)*10;
		int col = colD.intValue();
		assertEquals(row, q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getRow());
		assertEquals(col,q.getCurrentGame().getCurrentPosition().getWhitePosition().getTile().getColumn());
	}

	@Then("Black player's position shall be \\({double})")
	public void black_player_s_position_shall_be(Double double1) {
		Quoridor q = QuoridorApplication.getQuoridor();
		int row = double1.intValue();
		Double colD = (double1%1)*10;
		int col = colD.intValue();
		assertEquals(row, q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getRow());
		assertEquals(col,q.getCurrentGame().getCurrentPosition().getBlackPosition().getTile().getColumn());
	}

	@Then("White has {int} on stock")
	public void white_has_wwallno_on_stock(int int1) {
		Quoridor q = QuoridorApplication.getQuoridor();
		assertEquals(int1, q.getCurrentGame().getCurrentPosition().getWhiteWallsInStock());
	}

	@Then("Black has {int} on stock")
	public void black_has_on_stock(Integer int1) {
		Quoridor q = QuoridorApplication.getQuoridor();
		assertEquals(int1, q.getCurrentGame().getCurrentPosition().getBlackWallsInStock());
	}


}

