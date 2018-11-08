package DatabasePackage;

import VotingEntityPackage.iVotingEntity;

import java.time.LocalDate;
import java.util.List;

public interface iDatabaseController
{
	List<iVotingEntity> getVotingEntities();

	iVotingEntity getVotingEntity(int votingEntityId);
	iVotingEntity getVotingEntity(String name, LocalDate dateOfFounding);

	iVotingEntity addVotingEntity(iVotingEntity votingEntity);

	boolean deleteVotingEntity(int votingEntityId);

	iVotingEntity setVotingEntity(int votingEntityId, iVotingEntity newVotingEntity);
}
