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
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.view.QuoridorPage;
import java.awt.CardLayout;
import java.awt.GridBagLayout;
import ca.mcgill.ecse223.quoridor.view.main.BlackWallPanel;
import ca.mcgill.ecse223.quoridor.view.main.WhiteWallPanel;
import ca.mcgill.ecse223.quoridor.view.*;

public class MainGameWindow {

	// UI elements
	public static JFrame frmQuoridorPlay;
	// time remaining
	private JTextField timeRemaining_Label;
	private JTextField timeRemaining_TextField;
	// current player
	private JTextField currentPlayer_Label;
	private JTextField currentPlayer_TextField;
	// white player
	private JTextField whitePlayer_Label;
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
	private static int wallWidth = 185;
	private static int wallWidthV = 11;
	private static int wallHeight = 88;
	private static boolean WallGrabbed = false;
	private static int CurrRow;
	private static int CurrCol;
	private static JButton horizontal = new JButton("horizontal");
	private static JButton vertical = new JButton("vertical");
	static JLabel errorMessage = new JLabel("Incorrect Move");
	private static int wallIndex;
	private static int tileLength = 45;
	private static int tileWidth = 45;
	private static JPanel panel_10;
	private static JPanel panel_10_1 = new JPanel();
	private static JPanel panel_11 = new JPanel();
	private static JPanel centerPanel = new JPanel();
	private static JPanel boardPanel = new JPanel();
	private static JPanel navigationButtonsPanel = new JPanel();
	private WallMoveCandidate wallMoveCandidate;

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
		
		// elements for whitePlayer
		whitePlayer_Label = new JTextField();
		whitePlayer_Label.setEditable(false);
		whitePlayer_Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		whitePlayer_Label.setHorizontalAlignment(SwingConstants.CENTER);
		whitePlayer_Label.setText("White Player - Wall Stock");
		whitePlayer_Label.setToolTipText("");
		panel_3.add(whitePlayer_Label);
		whitePlayer_Label.setColumns(10);

		// layout
		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_3.add(panel_4);
		
		// walls in stock for whitePlayer
		wallsInStockWhitePlayer_Label = new JTextField();
		wallsInStockWhitePlayer_Label.setEditable(false);
		wallsInStockWhitePlayer_Label.setText("Walls in Stock:");
		panel_4.add(wallsInStockWhitePlayer_Label);
		wallsInStockWhitePlayer_Label.setColumns(10);
		wallsInStockWhitePlayer_TextField = new JTextField();
		wallsInStockWhitePlayer_TextField.setEditable(false);
		panel_4.add(wallsInStockWhitePlayer_TextField);
		wallsInStockWhitePlayer_TextField.setColumns(10);

		// layout
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_3.add(panel_5);
		
		// walls on board for whitePlayer
		wallsOnBoardWhitePlayer_Label = new JTextField();
		wallsOnBoardWhitePlayer_Label.setText("Walls on Board:");
		wallsOnBoardWhitePlayer_Label.setEditable(false);
		panel_5.add(wallsOnBoardWhitePlayer_Label);
		wallsOnBoardWhitePlayer_Label.setColumns(10);
		wallsOnBoardWhitePlayer_TextField = new JTextField();
		wallsOnBoardWhitePlayer_TextField.setEditable(false);
		panel_5.add(wallsOnBoardWhitePlayer_TextField);
		wallsOnBoardWhitePlayer_TextField.setColumns(10);

		// elements for label selectMove
		errorMessage.setBackground(Color.RED);
		errorMessage.setForeground(Color.RED);
		errorMessage.setFont(new Font("Tahoma", Font.BOLD, 25));
		errorMessage.setBounds(410, 350, 400, 52);
		frmQuoridorPlay.getContentPane().add(errorMessage);
		errorMessage.setVisible(false);

		// elements for blackPlayer
		BlackWallPanel whitePane = new BlackWallPanel(panel_10_1);
		WhiteWallPanel blackPane = new WhiteWallPanel(panel_11);
		panel_2.add(panel_10_1);
		panel_11 = blackPane.getWallPanel();
		panel_10 = whitePane.getWallPanel();

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
		boardPanel.setPreferredSize(new Dimension(x, y));
		boardPanel.setBackground(new Color(107, 142, 35));
		navigationButtonsPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		for (int row = 0; row < TOTAL_ROWS; row++) {
			for (int col = 0; col < TOTAL_COLS; col++) {

				errorMessage.setVisible(false);

				BufferedImage btnImg;

				// ImageIcon icon = new ImageIcon(btnImg);
				btnArray[row][col] = new JButton(new ImageIcon("./lightWall.png"));
				btnArray[row][col] = new JButton();
				// btnArray[row][col].setPreferredSize(new Dimension(5, 5));
				btnArray[row][col].setIcon(btnArray[row][col].getIcon());
				btnArray[row][col].setVisible(true);
				btnArray[row][col].setHorizontalAlignment(btnArray[row][col].CENTER);
				btnArray[row][col].setBounds((tileLength + 5) * row, (tileWidth + 5) * col, tileWidth, tileLength);

				boardPanel.add(btnArray[row][col]);
				btnArray[row][col].addMouseListener(new ButtonActionListener(row + 1, col + 1));

			}
		}

