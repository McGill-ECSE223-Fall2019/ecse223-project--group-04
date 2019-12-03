package ca.mcgill.ecse223.quoridor.utilities;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.controller.*;

public class ListNode {
    public static ListNode nextGP;
    public static ListNode prevGP;
    public  GamePosition gamePosition;

    public ListNode(GamePosition gampePosition){
        this.gamePosition = gamePosition;
    }

}