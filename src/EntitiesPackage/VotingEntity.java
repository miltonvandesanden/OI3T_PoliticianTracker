package EntitiesPackage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class VotingEntity implements iVotingEntity
{
	private int id;
	private String name;
	private LocalDate dateOfFounding;

	public VotingEntity(int id, String name, LocalDate dateOfFounding) throws IllegalArgumentException
	{
		if(name ==  null || name.isBlank())
		{
			throw new IllegalArgumentException("name is null or blank");
		}

		if(dateOfFounding ==  null)
		{
			throw new IllegalArgumentException("dateOfFounding is null");
		}

		if(dateOfFounding.isAfter(LocalDate.now()))
		{
			throw new IllegalArgumentException("dateOfFounding is in the future");
		}

		this.id = id;
		this.name = name;
		this.dateOfFounding = dateOfFounding;
	}

	public VotingEntity(String name, LocalDate dateOfFounding)
	{
		if(name ==  null || name.isBlank())
		{
			throw new IllegalArgumentException("name is null or blank");
		}

		if(dateOfFounding ==  null)
		{
			throw new IllegalArgumentException("dateOfFounding is null");
		}

		if(dateOfFounding.isAfter(LocalDate.now()))
		{
			throw new IllegalArgumentException("dateOfFounding is in the future");
		}

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
	public void setName(String name) throws IllegalArgumentException
	{
		if(name == null || name.isBlank())
		{
			throw new IllegalArgumentException("name is null or blank");
		}

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
			throw new IllegalArgumentException("dateOfFounding is in the future");
		}

		this.dateOfFounding = dateOfFounding;
	}
}
