package ca.mcgill.ecse223.quoridor.utilities;

import java.util.concurrent.TimeUnit;


/**
 * Class Used to maintain all helper methods for the controller. 
 *
 */

public class TimerUtilities {
	/**
	 * This method is to measure the time elapsed for each player. 
	 * It captures the start time at which a given method is started
	 * @author Ousmane Baricisse in seconds
	 * @return the time now.
	 */
	
	public static long getCurrentTime() {
		return System.currentTimeMillis()*1000;
	}
	
	/**
	 * Helper methods to convert seconds in Hours, Minute and Seconds 
	 * 
	 * @author Ousmane Baricisse 
	 * @param long seconds
	 * @return the correct time format in a string format
	 */
	
	public static String clockToString(long seconds) {

	    int hours = (int) seconds / 3600;
	    int minutes = (int) (seconds % 3600) / 60;
	    int sec = (int) seconds % 60;

	    return timeFormat(hours) + " : " + timeFormat(minutes) + " : " + timeFormat(sec);
	}
	
	/**
	 * Helper methods to convert integers to string
	 * 
	 * @author Ousmane Baricisse
	 * @param integer time
	 * @return string value of int time
	 */
	private static String timeFormat(int time) {

	    if (time == 0) {
	        return "00";
	    }

	    if (time / 10 == 0) {
	        return "0" + time;
	    }

	    return String.valueOf(time);
	}
	/**
	 * toString method to return the time elapsed in time format
	 * @param startTime in seconds and endTime in seconds
	 * @author Ousmane Baricisse
	 * @return String time format (HH:MM:SS)
	 */
	public String getTimeElapsed(long startTime, long endTime) {
		return TimerUtilities.clockToString(endTime - startTime);
	}
}
