package javagraphicstemplate;

public class Obstacle extends MyAppClass
{
	// Instance Variables
	private int x;
	private int y;
	private int angle;
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
	// If the obstacle is round, True is returned, meaning the obstacle is a ball
	public boolean isRound() 
	{
		return isRound;
	}
	// Returns the angle of the obstacle
	public int getAngle() 
	{
		return angle;
	}
}
