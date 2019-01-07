package root.stance;

public class StanceNotFoundException extends RuntimeException
{
	public StanceNotFoundException(int id)
	{
		super("Could not find Issue " + id);
	}
}
