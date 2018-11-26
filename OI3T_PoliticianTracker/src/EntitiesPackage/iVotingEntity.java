package EntitiesPackage;

import java.time.LocalDate;

public interface iVotingEntity
{
	int getId();

	String getName();
	void setName(String name) throws IllegalArgumentException;

	LocalDate getDateOfFounding();
	void setDateOfFounding(LocalDate dateOfFounding) throws IllegalArgumentException;
}
