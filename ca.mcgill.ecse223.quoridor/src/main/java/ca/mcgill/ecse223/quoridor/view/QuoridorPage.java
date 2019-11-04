package ca.mcgill.ecse223.quoridor.view;

import java.awt.EventQueue;
import ca.mcgill.ecse223.quoridor.controller.*;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Panel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import java.awt.CardLayout;
import java.awt.Color;
import javax.swing.JButton;
//import ca.mcgill.ecse223.quoridor.view.MouseListener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Font;

public class QuoridorPage {

	
	public static JButton[] WallArray = new JButton[20];
	private static JButton horizontal = new JButton();
	private static JButton vertical = new JButton();

	private static int idWhite = -1;
	private static int idBlack = 8;
	private static int CurrPlayer = 0;
	private static int index;

	private static JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					QuoridorPage window = new QuoridorPage();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public QuoridorPage() {
		initialize();
		
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public static void initialize() {
		frame = new JFrame("Quoridor");
		frame.setBounds(100, 100, 1165, 693);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblPleaseSelectMove = new JLabel("Please Select Move First");
		lblPleaseSelectMove.setBackground(Color.RED);
		lblPleaseSelectMove.setForeground(Color.BLACK);
		lblPleaseSelectMove.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblPleaseSelectMove.setBounds(449, 297, 155, 52);
		frame.getContentPane().add(lblPleaseSelectMove);

		JButton btnYeq = new JButton("yeq");
		btnYeq.setBounds(114, 130, 89, 23);
		frame.getContentPane().add(btnYeq);
	}





	
	



//	private void BoardListener() {
//
//		for(int i = 0; i<= btnArray.length; i++) {
//			index = i;
//			btnArray[i][i].addMouseListener(new MouseAdapter() {
//
//				public void mouseClicked(MouseEvent e) {
//
//					int col = 0;
//					while(index%9 != 0) {
//						col++;
//						index--;
//					}
//
//					int row = (index/9)+1;
//					if(CurrPlayer == 0) {
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idWhite) == true && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == true ) {
//
//							horizontal.setBounds(btnArray[index][index].getX(),btnArray[index][index].getY(), 89, 9);
//							frame.getContentPane().add(horizontal);
//							horizontal.setVisible(true);
//							vertical.setBounds(993, 399, 89, 9);
//							frame.getContentPane().add(vertical);
//							vertical.setVisible(true);
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idWhite) == true && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == false ) {
//
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idWhite) == false && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == true ) {
//
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idWhite) == false && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == false) {
//
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//
//					}
//					else {
//
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idBlack) == true && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == true ) {
//
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idBlack) == true && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == false ) {
//
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idBlack) == false && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == true ) {
//
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//						if(QuoridorController.initiatePosValidation(row, col, "horizontal", idBlack) == false && QuoridorController.initiatePosValidation(row, col, "vertical", idWhite) == false) {
//
//							// :TODO try implementing wall move here and add the button for position
//
//						}
//					}
//
//
//				}
//			});
//		}
//	}
//	

}
