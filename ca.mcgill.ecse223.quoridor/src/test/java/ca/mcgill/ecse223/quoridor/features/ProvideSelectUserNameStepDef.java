package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.User;
import cucumber.api.Pending;
import cucumber.api.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static ca.mcgill.ecse223.quoridor.features.TestingUtilities.*;
import static org.junit.Assert.assertEquals;
/**
 * Class used to encapsulate the step definitions related to the ProvideSelectUserName
 * feature
 *
 * @author Nayem Alam
 *
 */
public class ProvideSelectUserNameStepDef {

    private Player currentPlayer;
    // *********************************************
    // Select existing user name scenario
    // *********************************************

    /**
     * Method used to set next player username based on color
     *
     * @param color - String color can be white or black
     * @throws Exception the color of the user can only be white or black
     * @author Nayem Alam
     */
    @Given("Next player to set user name is {string}")
    public void next_player_to_set_user_name_is(String color) throws Exception {
        Game g = QuoridorApplication.getQuoridor().getCurrentGame();
//		Player currentPlayer = color.equals("white") ? g.getBlackPlayer() : g.getWhitePlayer();
        if(color.equals("white")) {
            currentPlayer = g.getBlackPlayer();
            currentPlayer.setNextPlayer(g.getWhitePlayer());
        }
        else if(color.equals("black")) {
            currentPlayer = g.getWhitePlayer();
            currentPlayer.setNextPlayer(g.getBlackPlayer());
        } else {
          throw new Exception("The color entered is not white or black.");
        }
    }

    /**
     * Method used to show that there is an existing user
     *
     * @param username - String username exists within the list of users
     * @author Nayem Alam
     */
    @And("There is existing user {string}")
    public void there_is_existing_user(String username) {
        // populate empty list with existing user
        QuoridorApplication.getQuoridor().addUser(username);
    }

    /**
     * Method used to allow player to select an existing user name
     *
     * @param username - String username already exists
     * @author Nayem Alam
     */
    @When("The player selects existing {string}")
    public void the_player_selects_existing(String username) {
        QuoridorController.selectExistingUserName(username, currentPlayer, QuoridorApplication.getQuoridor());
    }

    /**
     * Method used to verify the username given the corresponding color
     *
     * @param color - String color can be white or black
     * @param username - String username of player in new game
     * @author Nayem Alam
     */
    @Then("The name of player {string} in the new game shall be {string}")
    public void the_name_of_player_in_the_new_game_shall_be(String color, String username) {
        if(color.equals("white")){
            assertEquals(username, currentPlayer.getUser().getName());
        }
        else if (color.equals("black")) {
            assertEquals(username, currentPlayer.getUser().getName());
        }
    }

    // *********************************************
    // Create new user name scenario
    // *********************************************

    /* DONE during first scenario
        > Given Next player to set user name is "<color>"
    */

    /**
     * Method used to show that there is no existing user
     *
     * @param username - String username does not exist within the list of users
     * @author Nayem Alam
     */
    @And("There is no existing user {string}")
    public void there_is_no_existing_user(String username) {
        // the application does not have the username ^
        QuoridorApplication.getQuoridor().removeUser(new User(username, QuoridorApplication.getQuoridor()));
    }

    /**
     * Method used to allow player to provide a new username
     *
     * @param username - String new username  provided by player
     * @author Nayem Alam
     */
    @When("The player provides new user name: {string}")
    public void the_player_provides_new_user_name(String username) {
        QuoridorController.selectNewUserName(username, currentPlayer, QuoridorApplication.getQuoridor());
    }

    /* DONE during first scenario
        > The name of player "<color>" in the new game shall be "<username>"
    */

    // *********************************************
    // User name already exists scenario
    // *********************************************

    /* DONE during first scenario
        > Given Next player to set user name is "<color>"

        > And There is existing user "<username>"

        > When The player provides new user name: "<username>"
    */

    /**
     * Method used to warn player if the username already exists
     *
     * @param username - String username of player in new game
     * @author Nayem Alam
     */
    @Then("The player shall be warned that {string} already exists")
    public void the_player_shall_be_warned_that_already_exists(String username) {
        // definitely something to do in the GUI
        assertEquals("user name exists", username, currentPlayer.getUser().getName());
    }
    /**
     * Method used to verify the next player's username given the corresponding color
     *
     * @param color - String color determines the corresponding username of the next player in new game
     * @author Nayem Alam
     */
    @And("Next player to set user name shall be {string}")
    public void next_player_to_set_user_name_shall_be(String color) {
        assertEquals(color, getPlayerColor(currentPlayer.getNextPlayer()));
    }

}
