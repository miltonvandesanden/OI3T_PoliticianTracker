package EntitiesPackage;

import java.time.LocalDate;

public interface iStance
{
	int getId();

	int getVotingEntityId();
	void setVotingEntityId(int votingEntityId) throws IllegalArgumentException;

	int getIssueId();
	void setIssueId(int issueId) throws IllegalArgumentException;

	boolean getInFavor();
	void setInFavor(boolean inFavor);

	LocalDate getDate();
	void setDate(LocalDate date) throws IllegalArgumentException;
}
