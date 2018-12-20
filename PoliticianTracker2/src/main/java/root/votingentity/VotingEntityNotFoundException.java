package root.votingentity;

public class VotingEntityNotFoundException extends RuntimeException
{
	VotingEntityNotFoundException(int id)
	{
		super("Could not find VotingEntity " + id);
	}
}
