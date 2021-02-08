public abstract class PlayerAbstract
{
	protected ScoreCardClass card;
	
	protected PlayerAbstract()
	{
		card = new ScoreCardClass();
	}
	protected ScoreCardClass getCard()
	{
		return card;
	}
}