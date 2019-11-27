package ca.mcgill.ecse223.quoridor.view;
import javax.swing.*;
import ca.mcgill.ecse223.quoridor.controller.QuoridorController;
import javax.swing.text.Utilities;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import ca.mcgill.ecse223.quoridor.QuoridorApplication;
import ca.mcgill.ecse223.quoridor.model.*;
import ca.mcgill.ecse223.quoridor.model.Game.GameStatus;
import ca.mcgill.ecse223.quoridor.model.Game.MoveMode;


public class RotateWallComponent implements WindowListener,ActionListener {
	TextField text = new TextField(20);
    Button b;
    private int numClicks = 0;
	
	public static void main(String[] args) {
	JFrame f=new JFrame();//creating instance of JFrame  
	          
	JButton b=new JButton("Rotate Wall");//creating instance of JButton  
	b.setBounds(130,400,100, 40);//x axis, y axis, width, height  
	          
	f.add(b);//adding button in JFrame         
	f.setSize(400,500);//400 width and 500 height  
	f.setLayout(null);//using no layout managers  
	f.setVisible(true);//making the frame visible 
	}
	

    public RotateWallComponent (String title) {

           
            b.addActionListener(this);
    }

	public void actionPerformed(ActionEvent e) { 
		numClicks++;
        text.setText("Button Clicked " + numClicks + " times");
	    
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		dispose();
        System.exit(0);
		
	}

	private void dispose() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}  
	
	  

}
