package ca.mcgill.ecse223.quoridor.view;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JTextPane;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SpringLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicArrowButton;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game;
import ca.mcgill.ecse223.quoridor.model.Move;
import ca.mcgill.ecse223.quoridor.model.Player;
import ca.mcgill.ecse223.quoridor.model.Tile;
import ca.mcgill.ecse223.quoridor.utilities.ControllerUtilities;
import ca.mcgill.ecse223.quoridor.utilities.ControllerUtilities.PathAvailableToPlayers;
import ca.mcgill.ecse223.quoridor.view.QuoridorPage;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import ca.mcgill.ecse223.quoridor.view.main.BlackWallPanel;
import ca.mcgill.ecse223.quoridor.view.main.WhiteWallPanel;
import ca.mcgill.ecse223.quoridor.view.*;

import ca.mcgill.ecse223.quoridor.view.main.BlackWallPanel;
import ca.mcgill.ecse223.quoridor.view.main.WhiteWallPanel;
import ca.mcgill.ecse223.quoridor.view.*;
import javax.swing.JTextArea;

public class MainGameWindow {

	// UI elements
	public static JFrame frmQuoridorPlay;
	// time remaining
	private JTextField timeRemaining_Label;
	private static JTextField timeRemaining_TextFieldBlack;
	private static JTextField timeRemaining_TextFieldWhite;
	// current player
	private JTextField currentPlayer_Label;
	private JTextField currentPlayer_TextField;
	// white player

	private JTextField wallsInStockWhitePlayer_Label;
	private JTextField wallsInStockWhitePlayer_TextField;
	private JTextField wallsOnBoardWhitePlayer_Label;
	private JTextField wallsOnBoardWhitePlayer_TextField;
	// black player
	private JTextField blackPlayer_Label;
	private JTextField wallsInStockBlackPlayer_Label;
	private JTextField wallsInStockBlackPlayer_TextField;
	private JTextField wallsOnBoardBlackPlayer_Label;
	private JTextField wallsOnBoardBlackPlayer_TextField;
	// tiles
	private static final int TOTAL_NUMBER_OF_TILES = 81;
	private static final int TOTAL_ROWS = 9;
	private static final int TOTAL_COLS = 9;
	private static JButton[][] btnArray = new JButton[TOTAL_ROWS][TOTAL_COLS];
	// walls
	private static JButton[] wallArray = new JButton[20];
	private JButton btnPlaceNewWall;
	private JButton btnNewButton;
	private JButton button;
	private JButton saveGameButton = new JButton("Save Game");
	private static int wallWidth = 185;
	private static int wallWidthV = 11;
	private static int wallHeight = 102;
	private static boolean WallGrabbed = false;
	private static int CurrRow;
	private static int CurrCol;
	static JLabel errorMessage = new JLabel("Incorrect Move");
	private static int wallIndex;
	private static int tileLength = 45;
	private static int tileWidth = 87;
	private static JPanel panel_10 = new JPanel();

