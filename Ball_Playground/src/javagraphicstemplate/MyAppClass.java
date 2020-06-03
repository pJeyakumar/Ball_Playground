package javagraphicstemplate;

import javax.swing.JApplet;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.io.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import sun.audio.*;
import java.net.URL;
import java.util.*;

public class MyAppClass extends JPanel
{
	// Variable Declarations
	
	// Graphics and Timer variables
    Graphics me = null; 
    private Timer timer = new Timer();
    private Image offScreenImage = null;
    private Graphics offScreenGraphics = null;
    
    // Screen Variables
    int intScreen = 0;
    
    // Mouse Variables
    int mouseX;
    int mouseY;
    
    // Obstacle Object Variables
    int intObstacleType = 0;
    Obstacle[] stationaryObstacles = new Obstacle[5]; // Array holding all stationary obstacles, MAX 5 for now
    int intObstacleCounter = 0;
    Obstacle[] ballObstacle = new Obstacle[1]; // Array holding the ball obstacle, MAX 1 at all times
    
    public class MyKeyListener implements KeyListener
    {
    	public void keyTyped(KeyEvent e) 
    		{
    		}
    	// Class that checks which key is being pressed, code will be executed depending on which key was pressed.
    	public void keyPressed(KeyEvent e)
    	{
    		// Flat Rectangle Object is Equipped
    		if(e.getKeyCode() == '1') 
    		{
    			intObstacleType = 1;
    		}
    		// Slanted Rectangle Object is Equipped
    		else if(e.getKeyCode() == '2') 
    		{
    			intObstacleType = 2;
    		}
    		// Ball Object is Equipped
    		else if(e.getKeyCode() == 'B') 
    		{
    			intObstacleType = 3;
    		}
    		// Resetting Screen
    		else if(e.getKeyCode() == 'R') 
    		{
    			// Clear screen
	    		me.fillRect(0,600,1000,10);
	    		// Add options
                me.setFont(new Font("Serif",Font.PLAIN,18));
	    		me.drawString("Press 1 to Equip Flat Rectangle", 0, 625);
	    		me.drawString("Press 2 to Equip Slanted Rectangle", 0, 650);
	    		me.drawString("Press B to Equip Ball", 0, 675);
	    		me.drawString("Press R to Reset the Screen", 575, 690);
	    		
	    		// Resetting Obstacle counter
	    		intObstacleCounter = 0; 
	    		intObstacleType = 0;
	    		
	    		// Resetting ball Obstacle array
	    		ballObstacle[0] = null;
	    		
	    		// Resetting stationary Obstacle array
	    		for(int i = 0; i < stationaryObstacles.length; i++) 
	    		{
	    			stationaryObstacles[i] = null;
	    		}
    		}
    	}
    	// Class that checks which keys keys are released, to differentiate pressed keys and keys being pressed
    	public void keyReleased(KeyEvent e) 
			{
				System.out.println("keyReleased="+KeyEvent.getKeyText(e.getKeyCode()));
			}
    }
    public class MyMouseListener implements MouseListener
    {
    	// Class that checks when the mouse is pressed, remembers the mouse cursor's coordinates and draws an obstacle (of the selected type)
    	// at those coordinates.
    	public void mousePressed(MouseEvent e) 
    	{
    		// Storing mouse pointer's X and Y coordinates
    		mouseX = e.getX();
    		mouseY = e.getY();
    		
    		// Ensuring that the number of obstacles created doesnt go past 5 (Array out of bound error will occur if we go over 5)
    		if(intObstacleCounter < 5) 
    		{
    			// Create a FLAT obstacle at mouse coordinates
    			if(intObstacleType == 1) 
    			{
    				stationaryObstacles[intObstacleCounter] = new Obstacle(mouseX, mouseY, false, 0);
    				intObstacleCounter++;
    			}
    			// If Slanted Obstacle option has been chosen
    			else if(intObstacleType == 2)
    			{
    				for(int i = 0; i < stationaryObstacles.length; i++) 
    				{
    					// Ensuring the object we're looking at is not NULL
    					if(stationaryObstacles[i] != null) 
    					{
    						// Checking the mouse coordinates are within the borders of any drawn obstacles
    						if(mouseX >= stationaryObstacles[i].getX() && mouseX <= (stationaryObstacles[i].getX() + 150) && mouseY >= stationaryObstacles[i].getY() && mouseY <= (stationaryObstacles[i].getY() + 50) ) 
    						{
    							System.out.println("Tilt.");
    						}
    					}
    				}
    			}
    			// Drop Ball Object at mouse coordinates
    			else if(intObstacleType == 3)
    			{
    				ballObstacle[0] = new Obstacle(mouseX, mouseY, true, 0);
    				intObstacleCounter++;
    			}
    			// Obstacle type has not been chosen, remind user to choose obstacle type before placing
    			else if(intObstacleType == 0) 
    			{
    			
    			}
    		}
    	}
    	public void mouseClicked(MouseEvent e) {
    	}
    	public void mouseEntered(MouseEvent e) {
    		
    	}
    	public void mouseExited(MouseEvent e) {
    		
    	}
    	public void mouseReleased(MouseEvent e) 
    	{
    		System.out.println("clickReleased");
    	}
    }
	        	MyAppClass()
	        	{
	        		// maze 2 dimensional array	
	        		timer.schedule(new MyTimer(), 0, 1);
	        		KeyListener listener = new MyKeyListener();
	        		MouseListener mListener = new MyMouseListener();
	        		addMouseListener(mListener);
	        		addKeyListener(listener);
	                setFocusable(true);

	        	}
    private class MyTimer extends java.util.TimerTask 
		{ 
	    	public void run()    
	        	{ 
	            	// Run thread on event dispatching thread 
	                if (!EventQueue.isDispatchThread()) 
	                { 
	                	EventQueue.invokeLater(this); 
	                } 
	                else 
	                { 
	                	if (MyAppClass.this != null) 
	                    { 
	                		MyAppClass.this.repaint();                         
	                    } 
	                }  
	            } // End of Run 
		} 
    public void paint(Graphics g) 
		{
	    	final Dimension d = getSize(); 
	        if (offScreenImage == null) 
	        	{    
	            	// Double-buffer: clear the offscreen image.                 
	                offScreenImage = createImage(d.width, d.height);    
	            }         
	        offScreenGraphics  = offScreenImage.getGraphics();      
	        offScreenGraphics.setColor(Color.white); 
	        offScreenGraphics.fillRect(0, 0, d.width, d.height) ;      
	            
	        renderOffScreen(offScreenImage.getGraphics());  
	        g.drawImage(offScreenImage, 0, 0, null);
	     }
    public void renderOffScreen( Graphics g) 
		{ 
    		me = g;
	    	// Main Screen
	    	if(intScreen == 0) 
	    	{
	    		// Clearing Interface
	    		me.fillRect(0,600,1000,10);
	    		// Creating Bottom Menu Bar [Instructions on what keys do what]
                me.setFont(new Font("Serif",Font.PLAIN,18));
	    		me.drawString("Press 1 to Equip Flat Rectangle", 0, 625);
	    		me.drawString("Press 2 to Equip Slanted Rectangle", 0, 650);
	    		me.drawString("Press B to Equip Ball", 0, 675);
	    		me.drawString("Press R to Reset the Screen", 575, 690);	    		
	    	}
	    	// Instructions Menu TBM 
	    	if(intScreen == 1) 
	    	{
	    	
	    	}
	    	// For loop that goes through the array storing all STATIONARY obstacles
	    	for(int i = 0; i < stationaryObstacles.length; i++) 
	    	{
	    		// Ensuring the array index is NOT NULL
	    		if(stationaryObstacles[i] != null) 
	    		{
	    			// Drawing the rectangle using the objects X and Y coordinates
	    			me.drawRect(stationaryObstacles[i].getX(), stationaryObstacles[i].getY(), 150, 50);
	    				
	    		}
	    	}
	    }
}
