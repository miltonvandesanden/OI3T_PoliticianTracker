package StancePackage;

import DatabasePackage.DatabaseController;
import DatabasePackage.iDatabaseController;
import EntitiesPackage.iStance;

import java.time.LocalDate;
import java.util.List;

public class StanceController implements iStanceController
{
	private iDatabaseController databaseController;

	public StanceController()
	{
		databaseController = DatabaseController.getDatabaseController();
	}

	@Override
	public List<iStance> getStancesOfVotingEntity(int votingEntityId)
	{
		return databaseController.getStancesOfVotingEntity(votingEntityId);
	}

	@Override
	public List<iStance> getStancesOfIssue(int issueId)
	{
		return databaseController.getStancesOfIssue(issueId);
	}

	@Override
	public List<iStance> getStancesOfVotingEntityAndIssue(int votingEntityId, int issueId)
	{
		return databaseController.getStancesOfVotingEntityAndIssue(votingEntityId, issueId);
	}

	@Override
	public iStance getStance(int stanceId)
	{
		return databaseController.getStance(stanceId);
	}

	@Override
	public iStance getStance(int votingEntityId, int issueId, LocalDate date)
	{
		return databaseController.getStance(votingEntityId, issueId, date);
	}

	@Override
	public iStance addStance(iStance stance)
	{
		if(databaseController.getVotingEntity(stance.getVotingEntityId()) == null)
		{
			return null;
		}

		if(databaseController.getIssue(stance.getIssueId()) == null)
		{
			return null;
		}

		if(databaseController.getStance(stance.getVotingEntityId(), stance.getIssueId(), stance.getDate()) != null)
		{
			return null;
		}

		return databaseController.addStance(stance);
	}

	@Override
	public iStance setStance(int stanceId, iStance stance)
	{
		if(databaseController.getStance(stanceId) == null)
		{
			return null;
		}

		if(databaseController.getVotingEntity(stance.getVotingEntityId()) == null)
		{
			return null;
		}

		if(databaseController.getIssue(stance.getIssueId()) == null)
		{
			return null;
		}

		return databaseController.setStance(stanceId, stance);
	}

	@Override
	public boolean deleteStance(int stanceId)
	{
		return databaseController.deleteStance(stanceId);
	}
}
