package IssuePackage;

import DatabasePackage.DatabaseController;
import DatabasePackage.iDatabaseController;
import EntitiesPackage.iIssue;

import java.util.List;

public class IssueController implements iIssueController
{
	private iDatabaseController databaseController;

	public IssueController()
	{
		databaseController = DatabaseController.getDatabaseController();
	}

	@Override
	public List<iIssue> getIssues()
	{
		return databaseController.getIssues();
	}

	@Override
	public iIssue getIssue(int issueId)
	{
		return databaseController.getIssue(issueId);
	}

	@Override
	public iIssue getIssue(String name, String description)
	{
		return databaseController.getIssue(name, description);
	}

	@Override
	public iIssue addIssue(iIssue issue)
	{
		if(databaseController.getIssue(issue.getName(), issue.getDescription()) != null)
		{
			return null;
		}

		return databaseController.addIssue(issue);
	}

	@Override
	public iIssue setIssue(int issueId, iIssue issue)
	{
		if(databaseController.getIssue(issueId) == null)
		{
			return null;
		}

		if(databaseController.getIssue(issue.getName(), issue.getDescription()) != null)
		{
			return null;
		}

		return databaseController.setIssue(issueId, issue);
	}

	@Override
	public boolean deleteIssue(int issueId)
	{
		if(databaseController.getIssue(issueId) == null)
		{
			return false;
		}

		if(!databaseController.getStancesOfIssue(issueId).isEmpty())
		{
			return false;
		}

		return databaseController.deleteIssue(issueId);
	}
}
