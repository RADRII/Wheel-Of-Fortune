import java.io.FileNotFoundException;
import java.util.Scanner;

public class HumanPlayerClass extends PlayerAbstract
{
	static Scanner sc=new Scanner(System.in);
	private String name;
	private int moneyFromSpin;
	private int money;

	public HumanPlayerClass(String name)
	//receives a name and constructs the class
	{
		this.name = name;
		moneyFromSpin = 0;
	}
	public String getName()
	//returns the name of the player
	{
		return name;
	}
	public void takeTurn(WheelClass wheel, BoardClass board) throws FileNotFoundException
	//asks what the player want to do and send them to the right method
	{
		boolean goAgain = true;
		while(goAgain == true)
		{

			String guess = whatWillPlayerDo(wheel,board);
			if(guess.equals("***")) //got bankrupt or lost a turn
				goAgain = false;

			else if(guess.length() == 1) //guessed a letter
			{
				char letter = guess.charAt(0);
				int howMany = board.processLetter(letter);
				money += moneyFromSpin * howMany;
				if(howMany == 0)
					goAgain = false;
				else
					System.out.println("You earned " + moneyFromSpin * howMany + "$");
				moneyFromSpin = 0;
			}
			else //guessed a phrase
			{
				boolean guessRight = board.processGuess(guess);
				if(guessRight==true)
					System.out.println("You solved the board!");
				else
					System.out.println("Close but no cigar.");
				goAgain = false;
			}
			if(board.isGameOver() == true)
				goAgain = false;
			if(goAgain == true)
				board.toPrint();

		}
		return;
	}
	public String whatWillPlayerDo(WheelClass wheel, BoardClass board)
	//gives the player their options
	//returns which thyey chose
	{
		while(4>3)
		{
			System.out.println(name + ", would you like to \n 1.Spin the wheel \n 2.Buy a vowel \n 3.Guess the entire phrase");
			int choice = sc.nextInt();

			if(choice == 1)
				return spin(wheel, board);
			else if(choice == 2)
			{

				if(canBuyVowel(board) == true)
					return buyVowel(board);
				else
					continue;
			}
			else if(choice == 3)
			{
				return guess();
			}
		}
	}
	public String guess()
	//gets and returns the phrase the player guesses
	{
		String s;
		while(4>3)
		{
			System.out.println("What do you think the phrase is?");
			sc.nextLine();
			s= sc.nextLine();

			if(s.length() > 1)
				return s;
			System.out.println("I'm pretty sure the phrase has more than one letter.");
		}

	}
	public boolean canBuyVowel(BoardClass board)
	//checks if the player can buy a vowel
	//returns true or false depending on the answer
	{

		if(money<250)
		{
			System.out.println("You don't have enough money to do so.");
			return false;
		}
		else if((board.hasBeenGuessed("a") == true && board.hasBeenGuessed("e") == true && board.hasBeenGuessed("i") == true && board.hasBeenGuessed("o") == true && board.hasBeenGuessed("u") == true && board.hasBeenGuessed("y") == true))
		{
			System.out.println("All vowels have been guessed.");
			return false;
		}
		else
			return true;
	}
	public String buyVowel(BoardClass board)
	//gets the players choice for what vowel they want to pick
	//returns the vowel
	{
		String vowel = "";
		money -= 250;
		while(2>1)
		{
			System.out.println("Enter a vowel");
			vowel = sc.next();
			vowel = vowel.toUpperCase();
			if(!vowel.equalsIgnoreCase("A") &&!vowel.equalsIgnoreCase("e") && !vowel.equalsIgnoreCase("I") && !vowel.equalsIgnoreCase("o") && !vowel.equalsIgnoreCase("u") && !vowel.equalsIgnoreCase("y")  )
			{
				System.out.println("That is not a vowel.");
			}
			else if(board.hasBeenGuessed(vowel))
			{
				System.out.println("Sorry that vowel has been taken.");
			}
			else
				break;
		}
		vowel.toUpperCase();
		return vowel;

	}
	public String spin(WheelClass wheel, BoardClass board)
	//spins the wheel 
	//returns either *** if they lost a turn, or their choice of consonant
	{
		int outcome = wheel.spin();

		if(outcome == 0)
		{
			System.out.println("You lose a turn!");
			return "***";
		}
		else if(outcome == -1)
		{
			System.out.println("BANKRUPT!");
			card.bankrupt();
			money = 0;
			return "***";
		}
		else
		{
			System.out.println("You have a chance to win " + outcome + "$ for each instance of the letter you guess!");
			moneyFromSpin = outcome;
			return chooseAConsonant(board);
		}

	}
	public String chooseAConsonant(BoardClass board)
	//gets the players choice of consonant and returns it
	{
		String consonant = "";
		while(2>1)
		{
			System.out.println("Enter a consanant");
			consonant = sc.next();
			consonant = consonant.toUpperCase(); //converts it to upper case just to make things easier

			int con = (int) consonant.charAt(0);
			if(con == 65||con == 69|| con==73 || con ==  79|| con == 85|| con == 121 || con <65 || con > 90 || con == 89) //checks if it is a consonant
			{
				System.out.println("That is not a consonant.");
			}
			else if(board.hasBeenGuessed(consonant))
			{
				System.out.println("Sorry that consonant has been guessed already.");
			}
			else
				break;
		}
		return consonant;
	}
	public void maybeKeepMoney(int whoWon)
	//gets who won and if it equals 1 the player gets to keep their money
	{
		if(whoWon == 1)
		{
			super.card.addMoney(money);
			super.card.upRW();
		}
		else
			money = 0;

	}
}
