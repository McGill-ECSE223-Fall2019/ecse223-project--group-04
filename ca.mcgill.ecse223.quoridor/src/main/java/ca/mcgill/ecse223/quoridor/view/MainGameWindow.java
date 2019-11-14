package ca.mcgill.ecse223.quoridor.view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

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
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import ca.mcgill.ecse223.quoridor.view.QuoridorPage;
import java.awt.CardLayout;
import java.awt.GridBagLayout;

import ca.mcgill.ecse223.quoridor.view.main.BlackWallPanel;
import ca.mcgill.ecse223.quoridor.view.main.WhiteWallPanel;

public class MainGameWindow {

	public static JFrame frmQuoridorPlay;
	private JTextField txtCurrentPlayer;
	private JTextField textField_1;
	private JTextField txtTimeRemaining;
	private JTextField textField_2;
	private JTextField txtWhitePlayer;
	private JTextField txtWallsInStock;
	private JTextField wallsInStockWhitePlayer;
	private JTextField txtBlackPlayer;
	private JTextField txtWallsOnBoard_1;
	private JTextField wallsOnBoardWhitePlayer;
	private JTextField txtWallsInStock_1;
	private JTextField wallsInStockBlackPlayer;
	private JTextField txtWallsOnBoard;
	private JTextField wallsOnBoardBlackPlayer;
	private static final int TOTAL_NUMBER_OF_TILES = 81;
	private static final int TOTAL_ROWS = 9;
	private static final int TOTAL_COLS = 9;
	private static JButton[][] btnArray = new JButton[TOTAL_ROWS][TOTAL_COLS];
	private static JButton[] wallArray = new JButton[20];
	private JButton btnPlaceNewWall;
	private JButton btnNewButton;
	private JButton button;
	private static int wallWidth = 185;
	private static int wallWidthV = 101;
	private static int wallHeight = 10;
	private static boolean WallGrabbed = false;
	private static int CurrRow;
	private static int CurrCol;
	private static JButton horizontal = new JButton("horizontal");
	private static JButton vertical = new JButton("vertical");
	static JLabel lblPleaseSelectMove = new JLabel("Incorrect Move");
	private static int wallIndex;
	private static int tileLength = 45;
	private static int tileWidth = 45;
	private static JPanel panel_10 = new JPanel();
	private static JPanel panel_11 = new JPanel();
	private static JPanel centerPanel = new JPanel();
	private static JPanel boardPanel = new JPanel();
	private static JPanel navigationButtonsPanel = new JPanel();

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
	private void initialize() throws InterruptedException  {
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




		txtWhitePlayer = new JTextField();
		txtWhitePlayer.setEditable(false);
		txtWhitePlayer.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtWhitePlayer.setHorizontalAlignment(SwingConstants.CENTER);
		
		txtWhitePlayer.setText("White Player - Wall Stock");
		txtWhitePlayer.setToolTipText("");
		panel_3.add(txtWhitePlayer);
		txtWhitePlayer.setColumns(10);

		JPanel panel_4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.LEFT);
		panel_3.add(panel_4);

		txtWallsInStock = new JTextField();
		txtWallsInStock.setEditable(false);
		txtWallsInStock.setText("Walls in Stock:");
		panel_4.add(txtWallsInStock);
		txtWallsInStock.setColumns(10);

		wallsInStockWhitePlayer = new JTextField();
		wallsInStockWhitePlayer.setEditable(false);
		panel_4.add(wallsInStockWhitePlayer);
		wallsInStockWhitePlayer.setColumns(10);

		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		panel_3.add(panel_5);

		txtWallsOnBoard_1 = new JTextField();
		txtWallsOnBoard_1.setText("Walls on Board:");
		txtWallsOnBoard_1.setEditable(false);
		panel_5.add(txtWallsOnBoard_1);
		txtWallsOnBoard_1.setColumns(10);

		wallsOnBoardWhitePlayer = new JTextField();
		wallsOnBoardWhitePlayer.setEditable(false);
		panel_5.add(wallsOnBoardWhitePlayer);
		wallsOnBoardWhitePlayer.setColumns(10);



		lblPleaseSelectMove.setBackground(Color.RED);
		lblPleaseSelectMove.setForeground(Color.RED);
		lblPleaseSelectMove.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblPleaseSelectMove.setBounds(410, 350, 400, 52);
		frmQuoridorPlay.getContentPane().add(lblPleaseSelectMove);
		lblPleaseSelectMove.setVisible(false);