		// elements for bottom panel
		frmQuoridorPlay.repaint();
		JButton grabWall = new JButton("Grab Wall");
		JButton dropWall = new JButton("Drop Wall");
		JButton rotateWall = new JButton("Rotate Wall");
		navigationButtonsPanel.add(rotateWall);
		navigationButtonsPanel.add(grabWall);
		navigationButtonsPanel.add(dropWall);

		WallCandidateHandler(grabWall, dropWall);
		handleRotateWall(rotateWall);
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
		timeRemaining_TextField = new JTextField();
		timeRemaining_TextField.setEditable(false);
		panel_1.add(timeRemaining_TextField);
		timeRemaining_TextField.setColumns(10);

		// layout
		JPanel southPanel = new JPanel();
		frmQuoridorPlay.getContentPane().add(southPanel, BorderLayout.SOUTH);
		southPanel.add(vertical);
		southPanel.add(horizontal);
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

		// elements for blackPlayer
		blackPlayer_Label = new JTextField();
		blackPlayer_Label.setEditable(false);
		blackPlayer_Label.setFont(new Font("Tahoma", Font.BOLD, 13));
		blackPlayer_Label.setHorizontalAlignment(SwingConstants.CENTER);
		blackPlayer_Label.setText("Black Player - Wall Stock");
		panel_7.add(blackPlayer_Label);
		blackPlayer_Label.setColumns(10);
		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8);

		// walls in stock for blackPlayer
		wallsInStockBlackPlayer_Label = new JTextField();
		wallsInStockBlackPlayer_Label.setEditable(false);
		wallsInStockBlackPlayer_Label.setText("Walls in Stock:");
		panel_8.add(wallsInStockBlackPlayer_Label);
		wallsInStockBlackPlayer_Label.setColumns(10);
		wallsInStockBlackPlayer_TextField = new JTextField();
		wallsInStockBlackPlayer_TextField.setEditable(false);
		panel_8.add(wallsInStockBlackPlayer_TextField);
		wallsInStockBlackPlayer_TextField.setColumns(10);

		// layout
		JPanel panel_9 = new JPanel();
		panel_7.add(panel_9);
		
		// walls on board for blackPlayer
		wallsOnBoardBlackPlayer_Label = new JTextField();
		wallsOnBoardBlackPlayer_Label.setEditable(false);
		wallsOnBoardBlackPlayer_Label.setText("Walls on Board:");
		panel_9.add(wallsOnBoardBlackPlayer_Label);
		wallsOnBoardBlackPlayer_Label.setColumns(10);
		wallsOnBoardBlackPlayer_TextField = new JTextField();
		wallsOnBoardBlackPlayer_TextField.setEditable(false);
		panel_9.add(wallsOnBoardBlackPlayer_TextField);
		wallsOnBoardBlackPlayer_TextField.setColumns(10);
		
		// layout
		panel_6.add(panel_11);

	}

	// methods
	private void handleRotateWall(JButton rotateWall) {
		rotateWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (wallMoveCandidate != null) {

					if (!wallMoveCandidate.isRotated) {
						int temp = wallHeight;
						wallHeight = wallWidthV;
						wallWidthV = temp;
						setHorizontal();
						wallMoveCandidate.isRotated = true;
					} else {
						int temp = wallHeight;
						wallHeight = wallWidthV;
						wallWidthV = temp;
						setVerticle();
						wallMoveCandidate.isRotated  = false;
					}

				}

			}
		});
	}

	private void setHorizontal() {
		if (wallMoveCandidate != null && !wallMoveCandidate.isRotated) {
			wallMoveCandidate.wallMoveBtn.setBounds(
					btnArray[wallMoveCandidate.row + 1][wallMoveCandidate.col].getX() + tileLength + 7,
					btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY() + tileLength - 3, wallWidthV, wallHeight);
		}
	}

	private void setVerticle() {
		wallMoveCandidate.wallMoveBtn.setBounds(
				btnArray[wallMoveCandidate.row + 1][wallMoveCandidate.col].getX() + tileLength - 2,
				btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY() + tileLength + 7, wallWidthV, wallHeight);
	}

	private void moveUpHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wallMoveCandidate != null && wallMoveCandidate.col > 0) {
					System.out.println("wall Move not null");
					wallMoveCandidate.wallMoveBtn.setBounds(
							btnArray[wallMoveCandidate.row][--wallMoveCandidate.col].getX() + tileLength - 3,
							btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY() + 3, wallWidthV, wallHeight);
					frmQuoridorPlay.repaint();
				} else {
					// handlle message : please grab a wall if there are still more walls.
				}

			}
		});
	}

	private void moveDownHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wallMoveCandidate != null && wallMoveCandidate.col < 7) {
					System.out.println("wall Move not null");
					wallMoveCandidate.wallMoveBtn.setBounds(
							btnArray[wallMoveCandidate.row][++wallMoveCandidate.col].getX() + tileLength - 3,
							btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY() + 3, wallWidthV, wallHeight);
					frmQuoridorPlay.repaint();
				} else {
					// handlle message : please grab a wall if there are still more walls.
				}

			}
		});
	}

	private void moveLeftHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wallMoveCandidate != null && wallMoveCandidate.row < 7) {
					System.out.println("wall Move not null");
					wallMoveCandidate.wallMoveBtn.getBounds().getWidth();
					wallMoveCandidate.wallMoveBtn.setBounds(
							btnArray[++wallMoveCandidate.row][wallMoveCandidate.col].getX() + tileLength - 3,
							btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY() + 3,
							wallMoveCandidate.wallMoveBtn.getWidth(), wallMoveCandidate.wallMoveBtn.getHeight());
					frmQuoridorPlay.repaint();
				} else {
					// handlle message : please grab a wall if there are still more walls.
				}

			}
		});
	}

	private void moveRightHandler(BasicArrowButton btn) {
		btn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (wallMoveCandidate != null && wallMoveCandidate.row > 0) {
					System.out.println("wall Move not null");

					wallMoveCandidate.wallMoveBtn.setBounds(
							btnArray[--wallMoveCandidate.row][wallMoveCandidate.col].getX() + tileLength - 3,
							btnArray[wallMoveCandidate.row][wallMoveCandidate.col].getY() + 3, wallWidthV, wallHeight);

					frmQuoridorPlay.repaint();
				} else {
					// handlle message : please grab a wall if there are still more walls.
				}

			}
		});
	}

	private void WallCandidateHandler(JButton grabWall, JButton dropWall) {

		grabWall.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (wallMoveCandidate == null) {
					JButton wallMoveBtn = createWallMoveCandidate();

					wallMoveCandidate = new WallMoveCandidate(wallMoveBtn, 0, 4);
				} else {
					// tell user to drop the current wall
				}
				dropWallHandler(dropWall);
				frmQuoridorPlay.repaint();
			}
		});

	}

	private void dropWallHandler(JButton dropWall) {
		dropWall.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wallMoveCandidate.wallMoveBtn.setIcon(new ImageIcon("./dropedWall.png"));
				wallMoveCandidate = null;
				frmQuoridorPlay.repaint();
			}
		});
	}

	private JButton createWallMoveCandidate() {
		JButton btn = new JButton(new ImageIcon("./lightWall.png"));
		btn.setBounds(btnArray[0][7].getX() + tileLength - 3, btnArray[0][7].getY() + 3, wallWidthV, wallHeight);
		btn.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
		btn.setBorder(new EmptyBorder(2, 2, 2, 2));
		boardPanel.add(btn);

		return btn;
	}

	private void createNavigationButtons() {
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.X_AXIS));
		JPanel newPanel = new JPanel();
		newPanel.setLayout(new BoxLayout(newPanel, BoxLayout.Y_AXIS));

		navigationButtonsPanel.add(east, BorderLayout.NORTH);

		BasicArrowButton north = new BasicArrowButton(BasicArrowButton.NORTH) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(100, 100);
			}
		};
		;

		BasicArrowButton south = new BasicArrowButton(BasicArrowButton.SOUTH) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(100, 100);
			}
		};
		;
		BasicArrowButton west = new BasicArrowButton(BasicArrowButton.WEST) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(100, 100);
			}
		};
		;
		BasicArrowButton est = new BasicArrowButton(BasicArrowButton.EAST, Color.blue, Color.green, Color.PINK,
				Color.lightGray) {
			@Override
			public Dimension getPreferredSize() {
				return new Dimension(100, 100);
			}
		};
		;

		west.setBackground(Color.pink);

		newPanel.add(north, BorderLayout.NORTH); // up
		newPanel.add(south, BorderLayout.SOUTH); // down
		east.add(west, BorderLayout.WEST);
		east.add(newPanel, BorderLayout.CENTER);
		east.add(est, BorderLayout.EAST);
		moveUpHandler(north);
		moveDownHandler(south);
		moveLeftHandler(est);
		moveRightHandler(west);
		frmQuoridorPlay.repaint();
	}

	private void createWalls(JPanel jPanel) {
		for (int i = 0; i < 10; i++) { // Initializing the walls for both players
			JButton btn = new JButton("Wall" + i);
			btn.setBounds(10, 11 + i * (wallHeight + 5), wallWidth, wallHeight);
			jPanel.add(btn);
		}
	}

	private void validatePawnPosition() {

		int Player = QuoridorController.currentPlayerInt();
		if (Player == 1) {

			int row = QuoridorController.getBlackPlayerPlayerPosition().getTile().getRow();
			int col = QuoridorController.getBlackPlayerPlayerPosition().getTile().getColumn();

			if (QuoridorController.initializeValidatePosition(row - 1, col) == true) {

				btnArray[row - 1][col].setBackground(Color.GREEN);

			}
			if (QuoridorController.initializeValidatePosition(row + 1, col) == true) {

				btnArray[row + 1][col].setBackground(Color.GREEN);

			}
			if (QuoridorController.initializeValidatePosition(row, col - 1) == true) {

				btnArray[row][col - 1].setBackground(Color.GREEN);

			}
			if (QuoridorController.initializeValidatePosition(row, col + 1) == true) {

				btnArray[row][col + 1].setBackground(Color.GREEN);

			}
		}
		if (Player == 0) {

			int row = QuoridorController.getWhitePlayerPosition().getTile().getRow();
			int col = QuoridorController.getWhitePlayerPosition().getTile().getColumn();

			if (QuoridorController.initializeValidatePosition(row - 1, col) == true) {

				btnArray[row - 1][col].setBackground(Color.GREEN);

			}
			if (QuoridorController.initializeValidatePosition(row + 1, col) == true) {

				btnArray[row + 1][col].setBackground(Color.GREEN);

			}
			if (QuoridorController.initializeValidatePosition(row, col - 1) == true) {

				btnArray[row][col - 1].setBackground(Color.GREEN);

			}
			if (QuoridorController.initializeValidatePosition(row, col + 1) == true) {

				btnArray[row][col + 1].setBackground(Color.GREEN);

			}
		}

	}

	public static void tileListener(int row, int col) {

		for (int i = 0; i < 20; i++) {

			if (wallArray[i].isVisible() == false) {// checking if there is one wall grabbed or not

				WallGrabbed = true;
				wallIndex = i;

			} else {
				continue;
			}
		}
		if (WallGrabbed == true) {

			vertical.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {
					moveWallView(row, col, "vertical", wallIndex);
				}
			});

			horizontal.addMouseListener(new MouseAdapter() {

				public void mouseClicked(MouseEvent e) {

					moveWallView(row, col, "horizontal", wallIndex);

				}
			});

		}
		if (WallGrabbed == false) {

			if (btnArray[CurrRow][CurrCol].getBackground().equals(Color.GREEN)) {

				// TODO implement pawn move

			} else {

				errorMessage.setVisible(true);
				SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);

			}

		}
		WallGrabbed = false;

	}

	public static void moveWallView(int row, int col, String dir, int wallIndex) {
		if (wallIndex <= 9) {

			panel_10.remove(wallArray[wallIndex]);
			centerPanel.add(wallArray[wallIndex]);
			wallArray[wallIndex].setVisible(true);
			if (QuoridorController.wallMove(row, col, dir, QuoridorController.getWall(wallIndex),
					QuoridorController.getCurrentPlayer()) == true) {
				if (dir.equals("horizontal")) {

					wallArray[wallIndex].setBounds(btnArray[row - 1][col - 1].getX(),
							btnArray[row - 1][col - 1].getY() + tileWidth, wallWidth, wallHeight);
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);

				} else {
					wallArray[wallIndex].setBounds(btnArray[row - 1][col - 1].getX() + tileLength,
							btnArray[row - 1][col - 1].getY(), wallHeight, wallWidthV);
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);
				}

			} else {
				errorMessage.setVisible(true);

			}

		}
		if (wallIndex > 9) {

			panel_11.remove(wallArray[wallIndex]);
			centerPanel.add(wallArray[wallIndex]);
			if (QuoridorController.wallMove(row, col, dir, QuoridorController.getWall(wallIndex),
					QuoridorController.getCurrentPlayer()) == true) {
				if (dir.equals("horizontal")) {
					btnArray[row][col].setBackground(Color.RED);
					wallArray[wallIndex].setBounds(btnArray[row - 1][col - 1].getX(),
							btnArray[row - 1][col - 1].getY() + tileWidth, wallWidth, wallHeight);
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);

				} else {
					wallArray[wallIndex].setBounds(btnArray[row - 1][col - 1].getX() - 11,
							btnArray[row - 1][col - 1].getY(), wallHeight, wallWidthV);
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);
				}

			} else {
				errorMessage.setVisible(true);

			}
		}

	}
}