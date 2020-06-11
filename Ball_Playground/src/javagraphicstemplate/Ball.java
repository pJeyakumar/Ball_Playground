package javagraphicstemplate;

public class Ball {
	private int x;
	private int y;
	private int radius;
	private int velx;
	private int vely;
	
	//Constructor
	Ball(int x, int y, int radius)
	{
		this.x = x;
		this.y = y;
		this.velx = 1;
		this.vely = 1;
		this.radius = radius;
	}
	// Returning X Coordinate
	public int getX() 
	{
		return x;
	}
	// Returning Y Coordinate
	public int getY() 
	{
		return y;
	}
	//Return radius
	public int getRadius()
	{
		return radius;
	}
	//get horizontal velocity
	public int getVelx()
	{
		return velx;
	}
	//get vertical velocity
	public int getVely()
	{
		return vely;
	}
	// Set the x coordinate of the ball
	public void setX(int x) 
	{
		this.x = x;
	}
	// Set the y coordinate of the ball
	public void setY(int y) 
	{
		this.y = y;
	}	
	//Set the x velocity of ball
	public void setVelx(int velx)
	{
		this.velx = velx;
	}
	//Set the y velocity of ball
	public void setVely(int vely)
	{
		this.vely = vely;
	}
}
