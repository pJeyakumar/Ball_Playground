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
    	public void mousePressed(MouseEvent e)
    	{
    		// Storing mouse pointer's X and Y coordinates
    		mouseX = e.getX();
    		mouseY = e.getY();
    		
    		// Create a FLAT obstacle at mouse coordinates
    		if(intObstacleType == 1) 
    		{
    			
    		}
    		// Create a SLANTED obstacle at mouse coordinates
    		else if(intObstacleType == 2)
    		{
    			
    		}
    		// Drop Ball Object at mouse coordinates
    		else if(intObstacleType == 3)
    		{
    			
    		}
    		// Obstacle type has not been chosen, remind user to choose obstacle type before placing
    		else if(intObstacleType == 0) 
    		{
    			
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
    		
    	}
    }
	        	MyAppClass()
	        	{
	        		// maze 2 dimensional array	
	        		timer.schedule(new MyTimer(), 0, 1);
	        		KeyListener listener = new MyKeyListener();
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
	    		// Creating Bottom Menu Bar [Instructions on what keys do what]
	    		me.fillRect(0,600,1000,10);
                me.setFont(new Font("Serif",Font.PLAIN,18));
	    		me.drawString("Press 1 to Equip Flat Rectangle", 0, 625);
	    		me.drawString("Press 2 to Equip Slanted Rectangle", 0, 650);
	    		me.drawString("Press B to Equip Ball", 0, 675);
	    		me.drawString("Press R to Reset the Screen", 575, 690);
	    	}
	    }
}
