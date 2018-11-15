package VotingEntityPackage;

import DatabasePackage.DatabaseController;
import DatabasePackage.iDatabaseController;
import EntitiesPackage.iVotingEntity;

import java.time.LocalDate;
import java.util.*;

public class VotingEntityController implements iVotingEntityController
{
	private iDatabaseController databaseController;

	public VotingEntityController()
	{
		databaseController = DatabaseController.getDatabaseController();
	}

	@Override
	public List<iVotingEntity> getVotingEntities()
	{
		return databaseController.getVotingEntities();
	}

	@Override
	public iVotingEntity getVotingEntity(int votingEntityId)
	{
		return databaseController.getVotingEntity(votingEntityId);
	}

	@Override
	public iVotingEntity getVotingEntity(String name, LocalDate dateOfFounding)
	{
		return databaseController.getVotingEntity(name, dateOfFounding);
	}

	@Override
	public iVotingEntity addVotingEntity(iVotingEntity votingEntity)
	{
		if(databaseController.getVotingEntity(votingEntity.getName(), votingEntity.getDateOfFounding()) != null)
		{
			return null;
		}

		return databaseController.addVotingEntity(votingEntity);
	}

	@Override
	public iVotingEntity setVotingEntity(int votingEntityId, iVotingEntity votingEntity)
	{
		if(databaseController.getVotingEntity(votingEntityId) == null)
		{
			return null;
		}

		if(databaseController.getVotingEntity(votingEntity.getName(), votingEntity.getDateOfFounding()) != null)
		{
			return null;
		}

		return databaseController.setVotingEntity(votingEntityId, votingEntity);
	}

	@Override
	public boolean deleteVotingEntity(int votingEntityId)
	{
		if(databaseController.getVotingEntity(votingEntityId) == null)
		{
			return false;
		}

		return databaseController.deleteVotingEntity(votingEntityId);
	}
}
