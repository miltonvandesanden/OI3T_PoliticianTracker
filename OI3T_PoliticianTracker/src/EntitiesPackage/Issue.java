package EntitiesPackage;

public class Issue implements iIssue
{
	private int id;
	private String name;
	private String description;

	public Issue(int id, String name, String description) throws IllegalArgumentException
	{
		if(name == null || name.isBlank())
		{
			throw new IllegalArgumentException("name is null or blank");
		}

		if(description == null || description.isBlank())
		{
			throw new IllegalArgumentException("description is null or blank");
		}

		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Issue(String name, String description)  throws IllegalArgumentException
	{
		if(name == null || name.isBlank())
		{
			throw new IllegalArgumentException("name is null or blank");
		}

		if(description == null || description.isBlank())
		{
			throw new IllegalArgumentException("description is null or blank");
		}

		id = -1;
		this.name = name;
		this.description = description;
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
	public String getDescription()
	{
		return description;
	}

	@Override
	public void setDescription(String description) throws IllegalArgumentException
	{
		if(description == null || description.isBlank())
		{
			throw new IllegalArgumentException("description is null or blank");
		}

		this.description = description;
	}
}