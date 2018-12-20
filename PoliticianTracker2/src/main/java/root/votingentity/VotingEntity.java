package root.votingentity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VotingEntity
{
	private int id;
	private String name;
	private LocalDate dateOfFounding;

	public VotingEntity(){}

	public VotingEntity(int id, String name, LocalDate dateOfFounding)
	{
		this.id = id;
		this.name = name;
		this.dateOfFounding = dateOfFounding;
	}

	public VotingEntity(String name, LocalDate dateOfFounding)
	{
		id = -1;
		this.name = name;
		this.dateOfFounding = dateOfFounding;
	}
}
