package VotingEntityPackage;

import java.util.*;

public class VotingEntityController implements iVotingEntityController
{
	private List<iVotingEntity> votingEntities;

	public VotingEntityController()
	{
		votingEntities = new ArrayList<>();
	}

	@Override
	public List<iVotingEntity> getVotingEntities()
	{
		return votingEntities;
	}

	@Override
	public iVotingEntity getVotingEntity(int votingEntityId) throws NoSuchElementException
	{
		for(iVotingEntity votingEntity : votingEntities)
		{
			if(votingEntity.getId() == votingEntityId)
			{
				return votingEntity;
			}
		}

		throw new NoSuchElementException("Unable to locate votingEntity with id: " + votingEntityId);
	}

	@Override
	public boolean addVotingEntity(iVotingEntity newVotingEntity)
	{
		for(iVotingEntity votingEntity : votingEntities)
		{
			if(votingEntity.getName().equals(newVotingEntity.getName()))
			{
				return false;
			}
		}

		votingEntities.add(newVotingEntity);

		return true;
	}

	@Override
	public void deleteVotingEntity(int votingEntityId)
	{
		ListIterator<iVotingEntity> votingEntitiesIterator = votingEntities.listIterator();

		while(votingEntitiesIterator.hasNext())
		{
			if(votingEntitiesIterator.next().getId() == votingEntityId)
			{
				votingEntitiesIterator.remove();
			}
		}
	}

	@Override
	public boolean updateVotingEntity(int votingEntityId, iVotingEntity newVotingEntity)
	{

		return false;
	}
}
