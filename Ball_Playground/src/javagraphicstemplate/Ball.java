package javagraphicstemplate;

public class Ball {
	private double x;
	private double y;
	private double radius;
	private double velx;
	private double vely;
	private double gravityY;
	private double accleration;
	
	//Constructor
	Ball(double x, double y, double radius)
	{
		this.x = x;
		this.y = y;
		this.velx = 0.2;
		this.vely = 0.0;
		this.gravityY = 0.0;
		this.accleration = 0.0002;
		this.radius = radius;
	}
	// Returning X Coordinate
	public double getX() 
	{
		return x;
	}
	// Returning Y Coordinate
	public double getY() 
	{
		return y;
	}
	//Return radius
	public double getRadius()
	{
		return radius;
	}
	//get horizontal velocity
	public double getVelx()
	{
		return velx;
	}
	//get vertical velocity
	public double getVely()
	{
		return vely;
	}
	//get accleration (gravity)
	public double getAcc()
	{
		return accleration;
	}
	//get gravityY
	public double getGravityY()
	{
		return gravityY;
	}
	// Set the x coordinate of the ball
	public void setX(double x) 
	{
		this.x = x;
	}
	// Set the y coordinate of the ball
	public void setY(double y) 
	{
		this.y = y;
	}	
	//Set y velocity with gravity
	public void setGravityY(double y)
	{
		this.gravityY = y;
	}
	//Set the x velocity of ball
	public void setVelx(double velx)
	{
		this.velx = velx;
	}
	//Set the y velocity of ball
	public void setVely(double vely)
	{
		this.vely = vely;
	}
}
