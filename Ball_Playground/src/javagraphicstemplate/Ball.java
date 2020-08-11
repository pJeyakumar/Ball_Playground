package javagraphicstemplate;

public class Ball {
	private double x;
	private double y;
	private double radius;
	private double bounce;
	private double velx;
	private double vely;
	private double gravityY;
	private double accleration;
	
	//Constructor
	Ball(double x, double y, double radius, int ball_type)
	{
		this.x = x;
		this.y = y;
		this.velx = 0.4;
		this.vely = 0.0;
		this.gravityY = 0.0;
		this.accleration = 0.0004;
		//Bouncy Ball
		if (ball_type == 0)
		{
			this.bounce = -1.0;
		}
		else if(ball_type == 1)
		{
			this.bounce = -1.25;
		}
		else
		{
			this.bounce = -0.85;
		}
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
	//Get the elasticity value of the ball
	public double getBounce()
	{
		return bounce;
	}
}
