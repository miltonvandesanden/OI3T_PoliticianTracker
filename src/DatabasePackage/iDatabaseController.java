package DatabasePackage;

import VotingEntityPackage.iVotingEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

public interface iDatabaseController
{
	List<iVotingEntity> getVotingEntities();

	iVotingEntity getVotingEntity(int votingEntityId) throws NoSuchElementException;
	iVotingEntity getVotingEntity(String name, LocalDate dateOfFounding) throws NoSuchElementException;

	iVotingEntity addVotingEntity(iVotingEntity votingEntity) throws Exception;

	iVotingEntity setVotingEntity(int votingEntityId, iVotingEntity newVotingEntity) throws NoSuchElementException;

	void deleteVotingEntity(int votingEntityId) throws NoSuchElementException;
}
