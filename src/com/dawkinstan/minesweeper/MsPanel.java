import java.awt.*;
import javax.swing.*; 

public class MsPanel extends JPanel
{

    // Buttons
    private static final int l = 10;
    private static final int h = 10;
    
    MsLabel[][] grid;

	public MsPanel()
	{
		this(l, h);
	}

	public MsPanel(int a, int b)
	{
		super();
		setLayout(new GridLayout(a, b));
		grid = new MsLabel[a][b];
		for(int y = 0; y < b; y++)
        {
        	for(int x = 0; x < a; x++)
        	{
        		grid[x][y] = new MsLabel();
        		grid[x][y].setGridArray(grid);
        		grid[x][y].setCoordinates(y, x);
        		add(grid[x][y]);
        	}
        }
		
	}
	
	public MsPanel(MsLabel[][] grid)
	{
		super();
		setLayout(new GridLayout(grid.length, grid.length));
		
		for(int y = 0; y < grid.length; y++)
		{
			for(int x = 0; x < grid.length; x++)
			{
				grid[x][y].setGridArray(grid);
				grid[x][y].setMouse();
				add(grid[x][y]);
			}
		}
	}

	public MsLabel[][] getGrid()
	{
		return this.grid;
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}
}