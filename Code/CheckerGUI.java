/*
 * CheckerGUI.java
 * 
 * The actual board.
 *
 * Created on January 25, 2002, 2:34 PM
 * 
 * Version
 * $Id: CheckerGUI.java,v 1.1 2002/10/22 21:12:52 se362 Exp $
 * 
 * Revisions
 * $Log: CheckerGUI.java,v $
 * Revision 1.1  2002/10/22 21:12:52  se362
 * Initial creation of case study
 *
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import java.net.*;

/**
 * 
 * @author
 * @version
 */

public class CheckerGUI extends JFrame implements ActionListener
{

	// the facade for the game

	private static Facade theFacade; // the facade
	private Vector<JButton> possibleSquares = new Vector<JButton>();// a vector
																	// of the
																	// squares
	private int timeRemaining;// the time remaining

	private ArrayList<String> test;
	
	
	private JLabel PlayerOnelabel;
	private JLabel playerTwoLabel;
	private JLabel timeRemainingLabel;
	private JLabel secondsLeftLabel;
	private JButton ResignButton;
	private JButton DrawButton;
	private JLabel warningLabel, whosTurnLabel;

	// the names and time left
	private static String playerOnesName = "", playerTwosName = "",
			timeLeft = "";

	/**
	 * 
	 * Constructor, creates the GUI and all its components
	 * 
	 * @param facade
	 *            the facade for the GUI to interact with
	 * @param name1
	 *            the first players name
	 * @param name2
	 *            the second players name
	 * 
	 */
	public CheckerGUI(Facade facade, String name1, String name2)
	{

		super("Checkers");

		// long names mess up the way the GUI displays
		// this code shortens the name if it is too long
		String nameOne = "", nameTwo = "";
		if (name1.length() > 7)
		{
			nameOne = name1.substring(0, 7);
		}
		else
		{
			nameOne = name1;
		}
		if (name2.length() > 7)
		{
			nameTwo = name2.substring(0, 7);
		}
		else
		{
			nameTwo = name2;
		}

		playerOnesName = nameOne;
		playerTwosName = nameTwo;
		theFacade = facade;
		register();

		initComponents();
		pack();
		update();
		// updateTime();
	}

	/**
	 * This method handles setting up the timer
	 */
	private void register()
	{

		try
		{
			theFacade.addActionListener(this);

		}
		catch (Exception e)
		{

			System.err.println(e.getMessage());

		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * It initializes the components adds the buttons to the Vecotr of squares
	 * and adds an action listener to the components
	 * 
	 */
	private void initComponents()
	{
		int gridx = 0;
		int gridy = 1;
		int gridxCount = 0;
		int gridyCount = 0;
		int MAXX = 8;
		int MAXY = 9;

		
				
		this.setResizable(false);
		
		PlayerOnelabel = new JLabel();
		playerTwoLabel = new JLabel();
		whosTurnLabel = new JLabel();

		warningLabel = new JLabel();
		timeRemainingLabel = new JLabel();
		secondsLeftLabel = new JLabel();

		ResignButton = new JButton();
		ResignButton.addActionListener(this);

		DrawButton = new JButton();
		DrawButton.addActionListener(this);

		// sets the layout and adds listener for closing window
		getContentPane().setLayout(new GridBagLayout());
		GridBagConstraints gridBagConstraints1;

		// add window listener
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent evt)
			{
				exitForm(evt);
			}
		});
		
		Color dark = new Color(204, 204, 153);
		test = new ArrayList<String>();
		//System.out.println("The size of the test ArrayList is: " + test.size());
		for (int i = 0; i < 64; i++)
		{
			JButton button = new JButton();
			possibleSquares.add(button);
			button.addActionListener(this);
			
			button.setPreferredSize(new java.awt.Dimension(80, 80));
			String index = Integer.toString(i);
			button.setActionCommand(index);
			
			/*
			 * determine which space to add color to, either white
			 * or dark depending on if a piece should be there.
			 */
			if (gridy % 2 == 0)
			{
				if (gridx % 2 == 1)
				{
					button.setBackground(Color.white);
				} else
				{
					button.setBackground(dark);
					test.add(index);
					//System.out.println(index + " has been added to test.");
				}
			} else
			{
				if (gridx % 2 == 0)
				{
					button.setBackground(Color.white);
				} else
				{
					button.setBackground(dark);
					test.add(index);
					//System.out.println(index + " has been added to test.");
				}
			}
			//System.out.println("The size of the test ArrayList is: " + test.size());
			

			gridBagConstraints1 = new java.awt.GridBagConstraints();
			gridBagConstraints1.gridx = gridx;
			gridBagConstraints1.gridy = gridy;
			getContentPane().add(button, gridBagConstraints1);

			//System.out.println("**************************");
			//System.out.println("button "+ (i + 1));
			//System.out.println("gridx" + (i+1) + ": " + gridx);
			//System.out.println("gridy" + (i+1) + ": " + gridy);
			
			
			/*
			 * increment gridx until 8 spaces filled, and then reset gridx to zero, then 
			 * increment gridy by 1 for next column.
			 */
			gridx += 1;
			gridxCount += 1;
			if (gridx == MAXX && gridxCount == MAXX) {
				gridx = 0;
				gridxCount = 0;
				gridy +=1;
				gridyCount += 1;
				
			}
			if (gridy == MAXY && gridyCount == MAXY) {
				gridy = 0;
				gridyCount = 0;
			}
		}
		
		
		PlayerOnelabel.setText("Player 1:     " + playerOnesName);
		PlayerOnelabel.setForeground(Color.black);

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 0;
		gridBagConstraints1.gridwidth = 4;
		getContentPane().add(PlayerOnelabel, gridBagConstraints1);

