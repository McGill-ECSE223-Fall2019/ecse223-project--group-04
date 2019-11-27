package ca.mcgill.ecse223.quoridor.features;

import io.cucumber.java.en.Given;

public class JumpPawnFeatureStepDef {
	
	//most clauses in the JumpPawn feature are
	//already defined in the MovePawn feature Step Def

	@Given("There are no {string} walls {string} from the player nearby")
	public void there_are_no_walls_from_the_player_nearby(String string, String string2) {
		// No walls to create, do nothing
	}
}