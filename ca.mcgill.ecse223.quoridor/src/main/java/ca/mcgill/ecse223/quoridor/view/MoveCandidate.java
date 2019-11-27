package ca.mcgill.ecse223.quoridor.view;

import javax.swing.JButton;


class MoveCandidate {
    JButton wallMoveBtn;
    int row;
    int col;
    boolean isRotated;

    public MoveCandidate(JButton wallMoveBtn, int row, int col){
        this.wallMoveBtn = wallMoveBtn;
        this.row = row;
        this.col = col;
        isRotated = false;
    }
}