		BlackWallPanel whitePane = new BlackWallPanel(panel_10);
		WhiteWallPanel blackPane = new WhiteWallPanel(panel_11);
		panel_2.add(panel_10);
		panel_11 = blackPane.getWallPanel();
	    panel_10 = whitePane.getWallPanel();
		
		frmQuoridorPlay.getContentPane().add(centerPanel, BorderLayout.CENTER);
		centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

		Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int) ((dimension.getWidth() - centerPanel.getWidth()) / 2);
		int y = (int) ((dimension.getHeight() - centerPanel.getHeight()) / 2);
		centerPanel.setLocation(x, y);
		
		centerPanel.add(boardPanel);
		centerPanel.add(navigationButtonsPanel);
		
		boardPanel.setLayout(null);
		boardPanel.setPreferredSize(new Dimension(x-200, y-200));
		boardPanel.setBorder(new LineBorder(new Color(0, 0 , 0)));
	
		// boardPanel.setBackground(new Color(255,255,255));
		navigationButtonsPanel.setBorder(new LineBorder(new Color(0, 0 , 0)));
		for(int row = 0; row < TOTAL_ROWS; row++) {
			for(int col = 0; col < TOTAL_COLS; col ++) {

				lblPleaseSelectMove.setVisible(false);
				btnArray[row][col] = new JButton();
				// btnArray[row][col].setPreferredSize(new Dimension(5, 5));
				btnArray[row][col].setVisible(true);
				btnArray[row][col].setHorizontalAlignment(btnArray[row][col].CENTER);
				btnArray[row][col].setBounds((tileLength +5)*row, (tileWidth+5)*col, tileLength, tileWidth);
				boardPanel.add(btnArray[row][col]);
				btnArray[row][col].addMouseListener(new ButtonActionListener(row+1, col+1));

			}
		}
		JButton btn = new JButton("a wall");
		btn.setBounds(btnArray[1][1].getX()+tileLength, btnArray[1][1].getY(), wallHeight, wallWidthV  );
		boardPanel.add(btn);
		
		// btnArray[5][5].setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
		// btnArray[5][4].setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.black));
		// btnArray[6][5].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));
		// btnArray[6][4].setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.black));



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

		txtCurrentPlayer = new JTextField();
		txtCurrentPlayer.setEditable(false);
		txtCurrentPlayer.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtCurrentPlayer.setText("Current Player:");
		txtCurrentPlayer.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(txtCurrentPlayer);
		txtCurrentPlayer.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panel.add(textField_1);
		textField_1.setColumns(10);

		JPanel panel_1 = new JPanel();
		northPanel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		txtTimeRemaining = new JTextField();
		txtTimeRemaining.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtTimeRemaining.setText("Time Remaining:");
		txtTimeRemaining.setEditable(false);
		panel_1.add(txtTimeRemaining);
		txtTimeRemaining.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setEditable(false);
		panel_1.add(textField_2);
		textField_2.setColumns(10);



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

		txtBlackPlayer = new JTextField();
		txtBlackPlayer.setEditable(false);
		txtBlackPlayer.setFont(new Font("Tahoma", Font.BOLD, 13));
		txtBlackPlayer.setHorizontalAlignment(SwingConstants.CENTER);
		txtBlackPlayer.setText("Black Player - Wall Stock");
		panel_7.add(txtBlackPlayer);
		txtBlackPlayer.setColumns(10);

		JPanel panel_8 = new JPanel();
		panel_7.add(panel_8);

		txtWallsInStock_1 = new JTextField();
		txtWallsInStock_1.setEditable(false);
		txtWallsInStock_1.setText("Walls in Stock:");
		panel_8.add(txtWallsInStock_1);
		txtWallsInStock_1.setColumns(10);

		wallsInStockBlackPlayer = new JTextField();
		wallsInStockBlackPlayer.setEditable(false);
		panel_8.add(wallsInStockBlackPlayer);
		wallsInStockBlackPlayer.setColumns(10);

		JPanel panel_9 = new JPanel();
		panel_7.add(panel_9);

		txtWallsOnBoard = new JTextField();
		txtWallsOnBoard.setEditable(false);
		txtWallsOnBoard.setText("Walls on Board:");
		panel_9.add(txtWallsOnBoard);
		txtWallsOnBoard.setColumns(10);

		wallsOnBoardBlackPlayer = new JTextField();
		wallsOnBoardBlackPlayer.setEditable(false);
		panel_9.add(wallsOnBoardBlackPlayer);
		wallsOnBoardBlackPlayer.setColumns(10);

		
		panel_6.add(panel_11);
	

	}
	private void createWalls(JPanel jPanel){
		for (int i = 0; i < 10; i++) { // Initializing the walls for both players
			JButton btn = new JButton("Wall" + i);
			btn.setBounds(10, 11 + i * (wallHeight + 5), wallWidth, wallHeight);
			jPanel.add(btn);
		}
	}
	
	private void validatePawnPosition() {

		int Player = QuoridorController.currentPlayerInt();
		if(Player == 1) {

			int row = QuoridorController.getBlackPlayerPlayerPosition().getTile().getRow();
			int col = QuoridorController.getBlackPlayerPlayerPosition().getTile().getColumn();


			if(QuoridorController.initializeValidatePosition(row-1 , col) == true) {


				btnArray[row-1][col].setBackground(Color.GREEN);

			}
			if(QuoridorController.initializeValidatePosition(row+1 , col) == true) {


				btnArray[row+1][col].setBackground(Color.GREEN);

			}
			if(QuoridorController.initializeValidatePosition(row , col-1) == true) {


				btnArray[row][col-1].setBackground(Color.GREEN);

			}
			if(QuoridorController.initializeValidatePosition(row , col+1) == true) {


				btnArray[row][col+1].setBackground(Color.GREEN);

			}
		}
		if(Player == 0) {

			int row = QuoridorController.getWhitePlayerPosition().getTile().getRow();
			int col = QuoridorController.getWhitePlayerPosition().getTile().getColumn();


			if(QuoridorController.initializeValidatePosition(row-1 , col) == true) {


				btnArray[row-1][col].setBackground(Color.GREEN);

			}
			if(QuoridorController.initializeValidatePosition(row+1 , col) == true) {


				btnArray[row+1][col].setBackground(Color.GREEN);

			}
			if(QuoridorController.initializeValidatePosition(row , col-1) == true) {


				btnArray[row][col-1].setBackground(Color.GREEN);

			}
			if(QuoridorController.initializeValidatePosition(row , col+1) == true) {


				btnArray[row][col+1].setBackground(Color.GREEN);

			}
		}



	}
	public static void tileListener( int row, int col){
		
		
		for(int i = 0; i<20; i++) {

			if(wallArray[i].isVisible() == false) {//checking if there is one wall grabbed or not

				WallGrabbed = true;
				wallIndex = i;

			}
			else {
				continue;
			}
		}
		if(WallGrabbed == true) {


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
		if(WallGrabbed == false) {

			if(btnArray[CurrRow][CurrCol].getBackground().equals(Color.GREEN)) {

				//TODO implement pawn move

			}
			else {

				lblPleaseSelectMove.setVisible(true);
				SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);

			}

		}
		WallGrabbed = false;

	}


	public static void moveWallView(int row, int col, String dir, int wallIndex) {
		if(wallIndex<=9) {
			
			panel_10.remove(wallArray[wallIndex]);
			centerPanel.add(wallArray[wallIndex]);
			wallArray[wallIndex].setVisible(true);
			if(QuoridorController.wallMove(row, col, dir, QuoridorController.getWall(wallIndex), QuoridorController.getCurrentPlayer()) == true) {
				if(dir.equals("horizontal")) {
					
					wallArray[wallIndex].setBounds(btnArray[row-1][col-1].getX(), btnArray[row-1][col-1].getY()+tileWidth, wallWidth, wallHeight);
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);
					

				}
				else {
					wallArray[wallIndex].setBounds(btnArray[row-1][col-1].getX()+tileLength, btnArray[row-1][col-1].getY(), wallHeight, wallWidthV  );
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);
				}

			}
			else {
				lblPleaseSelectMove.setVisible(true);

			}
			
		}
		if(wallIndex >9) {
			
			panel_11.remove(wallArray[wallIndex]);
			centerPanel.add(wallArray[wallIndex]);
			if(QuoridorController.wallMove(row, col, dir, QuoridorController.getWall(wallIndex), QuoridorController.getCurrentPlayer()) == true) {
				if(dir.equals("horizontal")) {
					btnArray[row][col].setBackground(Color.RED);
					wallArray[wallIndex].setBounds(btnArray[row-1][col-1].getX(), btnArray[row-1][col-1].getY()+tileWidth, wallWidth, wallHeight);
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);

				}
				else {
					wallArray[wallIndex].setBounds(btnArray[row-1][col-1].getX()-11, btnArray[row-1][col-1].getY(), wallHeight, wallWidthV);
					wallArray[wallIndex].setVisible(true);
					SwingUtilities.updateComponentTreeUI(frmQuoridorPlay);
				}

			}
			else {
				lblPleaseSelectMove.setVisible(true);

			}
		}
		
		
	}
}