import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.*;

public class MsFrame extends JFrame
{

	private int WINDOW_HEIGHT = 500;
    private int WINDOW_WIDTH = 500;
	private String title = "MineSweeper";
    private boolean isVisible = true;
	private MsPanel msp;

	public MsFrame()
	{
	
		// Defaults
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        // Content pane junk...
        Container contentPane = getContentPane();
       	msp = new MsPanel();
        contentPane.add(getMenu(), BorderLayout.PAGE_START);
        contentPane.add(msp, BorderLayout.CENTER);
        
        setVisible(isVisible);
	}
	
	private JMenuBar getMenu()
	{
		JMenuBar menuBar = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		
		// New Menu Items
		JMenu newMenu = new JMenu("New");
		JMenuItem itemEasy = new JMenuItem("Easy");
		itemEasy.addActionListener(new MenuListener("Easy"));
		newMenu.add(itemEasy);
		JMenuItem itemHard = new JMenuItem("Hard");
		itemHard.addActionListener(new MenuListener("Hard"));
		newMenu.add(itemHard);
		
		// Save
		JMenuItem itemSave = new JMenuItem("Save");
		itemSave.addActionListener(new MenuListener("Save"));
		
		// Load
		JMenuItem itemLoad = new JMenuItem("Load");
		itemLoad.addActionListener(new MenuListener("Load"));
		
		// Exit
		JMenuItem itemExit = new JMenuItem("Exit");
		itemExit.addActionListener(new MenuListener("Exit"));
		
		// Adds items to file menu
		fileMenu.add(newMenu);
		fileMenu.add(itemSave);
		fileMenu.add(itemLoad);
		fileMenu.add(itemExit);
		
		menuBar.add(fileMenu);
		return menuBar;
	}
	
	private class MenuListener implements ActionListener
	{
	
		String text;
		
		public MenuListener(String text)
		{
			this.text = text;
		}
	
		public void actionPerformed(ActionEvent e)
		{
			if(text.equals("Easy"))
			{
				getContentPane().remove(msp);
				msp = new MsPanel(10, 10);
				getContentPane().add(msp, BorderLayout.CENTER);
				getContentPane().revalidate();
				repaint();
			}
			else if(text.equals("Hard"))
			{
				getContentPane().remove(msp);
				msp = new MsPanel(20, 20);
				getContentPane().add(msp, BorderLayout.CENTER);
				getContentPane().revalidate();
				repaint();
			}
			else if(text.equals("Save"))
			{
				MsLabel[][] grid = msp.getGrid();
				File file = null;
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showSaveDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					file = chooser.getSelectedFile();
				}
				
				ObjectOutputStream outputStream = null;
				
				try
				{
					outputStream = new ObjectOutputStream(new FileOutputStream(file));
					outputStream.write("".getBytes());
					outputStream.flush();
					outputStream.writeObject(msp.getGrid());
				}
				catch (FileNotFoundException exception)
				{
					exception.printStackTrace();
				}
				catch (IOException exception)
				{
					exception.printStackTrace();
				}
				try
				{
					if(outputStream != null)
					{
						outputStream.flush();
						outputStream.close();
					}
				}
				catch(IOException exception)
				{
					exception.printStackTrace();
				}
			}
			else if(text.equals("Load"))
			{
				
				ObjectInputStream inputStream = null;
				Object object = null;
				MsLabel[][] grid = null;
				
				File file = null;
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(null);
				if(returnVal == JFileChooser.APPROVE_OPTION) 
				{
					file = chooser.getSelectedFile();
				}
				
				try
				{
					inputStream = new ObjectInputStream(new FileInputStream(file));
					while ((object = inputStream.readObject()) != null){}
				}
				catch(EOFException exception){}
				catch(Exception exception)
				{
					exception.printStackTrace();
				}
				try
				{
					if(inputStream != null)
					{
						grid = (MsLabel[][])object;
						getContentPane().remove(msp);
						msp = new MsPanel(grid);
						getContentPane().add(msp, BorderLayout.CENTER);
						getContentPane().revalidate();
						repaint();
						inputStream.close();
					}
				}
				catch(Exception exception)
				{
					exception.printStackTrace();
				}
				
				
			}
			else if(text.equals("Exit"))
			{
				System.exit(0);
			}
		}
	}
	
}