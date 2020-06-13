package javagraphicstemplate;

public class Obstacle 
{
	// Instance Variables
	private int x;
	private int y;
	private double angle;
	private boolean isRound;
	private int length;
	private int width;
	private int centerX;
	private int centerY;
	// Constructor 
	Obstacle(int x, int y, boolean isRound, int angle)
	{
		this.x = x;
		this.y = y;
		this.isRound = isRound;
		this.angle = angle;
		this.length = 150;
		this.width = 50;
		this.centerX = this.x + 75;
		this.centerY = this.y + 25;
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
	//Return centerY
	public int getCenterY()
	{
		return centerY;
	}
	//Return centerX
	public int getCenterX()
	{
		return centerX;
	}
	//return Width
	public int getWidth()
	{
		return width;
	}
	//return Length
	public int getLength()
	{
		return length;
	}
	// Set the x coordinate of the obstacle
	public void setX(int x) 
	{
		this.x = x;
	}
	// Set the y coordinate of the obstacle
	public void setY(int y) 
	{
		this.y = y;
	}
	// If the obstacle is round, True is returned, meaning the obstacle is a ball
	public boolean isRound() 
	{
		return isRound;
	}
	// Returns the angle of the obstacle
	public double getAngle() 
	{
		return angle;
	}
	// Set the angle of the obstacle
	public void setAngle(double angle)
	{
		this.angle = angle;
	}
}
