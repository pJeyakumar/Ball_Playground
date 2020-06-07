package javagraphicstemplate;

public class Obstacle extends MyAppClass
{
	// Instance Variables
	private int x;
	private int y;
	private double angle;
	private boolean isRound;
	// Constructor 
	Obstacle(int x, int y, boolean isRound, int angle)
	{
		this.x = x;
		this.y = y;
		this.isRound = isRound;
		this.angle = angle;
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
