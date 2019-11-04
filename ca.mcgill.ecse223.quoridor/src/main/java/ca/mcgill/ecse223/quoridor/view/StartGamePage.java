package ca.mcgill.ecse223.quoridor.view;

import ca.mcgill.ecse223.quoridor.controller.QuoridorController;

import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StartGamePage {

	// UI elements
	public JFrame frame;
	private JTextPane QuoridorTitleField;
	private JLabel errorMessage;
	// player 1
	private JLabel PlayerLabel_1;
	private JComboBox PlayerSelect_1;
	private JButton btnAddPlayer_1;
	private DefaultComboBoxModel model_1;
	// player 2
	private JLabel PlayerLabel_2;
	private JComboBox PlayerSelect_2;
	private JButton btnAddPlayer_2;
	private DefaultComboBoxModel model_2;

	// total thinking time
	private JLabel ThinkingTime_Label;
	private JTextArea Minutes_TextField;
	private JLabel Minutes_label;
	private JTextArea Seconds_TextField;
	private JLabel Seconds_label;
	private JButton btnSetTime;
	// start game
	private JButton btnStartGame;

	// data elements
	private String error = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGamePage window = new StartGamePage();
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
	public StartGamePage() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Content title
		QuoridorTitleField = new JTextPane();
		QuoridorTitleField.setEditable(false);
		QuoridorTitleField.setForeground(Color.BLACK);
		QuoridorTitleField.setBackground(SystemColor.activeCaption);
		QuoridorTitleField.setEditable(false);
		QuoridorTitleField.setFont(new Font("Monospaced", Font.BOLD, 30));
		QuoridorTitleField.setAlignmentX(SwingConstants.CENTER);
		QuoridorTitleField.setText("Quoridor");

		// elements for Player1
		PlayerLabel_1 = new JLabel("Player 1 Name:");
		PlayerLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		model_1 = new DefaultComboBoxModel(QuoridorController.getUsers(" ").toArray());
		PlayerSelect_1 = new JComboBox(model_1);
		PlayerSelect_1.setEditable(true);
		btnAddPlayer_1 = new JButton("Select");

		// elements for Player2
		PlayerLabel_2 = new JLabel("Player 2 Name:");
		model_2 = new DefaultComboBoxModel(QuoridorController.getUsers(" ").toArray());
		PlayerSelect_2 = new JComboBox(model_2);
		PlayerSelect_2.setEditable(true);
		btnAddPlayer_2 = new JButton("Select");

		// elements for setTotalThinkingTime
		ThinkingTime_Label = new JLabel("Set Thinking Time for Both Players");
		ThinkingTime_Label.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		ThinkingTime_Label.setHorizontalAlignment(SwingConstants.CENTER);
		Minutes_TextField = new JTextArea();
		Minutes_TextField.setToolTipText("Minutes");
		Minutes_label = new JLabel("Minutes");
		Minutes_label.setHorizontalAlignment(SwingConstants.CENTER);
		Seconds_label = new JLabel("Seconds");
		Seconds_label.setHorizontalAlignment(SwingConstants.CENTER);
		Seconds_TextField = new JTextArea();
		btnSetTime = new JButton("Set Time");

		// elements for error message
		errorMessage = new JLabel("");
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setForeground(Color.RED);

		// elements for starting new game
		btnStartGame = new JButton("START GAME");

		// global settings
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Quoridor - Start New Game");

		// listeners for player1
		btnAddPlayer_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addPlayerOneUsernameActionPerformed(evt);
			}
		});
		// listeners for player2
		btnAddPlayer_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addPlayerTwoUsernameActionPerformed(evt);
			}
		});
		// listeners for setting time
		btnSetTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setTotalThinkingTimeActionPerformed(evt);
			}
		});
		// listeners for starting game
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startGameActionPerformed(evt);
			}
		});

		// layout
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(PlayerLabel_1)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(PlayerSelect_1, 0, 216, Short.MAX_VALUE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(PlayerLabel_2)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(PlayerSelect_2, 0, 216, Short.MAX_VALUE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnAddPlayer_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAddPlayer_2, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(58)
							.addComponent(Minutes_label)
							.addGap(12)
							.addComponent(Minutes_TextField, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(Seconds_label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Seconds_TextField, GroupLayout.DEFAULT_SIZE, 65, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSetTime, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(ThinkingTime_Label, GroupLayout.DEFAULT_SIZE, 444, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(162)
					.addComponent(btnStartGame, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(164))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(errorMessage, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(QuoridorTitleField, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(QuoridorTitleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(PlayerLabel_1)
						.addComponent(PlayerSelect_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddPlayer_1))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(PlayerLabel_2)
						.addComponent(PlayerSelect_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAddPlayer_2))
					.addGap(12)
					.addComponent(ThinkingTime_Label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(Minutes_label)
						.addComponent(Minutes_TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(Seconds_label)
						.addComponent(Seconds_TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSetTime))
					.addGap(18)
					.addComponent(errorMessage, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnStartGame)
					.addGap(24))
		);
		frame.getContentPane().setLayout(groupLayout);

	}
	// Methods
	private void startGameActionPerformed(ActionEvent evt) {
		// clears error message
		error = null;
		try {
			errorMessage.setForeground(Color.BLACK);
			errorMessage.setText(QuoridorController.testMethod());
		} catch (Exception e) {
			errorMessage.setText(e.getMessage());
			System.out.println(e.getMessage());
		}
	}
	private void addPlayerOneUsernameActionPerformed(ActionEvent evt) {
		// NOTE: The model_1 and model_2 calls the controller methods that use the ProvideSelectUsername Features
		if(String.valueOf(PlayerSelect_1.getSelectedItem()).equals(String.valueOf(PlayerSelect_2.getSelectedItem()))) {
			errorMessage.setText("Both players cannot have the same username.");
			if (String.valueOf(PlayerSelect_1.getSelectedItem()).isEmpty() || " ".equals(String.valueOf(PlayerSelect_1.getSelectedItem()))) {
				errorMessage.setText("Please enter a username for player 1.");
			}
		}else {
			errorMessage.setText(null);
			// if the username does not exist within the list
			if (model_1.getIndexOf(PlayerSelect_1.getSelectedItem()) == -1) {
				model_1.addElement(PlayerSelect_1.getSelectedItem());
				model_2.addElement(PlayerSelect_1.getSelectedItem());
//				QuoridorController.setWhitePlayerUserName(PlayerSelect_1.getSelectedItem().toString());
			}
		}
	}

	private void addPlayerTwoUsernameActionPerformed(ActionEvent evt) {
		// NOTE: The model_1 and model_2 calls the controller methods that use the ProvideSelectUsername Features
		if(String.valueOf(PlayerSelect_1.getSelectedItem()).equals(String.valueOf(PlayerSelect_2.getSelectedItem()))) {
			errorMessage.setText("Both players cannot have the same username.");
			if (String.valueOf(PlayerSelect_2.getSelectedItem()).isEmpty() || " ".equals(String.valueOf(PlayerSelect_2.getSelectedItem()))) {
				errorMessage.setText("Please enter a username for player 2.");
			}
		}else {
			errorMessage.setText(null);
			// if the username does not exist within the list
			if (model_2.getIndexOf(PlayerSelect_2.getSelectedItem()) == -1) {
				model_2.addElement(PlayerSelect_2.getSelectedItem());
				model_1.addElement(PlayerSelect_2.getSelectedItem());
//				QuoridorController.setBlackPlayerUserName(PlayerSelect_2.getSelectedItem().toString());
			}
		}
	}

	private void setTotalThinkingTimeActionPerformed(ActionEvent evt) {
		// clears error message
		error = null;

		// call controller
		Integer min = Integer.parseInt(Minutes_TextField.getText());
		Integer sec = Integer.parseInt(Seconds_TextField.getText());
		long thinkingTime = min* 60L *1000 + sec* 1000L;
		try {
			QuoridorController.setThinkingTime(min, sec);
			errorMessage.setForeground(Color.BLACK);
			errorMessage.setText("Time set to: " + min +" minutes and " +sec+ " seconds.");
		} catch (Exception e) {
			error = e.getMessage();
			errorMessage.setForeground(Color.RED);
			errorMessage.setText("Invalid time input. Please enter integers.");
			System.err.println("Not calling controller method ... ");
		}
	}
}
