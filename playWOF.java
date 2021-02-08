import java.util.Scanner;
import java.util.Random;

public class playWOF
{
	static Scanner sc = new Scanner(System.in);
	static Random  rand = new Random ( );   
	public static void main (String[] args) throws Exception
	//The main method
	{
		System.out.println("Welcome to wheel of fortune! What is your name?");
		String name = sc.next();

		//used in the end statement to provide stats
		int gamesPlayed = 0;
		int gamesWon = 0;

		while(true)
		{
			String choice = getOptions(name);

			if(choice.equalsIgnoreCase("G"))
				printRules();
			else if(choice.equalsIgnoreCase("P"))
			{
				String whoWon = prepareToPlay(name);
				gamesPlayed++;
				if(whoWon.equalsIgnoreCase("P"))
					gamesWon++;
			}
			else if(choice.equals("q"))
				break;
			else
				System.out.println("Not a valid option");
		}
		System.out.println(name + ", thanks for playing wheel of fortune. \n Out of " + gamesPlayed + " games played you won " + gamesWon);
	}
	public static String prepareToPlay(String name) throws Exception
	//prints options for different types of wheel of fortune game the player can play
	//then sends the player into the game
	//checks whether the player wants to keep playing and if no, checks who won and returns it
	{
		int numP = 0;
		int compDif = 0;

		//All options for type of game
		do
		{
			System.out.println(name + ", would you like two or three players (enter 2 or 3)?");
			numP = sc.nextInt();
			if(numP != 2 && numP != 3)
				System.out.println("Not a valid answer");
		}
		while(numP != 2 && numP != 3);

		do
		{
			System.out.println(name + ", would you like the computer to be easy (1), medium (2), or hard(3)?");
			compDif = sc.nextInt();
			if(compDif != 1 && compDif != 2 && compDif != 3)
				System.out.println("Not a valid answer");
		}
		while(compDif != 1 && compDif != 2 && compDif != 3);

		System.out.println("Enter computer one's name!");
		String name1 = sc.next();
		String name2 = "";
		if(numP == 3)
		{
			System.out.println("Enter computer two's name!");
			name2 = sc.next();
		}
		//initializes the players
		HumanPlayerClass p = new HumanPlayerClass(name);
		ComputerPlayerClass c1 = new ComputerPlayerClass(compDif, name1, 2);
		ComputerPlayerClass c2 = new ComputerPlayerClass(compDif,name2, 3);

		int quit = 0;
		int winner = 0;
		do
		{
			winner = playGame(numP, name, p, c1, c2);

			//Depending on who won, checks which player keeps their money from the round
			p.maybeKeepMoney(winner);
			c1.maybeKeepMoney(winner);
			c2.maybeKeepMoney(winner);

			System.out.println("Would you like to play another round (0 for yes, 1 for no)");
			quit = sc.nextInt();
			if(quit == 0)
			{
				//Prints the money each player has before going into another round
				System.out.println("Player's money: " + p.card.getMoney());
				System.out.println(c1.getName() + "'s money: " + c1.card.getMoney());
				if(numP == 3)
					System.out.println(c2.getName() + "'s money: " + c2.card.getMoney());
			}
		}
		
		//Prints final scores
		while(quit == 0);
		System.out.println("Player");
		p.card.toPrint();
		System.out.println(c1.getName());
		c1.card.toPrint();
		if(numP == 3)
		{
			System.out.println(c2.getName());
			c2.card.toPrint();
		}


		String wonWholeGame = getWinner(p,c1,c2);
		System.out.println(wonWholeGame + " won the game!");

		return wonWholeGame;
	}
	public static String getWinner(HumanPlayerClass p, ComputerPlayerClass c1, ComputerPlayerClass c2)
	{
		if(p.getCard().getRW() >= c1.getCard().getRW() && p.getCard().getRW()  >= c2.getCard().getRW())
			return "P";
		else if(c1.getCard().getRW() >= c2.getCard().getRW())
			return "C1";
		else
			return "C2";
	}
	public static int playGame(int numP, String name, HumanPlayerClass p, ComputerPlayerClass c1, ComputerPlayerClass c2) throws Exception
	{
		//Initilizes everything needed to make the game play
		BoardClass board = new BoardClass();
		WheelClass wheel = new WheelClass();
		board.nextPhrase();
		c1.setBoard(board);
		c2.setBoard(board);
		board.toPrint();
		board.printIncorrect();
		
		//This loop goes through each turn
		int whoTurn = 1;
		while(board.isGameOver() == false)
		{

			if(whoTurn == 1)
			{
				System.out.println("It's the players turn!");
				p.takeTurn(wheel, board);
			}
			else if(whoTurn == 2)
			{
				System.out.println("It's " + c1.getName() + "'s turn!");
				c1.takeTurn(wheel);
			}
			else if(whoTurn == 3 && numP == 3)
			{
				System.out.println("It's " + c2.getName() + "'s turn!");
				c2.takeTurn(wheel);
			}
			if(board.isGameOver() == true)
				System.out.println("End of the round.");
			//sets the whoturn
			else if(whoTurn == 3)
				whoTurn = 1;
			else
				whoTurn++;
		}
		System.out.println("The final Solution was: ");
		board.toPrint();

		int winner = whoTurn;
		return winner;
	}
	public static void printRules()
	//Prints the rules on how to play wheel of fortune
	{
		System.out.println("The object of the game is to figure out the hidden expression. One example of an original board:");
		System.out.println("_ _    _ _ _ _     _ _ _    _ _ _ _ _             incorrect letters:");
		System.out.println("After a few turns, the board might look like:");
		System.out.println("_ P    C _ _ P    S C _    _ _ L E S             incorrect letters:  N D");
		System.out.println("On a turn, a player has the option of spinning the wheel, buying a vowel, or guessing the puzzle.\n The turn continues until the player guesses a wrong consonant, guesses incorrectly for the puzzle, spins Bankrupt or Lose Turn,\n or correctly guesses the secret phrase (ending the game). \n When a player spins the wheel, if a dollar amount appears, a consonant is chosen. \n If that consonant is on the board, the letter appears and the dollar amount is added to the contestant's total. \n Bankrupt not only loses a player's turn, but sends the player’s dollar total to zero for the current round.");
		System.out.println("After a board is completed, only the player who solved the board keeps the money that they earned that round.");
		System.out.println("The player with the most money at the end of whatever number of rounds wins the entire game.");
	}
	public static String getOptions(String name)
	//Prints the options of what the player can pick
	//Returns the players choice
	{
		System.out.println(name + ", would you like to:");
		System.out.println("G. Get Rules");
		System.out.println("P. Play the game");
		System.out.println("Q. Quit");
		String choice = sc.next();
		return choice;
	}
}