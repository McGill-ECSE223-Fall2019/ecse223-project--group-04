package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JButton;

import ca.mcgill.ecse223.quoridor.model.WallMove;

class WallMoveCandidate {
    JButton wallMoveBtn;
    int row;
    int col;
    boolean isRotated;

    public WallMoveCandidate(JButton wallMoveBtn, int row, int col){
        this.wallMoveBtn = wallMoveBtn;
        this.row = row;
        this.col = col;
        isRotated = false;
    }
}