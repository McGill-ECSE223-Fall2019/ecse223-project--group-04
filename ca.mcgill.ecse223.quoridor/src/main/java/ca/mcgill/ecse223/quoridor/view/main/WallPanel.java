package ca.mcgill.ecse223.quoridor.view.main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.security.PublicKey;

import javax.swing.*;

public class WallPanel {
	private final JFrame wallFrame;
	private final Dimension outerFrameDimension = new Dimension(600, 600);
	private final Dimension panelFrameDimension = new Dimension(60, 60);
	private final Dimension buttonDimension = new Dimension(10, 10);

	public WallPanel() {
		this.wallFrame = new JFrame("QUORIDOR GAME");
		this.wallFrame.setSize(outerFrameDimension);
		
		this.wallFrame.getContentPane().setBackground(Color.PINK.darker());
		JButton buton = new JButton();

		JPanel panel = new JPanel(new GridLayout(10, 0));
		panel.setSize(600, 600);
		panel.setBorder(BorderFactory.createEtchedBorder());
//		panel.setSize(panelFrameDimension);
		
		buton = new JButton();
		buton.setSize(buttonDimension);
		buton.setBackground(Color.BLACK);
		buton.setForeground(Color.black);
		buton.setOpaque(true);
		buton.setBorderPainted(false);
		
		buton.setContentAreaFilled(true);
		
		panel.add(buton);
		
		panel.setBackground(new Color(112, 53, 45));

//		panel.setOpaque(true);
//		panel.setVisible(true);
		this.wallFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.wallFrame.setMinimumSize(outerFrameDimension);
		this.wallFrame.getContentPane().add(panel);
		this.wallFrame.pack();
		this.wallFrame.setVisible(true);

	}

	public static WallPanel getQuoridorView() {
		return new WallPanel();
	}

}
