package ca.mcgill.ecse223.quoridor.utilities;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.controller.*;

public class ListNode {
    public static ListNode next;
    public static ListNode prev;
    public static StepMove playerMove;
    public static WallMove wallMove;
    public static boolean isPlayerMove;
 

    public ListNode(StepMove playerMove, WallMove wallMove, boolean isPlayerMove){
        this.playerMove = playerMove;
        this.wallMove = wallMove;
        this.isPlayerMove = isPlayerMove;
    }

}