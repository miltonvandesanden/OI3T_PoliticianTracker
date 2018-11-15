package StancePackage;

import EntitiesPackage.iStance;

import java.time.LocalDate;
import java.util.List;

public interface iStanceController
{
	List<iStance> getStancesOfVotingEntity(int votingEntityId);
	List<iStance> getStancesOfIssue(int issueId);
	List<iStance> getStancesOfVotingEntityAndIssue(int votingEntityId, int issueId);

	iStance getStance(int stanceId);
	iStance getStance(int votingEntityId, int issueId, LocalDate date);

	iStance addStance(iStance stance);
	iStance setStance(int stanceId, iStance stance);
	boolean deleteStance(int stanceId);
}