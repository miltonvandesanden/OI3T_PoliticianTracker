package root.votingentity;

public class VotingEntityNotSetException extends RuntimeException
{
	VotingEntityNotSetException()
	{
		super("VotingEntity not set");
	}
}
