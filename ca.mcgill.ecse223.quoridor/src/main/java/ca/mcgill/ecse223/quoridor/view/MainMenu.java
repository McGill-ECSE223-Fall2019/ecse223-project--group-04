package ca.mcgill.ecse223.quoridor.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.SwingConstants;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.SystemColor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu {

	public JFrame frame;
	private JTextField txtQuoridor;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
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
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		// Create users for Controller 
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// TODO: Set the correct names!
		quoridor.addUser("TestUser1");
		quoridor.addUser("TestUser2");
		
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Forces fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setBounds(0, 0, 432, 253);
		frame.getContentPane().add(centerPanel);
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
	    int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
	    centerPanel.setLocation(x, y);
		GridBagLayout gbl_centerPanel = new GridBagLayout();
		gbl_centerPanel.columnWidths = new int[]{392, 0};
		gbl_centerPanel.rowHeights = new int[]{49, 49, 49, 49, 0};
		gbl_centerPanel.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_centerPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_centerPanel);
		
		txtQuoridor = new JTextField();
		txtQuoridor.setBackground(SystemColor.activeCaption);
		txtQuoridor.setEditable(false);
		txtQuoridor.setFont(new Font("Monospaced", Font.BOLD, 50));
		txtQuoridor.setText("Quoridor");
		txtQuoridor.setHorizontalAlignment(SwingConstants.CENTER);
		GridBagConstraints gbc_txtQuoridor = new GridBagConstraints();
		gbc_txtQuoridor.fill = GridBagConstraints.BOTH;
		gbc_txtQuoridor.insets = new Insets(0, 0, 5, 0);
		gbc_txtQuoridor.gridx = 0;
		gbc_txtQuoridor.gridy = 0;
		centerPanel.add(txtQuoridor, gbc_txtQuoridor);
		txtQuoridor.setColumns(10);
		
		JButton btnNewButton = new JButton("Start New Game");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				frame.dispose();
				try {
					StartGamePage startPage = new StartGamePage();
					startPage.frame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setPreferredSize(new Dimension(40, 40));
		btnNewButton.setToolTipText("Click here to start a new Game");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.gridx = 0;
		gbc_btnNewButton.gridy = 1;
		centerPanel.add(btnNewButton, gbc_btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Load Game");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				frame.dispose();
				try {
					LoadPositionPage loadPage = new LoadPositionPage();
					loadPage.frame.setVisible(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_1.setToolTipText("Click here to load a game from a save file");
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton_1.gridx = 0;
		gbc_btnNewButton_1.gridy = 2;
		centerPanel.add(btnNewButton_1, gbc_btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Quit");
		btnNewButton_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
			}
		});
		btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton_2.setPreferredSize(new Dimension(40, 40));
		btnNewButton_2.setToolTipText("Click here to exit the game");
		GridBagConstraints gbc_btnNewButton_2 = new GridBagConstraints();
		gbc_btnNewButton_2.fill = GridBagConstraints.BOTH;
		gbc_btnNewButton_2.gridx = 0;
		gbc_btnNewButton_2.gridy = 3;
		centerPanel.add(btnNewButton_2, gbc_btnNewButton_2);
		frame.setVisible(true);
	}

}
