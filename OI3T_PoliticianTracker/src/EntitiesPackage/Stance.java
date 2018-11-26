package EntitiesPackage;

import java.time.LocalDate;

public class Stance implements iStance
{
	private int id;
	private int votingEntityId;
	private int issueId;
	private boolean inFavor;
	private LocalDate date;

	public Stance(int id, int votingEntityId, int issueId, boolean inFavor, LocalDate date) throws IllegalArgumentException
	{
		if(date == null)
		{
			throw new IllegalArgumentException("dateStart is null");
		}

		if(date.isAfter(LocalDate.now()))
		{
			throw new IllegalArgumentException("dateStart is in the future");
		}

		this.id = id;
		this.votingEntityId = votingEntityId;
		this.issueId = issueId;
		this.inFavor = inFavor;
		this.date = date;
	}

	public Stance(int votingEntityId, int issueId, boolean inFavor, LocalDate date) throws IllegalArgumentException
	{
		if(date == null)
		{
			throw new IllegalArgumentException("dateStart is null");
		}

		if(date.isAfter(LocalDate.now()))
		{
			throw new IllegalArgumentException("dateStart is in the future");
		}

		this.id = id;
		this.votingEntityId = votingEntityId;
		this.issueId = issueId;
		this.inFavor = inFavor;
		this.date = date;
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public int getVotingEntityId()
	{
		return votingEntityId;
	}

	@Override
	public void setVotingEntityId(int votingEntityId) throws IllegalArgumentException
	{
		if(votingEntityId < 0)
		{
			throw new IllegalArgumentException("votingEntityId: " + votingEntityId + " is invalid");
		}

		this.votingEntityId = votingEntityId;
	}

	@Override
	public int getIssueId()
	{
		return issueId;
	}

	@Override
	public void setIssueId(int issueId) throws IllegalArgumentException
	{
		if(issueId < 0)
		{
			throw new IllegalArgumentException("issueId: " + issueId + " is invalid");
		}

		this.issueId = issueId;
	}

	@Override
	public boolean getInFavor()
	{
		return inFavor;
	}

	@Override
	public void setInFavor(boolean inFavor)
	{
		this.inFavor = inFavor;
	}

	@Override
	public LocalDate getDate()
	{
		return date;
	}

	@Override
	public void setDate(LocalDate date) throws IllegalArgumentException
	{
		if(date == null)
		{
			throw new IllegalArgumentException("dateStart is null");
		}

		if(date.isAfter(LocalDate.now()))
		{
			throw new IllegalArgumentException("dateStart is in the future");
		}

		this.date = date;
	}
}