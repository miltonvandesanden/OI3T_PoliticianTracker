package root.stance;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Stance
{
	private int id;
	private int votingEntityId;
	private int issueId;
	private String position;
	private LocalDate date;

	public Stance(){}

	public Stance(int id, int votingEntityId, int issueId, String position, LocalDate date)
	{
		this.id = id;
		this.votingEntityId = votingEntityId;
		this.issueId= issueId;
		this.position = position;
		this.date = date;
	}

	public Stance(int votingEntityId, int issueId, String position, LocalDate date)
	{
		id = -1;
		this.votingEntityId = votingEntityId;
		this.issueId = issueId;
		this.position = position;
		this.date = date;
	}
}
