import java.util.Random;
public class WheelClass
{
	private int[] wheel;

	public WheelClass()
	//constructs the wheel
	{
		wheel = new int[21];
		for(int i = 0; i <= 18; i++)
		{
			wheel[i] = 100 + (i *50);
		}
		wheel[19] = 0; //this means lose a turn
		wheel[20] = -1; //this means bankrupt
				
	}
	public int spin()
	{
		Random  rand = new Random ( );   
		int whereLand = rand.nextInt(21);
		
		return wheel[whereLand];
		
	}
}
