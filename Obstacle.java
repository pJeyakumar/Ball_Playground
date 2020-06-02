package javagraphicstemplate;

public class Obstacle 
{
	// Instance Variables
	private int x;
	private int y;
	private boolean isFlat;
	// Constructor 
	Obstacle(int x, int y, boolean isFlat)
	{
		this.x = x;
		this.y = y;
		this.isFlat = isFlat;
	}
	
	// Returning X Coordinate
	private int getX() 
	{
		return this.x;
	}
	// Returning Y Coordinate
	private int getY() 
	{
		return this.y;
	}
	// If the Obstacle is Flat, True is returned, if the Obstacle is Slanted, False is returned
	private boolean isFlat() {
		return this.isFlat;
	}
}
