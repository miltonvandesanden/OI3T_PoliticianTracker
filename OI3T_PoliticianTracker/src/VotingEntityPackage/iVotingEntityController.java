package VotingEntityPackage;

import EntitiesPackage.iVotingEntity;

import java.time.LocalDate;
import java.util.List;

public interface iVotingEntityController
{
	List<iVotingEntity> getVotingEntities();

	iVotingEntity getVotingEntity(int votingEntityId);
	iVotingEntity getVotingEntity(String name, LocalDate dateOfFounding);

	iVotingEntity addVotingEntity(iVotingEntity votingEntity);
	iVotingEntity setVotingEntity(int votingEntityId, iVotingEntity votingEntity);
	boolean deleteVotingEntity(int votingEntityId);
}
