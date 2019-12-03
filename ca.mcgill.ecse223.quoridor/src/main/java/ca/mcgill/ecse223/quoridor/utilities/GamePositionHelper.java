package ca.mcgill.ecse223.quoridor.utilities;

import ca.mcgill.ecse223.quoridor.model.*;

import java.util.*;

import ca.mcgill.ecse223.quoridor.controller.*;

public class GamePositionHelper {
    public static List<ListNode> gamePositions;
    public  ListNode head;
    public GamePositionHelper(GamePosition gp){
        head = new ListNode(gp);
        gamePositions = new ArrayList<>();
        gamePositions.add(head);
    }
  
}