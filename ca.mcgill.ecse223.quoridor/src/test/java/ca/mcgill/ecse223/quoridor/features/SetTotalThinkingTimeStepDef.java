package ca.mcgill.ecse223.quoridor.features;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Player;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.sql.Time;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Class used to encapsulate the step definitions related to the SetTotalThinkingTime
 * feature
 *
 * @author Nayem Alam
 *
 */
public class SetTotalThinkingTimeStepDef {

    // *********************************************
    // Set thinking time for players scenario
    // *********************************************

    /**
     * Method used to set total thinking time (for both players)
     *
     * @param min sets the number of minutes
     * @param sec sets the number of seconds
     * @author Nayem Alam
     */
    @When("{int}:{int} is set as the thinking time")
    public void is_set_as_the_thinking_time(Integer min, Integer sec) {
        // controller method should set same thinking time for both player1 & player2
        QuoridorController.setThinkingTime(min, sec);
    }
    /**
     * Method used to verify that both players have the same remaining time left
     *
     * @param min sets the number of minutes
     * @param sec sets the number of seconds
     * @author Nayem Alam
     */
    @Then("Both players shall have {int}:{int} remaining time left")
    public void both_players_shall_have_remaining_time_left(Integer min, Integer sec) {
        // converts min and sec to long type (unix epoch time)
        Time t = new Time(min* 60L *1000 + sec* 1000L);
        Player bPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getBlackPlayer();
        Player wPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer();

        assertEquals(t, wPlayer.getRemainingTime());
        assertEquals(t, bPlayer.getRemainingTime());
    }
}
