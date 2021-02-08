import java.util.Scanner;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;
public class PhraseDataBank 
{
	private String currentPhrase;
	FileWriter file;
	Scanner sc;
	File inputFile;
	String[] temp;


	public PhraseDataBank() throws Exception
	{
		inputFile = new File("./src/WOFphrases");
		sc = new Scanner(inputFile);
		currentPhrase = sc.nextLine();
		temp = new String[45];
		
		

	}
	public void nextPhrase() throws Exception
	//sets the next phrase
	{
		readFile();
		
	}
	public void readFile() throws Exception
	//reads the file, sets the phrase equalto the next line
	{
		for(int i = 0; i < 44; i++)
		{
			temp[i] = sc.nextLine();
		}
		temp[44] = currentPhrase;

		
		file = new FileWriter("./src/WOFphrases",false);
		writeFile();
		sc = new Scanner(inputFile);

	}
	public void writeFile() throws Exception
	//moves the first line of the file to the last line
	{
		PrintWriter out = new PrintWriter(file);
		for(int i = 0; i <45; i++)
		{
			out.println(temp[i]);
		}
		out.close();

		currentPhrase = temp[0];

	}
	public String getPhrase()
	//returns the current phrase
	{
		return currentPhrase;
	}
}
