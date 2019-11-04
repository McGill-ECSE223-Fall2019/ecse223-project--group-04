package ca.mcgill.ecse223.quoridor.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JComboBox;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import ca.mcgill.ecse223.quoridor.controller.*;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class SavePositionPage {
	public JFrame frame;
	private QuoridorController QC;
	private boolean fileExists;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SavePositionPage window = new SavePositionPage();
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
	public SavePositionPage() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1165, 693);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel savePositionFileNameLabel = new JLabel("Enter Save Position File Name:");
		savePositionFileNameLabel.setBackground(Color.WHITE);
		savePositionFileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		savePositionFileNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		savePositionFileNameLabel.setBounds(10, 151, 348, 68);
		frame.getContentPane().add(savePositionFileNameLabel);

		JLabel EnterValidFileNameLabel = new JLabel("Please enter a valid file name");
		EnterValidFileNameLabel.setForeground(Color.RED);
		EnterValidFileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		EnterValidFileNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		EnterValidFileNameLabel.setBounds(441, 229, 232, 28);
		frame.getContentPane().add(EnterValidFileNameLabel);
		EnterValidFileNameLabel.setVisible(false);
		
		JTextField gameFileNameTextField = new JTextField();
		gameFileNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		gameFileNameTextField.setBounds(327, 151, 498, 68);
		frame.getContentPane().add(gameFileNameTextField);
		gameFileNameTextField.setColumns(10);

		JSplitPane PositionSavedPane = new JSplitPane();
		PositionSavedPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		PositionSavedPane.setBounds(269, 272, 614, 88);
		PositionSavedPane.setVisible(false);
		frame.getContentPane().add(PositionSavedPane);

		JLabel PositionSavedLabel = new JLabel("Position Succesfully Saved!");
		PositionSavedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		PositionSavedLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		PositionSavedPane.setLeftComponent(PositionSavedLabel);
		PositionSavedLabel.setVisible(false);

		JButton ReturntoMenu1 = new JButton("Return to Main Menu");
		ReturntoMenu1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		ReturntoMenu1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				MainGameWindow main = new MainGameWindow();
				main.frmQuoridorPlay.setVisible(true);
					
			}
		});
		PositionSavedPane.setRightComponent(ReturntoMenu1);
		PositionSavedPane.setVisible(false);
		
		JLabel OverwriteLabel = new JLabel("File already exists, Overwrite file?");
		OverwriteLabel.setHorizontalAlignment(SwingConstants.CENTER);
		OverwriteLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		OverwriteLabel.setBounds(447, 457, 258, 28);
		OverwriteLabel.setVisible(false);
		frame.getContentPane().add(OverwriteLabel);
		
		JSplitPane OverwritePane = new JSplitPane();
		OverwritePane.setBounds(314, 495, 524, 88);
		OverwritePane.setVisible(false);
		frame.getContentPane().add(OverwritePane);
		
		JButton saveFileButton = new JButton("Save File\r\n");
		saveFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				
				if(gameFileNameTextField.getText().isEmpty()) {
					EnterValidFileNameLabel.setVisible(true);
					return;
				}
				
				try {
					fileExists = QC.saveGamePosition(savePositionFileNameLabel.getText());
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(fileExists = true) {
					PositionSavedPane.setVisible(true);
					PositionSavedLabel.setVisible(true);
				}
				else {
					OverwritePane.setVisible(true);
					OverwriteLabel.setVisible(true);
				}
			}		
		});
		saveFileButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		saveFileButton.setBounds(977, 151, 165, 68);
		frame.getContentPane().add(saveFileButton);

		JLabel TitleLabel = new JLabel("Quoridor");
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		TitleLabel.setBounds(327, 10, 498, 111);
		frame.getContentPane().add(TitleLabel);

		JButton NoOvewriteButton = new JButton("No, do not overwrite the file");
		NoOvewriteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				OverwritePane.setVisible(false);
				OverwriteLabel.setVisible(false);
				
			}
		});
		NoOvewriteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		OverwritePane.setLeftComponent(NoOvewriteButton);

		JButton ConfirmOverwriteButton = new JButton("Yes, overwrite the file\r\n");
		ConfirmOverwriteButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					QC.overwriteGamePosition(gameFileNameTextField.getText());
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				OverwritePane.setVisible(false);
				OverwriteLabel.setVisible(false);
		
				PositionSavedPane.setVisible(true);
				PositionSavedLabel.setVisible(true);
			}
		});
		ConfirmOverwriteButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		OverwritePane.setRightComponent(ConfirmOverwriteButton);		
	}
}
