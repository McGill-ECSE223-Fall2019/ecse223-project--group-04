package ca.mcgill.ecse223.quoridor.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.io.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.*;
import ca.mcgill.ecse223.quoridor.model.Quoridor;

public class LoadPositionPage {
	public JFrame frame;
	private QuoridorController QC;
	private boolean valid;
	private JTextField LoadFileNameTextField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoadPositionPage window = new LoadPositionPage();
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
	public LoadPositionPage() {
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
		
		// Forces fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);

		

		LoadFileNameTextField = new JTextField();
		LoadFileNameTextField.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LoadFileNameTextField.setHorizontalAlignment(SwingConstants.CENTER);
		LoadFileNameTextField.setBounds(507, 151, 521, 68);
		frame.getContentPane().add(LoadFileNameTextField);
		LoadFileNameTextField.setColumns(10);
		
		JLabel InvalidLoadFileLabel = new JLabel("Invalid file, Please select a valid file to load");
		InvalidLoadFileLabel.setForeground(Color.RED);
		InvalidLoadFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		InvalidLoadFileLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		InvalidLoadFileLabel.setBounds(591, 121, 354, 20);
		frame.getContentPane().add(InvalidLoadFileLabel);
		InvalidLoadFileLabel.setVisible(false);
		
		JLabel LoadPositionFileLabel = new JLabel("Select File to Load:");
		LoadPositionFileLabel.setBackground(Color.WHITE);
		LoadPositionFileLabel.setHorizontalAlignment(SwingConstants.CENTER);
		LoadPositionFileLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		LoadPositionFileLabel.setBounds(132, 151, 332, 68);
		frame.getContentPane().add(LoadPositionFileLabel);
		
		JLabel InvalidFileNameLabel = new JLabel("Please enter a file name");
		InvalidFileNameLabel.setForeground(Color.RED);
		InvalidFileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		InvalidFileNameLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		InvalidFileNameLabel.setBounds(591, 229, 354, 26);
		frame.getContentPane().add(InvalidFileNameLabel);
		InvalidFileNameLabel.setVisible(false);
		
		JButton loadPositionFileButton = new JButton("Load File\r\n");
		loadPositionFileButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(LoadFileNameTextField.getText().isEmpty()) {
					InvalidFileNameLabel.setVisible(true);
					return;
				}
				try {
					valid = QC.loadSavedPosition(LoadFileNameTextField.getText());
					frame.dispose();
					MainGameWindow main = new MainGameWindow();
					main.frmQuoridorPlay.setVisible(true);
				} catch (IOException e1) {
					InvalidLoadFileLabel.setVisible(true);
				}
				if(valid = false) {
					InvalidLoadFileLabel.setVisible(true);
				}
			}
		});
		loadPositionFileButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		loadPositionFileButton.setBounds(1103, 151, 238, 68);
		frame.getContentPane().add(loadPositionFileButton);
		
		JLabel TitleLabel = new JLabel("Quoridor\r\n");
		TitleLabel.setFont(new Font("Tahoma", Font.PLAIN, 40));
		TitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		TitleLabel.setBounds(475, 10, 586, 101);
		frame.getContentPane().add(TitleLabel);
		
		JButton btnReturntoMainMenu = new JButton("Return to Main Menu");
		btnReturntoMainMenu.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frame.dispose();
				MainMenu main = new MainMenu();
				main.frame.setVisible(true);
			}
		});
		btnReturntoMainMenu.setFont(new Font("Tahoma", Font.PLAIN, 16));
		btnReturntoMainMenu.setBounds(0, 0, 194, 45);
		frame.getContentPane().add(btnReturntoMainMenu);
	}
}