		playerTwoLabel.setText("Player 2:     " + playerTwosName);
		playerTwoLabel.setForeground(Color.black);

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 2;
		gridBagConstraints1.gridy = 9;
		gridBagConstraints1.gridwidth = 4;
		getContentPane().add(playerTwoLabel, gridBagConstraints1);

		whosTurnLabel.setText("");
		whosTurnLabel.setForeground(new Color(0, 100, 0));

		gridBagConstraints1.gridx = 8;
		gridBagConstraints1.gridy = 1;
		getContentPane().add(whosTurnLabel, gridBagConstraints1);

		warningLabel.setText("");
		warningLabel.setForeground(Color.red);

		gridBagConstraints1.gridx = 8;
		gridBagConstraints1.gridy = 2;
		getContentPane().add(warningLabel, gridBagConstraints1);

		timeRemainingLabel.setText("Time Remaining:");
		timeRemainingLabel.setForeground(Color.black);

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 8;
		gridBagConstraints1.gridy = 3;
		getContentPane().add(timeRemainingLabel, gridBagConstraints1);

		secondsLeftLabel.setText(timeLeft + " sec.");
		secondsLeftLabel.setForeground(Color.black);

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 8;
		gridBagConstraints1.gridy = 4;
		getContentPane().add(secondsLeftLabel, gridBagConstraints1);

		ResignButton.setActionCommand("resign");
		ResignButton.setText("Resign");

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 8;
		gridBagConstraints1.gridy = 7;
		getContentPane().add(ResignButton, gridBagConstraints1);

		DrawButton.setActionCommand("draw");
		DrawButton.setText("Draw");

