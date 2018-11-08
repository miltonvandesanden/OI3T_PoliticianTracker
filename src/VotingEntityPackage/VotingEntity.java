package VotingEntityPackage;

import java.time.LocalDate;

public class VotingEntity implements iVotingEntity
{
	private int id;
	private String name;
	private LocalDate dateOfFounding;

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

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public String getName()
	{
		return name;
	}

	@Override
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public LocalDate getDateOfFounding()
	{
		return dateOfFounding;
	}

	@Override
	public void setDateOfFounding(LocalDate dateOfFounding) throws IllegalArgumentException
	{
		if(dateOfFounding.isAfter(LocalDate.now()))
		{
			throw new IllegalArgumentException("dateOfFounding is in the future!");
		}

		this.dateOfFounding = dateOfFounding;
	}
}
