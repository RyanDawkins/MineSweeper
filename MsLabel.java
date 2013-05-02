import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.Serializable;

public class MsLabel extends JLabel implements Serializable
{

	// Generic Labels
	private static final String UNKNOWN 	= "[?]";
	private static final String FLAG 		= "[F]";
	private static final String BOMB		= "[*]";
	
	private MsLabel[][] gridArr;
	private Integer x;
	private Integer y;
	
	LabelModel lb;
	
	private Color currentColor;
	private String currentText;

	public MsLabel()
	{
		super(UNKNOWN);
		
		this.currentText = UNKNOWN;
		this.currentColor = Color.BLACK;
		
		setHorizontalAlignment(0);
		lb = new LabelModel();
		setMouse();
		setBorder(BorderFactory.createRaisedBevelBorder());
		repaint();
	}
	
	public void setMouse()
	{
		addMouseListener(this.new MouseHandler());
	}
	
	public void setGridArray(MsLabel[][] gridArr)
	{ 
		this.gridArr = gridArr;
		lb.setArr(gridArr);
	}
	
	public void setCoordinates(int x, int y)
	{ 
		this.x = x; this.y = y;
		this.lb.setCoordinates(x, y);
	}
	
	public LabelModel getModel()
	{ return this.lb; }
	
	private class MouseHandler extends MouseAdapter
	{
	
		public void mousePressed(MouseEvent e)
		{
			if(e.getButton() == MouseEvent.BUTTON1)
			{
				if(lb != null)
				{
					if(lb.isDisabled()){
						repaint(); 
						return; 
					}
					if(!(lb.hasShown())) // THIS IS LINE 70
					{
						if(lb.isNotBomb())
						{
							// Sets text
							currentText = ""+lb.bombsSurrounding();
							setText(currentText);
							
							// Sets color
							currentColor = Color.BLUE;
							setForeground(currentColor);
		
							LabelModel.counter--;
							repaint();
						}
						else
						{
							// Sets color
							currentColor = Color.RED;
							currentText = BOMB;
							
							// Sets text
							setText(currentText);
							setForeground(currentColor);
							
							lb.reset();
						}
						lb.isShown();
					} else{}
				}
			}
			else if(e.getButton() == MouseEvent.BUTTON3)
			{
				if(!getText().equals(FLAG))
				{
					currentText = FLAG;
					setText(currentText);
					
					currentColor = Color.RED;
					setForeground(currentColor);
				}
				else
				{
					currentText = UNKNOWN;
					currentColor = Color.BLACK;
					
					setText(currentText);
					setForeground(currentColor);
				}
			}
			if(LabelModel.counter == 0)
			{
				JOptionPane.showMessageDialog(null, "You win!");
			}
			repaint();
		}
	}
	
}