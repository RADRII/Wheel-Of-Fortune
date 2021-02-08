
public class ScoreCardClass 
{
	private int roundsWon;
	private int money;
	
	public ScoreCardClass()
	{
		money = 0;
		roundsWon = 0;
	}
	public void bankrupt()
	{
		money = 0;
	}
	public void addMoney(int money)
	{
		this.money += money;
	}
	public void upRW()
	{
		roundsWon++;
	}
	public int getMoney()
	{
		return money;
	}
	public int getRW()
	{
		return roundsWon;
	}
	public void toPrint()
	{
		System.out.println("Rounds won: " + roundsWon);
		System.out.println("Total money: " + money);
	}

}
