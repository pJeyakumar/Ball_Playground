package javagraphicstemplate;

import javax.swing.JApplet;
import java.awt.geom.Ellipse2D;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.*;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import java.io.*;
import java.util.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;

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
    double mouseX;
    double mouseY;
    
    // Obstacle Object Variables
    int intObstacleType = 0;
    Obstacle[] stationaryObstacles = new Obstacle[5]; // Array holding all stationary obstacles, MAX 5 for now
    int intObstacleCounter = 0;
    Ball[] ballObstacle = new Ball[1]; // Array holding the ball obstacle, MAX 1 at all times
    boolean gravity = false; 
    int ball_type = 0;
    
    public class MyKeyListener implements KeyListener
    {
    	public void keyTyped(KeyEvent e) 
    		{
    		}
    	
    	// Class that checks which key is being pressed, code will be executed depending on which key was pressed.
    	public void keyPressed(KeyEvent e)
    	{
    		// Options on the Main Screen
    		if(intScreen == 0) 
    		{
    			// Flat Rectangle Object is Equipped
    			if(e.getKeyCode() == '1') 
    			{
    				intObstacleType = 1;
    			}
    			else if(e.getKeyCode() == '0')
    			{
    				intObstacleType = 0;
    			}
    			// Ability to Tilt Rectangle is Selected
    			else if(e.getKeyCode() == '2') 
    			{
    				intObstacleType = 2;
    			}
    			
    			// Ball Object is Equipped
    			else if(e.getKeyCode() == 'B') 
    			{
    				intObstacleType = 3;
    			}
    			
    			// Instruction Menu
    			else if(e.getKeyCode() == 'I') 
    			{
    				intScreen = 1;
    			}
			
    			else if(e.getKeyCode() == 'T') 
    			{
    				System.out.println(stationaryObstacles[0].getSide());
    			}
    			// Resetting Screen
    			else if(e.getKeyCode() == 'R') 
    			{
    				// Clear screen
    				me.fillRect(0,600,1000,10);
    				// Add options
    				me.setFont(new Font("Serif",Font.PLAIN,18));
    				me.drawString("Press 1 to Equip Flat Rectangle", 0, 625);
    				me.drawString("Press 2 to Tilt Rectangle", 0, 650);
    				me.drawString("Press B to Equip Ball", 0, 675); 
    				me.drawString("Press G for gravity", 536, 660);
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
    			//adding and removing gravity effect
    			else if (e.getKeyCode() == 'G')
    			{
    				if (!gravity)
    				{
    					gravity = true;
    				}
    				else
    				{
    					gravity = false;
    				}
    			}
    			//changing ball type
    			else if (e.getKeyCode() == 'C')
    			{
    				if (ballObstacle[0] == null)
    					{
		    				if (ball_type < 2)
		    				{
		    					ball_type++;
		    				}
		    				else {
		    					ball_type = 0;
		    				}
    					}
    			}
    		}
    		// Options on the Instruction Screen
    		if(intScreen == 1)
    		{
    			// Exit Instruction Menu and Return to Main Screen
    			if(e.getKeyCode() == 'E') 
    			{
    				intScreen = 0;
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
    				
    				stationaryObstacles[intObstacleCounter].setTLCrX(mouseX);
    				stationaryObstacles[intObstacleCounter].setTLCrY(mouseY);
    				
    				stationaryObstacles[intObstacleCounter].setTRCrX(mouseX + stationaryObstacles[intObstacleCounter].getLength());
    				stationaryObstacles[intObstacleCounter].setTRCrY(mouseY);
    				
    				stationaryObstacles[intObstacleCounter].setBRCrX(mouseX + stationaryObstacles[intObstacleCounter].getLength());
    				stationaryObstacles[intObstacleCounter].setBRCrY(mouseY + stationaryObstacles[intObstacleCounter].getWidth());
    				
    				stationaryObstacles[intObstacleCounter].setBLCrX(mouseX);
    				stationaryObstacles[intObstacleCounter].setBLCrY(mouseY + stationaryObstacles[intObstacleCounter].getWidth());
    				intObstacleCounter++;
    			}
    		}
			// If TILTING option has been chosen
    		if(intObstacleType == 2)
			{
				for(int i = 0; i < stationaryObstacles.length; i++) 
				{
					// Ensuring the object we're looking at is not NULL
					if(stationaryObstacles[i] != null) 
					{
						// Variable Declaration
						Boolean flatRectIntersect = false;
						Boolean tiltedRectIntersect = false;
						Obstacle iObstacle = stationaryObstacles[i];
						
						// Setting boolean variable to the result of checking if mouse in in a flat rectangle
						if(mouseX >= iObstacle.getX() && mouseX <= (iObstacle.getX() + 150) && mouseY >= iObstacle.getY() && mouseY <= (iObstacle.getY() + 50) && (iObstacle.getAngle() == 0 || iObstacle.getAngle() == 180)) 
						{
							flatRectIntersect = true;
						}
						
						// Setting boolean variable to the result of checking if the mouse is in a tilted rectangle
						if(iObstacle.inRotatedObstacle(mouseX, mouseY)) 
						{
							tiltedRectIntersect = true;
						}
						
						// Checking if the mouse intersects with the rectangle, whether it is tilted or flat
						if(flatRectIntersect || tiltedRectIntersect) 
						{
							// Updating the angle of the clicked obstacle
							iObstacle.setAngle(((iObstacle.getAngle() + 45)));	
							
							setCornerCoordinates(iObstacle);
						}
					}
				}
			}
			// Drop Ball Object at mouse coordinates
			else if(intObstacleType == 3)
			{
				if (ballObstacle[0] == null)
				{
					ballObstacle[0] = new Ball(mouseX, mouseY, 10, ball_type);		
				}
			}
			/*else if (intObstacleType == 0)
			{
				System.out.println(mouseX + " " + mouseY);
				System.out.println(stationaryObstacles[0].getTLCrX() + " " + stationaryObstacles[0].getTLCrY() + " TL");
				System.out.println(stationaryObstacles[0].getTRCrX() + " " + stationaryObstacles[0].getTRCrY() + " TR");
				System.out.println(stationaryObstacles[0].getBLCrX() + " " + stationaryObstacles[0].getBLCrY() + " BL");
				System.out.println(stationaryObstacles[0].getBRCrX() + " " + stationaryObstacles[0].getBRCrY() + " BR");
			}*/
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
    int screenWidth;
    int screenHeight;
    public void paint(Graphics g) 
		{
	    	final Dimension d = getSize(); 
	    	
	        if (offScreenImage == null) 
	        	{    
	            	// Double-buffer: clear the offscreen image.                 
	                offScreenImage = createImage(d.width, d.height);    
	                screenWidth = d.width;
	                screenHeight = d.height;
	        	}       
	        
	        offScreenGraphics  = offScreenImage.getGraphics();      
	        offScreenGraphics.setColor(Color.white); 
	        offScreenGraphics.fillRect(0, 0, d.width, d.height) ;      
	            
	        renderOffScreen(offScreenImage.getGraphics());  
	        g.drawImage(offScreenImage, 0, 0, null);
	     }
    //Function to bounce ball off of wall
    public void checkWalls(Ball b)
    {
    	//Check if ball hits left or right wall
    	if (b.getX()-b.getRadius() < 0 || b.getX() > screenWidth - b.getRadius())
    	{
    		b.setVelx(b.getVelx() * b.getBounce());
    	}
    	//Check if ball hits bottom or top wall
    	if (b.getY() - b.getRadius() < 0 || b.getY() > 600 - b.getRadius())
    	{
    		b.setVely(b.getVely() * b.getBounce());
    	}    	
    }
    //Check if ball intersects with an obstacle
    public boolean intersects(Ball b, Obstacle o)
    {
    	double testX = b.getX();
    	double testY = b.getY();
    	//Check where circle is closest with respect to rectangle
    	if (b.getX() < o.getX()) //left edge
    	{
    		testX = o.getX();
    	}
    	else if (b.getX() > o.getX() + 150) //right edge
    	{
    		testX = o.getX() + 150;
    	}
    	if (b.getY() < o.getY()) //top edge
    	{
    		testY = o.getY();
    	}
    	else if (b.getY() > o.getY() + 50)
    	{
    		testY = o.getY() + 50;
    	}
    	//get distance from closest edges
    	double distX = b.getX() - testX;
    	double distY = b.getY() - testY;
    	double distance = Math.sqrt((distX * distX) + (distY * distY));
    	if (distance <= b.getRadius())
    	{
    		return true;
    	}
    	return false; 	   
    } 
    //Check for collisions with obstacles
    public void checkObstacles(Ball b, Obstacle[] o)
    {
    	for (int i = 0; i < o.length; i++)
    	{
    		if (o[i] != null)
    		{
    			if (o[i].inRotatedObstacle(b.getX(), b.getY())|| intersects(b, o[i]))
    			{
    				//Untilted obstacle bounce behaviour
    				if (o[i].getAngle() == 0 || o[i].getAngle() == 180)
    				{
	    				if (b.getX() < o[i].getX()) //left edge
	    		    	{
	    					b.setVelx(b.getVelx() * b.getBounce());
	    		    	}
	    		    	else if (b.getX() > o[i].getX() + 150) //right edge
	    		    	{
	    		    		b.setVelx(b.getVelx() * b.getBounce());
	    		    	}
	    		    	if (b.getY()  < o[i].getY()) //top edge
	    		    	{
	    		    		b.setVely(b.getVely() * b.getBounce());
	    		    	}
	    		    	else if (b.getY() > o[i].getY() + 50)//bottom edge
	    		    	{
	    		    		b.setVely(b.getVely() * b.getBounce());
	    		    	}
    				}
    				//Titled obstacle bounce behaviour
    				else
    				{
    					 					
    				}
    			}
    		}
    	}
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
	    		me.drawString("Press 1 to Equip Flat Rectangle", 0, 635);
	    		me.drawString("Press 2 to Tilt Rectangle", 0, 660);
	    		me.drawString("Press B to Equip Ball", 0, 685);
	    		if (ball_type == 0)
	    		{
	    			me.drawString("(Bouncy Ball)", 160, 685);
	    		}
	    		else if (ball_type == 1)
	    		{
	    			me.drawString("(Basketball)", 160, 685);
	    		}
	    		else
	    		{
	    			me.drawString("(Bowling Ball)", 160, 685);
	    		}
	    		me.drawString("Press I to go to Instruction Menu", 536, 630);
	    		me.drawString("Press G for gravity", 536, 660);
	    		me.drawString("Press R to Reset the Screen", 536, 690);	    
	    		
		    	// Notification that Flat Obstacle is selected
		    	else if(intObstacleType == 2) 
		    	{
		    		me.setColor(Color.red);
		    		me.setFont(new Font("Serif",Font.BOLD,24));
		    		me.drawString("Tilting Mode ACTIVATED", 230, 40);
		    		me.setColor(Color.black);
		    	}
		    	
		    	// Notification that Ball is selected
		    	else if(intObstacleType == 3) 
		    	{
		    		me.setColor(Color.green);
		    		me.setFont(new Font("Serif",Font.BOLD,24));
		    		me.drawString("Ball Object EQUIPPED", 240, 40);
		    		me.drawString("Press C to change ball type", 230, 60);
		    		me.setColor(Color.black);
		    	}
		    	
		    	// Notification if the user hasn't selected an option yet
		    	else if(intObstacleType == 0) 
		    	{
		    		me.setColor(Color.magenta);
		    		me.setFont(new Font("Serif",Font.BOLD,24));
		    		me.drawString("No Options Currently SELECTED", 200, 40);
		    		me.setColor(Color.black);
		    	}
		    	// Notification for gravity 
		    	if (gravity)
		    	{
		    		me.setColor(Color.orange);
		    		me.setFont(new Font("Serif",Font.BOLD,24));
		    		me.drawString("Gravity Activated", 270, 17);
		    		me.setColor(Color.black);
		    	}
		    	
		    	//draw Ball
		    	for(int i = 0; i < ballObstacle.length; i++)		    	
		    	{
		    		if (ballObstacle[i] != null)
		    		{
		    			Graphics2D g1= (Graphics2D)g;
		    			Ellipse2D ball = new Ellipse2D.Double(ballObstacle[0].getX() - ballObstacle[0].getRadius(),ballObstacle[0].getY() - ballObstacle[0].getRadius(), ballObstacle[0].getRadius()*2, ballObstacle[0].getRadius()*2);		
		    			((Graphics2D) g1).fill(ball);
		    			//mimic movement off the ball
		    			if (gravity)
		    			{
		    				ballObstacle[i].setVely(ballObstacle[i].getVely() + ballObstacle[i].getAcc());
		    			}		    		
		    			ballObstacle[i].setY(ballObstacle[i].getY() + ballObstacle[i].getVely());
		    			ballObstacle[i].setX((ballObstacle[i].getX() + ballObstacle[i].getVelx()));
		    			checkWalls(ballObstacle[i]);
		    			checkObstacles(ballObstacle[i], stationaryObstacles);
		    		}
		    		
		    	}
		    	
		    	// For loop that goes through the array storing all STATIONARY obstacles
		    	for(int i = 0; i < stationaryObstacles.length; i++) 
		    	{
    				Graphics2D g2d = (Graphics2D)g;
		    		// Ensuring the array index is NOT NULL
		    		if(stationaryObstacles[i] != null) 
		    		{
		    			// If the angle is 0 or 180, we can simply draw a flat rectangle
		    			if(stationaryObstacles[i].getAngle() == 0 || stationaryObstacles[i].getAngle() == 180)
		    			{
		    				// Drawing the rectangle using the objects X and Y coordinates
		    				Rectangle2D rect = new Rectangle2D.Double(stationaryObstacles[i].getX(), stationaryObstacles[i].getY(), stationaryObstacles[i].getLength(), stationaryObstacles[i].getWidth());
		    				g2d.fill(rect);
		    			}
		    			// Otherwise, we need to rotate the rectangle to its given angle
		    			else 
		    			{
		    				// 2D Graphics and AffineTransformation declarations
		    				AffineTransform transform = new AffineTransform();
		    				
		    				// Creating a rectangle object using the obstacles information
		    				Rectangle2D rect = new Rectangle2D.Double(stationaryObstacles[i].getX(), stationaryObstacles[i].getY(), stationaryObstacles[i].getLength(), stationaryObstacles[i].getWidth());

		    				// Rotating the rectangle by the obstacles given angle around the CENTER of the rectangle
		    				transform.rotate(Math.toRadians(stationaryObstacles[i].getAngle()), rect.getX() + 75, rect.getY() + 25);
		    				
		    				// Creating the transformed object
		    				Shape transformed = transform.createTransformedShape(rect);
		    				
		    				// Drawing our transformed object
		    				g2d.fill(transformed);
		    			}
					if(ballObstacle[0] != null)
		    				stationaryObstacles[i].findSide(ballObstacle[0].getX(), ballObstacle[0].getY());
							double obstacleAngle = stationaryObstacles[i].getAngle();
							g2d.setColor(Color.red);
							g2d.setStroke(new BasicStroke(3));
							if(stationaryObstacles[i].getSide() == 1)
							{
								if (obstacleAngle == 0) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()));

								}
								else if (obstacleAngle == 45)
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()));

								}
								else if (obstacleAngle == 90) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 135) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 180) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 225) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 270) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()));

								}
								else if (obstacleAngle == 315) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()));

								}
								
							}
							else if(stationaryObstacles[i].getSide() == 2)
							{
								if (obstacleAngle == 0) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()));

								}
								else if (obstacleAngle == 45)
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()));

								}
								else if (obstacleAngle == 90) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()));

								}
								else if (obstacleAngle == 135) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 180) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 225) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 270) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 315) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()));

								}
							}
							else if(stationaryObstacles[i].getSide() == 3)
							{
								if (obstacleAngle == 0) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 45)
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 90) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()));

								}
								else if (obstacleAngle == 135) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()));

								}
								else if (obstacleAngle == 180) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()));

								}
								else if (obstacleAngle == 225) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()));

								}
								else if (obstacleAngle == 270) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 315) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
							}
							else if(stationaryObstacles[i].getSide() == 4)
							{
								if (obstacleAngle == 0) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 45)
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
								else if (obstacleAngle == 90) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));
	
								}
								else if (obstacleAngle == 135) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()), (int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()));

								}
								else if (obstacleAngle == 180) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()));

								}
								else if (obstacleAngle == 225) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getTRCrX()), (int)(stationaryObstacles[i].getTRCrY()));

								}
								else if (obstacleAngle == 270) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getTLCrX()), (int)(stationaryObstacles[i].getTLCrY()), (int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()));

								}
								else if (obstacleAngle == 315) 
								{
									g2d.drawLine((int)(stationaryObstacles[i].getBLCrX()), (int)(stationaryObstacles[i].getBLCrY()), (int)(stationaryObstacles[i].getBRCrX()), (int)(stationaryObstacles[i].getBRCrY()));

								}
							}
							g2d.setColor(Color.black);
		    		}	
		    	}
	    	}
	    	
	    	// Instructions Menu
	    	if(intScreen == 1) 
	    	{
	    		// Graphics and Rectangle declaration
	    		Graphics2D g2d = (Graphics2D)g;
				AffineTransform transform1 = new AffineTransform();
				AffineTransform transform2 = new AffineTransform();
				AffineTransform transform3 = new AffineTransform();
				
	    		// Setting background of menu to gray
	    		me.setColor(Color.gray);
	    		me.fillRect(0,0,800,800);
	    		// The Obstacle we will be using as an example
	    		Obstacle iObstacle = new Obstacle(60,100,false,0);
	    		
	    			    		// Displaying the FLAT Version of the Obstacle we declared
	    		me.setColor(Color.white);
	    		me.setFont(new Font("Serif", Font.BOLD, 16));
	    		me.drawString("DEFAULT Stationary Obstacle:", 60, 40);
	    		me.drawString("To place a flat obstacle, press 1 to equip flat obstacle mode, and on mouse click an obstacle will be placed", 60, 120);
	    		me.drawString("where the mouse cursor is", 60, 140);
	    		me.setColor(Color.black);
	    		Rectangle2D rect = new Rectangle2D.Double(iObstacle.getX(), 50, 150, 50);
	    		g2d.fill(rect);
	    		
	    		// Displaying the various ANGLES of the Obstacle we declared
	    		me.setColor(Color.white);
	    		me.setFont(new Font("Serif", Font.BOLD, 16));
	    		me.drawString("Here are the ANGLED obstacles that can be made:", 60, 215);
	    		me.drawString("To place an angled obstacle, first place a flat obstacle, switch to the tilting mode, and click on the flat", 60, 425);
	    		me.drawString("obstacle to change it's angle by 45", 60, 445);
	    		me.setColor(Color.black);
	    		
	    		// Explanation of Buttons
	    		me.setColor(Color.blue);
	    		me.drawString("Flat Obstacle EQUIPPED", 60, 500);
	    		me.setColor(Color.white);
	    		me.drawString("--- On click a flat obstacle will be placed at the mouse cursor.", 250, 500);
	    		me.setColor(Color.red);
	    		me.drawString("Tilting Mode ACTIVATED", 60, 525);
	    		me.setColor(Color.white);
	    		me.drawString("--- Click an obstacle to increase it's current angle by 45.", 250, 525);
	    		me.setColor(Color.green);
	    		me.drawString("Ball Object EQUIPPED", 60, 550);
	    		me.setColor(Color.white);
	    		me.drawString("--- On a normal ball object will be placed at the mouse cursor", 250, 550);
	    		me.setColor(Color.orange);
	    		me.drawString("Gravity Activated", 60, 575);
	    		me.setColor(Color.white);
	    		me.drawString("--- Gravity will now act on the ball", 250, 575);
	    		me.setColor(Color.black);
	    		
	    		// 45 Degrees
	    		iObstacle.setY(275);
	    		iObstacle.setAngle((iObstacle.getAngle() + 45) % 360);
	    		
				Rectangle2D rect1 = new Rectangle2D.Double(iObstacle.getX(), iObstacle.getY(), 150, 50);
	    		transform1.rotate(Math.toRadians(iObstacle.getAngle()), iObstacle.getX() + 75, iObstacle.getY() + 25);
				// Creating the transformed object
				Shape transformed1 = transform1.createTransformedShape(rect1);
				// Drawing our transformed object
				g2d.fill(transformed1);
				
				// 90 Degrees
				iObstacle.setX(200);
	    		iObstacle.setAngle((iObstacle.getAngle() + 45) % 360);
				
				Rectangle2D rect2 = new Rectangle2D.Double(iObstacle.getX(), iObstacle.getY(), 150, 50);
	    		transform2.rotate(Math.toRadians(iObstacle.getAngle()), iObstacle.getX() + 75, iObstacle.getY() + 25);
				// Creating the transformed object
				Shape transformed2 = transform2.createTransformedShape(rect2);
				// Drawing our transformed object
				g2d.fill(transformed2);
				
				// 135 Degrees
				iObstacle.setX(340);
				iObstacle.setAngle((iObstacle.getAngle() + 45) % 360);
				
				Rectangle2D rect3 = new Rectangle2D.Double(iObstacle.getX(), iObstacle.getY(), 150, 50);
	    		transform3.rotate(Math.toRadians(iObstacle.getAngle()), iObstacle.getX() + 75, iObstacle.getY() + 25);
				// Creating the transformed object
				Shape transformed3 = transform3.createTransformedShape(rect3);
				// Drawing our transformed object
				g2d.fill(transformed3);
	    	}
	    }
    public void setCornerCoordinates(Obstacle iObstacle) 
    {
    	// Variable Declaration
    	double tempX;
    	double tempY;
    	double rotatedX;
    	double rotatedY;
    	double cX = iObstacle.getCenterX();
    	double cY = iObstacle.getCenterY();
    	double angle = Math.toRadians(45);
    	
    	// Top Left Corner Adjustment
    	
    	// Translate the point to origin
    	tempX = iObstacle.getTLCrX() - cX;gg
    	tempY = iObstacle.getTLCrY() - cY;
    	
    	// Apply "rotation" to the point
    	rotatedX = tempX*Math.cos(angle) - tempY*Math.sin(angle);
    	rotatedY = tempX*Math.sin(angle) + tempY*Math.cos(angle);
    	
    	// Translate back rotated point back, this is the new point!
    	iObstacle.setTLCrX(rotatedX + cX);
    	iObstacle.setTLCrY(rotatedY + cY);
    	
    	// Top Right Corner Adjustment
    	tempX = iObstacle.getTRCrX() - cX;
    	tempY = iObstacle.getTRCrY() - cY;
    	rotatedX = tempX*Math.cos(angle) - tempY*Math.sin(angle);
    	rotatedY = tempX*Math.sin(angle) + tempY*Math.cos(angle);
    	iObstacle.setTRCrX(rotatedX + cX);	
    	iObstacle.setTRCrY(rotatedY + cY);
    	
    	// Bottom Right Corner Adjustment
    	tempX = iObstacle.getBRCrX() - cX;
    	tempY = iObstacle.getBRCrY() - cY;
    	rotatedX = tempX*Math.cos(angle) - tempY*Math.sin(angle);
    	rotatedY = tempX*Math.sin(angle) + tempY*Math.cos(angle);
    	iObstacle.setBRCrX(rotatedX + cX);
    	iObstacle.setBRCrY(rotatedY + cY);
    	// Bottom Left Corner Adjustment
    	tempX = iObstacle.getBLCrX() - cX;
    	tempY = iObstacle.getBLCrY() - cY;
    	rotatedX = tempX*Math.cos(angle) - tempY*Math.sin(angle);
    	rotatedY = tempX*Math.sin(angle) + tempY*Math.cos(angle);
    	iObstacle.setBLCrX(rotatedX + cX);
    	iObstacle.setBLCrY(rotatedY + cY);
    }
}
