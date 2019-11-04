package ca.mcgill.ecse223.quoridor.view;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class FrameTestBase extends JFrame {

    public static void main(String args[]) {
        FrameTestBase t = new FrameTestBase();

        final JPanel p = new JPanel();

        final JButton button = new JButton();
        
        button.setAction(new AbstractAction("Remove me!") {
            @Override
            public void actionPerformed(ActionEvent e) {
                p.remove(button);
                p.revalidate();
                p.repaint();
            }
        });

        p.add(button);
        t.setContentPane(p);

        t.setDefaultCloseOperation(EXIT_ON_CLOSE);
        t.setSize(400, 400);
        t.setVisible(true);
    }
}