package javagraphicstemplate;

public class Obstacle 
{
	// Instance Variables
	private double x;
	private double y;
	private double angle;
	private boolean isRound;
	private int length;
	private int width;
	private double centerX;
	private double centerY;
	private double topLeftCornerY;
	private double topLeftCornerX;
	private double topRightCornerY;
	private double topRightCornerX;
	private double botLeftCornerY;
	private double botLeftCornerX;
	private double botRightCornerY;
	private double botRightCornerX;
	
	// Constructor 
	Obstacle(double x, double y, boolean isRound, int angle)
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
	public double getX() 
	{
		return x;
	}
	// Returning Y Coordinate
	public double getY() 
	{
		return y;
	}
	//Return centerY
	public double getCenterY()
	{
		return centerY;
	}
	//Return centerX
	public double getCenterX()
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
	public void setX(double x) 
	{
		this.x = x;
	}
	// Set the y coordinate of the obstacle
	public void setY(double y) 
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
	// Setting the TR corner y coordinate
	public void setTRCrY(double crY) 
	{
		this.topRightCornerY = crY;
	}
	// Setting the TR corner x coordinate
	public void setTRCrX(double crX) 
	{
		this.topRightCornerX = crX;
	}
	// Getting the TR corner y coordinate
	public double getTRCrY() 
	{
		return this.topRightCornerY;
	}
	// Getting the TR corner x coordinate
	public double getTRCrX()
	{
		return this.topRightCornerX;
	}
	// Setting the BL corner y coordinate
	public void setBLCrY(double crY) 
	{
		this.botLeftCornerY = crY;
	}
	// Setting the BL corner x coordinate
	public void setBLCrX(double crX) 
	{
		this.botLeftCornerX = crX;
	}
	// Getting the BL corner y coordinate
	public double getBLCrY() 
	{
		return this.botLeftCornerY;
	}
	// Getting the BL corner x coordinate
	public double getBLCrX()
	{
		return this.botLeftCornerX;
	}
	// Setting the BR corner y coordinate
	public void setBRCrY(double crY) 
	{
		this.botRightCornerY = crY;
	}
	// Setting the BR corner x coordinate
	public void setBRCrX(double crX) 
	{
		this.botRightCornerX = crX;
	}
	// Getting the BR corner y coordinate
	public double getBRCrY() 
	{
		return this.botRightCornerY;
	}
	// Getting the BR corner x coordinate
	public double getBRCrX()
	{
		return this.botRightCornerX;
	}
	// Setting the TL corner y coordinate
	public void setTLCrY(double crY) 
	{
		this.topLeftCornerY = crY;
	}
	// Setting the TL corner x coordinate
	public void setTLCrX(double crX) 
	{
		this.topLeftCornerX = crX;
	}
	// Getting the TL corner y coordinate
	public double getTLCrY() 
	{
		return this.topLeftCornerY;
	}
	// Getting the TL corner x coordinate
	public double getTLCrX()
	{
		return this.topLeftCornerX;
	}
	public boolean inRotatedObstacle(double pX, double pY) 
	{
		// Triangle APB
		double Area1 = Math.abs((pX * topLeftCornerY - topLeftCornerX * pY) + (topRightCornerX * pY - pX * topRightCornerY) + (topLeftCornerX * topRightCornerY - topRightCornerX * topLeftCornerY))/2;
		// Triangle APC
		double Area2 = Math.abs((pX * topLeftCornerY - topLeftCornerX * pY) + (botLeftCornerX * pY - pX * botLeftCornerY) + (topLeftCornerX * botLeftCornerY - botLeftCornerX * topLeftCornerY))/2;
		// Triangle CPD
		double Area3 = Math.abs((pX * botLeftCornerY - botLeftCornerX * pY) + (botRightCornerX * pY - pX * botRightCornerY) + (botLeftCornerX * botRightCornerY - botRightCornerX * botLeftCornerY))/2;
		// Triangle DPB
		double Area4 = Math.abs((pX * botRightCornerY - botRightCornerX * pY) + (topRightCornerX * pY - pX * topRightCornerY) + (botRightCornerX * topRightCornerY - topRightCornerX * botRightCornerY))/2;
		
		double sumAreas = Area1 + Area2 + Area3 + Area4;
		
		if(sumAreas <= (this.getWidth() * this.getLength() + 0.1)) 
		{
			System.out.println(sumAreas);
			return true;
		}
		else 
		{
			//System.out.println(sumAreas);
			return false;
		}
	}
		public void findSide(double bX, double bY) 
	{
		double dblAngle = this.getAngle();
		
		if(dblAngle == 0) 
		{
			if(bY > this.getTLCrY() && bY < this.getBLCrY() && bX < this.getTLCrX()) 
			{
				obstacleSide = 1;
			}
			else if(bX > this.getTLCrX() && bX < this.getTRCrX() && bY < this.getTLCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bY > this.getTRCrY() && bY < this.getBRCrY() && bX > this.getTRCrX()) 
			{
				obstacleSide = 3;
			}
			else if (bX > this.getBLCrX() && bX < this.getBRCrX() && bY > this.getBLCrY()) 
			{
				obstacleSide = 4;
			}
		}
		else if(dblAngle == 45) 
		{
			if(bX < this.getTLCrX() && bY < this.getBLCrY()) 
			{
				obstacleSide = 1;
			}
			else if(bX > this.getTLCrX() && bY < this.getTRCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bX > this.getBRCrX() && bY > this.getTRCrY()) 
			{
				obstacleSide = 3;
			}
			else if(bX < this.getBRCrX() && bY > this.getBLCrY()) 
			{
				obstacleSide = 4;
			}
		}
		else if(dblAngle == 90)
		{
			if(bY > this.getBLCrY() && bY < this.getBRCrY() && bX < this.getBLCrX()) 
			{
				obstacleSide = 1;
			}
			else if(bX > this.getBLCrX() && bX < this.getTRCrX() && bY < this.getTLCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bY > this.getTLCrY() && bY < this.getBRCrY() && bX > this.getTLCrX()) 
			{
				obstacleSide = 3;
			}
			else if(bX > this.getBRCrX() && bX < this.getTLCrX() && bY > this.getTRCrY()) 
			{
				obstacleSide = 4;
			}
		}
		else if(dblAngle == 135) 
		{
			if(bX < this.getTRCrX() && bY > this.getBRCrY()) 
			{
				obstacleSide = 1;
			}
			else if(bX < this.getBLCrX() && bY < this.getBRCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bX > this.getBLCrX() && bY < this.getTLCrY()) 
			{
				obstacleSide = 3;
			}
			else if(bX > this.getTRCrX() && bY > this.getTLCrY()) 
			{
				obstacleSide = 4;
			}
		}
		if(dblAngle == 180) 
		{
			if(bY > this.getBRCrY() && bY < this.getTRCrY() && bX < this.getBRCrX()) 
			{
				obstacleSide = 1;
			}
			else if(bX > this.getBRCrX() && bX < this.getBLCrX() && bY < this.getBRCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bY > this.getBLCrY() && bY < this.getTLCrY() && bX > this.getBLCrX()) 
			{
				obstacleSide = 3;
			}
			else if (bX > this.getTRCrX() && bX < this.getTLCrX() && bY > this.getTRCrY()) 
			{
				obstacleSide = 4;
			}
		}
		else if(dblAngle == 225) 
		{
			if(bX < this.getBRCrX() && bY < this.getTRCrY()) 
			{
				obstacleSide = 1;
			}
			else if(bX > this.getBRCrX() && bY < this.getBLCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bX > this.getTLCrX() && bY > this.getBLCrY()) 
			{
				obstacleSide = 3;
			}
			else if(bX < this.getTLCrX() && bY > this.getTRCrY()) 
			{
				obstacleSide = 4;
			}
		}
		else if(dblAngle == 270)
		{
			if(bY > this.getTRCrY() && bY < this.getTLCrY() && bX < this.getTRCrX()) 
			{
				obstacleSide = 1;
			}
			else if(bX > this.getTRCrX() && bX < this.getBRCrX() && bY < this.getTRCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bY > this.getBRCrY() && bY < this.getBLCrY() && bX > this.getBRCrX()) 
			{
				obstacleSide = 3;
			}
			else if(bX > this.getTLCrX() && bX < this.getBLCrX() && bY > this.getTLCrY()) 
			{
				obstacleSide = 4;
			}
		}
		else if(dblAngle == 315) 
		{
			if(bX < this.getBLCrX() && bY > this.getTLCrY()) 
			{
				obstacleSide = 1;
			}
			else if(bX < this.getTRCrX() && bY < this.getTLCrY()) 
			{
				obstacleSide = 2;
			}
			else if(bX > this.getTRCrX() && bY < this.getBRCrY()) 
			{
				obstacleSide = 3;
			}
			else if(bX > this.getBLCrX() && bY > this.getBRCrY()) 
			{
				obstacleSide = 4;
			}
		}
	}
}
