#Hrabowya
import java.util.Random;
public class ComputerPlayerClass extends PlayerAbstract
{
	static Random  rand = new Random ( );   
	private int difficulty;
	private BoardClass board;
	private int money;
	private int moneyFromSpin;
	private String compName;
	private int compNum;

	public ComputerPlayerClass(int dif, String name, int num)
	{
		moneyFromSpin = 0;
		money = 0;
		this.difficulty = dif;
		this.card = new ScoreCardClass();
		compName = name;
		compNum = num;
	}
	public String getName()
	{
		return compName;
	}
	public void setBoard(BoardClass board)
	//lets the computer have the entire board for access
	{
		this.board = board;
	}
	public void takeTurn(WheelClass wheel)
	{
	//checks what difficulty the computer is, then sends them to the correct turn
		board.toPrint();
		board.printIncorrect();
		if(difficulty == 1)
			takeEasyTurn(wheel);
		else if(difficulty == 2)
			takeMediumTurn(wheel);
		else if(difficulty == 3)
			takeHardTurn(wheel);

	}
	public void takeMediumTurn(WheelClass wheel)
	//depending on a random int, has an equal chance of either taking an easy turn this time around or a hard turn
	{
		int whichType = rand.nextInt(2);
		if(whichType == 0)
			takeEasyTurn(wheel);
		else
			takeHardTurn(wheel);
	}
	public void takeHardTurn(WheelClass wheel)
	{
		boolean goAgain = true;
		//The computer has a 10 percent chance of taking an easy turn instead
		int shouldGoHard = rand.nextInt(10);
		if(shouldGoHard == 9)
			takeEasyTurn(wheel);
		else
		{
			while(goAgain == true)
			{

				//If 70 percent of all unique letters have been guessed, then the computer will solve the board
				if(board.getPercentGuessed() >= 0.70)
				{
					board.processGuess(board.getPhrase());
					System.out.println("The computer solved the board!");
					goAgain = false;
				}
				else if(canBuyVowel() == true && shouldBuyVowel() == true)
				{
					goAgain = guessVowel();
				}
				else 
				{
					goAgain = spinHard(wheel);
				}
				if(board.isGameOver() == true)
					goAgain = false;
				if(goAgain == true)
				{
					board.toPrint();
				}
			}

		}
	}
	public boolean spinHard(WheelClass wheel)
	//Spins the wheel, adds any necessary cash,
	//returns whether the computer should go again or no
	{
		int outcome = wheel.spin();

		if(outcome == 0)
		{
			System.out.println("The computer spun and loses its turn.");
			return false;
		}
		else if(outcome == -1)
		{
			System.out.println("The computer spun and bankrupted!");
			money = 0;
			card.bankrupt();
			return false;
		}
		else
		{
			System.out.println("The computer has a chance to win " + outcome + "$ for each instance of the letter it guesses!");
			//saves how much money the computer can earn seperately since technacally they havent earned it yet
			moneyFromSpin = outcome;

			String p = board.getPhrase();
			int noInfinite = 0;
			//loop only goes around the length of the phrase once
			for(int i = rand.nextInt(p.length()); i<p.length(); i++)
			{
				//Picks arandom letter from somewhere in the phrase each time
				int con = (int) p.substring(i, i+1).charAt(0);
				//checks conditions that it A. hasnt been guessed before and B. its not a vowel
				if(board.hasBeenGuessed(p.substring(i, i+1)) == false && con != 65&&con != 69&& con!=73 && con !=  79&& con != 85&& con != 121 && con >65 && con < 90)
				{
					System.out.println("The computer guessed the letter " + p.charAt(i));
					int howMany = board.processLetter(p.charAt(i));
					money += moneyFromSpin * howMany;
					System.out.println("The computer earned " + moneyFromSpin*howMany + "$");
					return true;
				}

				if(i == p.length()-1)
					i=-1;
				
				//to make sure comp doesnt get stuck in endless loop
				if(noInfinite >= 150)
				{
					char guess = chooseAConsonant();
					System.out.println("The computer guessed the letter " + guess);
					int howMany = board.processLetter(guess);
					money += moneyFromSpin * howMany;
					System.out.println("The computer earned " + moneyFromSpin*howMany + "$");
					return true;
				}
				noInfinite++;
			}
			return false;
		}
	}
	public void takeEasyTurn(WheelClass wheel)
	{
		//uses a random int to check whether it should get a vowel or a consonant
		int buyOrSpin = rand.nextInt(2);
		boolean goAgain = true;
		while(goAgain == true)
		{
			if(buyOrSpin == 0 && canBuyVowel() == true)
			{
				money -= 250;
				goAgain = guessVowel();

			}
			else
				buyOrSpin = 1;

			if(buyOrSpin == 1)
			{
				goAgain = spin(wheel);
			}
			if(board.isGameOver() == true)
				goAgain = false;
			if(goAgain == true)
			{
				board.toPrint();
			}
		}
	}
	public boolean spin(WheelClass wheel)
	{
		int outcome = wheel.spin();

		if(outcome == 0)
		{
			System.out.println("The computer spun and loses its turn.");
			return false;
		}
		else if(outcome == -1)
		{
			System.out.println("The computer spun and bankrupted!");
			card.bankrupt();
			money = 0;
			return false;
		}
		else
		{
			System.out.println("The computer has a chance to win " + outcome + "$ for each instance of the letter it guesses!");
			moneyFromSpin = outcome;
			char chosen = chooseAConsonant(); //chooses a consonant
			System.out.println("The computer guessed the letter " + chosen);
			int howMany = board.processLetter(chosen);

			if(howMany == 0)
				return false;
			else
			{
				money += howMany * moneyFromSpin;
				System.out.println("The computer won " + howMany * moneyFromSpin + "$");
				return true;
			}

		}	
	}
	public char chooseAConsonant()
	//picks a consonant at random and returns it
	//only used when taking an easy-turn
	{
		String[] consonants = {"B","C","D","F","G","H","J","K","L","M","N","P","Q","R","S","T","V","W","X","Y","Z"};
		int guess = rand.nextInt(21);

		//goes around until it finds a consonant to guess
		while(4>3)
		{
			boolean hasBeen = board.hasBeenGuessed(consonants[guess]);
			if(hasBeen ==  true)
				guess++;
			else
			{
				System.out.println("The computer guessed " + consonants[guess]);
				return consonants[guess].charAt(0);
			}

			if(guess >= 21)
				guess = 0;
		}


	}
	public boolean guessVowel()
	//returns a vowel thats chosen randomly
	{
		int whichVowel = rand.nextInt(6);
		String[] vowels = {"A","E","I","O","U","Y"};

		//only goes around enough time to check through each vowel
		for(int i = 0; i < 6; i++)
		{
			if(board.hasBeenGuessed(vowels[whichVowel])== false)
			{
				System.out.println("The computer bought the vowel " + vowels[whichVowel]);
				int has = board.processLetter(vowels[whichVowel].charAt(0));
				if(has == 0)
					return false;
				else
					return true;
			}

			if(whichVowel == 5)
				whichVowel = 0;
			else
				whichVowel++;
		}
		return false;
	}
	public boolean canBuyVowel()
	//checks condition to see if the computer can buy a vowel
	//can buy it only if all vowels havent been guessed and if it has enough money
	{
		if((board.hasBeenGuessed("A") == true && board.hasBeenGuessed("E") == true && board.hasBeenGuessed("I") == true && board.hasBeenGuessed("O") == true && board.hasBeenGuessed("U") == true && board.hasBeenGuessed("Y") == true))
		{
			return false;
		}
		else if(money<250 || card.getMoney() <250)
		{
			return false;
		}
		else
			return true;
	}
	public void maybeKeepMoney(int whoWon)
	//gets who won and if its equal to the computers number
	//the computer gets to keep its money from that round
	{
		if(whoWon == compNum)
		{
			super.card.addMoney(money);
			super.card.upRW();
		}
		else
			money = 0;

	}
	public boolean shouldBuyVowel()
	//only used by comps on hard-turn
	//returns true if the phrase still has unguessed vowels in it
	{
		String[] vowels = {"A","E","I","O","U","Y"};
		for(int i = 0; i< 6; i++)
		{
			if(board.getPhrase().contains(vowels[i]) && board.hasBeenGuessed(vowels[i]) == false)
				return true;
		}
		return false;

	}
}
