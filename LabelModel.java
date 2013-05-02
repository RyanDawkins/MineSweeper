import java.util.Random;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.io.Serializable;

public class LabelModel implements Serializable
{

	private boolean isNotBomb = false;
	private boolean isShown = false;
	private int x;
	private int y;
	private MsLabel[][] labelArr;
	private boolean isDisabled = false;
	private static boolean firstClick = true;
	public static int  counter = 0;

	public LabelModel()
	{
		// Defaults
		isShown = false;
		isNotBomb = false;
	
		// Bomb Gen
		int randomNum = 1 + (int)(Math.random() * 5);
		if(randomNum == 3)
		{ isNotBomb = false; }
		else 
		{
			counter++;
			isNotBomb = true;
		}
	}
	
	public boolean hasShown()
	{
		if(isShown)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public void isShown()
	{ isShown = true; }
	
	public boolean load()
	{
		return isNotBomb;
	}
	
	public boolean isNotBomb()
	{
		if(firstClick)
		{
			if(!isNotBomb)
			{
				isNotBomb = true;
				counter++;
			}
			firstClick = false;
		}
		return isNotBomb; 
	}
	
	public void setCoordinates(int x, int y)
	{ this.x = x; this.y = y;}
	
	public void setArr(MsLabel[][] labelArr)
	{
		this.labelArr = labelArr;
	}
	
	public void setDisabled(boolean isDisabled)
	{
		this.isDisabled = isDisabled;
	}
	
	public boolean isDisabled()
	{
		if(isDisabled)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public int bombsSurrounding()
	{
		int count = 0;
		if(this.y < labelArr.length - 1)
		{
			if(!labelArr[this.y + 1][this.x].getModel().isNotBomb()) count++;
		}
		if(this.x < labelArr.length - 1)
		{
			if(!labelArr[this.y][this.x + 1].getModel().isNotBomb()) count++;
		}
		if(this.y > 0)
		{
			if(!labelArr[this.y - 1][this.x].getModel().isNotBomb()) count++;
		}
		if(this.x > 0)
		{
			if(!labelArr[this.y][this.x - 1].getModel().isNotBomb()) count++;
		}
		
		return count;
	}
	
	public void reset()
	{
		try{
			JOptionPane.showMessageDialog(null, "Oh Crap! You suck!");
			for(int y = 0; y < labelArr.length; y++)
			{
				for(int x = 0; x < labelArr[y].length; x++)
				{
					labelArr[y][x].getModel().setDisabled(true);
					if(!labelArr[y][x].getModel().isNotBomb())
					{
						labelArr[y][x].setText("[*]");
						labelArr[y][x].setForeground(Color.RED);
					}
				}
			}
		} catch(Exception e){ System.out.println(e.getMessage()); }
	}
	
}