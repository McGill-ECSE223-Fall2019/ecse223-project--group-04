package ca.mcgill.ecse223.quoridor.utilities;

import java.util.Timer;

import ca.mcgill.ecse223.quoridor.model.*;
public class PlayerThread {
    public String player;
    public ThreadTimer thread;
    public Timer timer;
    public boolean hasStarted; 
    public PlayerThread(String player, ThreadTimer thread, Timer timer){
        this.player = player;
        this.thread = thread;
        this.timer = timer;
        hasStarted = false;
    }
}