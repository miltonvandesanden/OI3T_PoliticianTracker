package DatabasePackage;

import EntitiesPackage.iVotingEntity;
import EntitiesPackage.iIssue;
import EntitiesPackage.iStance;

import java.time.LocalDate;
import java.util.List;

public interface iDatabaseController
{
	List<iVotingEntity> getVotingEntities();

	iVotingEntity getVotingEntity(int votingEntityId);
	iVotingEntity getVotingEntity(String name, LocalDate dateOfFounding);

	iVotingEntity addVotingEntity(iVotingEntity votingEntity);
	iVotingEntity setVotingEntity(int votingEntityId, iVotingEntity votingEntity);
	boolean deleteVotingEntity(int votingEntityId);

	List<iIssue> getIssues();

	iIssue getIssue(int issueId);
	iIssue getIssue(String name, String description);

	iIssue addIssue(iIssue issue);
	iIssue setIssue(int issueId, iIssue issue);
	boolean deleteIssue(int issueId);

	List<iStance> getStancesOfVotingEntity(int votingEntityId);
	List<iStance> getStancesOfIssue(int issueId);
	List<iStance> getStancesOfVotingEntityAndIssue(int votingEntityId, int issueId);

	iStance getStance(int stanceId);
	iStance getStance(int votingEntityId, int issueId, LocalDate date);

	iStance addStance(iStance stance);
	iStance setStance(int stanceId, iStance stance);
	boolean deleteStance(int stanceId);
}
