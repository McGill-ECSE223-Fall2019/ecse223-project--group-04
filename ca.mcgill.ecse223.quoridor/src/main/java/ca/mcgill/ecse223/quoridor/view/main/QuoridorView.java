package ca.mcgill.ecse223.quoridor.view.main;
import ca.mcgill.ecse223.quoridor.controller.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*; 
import javax.swing.*;

class QuoridorView extends JFrame { 
	static Stack<JButton> stack = new Stack<>();
    // JFrame 
	private static Dimension buttonDimension = new Dimension(10, 10);
    static JFrame f; 
  
    // JButton 
    static JButton[] buttons; 
    static JButton tmpButton;
    private static JPanel wallPanel;
    // label to diaplay text 
    static JLabel l; 
  
    // main class 
    public static void main(String[] args) 
    { 	
        // create a new frame to store text field and button 
        f = new JFrame("panel"); 
        buttons = new JButton[10];
        // create a label to display text 
        tmpButton = buttons[0];
        l = new JLabel("panel label"); 
        int num = 1;
        for(int i=0; i<10; i++) {
        	if(i==9) buttons[i] = new JButton("Wall X");
        	else buttons[i] = new JButton("Wall " + num++);
        	buttons[i].setSize(buttonDimension);
        	buttons[i].setBackground(Color.BLACK);
        	buttons[i].setForeground(Color.black);
//        	buttons[i].setOpaque(true);
//        	buttons[i].setBorderPainted(false);
        	buttons[i].setContentAreaFilled(true);
        }
       JButton cancelBtn = new JButton("Cancel");
       
        
        // create a new buttons 

  
        // create a panel to add buttons and textfield and a layout 
        JPanel leftPanel = new JPanel(); 
        JPanel rightPanel = new JPanel(); 
        // set Box Layout 
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS)); 
        
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.X_AXIS)); 
  
        // add buttons and textfield to panel 
        for(JButton btn : buttons) {
        	leftPanel.add(btn);
        }
        leftPanel.setVisible(true);
        leftPanel.setOpaque(true);
        
        rightPanel.add(cancelBtn);
        
        // setbackground of panel 
        leftPanel.setBackground(Color.black);
        
        rightPanel.setBackground(Color.black); 
        rightPanel.setVisible(true);
        rightPanel.setOpaque(true);
        // add panel to frame 
        f.setLayout(new GridLayout(1, 2));
        f.add(rightPanel);
        f.add(leftPanel);
        

        num = 1;
        for(JButton btn : buttons) {
        	
        	btn.setAction(new AbstractAction("Wall " + num++ ) {
        		
                @Override
                public void actionPerformed(ActionEvent e) {
                	stack.push(btn);
                	
                    btn.setVisible(false);
                    leftPanel.revalidate();
                    leftPanel.repaint();
                }
            });
        }
        
        cancelBtn.addActionListener(new ActionListener()
        {
          public void actionPerformed(ActionEvent e)
          {
            // display/center the jdialog when the button is pressed
           if(stack.size() != 0) {
        	   stack.pop().setVisible(true);
           }
            leftPanel.revalidate();
            leftPanel.repaint();
          }
        });
        
        // set the size of frame 
        f.setSize(300, 300); 
  
        f.show(); 
    }
    public static void addPanel(JPanel p) {
    	f.add(p);
    }
    
    
} 