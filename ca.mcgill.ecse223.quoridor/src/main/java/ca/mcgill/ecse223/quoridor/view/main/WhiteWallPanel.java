package ca.mcgill.ecse223.quoridor.view.main;

import java.awt.*;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.security.PublicKey;
import java.awt.event.*;

import javax.swing.*;

public class WhiteWallPanel {
    private static int wallWidth = 185;
    private static int wallWidthV = 101;
    private static int wallHeight = 10;
    private static JButton[] wallArray = new JButton[10];
    private static JPanel wallPane;

    public WhiteWallPanel(JPanel panel) {
        wallPane = panel;
        wallPane.setLayout(null);
        for (int i = 0; i < wallArray.length; i++) { // Initializing the walls for both players

            int wId = i;
            wallArray[i] = new JButton("Wall" + i);
            wallArray[i].addMouseListener(new MouseAdapter() {

                public void mouseReleased(MouseEvent e) {

                    for (int j = 0; j < wallArray.length; j++) {
                        if (wallArray[j].isVisible() == false && wId != j) {

                            wallArray[j].setVisible(true);
                        }

                    }
                    wallArray[wId].setVisible(false);
                    // TODO call the method that specifies what happens to the wall once it is
                    // grabbed
                    // TODO call the method for rotate wall in here

                }

            });

            wallPane.add(wallArray[i]);
            wallArray[i].setBounds(10, 11 + i * (wallHeight + 5), wallWidth, wallHeight);
        }

    }
    public static JPanel getWallPanel(){
        return wallPane;
    }


}
