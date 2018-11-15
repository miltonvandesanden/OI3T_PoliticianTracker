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
		return null;
	}

	@Override
	public List<iStance> getStancesOfIssue(int issueId)
	{
		return null;
	}

	@Override
	public List<iStance> getStancesOfVotingEntityAndIssue(int votingEntityId, int issueId)
	{
		return null;
	}

	@Override
	public iStance getStance(int stanceId)
	{
		return null;
	}

	@Override
	public iStance getStance(int votingEntityId, int issueId, LocalDate date)
	{
		return null;
	}

	@Override
	public iStance addStance(iStance stance)
	{
		return null;
	}

	@Override
	public iStance setStance(int stanceId, iStance stance)
	{
		return null;
	}

	@Override
	public boolean deleteStance(int stanceId)
	{
		return false;
	}
}