	private static JPanel panel_10_1 = new JPanel();
	private static JPanel panel_11 = new JPanel();
	private static JPanel centerPanel = new JPanel();
	private static JPanel boardPanel = new JPanel();
	private static JPanel navigationButtonsPanel = new JPanel();
	private MoveCandidate wallMoveCandidate;
	private MoveCandidate blackPawnMove;
	private MoveCandidate whitePawnMove;
	private boolean gameStarted = false;
	private static BlackWallPanel blackPane;
	private static WhiteWallPanel whitePane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainGameWindow window = new MainGameWindow();
					window.frmQuoridorPlay.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainGameWindow() {
		try {
			initialize();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws InterruptedException {
		// layout
		frmQuoridorPlay = new JFrame();
		frmQuoridorPlay.setTitle("Quoridor - Play Game");
		frmQuoridorPlay.setBounds(100, 100, 1256, 876);
		frmQuoridorPlay.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmQuoridorPlay.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frmQuoridorPlay.setVisible(true);
		JPanel leftPanel = new JPanel();
		leftPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmQuoridorPlay.getContentPane().add(leftPanel, BorderLayout.WEST);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(null);
		leftPanel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new BoxLayout(panel_3, BoxLayout.Y_AXIS));

		playerTimeLineHandler(panel_3, "white");
		wallsHandler(panel_3, "white");

		JPanel southPanel = new JPanel();
		frmQuoridorPlay.getContentPane().add(southPanel, BorderLayout.SOUTH);
		saveGameButton.setText("SAVE");
		saveGameButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				frmQuoridorPlay.dispose();
				SaveGamePage saveGamePage = new SaveGamePage();
				saveGamePage.frame.setVisible(true);
			}
		});
		saveGameButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
		southPanel.add(saveGameButton);

		JButton resignBtn = new JButton("RESIGN");
		resignBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		southPanel.add(resignBtn);
		resignBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				resignGame(evt);
			}
		});
		JButton replayBtn = new JButton("Replay");
		frmQuoridorPlay.getContentPane().add(southPanel, BorderLayout.SOUTH);
		replayBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// ReplayMode rep = new ReplayMode();
				resetBackGame();
			}
		});
		replayBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		southPanel.add(replayBtn);
		JPanel rightPanel = new JPanel();
		rightPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmQuoridorPlay.getContentPane().add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS));
		JPanel panel_6 = new JPanel();
		rightPanel.add(panel_6);
		panel_6.setLayout(new GridLayout(2, 1, 0, 0));
		JPanel panel_7 = new JPanel();
		panel_6.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));

		playerTimeLineHandler(panel_7, "black");
		wallsHandler(panel_7, "black");
		// layout
		panel_6.add(panel_11);

		// elements for label selectMove
		errorMessage.setBackground(Color.RED);
		errorMessage.setForeground(Color.RED);
		errorMessage.setFont(new Font("Tahoma", Font.BOLD, 25));
		errorMessage.setBounds(410, 350, 400, 52);
		frmQuoridorPlay.getContentPane().add(errorMessage);
		errorMessage.setVisible(false);

		// elements for blackPlayer

		blackPane = new BlackWallPanel(panel_11);
		whitePane = new WhiteWallPanel(panel_10_1);
		panel_2.add(panel_10_1);

		// layout
		frmQuoridorPlay.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - centerPanel.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - centerPanel.getHeight()) / 2);
		centerPanel.setLocation(x, y);
		centerPanel.add(boardPanel);
		centerPanel.add(navigationButtonsPanel);
		boardPanel.setLayout(null);
		boardPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		boardPanel.setPreferredSize(new Dimension(x, y + 500));
		boardPanel.setBackground(Color.lightGray);
		navigationButtonsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));

		centerPanel.add(boardPanel);
		centerPanel.add(navigationButtonsPanel);

		boardPanel.setLayout(null);

		boardPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		boardPanel.setPreferredSize(new Dimension(x, y + 500));
		boardPanel.setBackground(Color.lightGray);
		navigationButtonsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		for (int row = 0; row < TOTAL_ROWS; row++) {
			for (int col = 0; col < TOTAL_COLS; col++) {
				errorMessage.setVisible(false);
				btnArray[row][col] = new JButton(new ImageIcon("./tile.png"));
				btnArray[row][col].setVisible(true);

				// This may look wrong, but making it right flips everything, so don't touch plz
				// -tbutch
				btnArray[row][col].setBounds((tileWidth + 11) * col, (tileLength + 11) * row, tileWidth, tileLength);
				boardPanel.add(btnArray[row][col]);
				// btnArray[row][col].addMouseListener(new ButtonActionListener(row + 1, col +
				// 1));
			}
		}
		createBlackAndWhitePawns();

		frmQuoridorPlay.repaint();
		JButton grabWall = new JButton("Grab Wall");
		JButton dropWall = new JButton("Drop Wall");
		JButton rotateWall = new JButton("Rotate Wall");
		JPanel wallActionsPanel = new JPanel();
		wallActionsPanel.setLayout(new GridLayout(1, 3));
		wallActionsPanel.add(grabWall);
		wallActionsPanel.add(dropWall);
		wallActionsPanel.add(rotateWall);
		navigationButtonsPanel.setLayout(new GridLayout(2, 1));
		navigationButtonsPanel.add(wallActionsPanel);

		WallCandidateHandler(grabWall, dropWall);
		dropWallHandler(dropWall, grabWall);
		rotateWallHandler(rotateWall);
		createNavigationButtons();

		JPanel northPanel = new JPanel();
		northPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frmQuoridorPlay.getContentPane().add(northPanel, BorderLayout.NORTH);
		northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));

		JTextPane txtpnQuori = new JTextPane();
		txtpnQuori.setBackground(SystemColor.activeCaption);
		txtpnQuori.setEditable(false);
		txtpnQuori.setFont(new Font("Tahoma", Font.BOLD, 30));
		txtpnQuori.setText("Quoridor");
		northPanel.add(txtpnQuori);

		JPanel panel = new JPanel();
		northPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		// elements for currentPlayer
		currentPlayer_Label = new JTextField();
		currentPlayer_Label.setEditable(false);
		currentPlayer_Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		currentPlayer_Label.setText("Current Player:");
		currentPlayer_Label.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(currentPlayer_Label);
		currentPlayer_Label.setColumns(10);
		currentPlayer_TextField = new JTextField();
		currentPlayer_TextField.setEditable(false);
		panel.add(currentPlayer_TextField);
		currentPlayer_TextField.setColumns(10);
		// print statement: for checking if we get the right users
		// this will get the names of player one and two that was set in prev. window
		String playerName = "";
		try {
			playerName = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.getUser().getName();
		} catch (Exception e) {
		}
		currentPlayer_TextField.setText(playerName);
		currentPlayer_TextField.setFont(new Font("Tahoma", Font.BOLD, 13));
		// layout
		JPanel panel_1 = new JPanel();
		northPanel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		// elements for remaining time
		timeRemaining_Label = new JTextField();
		timeRemaining_Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		timeRemaining_Label.setText("Time Remaining:");
		timeRemaining_Label.setEditable(false);
		panel_1.add(timeRemaining_Label);
		timeRemaining_Label.setColumns(10);
		timeRemaining_TextFieldBlack = new JTextField();
		timeRemaining_TextFieldBlack.setEditable(false);
		panel_1.add(timeRemaining_TextFieldBlack);
		timeRemaining_TextFieldBlack.setColumns(10);
		String remainingTimeValue = "";
		try {
			remainingTimeValue = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime()
					.toString();
		} catch (Exception e) {
		}
		timeRemaining_TextFieldBlack.setText(remainingTimeValue);

		JButton btnStartWhiteTimer = new JButton("Start White Timer to start the Game!");
		btnStartWhiteTimer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				QuoridorController.startClock();
				btnStartWhiteTimer.setVisible(false);
				getAvailableMovesToCurrentPlayer();
				gameStarted = true;
			}
		});
		northPanel.add(btnStartWhiteTimer);
	}

	private boolean StepBackHandler(MoveCandidate moveCandidate) {
		List<Move> list = QuoridorController.getPawnsPosition();

		Tile curPlayerTile;
		if (!QuoridorController.isWhitePlayer()) {
			curPlayerTile = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhitePosition()
					.getTile();
		} else {
			curPlayerTile = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackPosition()
					.getTile();
		}
		int row = curPlayerTile.getRow();
		int col = curPlayerTile.getColumn();
		int index = list.size() - 1;
		if (list.size() > 1) {

			Move prev;
			// int row = prev.get
			prev = list.get(index - 2);

			moveCandidate.row = prev.getTargetTile().getRow() - 1;
			moveCandidate.col = prev.getTargetTile().getColumn() - 1;
			Game game = QuoridorApplication.getQuoridor().getCurrentGame();
			switchCurrentPlayerGuiAndBackend();
			System.out.println(" row  + col : " + row + ", " + col);
			System.out.println(" row  + col : " + moveCandidate.row + ", " + moveCandidate.col);
			ControllerUtilities.MoveDirections dir = null;
			if (prev.getTargetTile().getRow() < row) {
				dir = ControllerUtilities.MoveDirections.up;

			} else if (prev.getTargetTile().getRow() > row) {
				dir = ControllerUtilities.MoveDirections.up;

			} else if (prev.getTargetTile().getColumn() < col) {
				dir = ControllerUtilities.MoveDirections.right;

			} else if (prev.getTargetTile().getColumn() > col) {
				dir = ControllerUtilities.MoveDirections.left;
			}
			Player curPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
					.getPlayerToMove();
			
			btnArray[row-1][col-1].remove(moveCandidate.wallMoveBtn);

			btnArray[moveCandidate.row][moveCandidate.col].add(moveCandidate.wallMoveBtn);
			// moveHandler(dir);
			QuoridorController.movePawn(curPlayer, dir.toString());

			if (prev.getPlayer().equals(QuoridorController.getWhitePlayer())) {
				whitePawnMove = moveCandidate;
			} else {
				blackPawnMove = moveCandidate;
			}
			

			if (wallMoveCandidate == null) {
				// QuoridorController.switchCurrentPlayer();
				switchCurrentPlayerGuiAndBackend();
				QuoridorController.updateGameStatus();
				GameStatus currentStatus = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
				if (!currentStatus.equals(GameStatus.Running)) {
					// Here, end the game and display the winner! or the draw if that is the case
					QuoridorController.endGameAndReportResult();
					gameStarted = false;
					GameWonPopup gameWon = new GameWonPopup();
					gameWon.frame.setVisible(true);
				}
				
				return true;

			}

			getAvailableMovesToCurrentPlayer();
			frmQuoridorPlay.repaint();
		}

		return false;
	}

	private void resetBackGame() {
		// Game
		Player currentPlayer = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition()
				.getPlayerToMove();
		MoveCandidate moveCandidate = !currentPlayer.equals(QuoridorController.getWhitePlayer()) ? whitePawnMove
				: blackPawnMove;
		if (StepBackHandler(moveCandidate)) {
			// switchCurrentPlayerGuiAndBackend();
			// currentPlayer = QuoridorController.getCurrentPlayer();

			// btnArray[moveCandidate.row][moveCandidate.col].remove(moveCandidate.wallMoveBtn);

			// btnArray[moveCandidate.row][moveCandidate.col].add(moveCandidate.wallMoveBtn);

			// if (currentPlayer.equals(QuoridorController.getWhitePlayer())) {
			// whitePawnMove = moveCandidate;
			// } else {
			// blackPawnMove = moveCandidate;
			// }

			// switchCurrentPlayerGuiAndBackend();

			frmQuoridorPlay.repaint();
		}
	}

	private void switchCurrentPlayerGuiAndBackend() {
		String playerName = "";
		try {
			playerName = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getPlayerToMove()
					.getUser().getName();
		} catch (Exception e) {
		}
		// validatePawnPosition();
		// Clocks are all taken care of in the switchCurrentPlayer method
		QuoridorController.switchCurrentPlayer();
		currentPlayer_TextField.setFont(new Font("Tahoma", Font.BOLD, 13));
		currentPlayer_TextField.setText(QuoridorController.getCurrentPlayer().getUser().getName());
		timeRemaining_TextFieldBlack.setText(QuoridorController.getCurrentPlayer().getRemainingTime().toString());

	}

	private void createBlackAndWhitePawns() {

		JButton whitePawn = new JButton();
		whitePawn.setIcon(new ImageIcon("./whitePawn.png"));
		whitePawn.setPreferredSize(new Dimension(3, 3));

		whitePawn.setMaximumSize(new Dimension(20, 20));

		whitePawn.setBounds(btnArray[0][4].getX() + tileWidth, btnArray[0][4].getY() + tileLength, 20, 20);
		JButton blackPawn = new JButton();
		blackPawn.setIcon(new ImageIcon("./lightWall.png"));

		btnArray[8][4].add(blackPawn);
		btnArray[0][4].add(whitePawn);

		blackPawn.setMaximumSize(new Dimension(20, 20));
		blackPawn.setBounds(btnArray[8][4].getX() + tileWidth / 2, btnArray[8][4].getY(), 20, 20);
		this.blackPawnMove = new MoveCandidate(blackPawn, 8, 4);
		this.whitePawnMove = new MoveCandidate(whitePawn, 0, 4);
	}

	private void wallsHandler(JPanel jPanel, String player) {
		// walls in stock for whitePlayer
		JPanel panel_4 = new JPanel();
		if (player.equalsIgnoreCase("black")) {

			FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
			flowLayout_1.setAlignment(FlowLayout.LEFT);
			wallsInStockBlackPlayer_Label = new JTextField();
			wallsInStockBlackPlayer_Label.setEditable(false);
			wallsInStockBlackPlayer_Label.setText("Walls in Stock:");
			panel_4.add(wallsInStockBlackPlayer_Label);
			wallsInStockBlackPlayer_Label.setColumns(10);
			wallsInStockBlackPlayer_TextField = new JTextField();
			wallsInStockBlackPlayer_TextField.setEditable(false);
			panel_4.add(wallsInStockBlackPlayer_TextField);
			wallsInStockBlackPlayer_TextField.setColumns(10);
			String value = "";
			try {
				value = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsInStock()
						.size() + "";
			} catch (Exception e) {
			}
			wallsInStockBlackPlayer_TextField.setText(value);
		} else if (player.equalsIgnoreCase("white")) {
			FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
			flowLayout_1.setAlignment(FlowLayout.LEFT);
			wallsInStockWhitePlayer_Label = new JTextField();
			wallsInStockWhitePlayer_Label.setEditable(false);
			wallsInStockWhitePlayer_Label.setText("Walls in Stock:");
			panel_4.add(wallsInStockWhitePlayer_Label);
			wallsInStockWhitePlayer_Label.setColumns(10);
			wallsInStockWhitePlayer_TextField = new JTextField();
			wallsInStockWhitePlayer_TextField.setEditable(false);
			panel_4.add(wallsInStockWhitePlayer_TextField);
			wallsInStockWhitePlayer_TextField.setColumns(10);
			String value = "";
			try {
				value = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsInStock()
						.size() + "";
			} catch (Exception e) {
			}
			wallsInStockWhitePlayer_TextField.setText(value);
		}

		JPanel panel_5 = new JPanel();

		if (player.equalsIgnoreCase("black")) {
			FlowLayout flowLayout_2 = (FlowLayout) panel_5.getLayout();
			flowLayout_2.setAlignment(FlowLayout.LEFT);
			wallsInStockBlackPlayer_Label = new JTextField();
			wallsInStockBlackPlayer_Label.setEditable(false);
			wallsInStockBlackPlayer_Label.setText("Walls On Board:");
			panel_5.add(wallsInStockBlackPlayer_Label);
			wallsInStockBlackPlayer_Label.setColumns(10);
			wallsInStockBlackPlayer_TextField = new JTextField();
			wallsInStockBlackPlayer_TextField.setEditable(false);
			panel_5.add(wallsInStockBlackPlayer_TextField);
			wallsInStockBlackPlayer_TextField.setColumns(10);
			String value = "";
			try {
				value = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getBlackWallsOnBoard()
						.size() + "";
			} catch (Exception e) {
			}
			wallsInStockBlackPlayer_TextField.setText(value);
		} else if (player.equalsIgnoreCase("white")) {
			FlowLayout flowLayout_2 = (FlowLayout) panel_5.getLayout();
			flowLayout_2.setAlignment(FlowLayout.LEFT);
			wallsInStockWhitePlayer_Label = new JTextField();
			wallsInStockWhitePlayer_Label.setEditable(false);
			wallsInStockWhitePlayer_Label.setText("Walls On Board:");
			panel_5.add(wallsInStockWhitePlayer_Label);
			wallsInStockWhitePlayer_Label.setColumns(10);
			wallsInStockWhitePlayer_TextField = new JTextField();
			wallsInStockWhitePlayer_TextField.setEditable(false);
			panel_5.add(wallsInStockWhitePlayer_TextField);
			wallsInStockWhitePlayer_TextField.setColumns(10);
			String value = "";
			try {
				value = QuoridorApplication.getQuoridor().getCurrentGame().getCurrentPosition().getWhiteWallsOnBoard()
						.size() + "";
			} catch (Exception e) {
			}
			wallsInStockWhitePlayer_TextField.setText(value);
		}

		jPanel.add(panel_4);
		jPanel.add(panel_5);

		frmQuoridorPlay.repaint();
	}

	private void playerTimeLineHandler(JPanel jPanel, String player) {

		JPanel timePanel = new JPanel();
		timeRemaining_Label = new JTextField();
		timeRemaining_Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		timeRemaining_Label.setText("Time Remaining:");
		timeRemaining_Label.setEditable(false);
		timePanel.add(timeRemaining_Label);
		timeRemaining_Label.setColumns(10);
		timeRemaining_TextFieldWhite = new JTextField();
		timeRemaining_TextFieldWhite.setEditable(false);
		timePanel.add(timeRemaining_TextFieldWhite);
		timeRemaining_TextFieldWhite.setColumns(10);
		String time = "";
		try {
			time = QuoridorApplication.getQuoridor().getCurrentGame().getWhitePlayer().getRemainingTime().toString();
		} catch (Exception e) {
		}

		timeRemaining_TextFieldWhite.setText(time);
		JPanel playerPanel = new JPanel();
		JTextField newPlayerLabel = new JTextField();
		newPlayerLabel.setEditable(false);
		newPlayerLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		if (player.equalsIgnoreCase("White")) {
			newPlayerLabel.setText("White Player:");
		} else if (player.equalsIgnoreCase("Black")) {
			newPlayerLabel.setText("Black Player:");
		}
		JTextField newPlayerTextFeild = new JTextField();

		newPlayerLabel.setHorizontalAlignment(SwingConstants.LEFT);
		playerPanel.add(newPlayerLabel);
		newPlayerLabel.setColumns(10);

		newPlayerTextFeild.setEditable(false);
		playerPanel.add(newPlayerTextFeild);
		newPlayerTextFeild.setColumns(10);
		// print statement: for checking if we get the right users
		// this will get the names of player one and two that was set in prev. window
		String name = "";
		try {
			name = player.equalsIgnoreCase("white") ? QuoridorApplication.getQuoridor().getUser(0).getName()
					: QuoridorApplication.getQuoridor().getUser(1).getName();
		} catch (Exception e) {
		}
		newPlayerTextFeild.setText(name);
		jPanel.add(playerPanel);
		jPanel.add(timePanel);
		frmQuoridorPlay.repaint();
	}

	private void rotateWallHandler(JButton rotateWall) {
		rotateWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wallMoveCandidate != null) {
					// if rotated then it is in vertical position
					if (wallMoveCandidate.isRotated) {
						setHorizontal();
						wallMoveCandidate.isRotated = false;
					} else {
						setVertical();
						wallMoveCandidate.isRotated = true;
					}
				}
			}
		});
	}

	private void setHorizontal() {

		wallMoveCandidate.wallMoveBtn.setBounds(btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getX(),
				btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY() + tileLength, wallWidth, 13);

	}

	private void setVertical() {
		wallMoveCandidate.wallMoveBtn.setBounds(
				btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getX() + tileWidth,
				btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY(), 13, 102);
	}

	private void moveUpHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.up);
			}
		});
	}

	private void moveDownHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.down);
			}
		});
	}

	private void moveLeftHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.left);
			}
		});
	}

	private void moveRightHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.right);
			}
		});
	}

	private void moveUpRightHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.upright);
			}
		});
	}

	private void moveDownRightHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.downright);
			}
		});
	}

	private void moveUpLeftHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.upleft);
			}
		});
	}

	private void moveDownLeftHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moveHandler(ControllerUtilities.MoveDirections.downleft);
			}
		});
	}

	private void moveHandler(ControllerUtilities.MoveDirections dir) {

		// If a wall move is instantiated, move the wall move candidate!
		boolean playerMoved = false;

		if (!gameStarted) {
			return;
		}
		if (wallMoveCandidate != null) {
			if (dir.equals(ControllerUtilities.MoveDirections.up)
					&& wallMoveIsValidated(wallMoveCandidate.row - 1, wallMoveCandidate.col)) {
				wallMoveCandidate.row -= 1;
			} else if (dir.equals(ControllerUtilities.MoveDirections.down)
					&& wallMoveIsValidated(wallMoveCandidate.row + 1, wallMoveCandidate.col)) {
				wallMoveCandidate.row += 1;
			} else if (dir.equals(ControllerUtilities.MoveDirections.left)
					&& wallMoveIsValidated(wallMoveCandidate.row, wallMoveCandidate.col - 1)) {
				wallMoveCandidate.col -= 1;
			} else if (dir.equals(ControllerUtilities.MoveDirections.right)
					&& wallMoveIsValidated(wallMoveCandidate.row, wallMoveCandidate.col + 1)) {
				wallMoveCandidate.col += 1;
			}

			// Rotate the wall if necessary
			if (!wallMoveCandidate.isRotated) {
				setHorizontal();
			} else {
				setVertical();
			}

			// If no wall move candidate exists, move the pawn!
		} else {
			Player currentPlayer = QuoridorController.getCurrentPlayer();
			int targetRow = 0;
			int targetCol = 0;
			boolean targetModified = false;

			MoveCandidate moveCandidate = currentPlayer.equals(QuoridorController.getWhitePlayer()) ? whitePawnMove
					: blackPawnMove;
			targetRow = moveCandidate.row;
			targetCol = moveCandidate.col;
			// TODO check this out
			if (dir.equals(ControllerUtilities.MoveDirections.up)) {
				if (validateIfPawnMoveIsPossible(moveCandidate.row - 1, moveCandidate.col)) {
					targetModified = true;
					targetRow = moveCandidate.row - 1;
				} else if (validateIfPawnMoveIsPossible(moveCandidate.row - 2, moveCandidate.col)) {
					targetModified = true;
					targetRow = moveCandidate.row - 2;
				}
			} else if (dir.equals(ControllerUtilities.MoveDirections.down)) {
				if (validateIfPawnMoveIsPossible(moveCandidate.row + 1, moveCandidate.col)) {
					targetModified = true;
					targetRow = moveCandidate.row + 1;
				} else if (validateIfPawnMoveIsPossible(moveCandidate.row + 2, moveCandidate.col)) {
					targetModified = true;
					targetRow = moveCandidate.row + 2;
				}
			} else if (dir.equals(ControllerUtilities.MoveDirections.left)) {
				if (validateIfPawnMoveIsPossible(moveCandidate.row, moveCandidate.col - 1)) {
					targetModified = true;
					targetCol = moveCandidate.col - 1;
				} else if (validateIfPawnMoveIsPossible(moveCandidate.row, moveCandidate.col - 2)) {
					targetModified = true;
					targetCol = moveCandidate.col - 2;
				}
			} else if (dir.equals(ControllerUtilities.MoveDirections.right)) {
				if (validateIfPawnMoveIsPossible(moveCandidate.row, moveCandidate.col + 1)) {
					targetModified = true;
					targetCol = moveCandidate.col + 1;
				} else if (validateIfPawnMoveIsPossible(moveCandidate.row, moveCandidate.col + 2)) {
					targetModified = true;
					targetCol = moveCandidate.col + 2;
				}
			} else if (dir.equals(ControllerUtilities.MoveDirections.upleft)
					&& validateIfPawnMoveIsPossible(moveCandidate.row - 1, moveCandidate.col - 1)) {
				targetModified = true;
				targetCol = moveCandidate.col - 1;
				targetRow = moveCandidate.row - 1;
			} else if (dir.equals(ControllerUtilities.MoveDirections.downleft)
					&& validateIfPawnMoveIsPossible(moveCandidate.row + 1, moveCandidate.col - 1)) {
				targetModified = true;
				targetCol = moveCandidate.col - 1;
				targetRow = moveCandidate.row + 1;
			} else if (dir.equals(ControllerUtilities.MoveDirections.upright)
					&& validateIfPawnMoveIsPossible(moveCandidate.row - 1, moveCandidate.col + 1)) {
				targetModified = true;
				targetCol = moveCandidate.col + 1;
				targetRow = moveCandidate.row - 1;
			} else if (dir.equals(ControllerUtilities.MoveDirections.downright)
					&& validateIfPawnMoveIsPossible(moveCandidate.row + 1, moveCandidate.col + 1)) {
				targetModified = true;
				targetCol = moveCandidate.col + 1;
				targetRow = moveCandidate.row + 1;
			}

			if (targetModified) {
				btnArray[moveCandidate.row][moveCandidate.col].remove(moveCandidate.wallMoveBtn);
				moveCandidate.row = targetRow;
				moveCandidate.col = targetCol;
				btnArray[moveCandidate.row][moveCandidate.col].add(moveCandidate.wallMoveBtn);
				playerMoved = true;

				// Call movePawn method to move the pawn
				QuoridorController.movePawn(currentPlayer, dir.toString());

				// Reset the targetMove to the original move
				if (currentPlayer.equals(QuoridorController.getWhitePlayer())) {
					whitePawnMove = moveCandidate;
				} else {
					blackPawnMove = moveCandidate;
				}
			}
		}
		if (wallMoveCandidate == null && playerMoved) {
			switchCurrentPlayerGuiAndBackend();
			QuoridorController.updateGameStatus();
			GameStatus currentStatus = QuoridorApplication.getQuoridor().getCurrentGame().getGameStatus();
			if (!currentStatus.equals(GameStatus.Running)) {
				// Here, end the game and display the winner! or the draw if that is the case
				QuoridorController.endGameAndReportResult();
				gameStarted = false;
				GameWonPopup gameWon = new GameWonPopup();
				gameWon.frame.setVisible(true);
			}
		}
		getAvailableMovesToCurrentPlayer();
		frmQuoridorPlay.repaint();
	}

	private boolean validatePawnMoveIndicies(int row, int column) {
		boolean rowIsValid = row >= 0 && row < 9;
		boolean colIsValid = column >= 0 && column < 9;
		return rowIsValid & colIsValid;
	}

	private boolean wallMoveIsValidated(int row, int column) {
		boolean rowIsValid = row >= 0 && row < 8;
		boolean colIsValid = column >= 0 && column < 8;
		return rowIsValid & colIsValid;
	}

	private void WallCandidateHandler(JButton grabWall, JButton dropWall) {

		grabWall.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (wallMoveCandidate == null && QuoridorController.grabWall(QuoridorApplication.getQuoridor())) {
					JButton wallMoveBtn = createWallMoveCandidate();
					wallMoveCandidate = new MoveCandidate(wallMoveBtn, 0, 0);
					if (QuoridorController.isWhitePlayer()) {
						whitePane.removeWall();
					} else {
						blackPane.removeWall();
					}
					// WallMove actualCandidate = new WallMove(1, 1,
					// QuoridorController.getCurrentPlayer(),
					// QuoridorController.getTileAtRowCol(1, 1),
					// QuoridorApplication.getQuoridor().getCurrentGame(),
					// Direction.Horizontal,
					// QuoridorController.getNextAvailableWall(QuoridorController.getCurrentPlayer()));
					//
					// QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(actualCandidate);
					grabWall.setText("Cancel Move");
				} else {
					boardPanel.remove(wallMoveCandidate.wallMoveBtn);
					wallMoveCandidate = null;
					grabWall.setText("Grab Wall");
					if (QuoridorController.isWhitePlayer()) {
						whitePane.cancelWallMove();
					} else {
						blackPane.cancelWallMove();
					}
					QuoridorApplication.getQuoridor().getCurrentGame().getWallMoveCandidate().delete();
					QuoridorApplication.getQuoridor().getCurrentGame().setWallMoveCandidate(null);

				}
				frmQuoridorPlay.repaint();
			}
		});

	}

	private void dropWallHandler(JButton dropWall, JButton grabWall) {
		dropWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Create wall move candidate
				if ( wallMoveCandidate != null && QuoridorController.wallMove(wallMoveCandidate.row + 1, wallMoveCandidate.col + 1,
						QuoridorController.getWallDirection(wallMoveCandidate.isRotated).toString(),
						QuoridorController.getWallMoveCandidate(), QuoridorController.getCurrentPlayer())) {
					if(!QuoridorController.checkIfPathExists().equals(PathAvailableToPlayers.both)){
						return;
					}
					wallMoveCandidate.wallMoveBtn.setIcon(new ImageIcon("./dropped.png"));
					try {
						QuoridorController.dropWall();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					wallMoveCandidate = null;
					grabWall.setText("Grab Wall");
					switchCurrentPlayerGuiAndBackend();
					getAvailableMovesToCurrentPlayer();
					frmQuoridorPlay.repaint();
				}

			}
		});
	}

	private JButton createWallMoveCandidate() {
		JButton btn = new JButton(new ImageIcon("./lightWall.png"));
		btn.setBounds(btnArray[0][0].getX(), btnArray[0][0].getY() + tileLength, wallWidth, 13);
		btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		btn.setBorder(new EmptyBorder(2, 2, 2, 2));
		boardPanel.add(btn);

		return btn;
	}

	private void createNavigationButtons() {
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.X_AXIS));
		JPanel centerButtonPanel = new JPanel();
		centerButtonPanel.setLayout(new BoxLayout(centerButtonPanel, BoxLayout.Y_AXIS));

		navigationButtonsPanel.add(east, BorderLayout.NORTH);

		BasicArrowButton north = new BasicArrowButton(BasicArrowButton.NORTH) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 70);
			}
		};

		BasicArrowButton south = new BasicArrowButton(BasicArrowButton.SOUTH) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 70);
			}
		};

		centerButtonPanel.add(north, BorderLayout.NORTH); // up
		centerButtonPanel.add(south, BorderLayout.SOUTH);

		JPanel leftPanel = new JPanel();
		east.add(leftPanel);
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

		BasicArrowButton leftUp = new BasicArrowButton(BasicArrowButton.WEST) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 30);
			}
		};

		leftPanel.add(leftUp);
		moveUpLeftHandler(leftUp);

		BasicArrowButton west = new BasicArrowButton(BasicArrowButton.WEST) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 20);
			}
		};
		leftPanel.add(west);
		moveLeftHandler(west);

		BasicArrowButton bottomLeft = new BasicArrowButton(BasicArrowButton.WEST) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 30);
			}
		};

		leftPanel.add(bottomLeft);
		moveDownLeftHandler(bottomLeft);

		east.add(centerButtonPanel, BorderLayout.CENTER);
		moveUpHandler(north);
		moveDownHandler(south);

		JPanel panel = new JPanel();
		east.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		BasicArrowButton rightUp = new BasicArrowButton(BasicArrowButton.EAST) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 70);
			}
		};
		panel.add(rightUp);
		moveUpRightHandler(rightUp);

		BasicArrowButton est = new BasicArrowButton(BasicArrowButton.EAST) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 70);
			}
		};
		panel.add(est);
		moveRightHandler(est);

		BasicArrowButton rightDown = new BasicArrowButton(BasicArrowButton.EAST) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(70, 70);
			}
		};
		panel.add(rightDown);
		moveDownRightHandler(rightDown);
		frmQuoridorPlay.repaint();
	}

	// private void createWalls(JPanel jPanel) {
	// for (int i = 0; i < 10; i++) { // Initializing the walls for both players
	// JButton btn = new JButton("Wall" + i);
	// btn.setBounds(10, 11 + i * (wallHeight + 5), wallWidth, wallHeight);
	// jPanel.add(btn);
	// }
	// }

	/**
	 * This methods sets all the available tiles for pawn move in green
	 * 
	 * @author Alexander Legouverneur
	 */
	private void getAvailableMovesToCurrentPlayer() {
		QuoridorController.mainValidateMovePawn(QuoridorController.getCurrentPlayer());
		for (int column = 1; column <= 9; column++) {
			for (int row = 1; row <= 9; row++) {
				if (QuoridorController.compareAvailableTiles(row, column)) {
					btnArray[row - 1][column - 1].setBackground(Color.GREEN);
					btnArray[row - 1][column - 1].setIcon(new ImageIcon("./availableTiles.png"));
				} else {
					btnArray[row - 1][column - 1].setBackground(Color.GRAY);
					btnArray[row - 1][column - 1].setIcon(new ImageIcon("./tile.png"));
				}
			}
		}
		frmQuoridorPlay.repaint();
	}

	private boolean validateIfPawnMoveIsPossible(int targetRow, int targetCol) {

		boolean rowIsValid = targetRow >= 0 && targetRow < 9;
		boolean colIsValid = targetCol >= 0 && targetCol < 9;
		if (!rowIsValid || !colIsValid) {
			return false;
		}
		boolean movePossible = false;
		QuoridorController.mainValidateMovePawn(QuoridorController.getCurrentPlayer());
		for (Tile tile : QuoridorController.getAvailableTiles()) {
			if (targetRow + 1 == tile.getRow() && targetCol + 1 == tile.getColumn()) {
				return true;
			}
		}
		return movePossible;
	}

	/**
	 * Method used to update the remaining time for the currentplayer Should be
	 * called on an interrupt basis.
	 */
	public static void updateTime() {
		Player currPlayer = QuoridorController.getCurrentPlayer();
		timeRemaining_TextFieldBlack.setText(currPlayer.getRemainingTime().toString());
		if (currPlayer.equals(QuoridorController.getWhitePlayer())) {
			timeRemaining_TextFieldWhite.setText(currPlayer.getRemainingTime().toString());
		} else {
			timeRemaining_TextFieldBlack.setText(currPlayer.getRemainingTime().toString());
		}
		frmQuoridorPlay.repaint();
	}

	private void resignGame(ActionEvent evt) {
		if(QuoridorController.getCurrentPlayer().equals(QuoridorController.getWhitePlayer())) {
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.BlackWon);
			GameWonPopup gameWon = new GameWonPopup();
			gameWon.frame.setVisible(true);
		} else if(QuoridorController.getCurrentPlayer().equals(QuoridorController.getBlackPlayer())) {
			QuoridorApplication.getQuoridor().getCurrentGame().setGameStatus(GameStatus.WhiteWon);
			GameWonPopup gameWon = new GameWonPopup();
			gameWon.frame.setVisible(true);
		}
		try {
			QuoridorController.playerInitiatesToResign(QuoridorController.getCurrentPlayer());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Not calling controller method ... ");
		}
	}

}
