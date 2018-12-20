package EntitiesPackage;

public interface iIssue
{
	int getId();
	String getName();
	void setName(String name) throws IllegalArgumentException;
	String getDescription();
	void setDescription(String description) throws IllegalArgumentException;
}
