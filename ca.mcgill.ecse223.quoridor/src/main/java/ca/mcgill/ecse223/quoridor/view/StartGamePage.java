package ca.mcgill.ecse223.quoridor.view;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Direction;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Quoridor;
import ca.mcgill.ecse223.quoridor.model.User;
import ca.mcgill.ecse223.quoridor.utilities.ControllerUtilities;

import java.awt.*;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.Time;
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
	private JButton btnNewButton;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartGamePage window = new StartGamePage();
					initGame();
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
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - frame.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - frame.getHeight()) / 2);
		frame.getContentPane().setLocation(x, y);
		// Forces fullscreen
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.getContentPane().setLayout(null);
		model_1 = new DefaultComboBoxModel(QuoridorController.getUsers(" ").toArray());
		model_2 = new DefaultComboBoxModel(QuoridorController.getUsers(" ").toArray());


		// global settings
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setTitle("Quoridor - Start New Game");

		// Content title
		QuoridorTitleField = new JTextPane();
		QuoridorTitleField.setLocation(50, 50);
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
		PlayerSelect_1 = new JComboBox(model_1);
		PlayerSelect_1.setEditable(true);
		btnAddPlayer_1 = new JButton("Select");

		// listeners for player1
		btnAddPlayer_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addPlayerOneUsernameActionPerformed(evt);
			}
		});

		// elements for Player2
		PlayerLabel_2 = new JLabel("Player 2 Name:");
		PlayerSelect_2 = new JComboBox(model_2);
		PlayerSelect_2.setEditable(true);
		btnAddPlayer_2 = new JButton("Select");
		// listeners for player2
		btnAddPlayer_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				addPlayerTwoUsernameActionPerformed(evt);
			}
		});

		// elements for setTotalThinkingTime
		ThinkingTime_Label = new JLabel("Set Thinking Time for Both Players");
		ThinkingTime_Label.setFont(new Font("Lucida Grande", Font.BOLD, 13));
		ThinkingTime_Label.setHorizontalAlignment(SwingConstants.CENTER);
		Minutes_label = new JLabel("Minutes");
		Minutes_label.setHorizontalAlignment(SwingConstants.CENTER);
		Minutes_TextField = new JTextArea();
		Minutes_TextField.setToolTipText("Minutes");
		Seconds_label = new JLabel("Seconds");
		Seconds_label.setHorizontalAlignment(SwingConstants.CENTER);
		Seconds_TextField = new JTextArea();

		// elements for starting new game
		btnStartGame = new JButton("START GAME");
		// listeners for starting game
		btnStartGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				startGameActionPerformed(evt);
			}
		});
		btnSetTime = new JButton("Set Time");
		// listeners for setting time
		btnSetTime.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				setTotalThinkingTimeActionPerformed(evt);
			}
		});
		// listeners for back button
		btnNewButton = new JButton("BACK");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				backActionPerformed(evt);
			}
		});

		// elements for error message
		errorMessage = new JLabel("");
		errorMessage.setFont(new Font("Monospaced", Font.BOLD, 30));
		errorMessage.setHorizontalAlignment(SwingConstants.CENTER);
		errorMessage.setForeground(Color.RED);
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(QuoridorTitleField, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(PlayerLabel_1, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(PlayerSelect_1, 0, 200, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(btnAddPlayer_1, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(PlayerLabel_2, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(PlayerSelect_2, 0, 200, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(btnAddPlayer_2, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE))
				.addComponent(ThinkingTime_Label, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(73)
					.addComponent(Minutes_label, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(Minutes_TextField, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(Seconds_label, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(Seconds_TextField, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
					.addGap(27)
					.addComponent(btnSetTime, GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE)
					.addGap(23))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnStartGame, GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
					.addContainerGap())
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(12)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(errorMessage, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)
							.addGap(21))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(QuoridorTitleField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(PlayerSelect_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(PlayerLabel_1)))
						.addComponent(btnAddPlayer_1))
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(PlayerSelect_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(PlayerLabel_2)))
						.addComponent(btnAddPlayer_2))
					.addGap(5)
					.addComponent(ThinkingTime_Label)
					.addGap(5)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(Minutes_label))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(Minutes_TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(Seconds_label))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(6)
							.addComponent(Seconds_TextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnSetTime))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStartGame, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(errorMessage, GroupLayout.DEFAULT_SIZE, 11, Short.MAX_VALUE)
					.addGap(17)
					.addComponent(btnNewButton))
		);
		frame.getContentPane().setLayout(groupLayout);

	}
	// Methods
	private void startGameActionPerformed(ActionEvent evt) {
		// clears error message
		error = null;
		frame.dispose();
		try {
			MainGameWindow gameWindow = new MainGameWindow();
			MainGameWindow.frmQuoridorPlay.setVisible(true);
			frame.dispose();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMessage.setText(e.getMessage());
		}
	}
	private void backActionPerformed(ActionEvent evt) {
		// clears error message
		error = null;
		try {
			MainMenu mainMenu = new MainMenu();
			mainMenu.frame.setVisible(true);
			frame.dispose();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			errorMessage.setText(e.getMessage());
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
				errorMessage.setForeground(Color.BLACK);
				errorMessage.setText("Username: " + PlayerSelect_1.getSelectedItem() + " created.");
				try {
					QuoridorController.createNewUsernameGUI(PlayerSelect_1.getSelectedItem().toString());
				} catch (Exception e) {
					error = e.getMessage();
					System.err.println("Not calling controller method ... ");
				}
			} else {
				QuoridorController.selectAnExistingUsernameGUI(PlayerSelect_1.getSelectedItem().toString());
				errorMessage.setForeground(Color.BLACK);
				errorMessage.setText("Selected existing username: "+ PlayerSelect_1.getSelectedItem());
				System.out.println("User selected an existing username.");
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
				errorMessage.setForeground(Color.BLACK);
				errorMessage.setText("Username: " + PlayerSelect_2.getSelectedItem() + " created.");
				try {
					QuoridorController.createNewUsernameGUI(PlayerSelect_2.getSelectedItem().toString());
				} catch (Exception e) {
					error = e.getMessage();
					System.err.println("Not calling controller method ... ");
				}
			} else {
				QuoridorController.selectAnExistingUsernameGUI(PlayerSelect_2.getSelectedItem().toString());
				errorMessage.setForeground(Color.BLACK);
				errorMessage.setText("Selected existing username: " + PlayerSelect_2.getSelectedItem());
				System.out.println("User selected an existing username.");
			}
		}
	}

	private void setTotalThinkingTimeActionPerformed(ActionEvent evt) {
		// clears error message
		error = null;

		// call controller
		Integer min = Integer.parseInt(Minutes_TextField.getText().trim());
		Integer sec = Integer.parseInt(Seconds_TextField.getText().trim());
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
	
	private static void initGame() {
		Quoridor quoridor = QuoridorApplication.getQuoridor();
		// TODO: Set the correct names!
		User userWhite = quoridor.addUser("DummyName1");
		User userBlack = quoridor.addUser("DummyName2");
		Player whitePlayer = new Player(new Time(0), quoridor.getUser(0), ControllerUtilities.BLACK_TILE_INDEX, Direction.Horizontal);
		Player blackPlayer = new Player(new Time(0), quoridor.getUser(1), ControllerUtilities.WHITE_TILE_INDEX, Direction.Horizontal);
		try {
			QuoridorController.initializeNewGame(quoridor, whitePlayer, blackPlayer);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}