package ca.mcgill.ecse223.quoridor.view.main;

import java.awt.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.security.PublicKey;
import java.awt.event.*;

import javax.swing.*;

public class BlackWallPanel {
    private static int wallWidth = 185;
    private static int wallWidthV = 101;
    private static int wallHeight = 10;
    private static JButton[] wallArray = new JButton[10];
    private static int index;
    private static JPanel wallPane;

    public BlackWallPanel(JPanel panel) {
        wallPane = panel;
        wallPane.setLayout(null);
        for (int i = 0; i < wallArray.length; i++) { // Initializing the walls for both players

            int wId = i;
            wallArray[i] = new JButton("Wall" + i);
            wallPane.add(wallArray[i]);
            wallArray[i].setBounds(10, 11 + i * (wallHeight + 5), wallWidth, wallHeight);
        }

    }

    public static JPanel getWallPanel() {
        return wallPane;
    }

    public void removeWall() {
        if (index < 10) {
            wallPane.remove(wallArray[index]);
            index++;
        }

    }

    public void cancelWallMove() {
        if (index > 0) {
            index--;
            wallPane.add(wallArray[index]);
        }
    }

}