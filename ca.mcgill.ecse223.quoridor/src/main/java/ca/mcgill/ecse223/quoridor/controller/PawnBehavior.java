/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.quoridor.controller;
import ca.mcgill.ecse223.quoridor.model.*;

// line 5 "../../../../../PawnStateMachine.ump"
public class PawnBehavior
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PawnBehavior State Machines
  public enum PawnSM { InGame }
  public enum PawnSMInGameVertical { Null, Vertical }
  public enum PawnSMInGameVerticalVertical { Null, Start, TrueNorth, SoftNorth, MiddleVertical, TrueSouth, SoftSouth }
  public enum PawnSMInGameHorizontal { Null, Horizontal }
  public enum PawnSMInGameHorizontalHorizontal { Null, MiddleHorizontal, TrueWest, SoftWest, TrueEast, SoftEast }
  private PawnSM pawnSM;
  private PawnSMInGameVertical pawnSMInGameVertical;
  private PawnSMInGameVerticalVertical pawnSMInGameVerticalVertical;
  private PawnSMInGameHorizontal pawnSMInGameHorizontal;
  private PawnSMInGameHorizontalHorizontal pawnSMInGameHorizontalHorizontal;

  //PawnBehavior Associations
  private Game currentGame;
  private Player player;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PawnBehavior()
  {
    setPawnSMInGameVertical(PawnSMInGameVertical.Null);
    setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Null);
    setPawnSMInGameHorizontal(PawnSMInGameHorizontal.Null);
    setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.Null);
    setPawnSM(PawnSM.InGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getPawnSMFullName()
  {
    String answer = pawnSM.toString();
    if (pawnSMInGameVertical != PawnSMInGameVertical.Null) { answer += "." + pawnSMInGameVertical.toString(); }
    if (pawnSMInGameVerticalVertical != PawnSMInGameVerticalVertical.Null) { answer += "." + pawnSMInGameVerticalVertical.toString(); }
    if (pawnSMInGameHorizontal != PawnSMInGameHorizontal.Null) { answer += "." + pawnSMInGameHorizontal.toString(); }
    if (pawnSMInGameHorizontalHorizontal != PawnSMInGameHorizontalHorizontal.Null) { answer += "." + pawnSMInGameHorizontalHorizontal.toString(); }
    return answer;
  }

  public PawnSM getPawnSM()
  {
    return pawnSM;
  }

  public PawnSMInGameVertical getPawnSMInGameVertical()
  {
    return pawnSMInGameVertical;
  }

  public PawnSMInGameVerticalVertical getPawnSMInGameVerticalVertical()
  {
    return pawnSMInGameVerticalVertical;
  }

  public PawnSMInGameHorizontal getPawnSMInGameHorizontal()
  {
    return pawnSMInGameHorizontal;
  }

  public PawnSMInGameHorizontalHorizontal getPawnSMInGameHorizontalHorizontal()
  {
    return pawnSMInGameHorizontalHorizontal;
  }

  public boolean startGame()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameVerticalVertical aPawnSMInGameVerticalVertical = pawnSMInGameVerticalVertical;
    switch (aPawnSMInGameVerticalVertical)
    {
      case Start:
        if (getCurrentGame().equals(getPlayer().getGameAsWhite()))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueSouth);
          wasEventProcessed = true;
          break;
        }
        if (getCurrentGame().equals(getPlayer().getGameAsBlack()))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueNorth);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameVerticalVertical aPawnSMInGameVerticalVertical = pawnSMInGameVerticalVertical;
    switch (aPawnSMInGameVerticalVertical)
    {
      case TrueNorth:
        exitPawnSMInGameVerticalVertical();
        // line 18 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueNorth);
        wasEventProcessed = true;
        break;
      case SoftNorth:
        exitPawnSMInGameVerticalVertical();
        // line 24 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftNorth);
        wasEventProcessed = true;
        break;
      case MiddleVertical:
        if (isLegalJump(MoveDirection.North)&&getCurrentPawnRow()==4)
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftNorth);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.North)&&getCurrentPawnRow()==3)
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueNorth);
          wasEventProcessed = true;
          break;
        }
        if (!(isLegalJump(MoveDirection.North)))
        {
          exitPawnSMInGameVerticalVertical();
        // line 33 "../../../../../PawnStateMachine.ump"
          illegalMove();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameVerticalVertical();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
        wasEventProcessed = true;
        break;
      case TrueSouth:
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftSouth:
        if (isLegalJump(MoveDirection.North))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean stepUp()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameVerticalVertical aPawnSMInGameVerticalVertical = pawnSMInGameVerticalVertical;
    switch (aPawnSMInGameVerticalVertical)
    {
      case TrueNorth:
        exitPawnSMInGameVerticalVertical();
        // line 19 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueNorth);
        wasEventProcessed = true;
        break;
      case SoftNorth:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueNorth);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleVertical:
        if (isLegalStep(MoveDirection.North)&&getCurrentPawnRow()==3)
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftNorth);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameVerticalVertical();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
        wasEventProcessed = true;
        break;
      case TrueSouth:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftSouth);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftSouth:
        if (isLegalStep(MoveDirection.North))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean stepDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameVerticalVertical aPawnSMInGameVerticalVertical = pawnSMInGameVerticalVertical;
    switch (aPawnSMInGameVerticalVertical)
    {
      case TrueNorth:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftNorth);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftNorth:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleVertical:
        if (isLegalStep(MoveDirection.South)&&getCurrentPawnRow()==7)
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftSouth);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameVerticalVertical();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
        wasEventProcessed = true;
        break;
      case TrueSouth:
        exitPawnSMInGameVerticalVertical();
        // line 44 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueSouth);
        wasEventProcessed = true;
        break;
      case SoftSouth:
        if (isLegalStep(MoveDirection.South))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueSouth);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpDown()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameVerticalVertical aPawnSMInGameVerticalVertical = pawnSMInGameVerticalVertical;
    switch (aPawnSMInGameVerticalVertical)
    {
      case TrueNorth:
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftNorth:
        if (isLegalJump(MoveDirection.South))
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
          wasEventProcessed = true;
          break;
        }
        break;
      case MiddleVertical:
        if (isLegalJump(MoveDirection.South)&&getCurrentPawnRow()==6)
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftSouth);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.South)&&getCurrentPawnRow()==7)
        {
          exitPawnSMInGameVerticalVertical();
          setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueSouth);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameVerticalVertical();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.MiddleVertical);
        wasEventProcessed = true;
        break;
      case TrueSouth:
        exitPawnSMInGameVerticalVertical();
        // line 43 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.TrueSouth);
        wasEventProcessed = true;
        break;
      case SoftSouth:
        exitPawnSMInGameVerticalVertical();
        // line 49 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.SoftSouth);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean stepLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameHorizontalHorizontal aPawnSMInGameHorizontalHorizontal = pawnSMInGameHorizontalHorizontal;
    switch (aPawnSMInGameHorizontalHorizontal)
    {
      case MiddleHorizontal:
        if (isLegalStep(MoveDirection.West)&&getCurrentPawnColumn()==3)
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftWest);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameHorizontalHorizontal();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
        wasEventProcessed = true;
        break;
      case TrueWest:
        exitPawnSMInGameHorizontalHorizontal();
        // line 71 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueWest);
        wasEventProcessed = true;
        break;
      case SoftWest:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueWest);
          wasEventProcessed = true;
          break;
        }
        break;
      case TrueEast:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftEast);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftEast:
        if (isLegalStep(MoveDirection.West))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpLeft()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameHorizontalHorizontal aPawnSMInGameHorizontalHorizontal = pawnSMInGameHorizontalHorizontal;
    switch (aPawnSMInGameHorizontalHorizontal)
    {
      case MiddleHorizontal:
        if (isLegalJump(MoveDirection.West)&&getCurrentPawnColumn()==4)
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftWest);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.West)&&getCurrentPawnColumn()==3)
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueWest);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameHorizontalHorizontal();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
        wasEventProcessed = true;
        break;
      case TrueWest:
        exitPawnSMInGameHorizontalHorizontal();
        // line 70 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueWest);
        wasEventProcessed = true;
        break;
      case SoftWest:
        exitPawnSMInGameHorizontalHorizontal();
        // line 76 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftWest);
        wasEventProcessed = true;
        break;
      case TrueEast:
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftEast:
        if (isLegalJump(MoveDirection.West))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean stepRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameHorizontalHorizontal aPawnSMInGameHorizontalHorizontal = pawnSMInGameHorizontalHorizontal;
    switch (aPawnSMInGameHorizontalHorizontal)
    {
      case MiddleHorizontal:
        if (isLegalStep(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftEast);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameHorizontalHorizontal();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
        wasEventProcessed = true;
        break;
      case TrueWest:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftWest);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftWest:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
          wasEventProcessed = true;
          break;
        }
        break;
      case TrueEast:
        exitPawnSMInGameHorizontalHorizontal();
        // line 83 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueEast);
        wasEventProcessed = true;
        break;
      case SoftEast:
        if (isLegalStep(MoveDirection.East))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueEast);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean jumpRight()
  {
    boolean wasEventProcessed = false;
    
    PawnSMInGameHorizontalHorizontal aPawnSMInGameHorizontalHorizontal = pawnSMInGameHorizontalHorizontal;
    switch (aPawnSMInGameHorizontalHorizontal)
    {
      case MiddleHorizontal:
        if (isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==6)
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftEast);
          wasEventProcessed = true;
          break;
        }
        if (isLegalJump(MoveDirection.East)&&getCurrentPawnColumn()==7)
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueEast);
          wasEventProcessed = true;
          break;
        }
        exitPawnSMInGameHorizontalHorizontal();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
        wasEventProcessed = true;
        break;
      case TrueWest:
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
          wasEventProcessed = true;
          break;
        }
        break;
      case SoftWest:
        if (isLegalJump(MoveDirection.East))
        {
          exitPawnSMInGameHorizontalHorizontal();
          setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal);
          wasEventProcessed = true;
          break;
        }
        break;
      case TrueEast:
        exitPawnSMInGameHorizontalHorizontal();
        // line 82 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.TrueEast);
        wasEventProcessed = true;
        break;
      case SoftEast:
        exitPawnSMInGameHorizontalHorizontal();
        // line 88 "../../../../../PawnStateMachine.ump"
        illegalMove();
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.SoftEast);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitPawnSM()
  {
    switch(pawnSM)
    {
      case InGame:
        exitPawnSMInGameVertical();
        exitPawnSMInGameHorizontal();
        break;
    }
  }

  private void setPawnSM(PawnSM aPawnSM)
  {
    pawnSM = aPawnSM;

    // entry actions and do activities
    switch(pawnSM)
    {
      case InGame:
        if (pawnSMInGameVertical == PawnSMInGameVertical.Null) { setPawnSMInGameVertical(PawnSMInGameVertical.Vertical); }
        if (pawnSMInGameHorizontal == PawnSMInGameHorizontal.Null) { setPawnSMInGameHorizontal(PawnSMInGameHorizontal.Horizontal); }
        break;
    }
  }

  private void exitPawnSMInGameVertical()
  {
    switch(pawnSMInGameVertical)
    {
      case Vertical:
        exitPawnSMInGameVerticalVertical();
        setPawnSMInGameVertical(PawnSMInGameVertical.Null);
        break;
    }
  }

  private void setPawnSMInGameVertical(PawnSMInGameVertical aPawnSMInGameVertical)
  {
    pawnSMInGameVertical = aPawnSMInGameVertical;
    if (pawnSM != PawnSM.InGame && aPawnSMInGameVertical != PawnSMInGameVertical.Null) { setPawnSM(PawnSM.InGame); }

    // entry actions and do activities
    switch(pawnSMInGameVertical)
    {
      case Vertical:
        if (pawnSMInGameVerticalVertical == PawnSMInGameVerticalVertical.Null) { setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Start); }
        break;
    }
  }

  private void exitPawnSMInGameVerticalVertical()
  {
    switch(pawnSMInGameVerticalVertical)
    {
      case Start:
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Null);
        break;
      case TrueNorth:
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Null);
        break;
      case SoftNorth:
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Null);
        break;
      case MiddleVertical:
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Null);
        break;
      case TrueSouth:
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Null);
        break;
      case SoftSouth:
        setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical.Null);
        break;
    }
  }

  private void setPawnSMInGameVerticalVertical(PawnSMInGameVerticalVertical aPawnSMInGameVerticalVertical)
  {
    pawnSMInGameVerticalVertical = aPawnSMInGameVerticalVertical;
    if (pawnSMInGameVertical != PawnSMInGameVertical.Vertical && aPawnSMInGameVerticalVertical != PawnSMInGameVerticalVertical.Null) { setPawnSMInGameVertical(PawnSMInGameVertical.Vertical); }
  }

  private void exitPawnSMInGameHorizontal()
  {
    switch(pawnSMInGameHorizontal)
    {
      case Horizontal:
        exitPawnSMInGameHorizontalHorizontal();
        setPawnSMInGameHorizontal(PawnSMInGameHorizontal.Null);
        break;
    }
  }

  private void setPawnSMInGameHorizontal(PawnSMInGameHorizontal aPawnSMInGameHorizontal)
  {
    pawnSMInGameHorizontal = aPawnSMInGameHorizontal;
    if (pawnSM != PawnSM.InGame && aPawnSMInGameHorizontal != PawnSMInGameHorizontal.Null) { setPawnSM(PawnSM.InGame); }

    // entry actions and do activities
    switch(pawnSMInGameHorizontal)
    {
      case Horizontal:
        if (pawnSMInGameHorizontalHorizontal == PawnSMInGameHorizontalHorizontal.Null) { setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.MiddleHorizontal); }
        break;
    }
  }

  private void exitPawnSMInGameHorizontalHorizontal()
  {
    switch(pawnSMInGameHorizontalHorizontal)
    {
      case MiddleHorizontal:
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.Null);
        break;
      case TrueWest:
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.Null);
        break;
      case SoftWest:
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.Null);
        break;
      case TrueEast:
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.Null);
        break;
      case SoftEast:
        setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal.Null);
        break;
    }
  }

  private void setPawnSMInGameHorizontalHorizontal(PawnSMInGameHorizontalHorizontal aPawnSMInGameHorizontalHorizontal)
  {
    pawnSMInGameHorizontalHorizontal = aPawnSMInGameHorizontalHorizontal;
    if (pawnSMInGameHorizontal != PawnSMInGameHorizontal.Horizontal && aPawnSMInGameHorizontalHorizontal != PawnSMInGameHorizontalHorizontal.Null) { setPawnSMInGameHorizontal(PawnSMInGameHorizontal.Horizontal); }
  }
  /* Code from template association_GetOne */
  public Game getCurrentGame()
  {
    return currentGame;
  }

  public boolean hasCurrentGame()
  {
    boolean has = currentGame != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setCurrentGame(Game aNewCurrentGame)
  {
    boolean wasSet = false;
    currentGame = aNewCurrentGame;
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setPlayer(Player aNewPlayer)
  {
    boolean wasSet = false;
    player = aNewPlayer;
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    currentGame = null;
    player = null;
  }


  /**
   * Returns the current row number of the pawn
   */
  // line 99 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnRow(){
    Tile currentTile = QuoridorController.getPlayerPosition(this.player);
    	return currentTile.getRow();
  }


  /**
   * Returns the current column number of the pawn
   */
  // line 105 "../../../../../PawnStateMachine.ump"
  public int getCurrentPawnColumn(){
    Tile currentTile = QuoridorController.getPlayerPosition(this.player);
    	return currentTile.getColumn();
  }


  /**
   * Returns if it is legal to step in the given direction
   */
  // line 110 "../../../../../PawnStateMachine.ump"
  public boolean isLegalStep(MoveDirection dir){
    try{
    		QuoridorController.movePawn(this.player, dir.toString());
    	} catch(Exception e){
    		return false;
    	}
    	return true;
  }


  /**
   * Returns if it is legal to jump in the given direction
   */
  // line 119 "../../../../../PawnStateMachine.ump"
  public boolean isLegalJump(MoveDirection dir){
    Boolean playerAdjacent =  QuoridorController.pawnOnWay(this.player, false);
    	// TODO: validate jump direction.
    	return playerAdjacent;
  }


  /**
   * Action to be called when an illegal move is attempted
   */
  // line 126 "../../../../../PawnStateMachine.ump"
  public void illegalMove(){
    throw new IllegalArgumentException("Move out of bounds!");
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 131 "../../../../../PawnStateMachine.ump"
  enum MoveDirection 
  {
    East, South, West, North;
  }

  
}