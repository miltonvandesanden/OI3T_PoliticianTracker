package VotingEntityPackage;

import java.util.List;
import java.util.NoSuchElementException;

public interface iVotingEntityController
{
	List<iVotingEntity> getVotingEntities();
	iVotingEntity getVotingEntity(int votingEntityId) throws NoSuchElementException;

	boolean addVotingEntity(iVotingEntity votingEntity);
	void deleteVotingEntity(int votingEntityId);
	boolean updateVotingEntity(int votingEntityId, iVotingEntity newVotingEntity);
}