		gridBagConstraints1 = new java.awt.GridBagConstraints();
		gridBagConstraints1.gridx = 8;
		gridBagConstraints1.gridy = 6;
		getContentPane().add(DrawButton, gridBagConstraints1);

	}

	/**
	 * 
	 * Exit the Application
	 * 
	 * @param the
	 *            window event
	 * 
	 */
	private void exitForm(java.awt.event.WindowEvent evt)
	{
		theFacade.pressQuit();
	}

	/**
	 * Takes care of input from users, handles any actions performed
	 * 
	 * @param e
	 *            the event that has occured
	 */
	public void actionPerformed(ActionEvent e)
	{

		try
		{
			// if a valid space with a piece on it is clicked
			if (test.contains(e.getActionCommand()))
			{

				// call selectSpace with the button pressed
				theFacade.selectSpace(Integer.parseInt(e.getActionCommand()));

				// if draw is pressed
			}
			else if (e.getActionCommand().equals("draw"))
			{
				// does sequence of events for a draw
				theFacade.pressDraw();

				// if resign is pressed
			}
			else if (e.getActionCommand().equals("resign"))
			{
				// does sequence of events for a resign
				theFacade.pressQuit();

				// if the source came from the facade
			}
			else if (e.getSource().equals(theFacade))
			{

				// if its a player switch event
				if ((e.getActionCommand()).equals(theFacade.playerSwitch))
				{
					// set a new time
					timeRemaining = theFacade.getTimer();
					// if it is an update event
				}
				else if ((e.getActionCommand()).equals(theFacade.update))
				{
					// update the GUI
					update();
				}
				else
				{
					throw new Exception("unknown message from facade");
				}
			}
			// catch various Exceptions
		}
		catch (NumberFormatException excep)
		{
			System.err
					.println("GUI exception: Error converting a string to a number");
		}
		catch (NullPointerException exception)
		{
			System.err.println("GUI exception: Null pointerException "
					+ exception.getMessage());
			exception.printStackTrace();
		}
		catch (Exception except)
		{
			System.err.println("GUI exception: other: " + except.getMessage());
			except.printStackTrace();
		}

	}

	/**
	 * Updates the GUI reading the pieces in the board Puts pieces in correct
	 * spaces, updates whos turn it is
	 * 
	 * @param the
	 *            board
	 */

	private void update()
	{

		if (checkEndConditions())
		{

			theFacade.showEndGame(" ");
		}
		// the board to read information from
		Board board = theFacade.stateOfBoard();
		// a temp button to work with
		JButton temp = new JButton();

		// go through the board
		for (int i = 1; i < board.sizeOf(); i++)
		{

			// if there is a piece there
			if (board.occupied(i))
			{

				// check to see if color is blue
				if (board.colorAt(i) == Color.blue)
				{

					// if there is a single piece there
					if ((board.getPieceAt(i)).getType() == board.SINGLE)
					{

						// show a blue single piece in that spot board
						temp = (JButton) possibleSquares.get(i);

						temp.setIcon(new ImageIcon(CheckerGUI.class
								.getResource("BlueSingle.gif")));

						// if there is a kinged piece there
					}
					else if ((board.getPieceAt(i)).getType() == board.KING)
					{

						// show a blue king piece in that spot board
						temp = (JButton) possibleSquares.get(i);

						// get the picture formt the web
						try
						{
							temp.setIcon(new ImageIcon(CheckerGUI.class
									.getResource("BlueKing.gif")));
						}
						catch (Exception e)
						{
						}

					}

					// check to see if the color is white
				}
				else if (board.colorAt(i) == Color.white)
				{

					// if there is a single piece there
					if ((board.getPieceAt(i)).getType() == board.SINGLE)
					{

						// show a blue single piece in that spot board
						temp = (JButton) possibleSquares.get(i);

						// get the picture from the web
						try
						{
							temp.setIcon(new ImageIcon(CheckerGUI.class
									.getResource("WhiteSingle.gif")));
						}
						catch (Exception e)
						{
						}

						// if there is a kinged piece there
					}
					else if ((board.getPieceAt(i)).getType() == board.KING)
					{

						// show a blue king piece in that spot board
						temp = (JButton) possibleSquares.get(i);

						// get the picture from the web
						try
						{
							temp.setIcon(new ImageIcon(CheckerGUI.class
									.getResource("WhiteKing.gif")));
						}
						catch (Exception e)
						{
						}
					}
					// if there isnt a piece there
				}
			}
			else
			{
				// show no picture
				temp = (JButton) possibleSquares.get(i);
				temp.setIcon(null);
			}
		}

		// this code updates whos turn it is
		if (theFacade.whosTurn() == 2)
		{
			playerTwoLabel.setForeground(Color.red);
			PlayerOnelabel.setForeground(Color.black);
			whosTurnLabel.setText(playerTwosName + "'s turn ");
		}
		else if (theFacade.whosTurn() == 1)
		{
			PlayerOnelabel.setForeground(Color.red);
			playerTwoLabel.setForeground(Color.black);
			whosTurnLabel.setText(playerOnesName + "'s turn");
		}
	}

	/**
	 * 
	 * Update the timer
	 * 
	 */

	public void updateTime()
	{

		if (theFacade.getTimer() > 0)
		{

			// if the time has run out but not in warning time yet
			// display warning and count warning time
			if (timeRemaining <= 0 && (warningLabel.getText()).equals(""))
			{
				timeRemaining = theFacade.getTimerWarning();
				warningLabel.setText("Time is running out!!!");

				// if the time has run out and it was in warning time quit game
			}
			else if (timeRemaining <= 0 && !(warningLabel.getText()).equals(""))
			{

				theFacade.pressQuit();

			}
			else
			{

				timeRemaining--;

			}

			secondsLeftLabel.setText(timeRemaining + " sec.");

		}
		else
		{
			secondsLeftLabel.setText("*****");
		}
	}

	/**
	 * Checks the ending condotions for the game see if there a no pieces left
	 * 
	 * @return the return value for the method true if the game should end false
	 *         if game needs to continue
	 */

	public boolean checkEndConditions()
	{

		// the return value
		boolean retVal = false;
		try
		{
			// the number of each piece left
			int whitesGone = 0, bluesGone = 0;

			// the board to work with
			Board temp = theFacade.stateOfBoard();

			// go through all the spots on the board
			for (int i = 1; i < temp.sizeOf(); i++)
			{
				// if there is a piece there
				if (temp.occupied(i))
				{
					// if its a blue piece there
					if ((temp.getPieceAt(i)).getColor() == Color.blue)
					{
						// increment number of blues
						bluesGone++;
						// if the piece is white
					}
					else if ((temp.getPieceAt(i)).getColor() == Color.white)
					{
						// increment number of whites
						whitesGone++;
					}
				}
			}// end of for loop

			// if either of the number are 0
			if (whitesGone == 0 || bluesGone == 0)
			{
				retVal = true;
			}

		}
		catch (Exception e)
		{

			System.err.println(e.getMessage());

		}
		return retVal;

	}// checkEndConditions

}// checkerGUI.java
