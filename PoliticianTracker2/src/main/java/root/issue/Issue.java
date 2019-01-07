package root.issue;

import lombok.Data;

@Data
public class Issue
{
	private int id;
	private String name;
	private String description;

	public Issue(){}

	public Issue(int id, String name, String description)
	{
		this.id = id;
		this.name = name;
		this.description = description;
	}

	public Issue(String name, String description)
	{
		id = -1;
		this.name = name;
		this.description= description;
	}
}
