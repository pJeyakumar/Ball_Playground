package javagraphicstemplate;

public class Obstacle extends MyAppClass
{
	// Instance Variables
	private int x;
	private int y;
	private boolean isFlat;
	private boolean isRound;
	// Constructor 
	Obstacle(int x, int y, boolean isFlat, boolean isRound)
	{
		this.x = x;
		this.y = y;
		this.isFlat = isFlat;
		this.isRound = isRound;
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
	// If the Obstacle is Flat, True is returned, if the Obstacle is Slanted, False is returned
	public boolean isFlat() 
	{
		return isFlat;
	}
	// If the obstacle is round, True is returned, meaning the obstacle is a ball
	public boolean isRound() 
	{
		return isRound;
	}
}
