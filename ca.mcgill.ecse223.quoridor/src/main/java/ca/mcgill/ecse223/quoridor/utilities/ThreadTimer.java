package ca.mcgill.ecse223.quoridor.utilities;

import java.sql.Time;
import java.util.TimerTask;

import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.view.*;

public class ThreadTimer extends TimerTask{
    private static final int SECOND_IN_MS = 1000;
    
    Player player;
    public ThreadTimer(Player player){
    	this.player = player;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub
    	long timeLeft = player.getRemainingTime().getTime();
    	Time newTime = new Time(timeLeft - SECOND_IN_MS);
    	player.setRemainingTime(newTime);
    	if(MainGameWindow.frmQuoridorPlay != null) {
    		MainGameWindow.updateTime();
    	}
    	
   }

}