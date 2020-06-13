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
import java.awt.geom.AffineTransform;

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
    Ball[] ballObstacle = new Ball[1]; // Array holding the ball obstacle, MAX 1 at all times
    
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
						// Checking the mouse coordinates are within the borders of any drawn obstacles
						if(mouseX >= stationaryObstacles[i].getX() && mouseX <= (stationaryObstacles[i].getX() + 150) && mouseY >= stationaryObstacles[i].getY() && mouseY <= (stationaryObstacles[i].getY() + 50) ) 
						{
							// Adjust angle of obstacle (add 45 rads to existing angle)
							stationaryObstacles[i].setAngle(((stationaryObstacles[i].getAngle() + 45) % 360));
						}
					}
				}
			}
			// Drop Ball Object at mouse coordinates
			else if(intObstacleType == 3)
			{
				if (ballObstacle[0] == null)
				{
					ballObstacle[0] = new Ball(mouseX, mouseY, 10);		
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
    		b.setVelx(b.getVelx() * (-1));
    	}
    	//Check if ball hits bottom or top wall
    	if (b.getY() - b.getRadius() < 0 || b.getY() > 600 - b.getRadius())
    	{
    		b.setVely(b.getVely() * (-1));
    	}    	
    }
    //Check if ball intersects with an obstacle
    public boolean intersects(Ball b, Obstacle o)
    {
    	//Rotate circle's center 
    	double unrotatedCircleX = Math.cos(o.getAngle()) * (b.getX() - o.getCenterX()) - Math.sin(o.getAngle()) * (b.getY() - o.getCenterY()) +o.getCenterX();
    	double unrotatedCircleY = Math.sin(o.getAngle()) * (b.getX() - o.getCenterX()) + Math.cos(o.getAngle()) * (b.getY() - o.getCenterY()) + o.getCenterY();
    	
    	//Closest point in rectangle to center of rotated circle
    	double closestX = unrotatedCircleX;
    	double closestY = unrotatedCircleY;
    		
    	//Find closest x point from center of circle
    	if (unrotatedCircleX < o.getX())
    	{
    		closestX = o.getX();
    	}
    	else if (unrotatedCircleX > o.getX() + o.getLength())
    	{
    		closestX = o.getX() + o.getLength();
    	}
    		
    	//Find closest y point from center of circle
    	if (unrotatedCircleY < o.getY())
    	{
    		closestY = o.getY();
    	}
    	else if (unrotatedCircleY > o.getY() + o.getWidth())
    	{
    		closestY = o.getY() + o.getWidth();
    	}  
    	//Get distance from closest edges
    	double distX = Math.abs(unrotatedCircleX - closestX);
	    double distY = Math.abs(unrotatedCircleY - closestY);
	    double distance = (distX * distX) + (distY * distY);
	    
	    if (distance <= (b.getRadius() * b.getRadius()))
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
    			if (intersects(b, o[i]))
    			{
    				if (o[i].getAngle() == 0 || o[i].getAngle() == 180)
    				{
	    				if (b.getX() < o[i].getX()) //left edge
	    		    	{
	    					b.setVelx(b.getVelx() * (-1));
	    		    	}
	    		    	else if (b.getX() > o[i].getX() + 150) //right edge
	    		    	{
	    		    		b.setVelx(b.getVelx() * (-1));
	    		    	}
	    		    	if (b.getY()  < o[i].getY()) //top edge
	    		    	{
	    		    		b.setVely(b.getVely() * (-1));
	    		    	}
	    		    	else if (b.getY() > o[i].getY() + 50)//bottom edge
	    		    	{
	    		    		b.setVely(b.getVely() * (-1));
	    		    	}
    				}
    				else
    				{
    					System.out.println("hit");
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
	    		me.drawString("Press I to go to Instruction Menu", 536, 660);
	    		me.drawString("Press R to Reset the Screen", 575, 690);	   
	    		
		    	// Notification that Flat Obstacle is selected
		    	if(intObstacleType == 1) 
		    	{
		    		me.setColor(Color.blue);
		    		me.setFont(new Font("Serif",Font.BOLD,24));
		    		me.drawString("Flat Obstacle EQUIPPED", 230, 40);
		    		me.setColor(Color.black);
		    	}
		    	
		    	// Notification that Tilting mode is selected
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
		    		me.drawString("Ball Object EQUIPPED", 230, 40);
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
		    	
		    	//draw Ball
		    	for(int i = 0; i < ballObstacle.length; i++)		    	
		    	{
		    		if (ballObstacle[i] != null)
		    		{
		    			me.fillOval(ballObstacle[0].getX() - ballObstacle[0].getRadius(), ballObstacle[0].getY() - ballObstacle[0].getRadius(), ballObstacle[0].getRadius()*2, ballObstacle[0].getRadius()*2);		
		    			//mimic movement off the ball
		    			ballObstacle[i].setY(ballObstacle[i].getY() + ballObstacle[i].getVely());
		    			ballObstacle[i].setX(ballObstacle[i].getX() + ballObstacle[i].getVelx());
		    			checkWalls(ballObstacle[i]);
		    			checkObstacles(ballObstacle[i], stationaryObstacles);
		    		}
		    		
		    	}
		    	
		    	// For loop that goes through the array storing all STATIONARY obstacles
		    	for(int i = 0; i < stationaryObstacles.length; i++) 
		    	{
		    		// Ensuring the array index is NOT NULL
		    		if(stationaryObstacles[i] != null) 
		    		{
		    			// If the angle is 0 or 180, we can simply draw a flat rectangle
		    			if(stationaryObstacles[i].getAngle() == 0 || stationaryObstacles[i].getAngle() == 180)
		    			{
		    				// Drawing the rectangle using the objects X and Y coordinates
		    				me.fillRect(stationaryObstacles[i].getX(), stationaryObstacles[i].getY(), stationaryObstacles[i].getLength(), stationaryObstacles[i].getWidth());
		    			}
		    			// Otherwise, we need to rotate the rectangle to its given angle
		    			else 
		    			{
		    				// 2D Graphics and AffineTransformation declarations
		    				Graphics2D g2d = (Graphics2D)g;
		    				AffineTransform transform = new AffineTransform();
		    				
		    				// Creating a rectangle object using the obstacles information
		    				Rectangle rect = new Rectangle(stationaryObstacles[i].getX(), stationaryObstacles[i].getY(), stationaryObstacles[i].getLength(), stationaryObstacles[i].getWidth());
		    				
		    				// Rotating the rectangle by the obstacles given angle around the CENTER of the rectangle
		    				transform.rotate(Math.toRadians(stationaryObstacles[i].getAngle()), rect.getX() + 75, rect.getY() + 25);
		    				
		    				// Creating the transformed object
		    				Shape transformed = transform.createTransformedShape(rect);
		    				
		    				// Drawing our transformed object
		    				g2d.fill(transformed);
		    			}
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
	    		me.drawString("Here is an example of the DEFAULT obstacle", 60, 80);
	    		me.setColor(Color.black);
	    		me.fillRect(iObstacle.getX(), iObstacle.getY(), 150, 50);
	    		
	    		// Displaying the various ANGLES of the Obstacle we declared
	    		me.setColor(Color.white);
	    		me.setFont(new Font("Serif", Font.BOLD, 16));
	    		me.drawString("Here are the ANGLED obstacles that can be made", 60, 200);
	    		me.setColor(Color.black);
	    		
	    		// 45 Degrees
	    		iObstacle.setY(275);
	    		iObstacle.setAngle((iObstacle.getAngle() + 45) % 360);
	    		
				Rectangle rect1 = new Rectangle(iObstacle.getX(), iObstacle.getY(), 150, 50);
	    		transform1.rotate(Math.toRadians(iObstacle.getAngle()), iObstacle.getX() + 75, iObstacle.getY() + 25);
				// Creating the transformed object
				Shape transformed1 = transform1.createTransformedShape(rect1);
				// Drawing our transformed object
				g2d.fill(transformed1);
				
				// 90 Degrees
				iObstacle.setX(200);
	    		iObstacle.setAngle((iObstacle.getAngle() + 45) % 360);
				
				Rectangle rect2 = new Rectangle(iObstacle.getX(), iObstacle.getY(), 150, 50);
	    		transform2.rotate(Math.toRadians(iObstacle.getAngle()), iObstacle.getX() + 75, iObstacle.getY() + 25);
				// Creating the transformed object
				Shape transformed2 = transform2.createTransformedShape(rect2);
				// Drawing our transformed object
				g2d.fill(transformed2);
				
				// 135 Degrees
				iObstacle.setX(340);
				iObstacle.setAngle((iObstacle.getAngle() + 45) % 360);
				
				Rectangle rect3 = new Rectangle(iObstacle.getX(), iObstacle.getY(), 150, 50);
	    		transform3.rotate(Math.toRadians(iObstacle.getAngle()), iObstacle.getX() + 75, iObstacle.getY() + 25);
				// Creating the transformed object
				Shape transformed3 = transform3.createTransformedShape(rect3);
				// Drawing our transformed object
				g2d.fill(transformed3);
	    	}
	    }
}
