package root.issue;

public class IssueNotFoundException extends RuntimeException
{
	IssueNotFoundException(int id)
	{
		super("Could not find Issue " + id);
	}
}
