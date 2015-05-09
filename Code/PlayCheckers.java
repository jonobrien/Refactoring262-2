/*

 * PlayCheckers.java

 *

 * 

 * Version:

 *   $Id: PlayCheckers.java,v 1.1 2002/10/22 21:12:53 se362 Exp $

 *

 * Revisions:

 *   $Log: PlayCheckers.java,v $
 *   Revision 1.1  2002/10/22 21:12:53  se362
 *   Initial creation of case study
 *
 */

/**
 * 
 * The main class to run the game. It creates the driver and first screen
 * 
 * @author
 * 
 */
class PlayCheckers
{
	static Firstscreen first;
	static Driver theDriver;

	/*
	 * The main method to play checkers
	 * 
	 * @param args[] the command line arguments
	 */

	public static void main(String args[])
	{
		playCheckers();
		
		
	}
	
	/**
	 * This method runs the game, calls the screens to seed
	 * initial settings and data for use by the Driver
	 */
	public static void playCheckers() {
		theDriver = new Driver();
		first = new Firstscreen(theDriver.getFacade());
		first.show();
	}
	
	/**
	 * This method hides the old game, and then resets the game to run
	 * again, at the completion of the game via any condition to
	 * normally exit the system. 
	 * - Maintains the same functionality as per the original design
	 */
	public static void restart() {
		if (theDriver.getFacade().getGUI() instanceof CheckerGUI) {
			theDriver.getFacade().getGUI().setVisible(false);
		}
		Driver restartDriver = new Driver();
		theDriver = restartDriver;
		Firstscreen restartFirst = new Firstscreen(restartDriver.getFacade());
		first = restartFirst;
		first.show();
	}

}// PlayCheckers
