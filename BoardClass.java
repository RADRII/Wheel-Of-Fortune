
import java.util.ArrayList;

public class BoardClass 
{
	private String phrase;
	private StringBuffer onTheBoard;
	private ArrayList<String> guessedLetters;
	private ArrayList<String> incorrectGuessedLetters;
	private int totalLetters;
	//^ how many unique letters the phrase has
	private int guessedCorrect;
	PhraseDataBank WOF;

	public BoardClass() throws Exception
	{
		WOF = new PhraseDataBank();
		phrase = WOF.getPhrase().toUpperCase();
		onTheBoard = format();
		totalLetters = uniqueLetters();
		guessedLetters = new ArrayList<String>(0);
		incorrectGuessedLetters = new ArrayList<String>();
		guessedCorrect = 0;
	}
	public int uniqueLetters()
	//sets the total letters
	{
		String contains = "";
		for(int i = 0; i < phrase.length(); i++)
		{
			char a = phrase.charAt(i);
			if((int) a == 32)
				continue;
			else if(contains.indexOf(a) == -1)
			{
				contains += a;
			}
		}
		return contains.length();
	}
	public double getPercentageGuessed()
	{
		return guessedCorrect/(double)totalLetters;
	}
	public StringBuffer format()
	//formats a stringbuffer into what should be on the board
	{
		StringBuffer s = new StringBuffer();
		for(int i = 0; i< phrase.length(); i++)
		{
			if((int)phrase.charAt(i) != 32)
				s.append("-");
			else
				s.append(" ");
		}
		return s;
	}
	public int processLetter(char letter)
	//receives the guess, checks if it is in the phrase
	//returns how many times that letter is in the phrase
	{
		int howMany = 0;
		for(int i = 0; i<phrase.length(); i++)
		{
			if(phrase.charAt(i) == letter)
			{
				howMany++;
				//if its in the board sets the board to show the letter instead of the hyphen at that spot
				onTheBoard.deleteCharAt(i);
				onTheBoard.insert(i,letter);

			}

		}
		//checks how many times the letter is in the phrase and adds that letters into all appropriate board array lists
		if(howMany == 0)
		{
			String s = String.valueOf(letter);
			guessedLetters.add(s);
			incorrectGuessedLetters.add(s);
			System.out.println("Sorry that Letter isn't on the board!");
		}
		else
		{
			String s = String.valueOf(letter);
			guessedLetters.add(s);
			guessedCorrect++;
			System.out.println("Nice Guess!");
		}
		return howMany;	
	}
	public boolean processGuess(String guess)
	//receives an entire guess, checks if it is the phrase
	//returns true or false depending on the answer
	{	
		if(guess.equalsIgnoreCase(phrase) == false)
			return false;
		//sets the board to show the entire phrase
		onTheBoard.delete(0, phrase.length() + 1);
		onTheBoard.append(phrase);
		return true;

	}
	public boolean hasBeenGuessed(String letter)
	//receives a letter that has been guessed
	//checks if its already been guissed and returns true or false depending on the answer
	{
		if(guessedLetters.isEmpty() == true)
			return false;
		if(guessedLetters.contains(letter))
			return true;
		return false;
	}
	public boolean isGameOver()
	//checks whether the board is equal to the phrase
	//returns true or false depending on the answer
	{
		String OTB = onTheBoard.toString();
		if(OTB.equalsIgnoreCase(phrase))
			return true;
		return false;
	}
	public void toPrint()
	//prints the board
	{
		System.out.print("Board: ");
		System.out.print(onTheBoard.toString());
		System.out.println("");
	}
	public void printIncorrect()
	//prints the array list of incorrect guessed letters
	{
		System.out.println("Incorrect Guessed Letters: ");
		if(incorrectGuessedLetters.isEmpty() == true)
		{
			System.out.print("NONE");
			System.out.println("");
			return;
		}

		for(int i = 0; i< incorrectGuessedLetters.size(); i++)
		{
			System.out.print(incorrectGuessedLetters.get(i));
			System.out.print(" ");
		}

		System.out.println(" ");
	}
	public double getPercentGuessed()
	//returns the percent of unique letters already guessed
	{
		return guessedCorrect/(double)totalLetters;
	}
	public String getPhrase()
	//returns the phrase 
	{
		return phrase;
	}
	public void nextPhrase() throws Exception
	//sets the file reader onto the next phrase
	{
		WOF.nextPhrase();

	}
}
