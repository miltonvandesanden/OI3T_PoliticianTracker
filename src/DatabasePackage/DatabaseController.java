package DatabasePackage;

import EntitiesPackage.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DatabaseController implements iDatabaseController
{
	private static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	private static iDatabaseController databaseController = null;

	// private constructor restricted to this class itself
	private DatabaseController(){ }

	// static method to create instance of DatabaseController class
	public static iDatabaseController getDatabaseController()
	{
		if (databaseController == null)
		{
			databaseController = new DatabaseController();
		}

		return databaseController;
	}

	private boolean open()
	{
		boolean success;

		try
		{
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			success = !connection.isClosed();
		}
		catch(Exception exception)
		{
			success = false;
			exception.printStackTrace();
		}

		return success;
	}

	private boolean close()
	{
		boolean success = false;

		try
		{
			if(!connection.isClosed())
			{
				connection.close();
			}

			if(!resultSet.isClosed())
			{
				resultSet.close();
			}

			if(!preparedStatement.isClosed())
			{
				preparedStatement.close();
			}

			if(connection.isClosed() && resultSet.isClosed() && preparedStatement.isClosed())
			{
				success = true;
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}

		return success;
	}

	@Override
	public List<iVotingEntity> getVotingEntities()
	{
		List<iVotingEntity> votingEntities = new ArrayList<>();
		String query = "SELECT * FROM votingentity";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				iVotingEntity votingEntity = new VotingEntity(resultSet.getInt("idVotingEntity"), resultSet.getString("name"), resultSet.getDate("dateOfFounding").toLocalDate());

				votingEntities.add(votingEntity);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return votingEntities;
	}

	@Override
	public iVotingEntity getVotingEntity(int votingEntityId)
	{
		iVotingEntity votingEntity = null;

		String query = "SELECT * FROM votingentity WHERE idVotingEntity=?";

		try
		{
			open();

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.first())
			{
				votingEntity = new VotingEntity(votingEntityId, resultSet.getString("name"), resultSet.getDate("dateOfFounding").toLocalDate());
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return votingEntity;
	}

	@Override
	public iVotingEntity getVotingEntity(String name, LocalDate dateOfFounding)
	{
		iVotingEntity votingEntity = null;

		String query = "SELECT * FROM votingentity WHERE name=? AND dateOfFounding=?";

		try
		{
			open();

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setDate(2, Date.valueOf(dateOfFounding));
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.first())
			{
				votingEntity = new VotingEntity(resultSet.getInt("idVotingEntity"), resultSet.getString("name"), resultSet.getDate("dateOfFounding").toLocalDate());
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return votingEntity;
	}

	@Override
	public iVotingEntity addVotingEntity(iVotingEntity votingEntity)
	{
		iVotingEntity result = null;
		String query = "INSERT INTO votingentity (name, dateOfFounding) VALUES (?, ?);";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, votingEntity.getName());
			preparedStatement.setDate(2, Date.valueOf(votingEntity.getDateOfFounding()));

			if(preparedStatement.executeUpdate() > 0)
			{
				result = getVotingEntity(votingEntity.getName(), votingEntity.getDateOfFounding());
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public iVotingEntity setVotingEntity(int votingEntityId, iVotingEntity votingEntity)
	{
		iVotingEntity result = null;
		String query = "UPDATE votingentity SET name=?, dateOfFounding=? WHERE idVotingEntity=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, votingEntity.getName());
			preparedStatement.setDate(2, Date.valueOf(votingEntity.getDateOfFounding()));
			preparedStatement.setInt(3, votingEntityId);

			if(preparedStatement.executeUpdate() > 0)
			{
				result = getVotingEntity(votingEntityId);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public boolean deleteVotingEntity(int votingEntityId)
	{
		boolean result = false;
		String query = "DELETE FROM votingentity WHERE idVotingEntity=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);

			if(preparedStatement.executeUpdate() > 0)
			{
				result = true;
			}
		}
		catch(Exception exception)
		{
			result = false;
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public List<iIssue> getIssues()
	{
		List<iIssue> issues = new ArrayList<>();
		String query = "SELECT * FROM issue";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				iIssue issue = new Issue(resultSet.getInt("idIssue"), resultSet.getString("name"), resultSet.getString("description"));

				issues.add(issue);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return issues;
	}

	@Override
	public iIssue getIssue(int issueId)
	{
		iIssue issue = null;
		String query = "SELECT * FROM issue WHERE idIssue=?";

		try
		{
			open();

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, issueId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.first())
			{
				issue = new Issue(issueId, resultSet.getString("name"), resultSet.getString("description"));
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return issue;
	}

	@Override
	public iIssue getIssue(String name, String description)
	{
		iIssue issue = null;

		String query = "SELECT * FROM issue WHERE name=? AND description=?";

		try
		{
			open();

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, name);
			preparedStatement.setString(2, description);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.first())
			{
				issue = new Issue(resultSet.getInt("issueId"), name, description);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return issue;
	}

	@Override
	public iIssue addIssue(iIssue issue)
	{
		iIssue result = null;
		String query = "INSERT INTO issue (name, description) VALUES (?, ?);";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, issue.getName());
			preparedStatement.setString(2, issue.getDescription());

			if(preparedStatement.executeUpdate() > 0)
			{
				result = getIssue(issue.getName(), issue.getDescription());
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public iIssue setIssue(int issueId, iIssue issue)
	{
		iIssue result = null;
		String query = "UPDATE issue SET name=?, description=? WHERE idIssue=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, issue.getName());
			preparedStatement.setString(2, issue.getDescription());
			preparedStatement.setInt(3, issueId);

			if(preparedStatement.executeUpdate() > 0)
			{
				result = getIssue(issueId);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public boolean deleteIssue(int issueId)
	{
		boolean success = false;
		String query = "DELETE FROM issue WHERE idIssue=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, issueId);

			if(preparedStatement.executeUpdate() > 0)
			{
				success = true;
			}
		}
		catch(Exception exception)
		{
			success = false;
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return success;
	}

	@Override
	public List<iStance> getStancesOfVotingEntity(int votingEntityId)
	{
		List<iStance> stances = new ArrayList<>();
		String query = "SELECT * FROM stance WHERE VotingEntity_idVotingEntity=? ";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				stances.add
				(
					new Stance
					(
							resultSet.getInt("idStance"),
							resultSet.getInt("VotingEntity_idVotingEntity"),
							resultSet.getInt("Issue_idIssue"),
							resultSet.getBoolean("inFavor"),
							resultSet.getDate("date").toLocalDate()
					)
				);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return stances;
	}

	@Override
	public List<iStance> getStancesOfIssue(int issueId)
	{
		List<iStance> stances = new ArrayList<>();
		String query = "SELECT * FROM stance WHERE Issue_idIssue=? ";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, issueId);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				stances.add
				(
					new Stance
					(
						resultSet.getInt("idStance"),
						resultSet.getInt("VotingEntity_idVotingEntity"),
						resultSet.getInt("Issue_idIssue"),
						resultSet.getBoolean("inFavor"),
						resultSet.getDate("date").toLocalDate()
					)
				);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return stances;
	}

	@Override
	public List<iStance> getStancesOfVotingEntityAndIssue(int votingEntityId, int issueId)
	{
		List<iStance> stances = new ArrayList<>();
		String query = "SELECT * FROM stance WHERE Issue_idIssue=? AND Issue_idIssue=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);
			preparedStatement.setInt(2, issueId);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				stances.add
				(
					new Stance
					(
						resultSet.getInt("idStance"),
						resultSet.getInt("VotingEntity_idVotingEntity"),
						resultSet.getInt("Issue_idIssue"),
						resultSet.getBoolean("inFavor"),
						resultSet.getDate("date").toLocalDate()
					)
				);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return stances;
	}

	@Override
	public iStance getStance(int stanceId)
	{
		iStance stance = null;
		String query = "SELECT * FROM stance WHERE idStance=?";

		try
		{
			open();

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, stanceId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.first())
			{
				stance = new Stance
				(
					resultSet.getInt("idStance"),resultSet.getInt("VotingEntity_idVotingEntity"),
					resultSet.getInt("Issue_idIssue"),
					resultSet.getBoolean("inFavor"),
					resultSet.getDate("date").toLocalDate()
				);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return stance;
	}

	@Override
	public iStance getStance(int votingEntityId, int issueId, LocalDate date)
	{
		iStance stance = null;
		String query = "SELECT * FROM stance WHERE VotingEntity_idVotingEntity=? AND Issue_idIssue=? AND date=?";

		try
		{
			open();

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);
			preparedStatement.setInt(2, issueId);
			preparedStatement.setDate(1, Date.valueOf(date));
			ResultSet resultSet = preparedStatement.executeQuery();

			if(resultSet.first())
			{
				stance = new Stance
				(
					resultSet.getInt("idStance"),resultSet.getInt("VotingEntity_idVotingEntity"),
					resultSet.getInt("Issue_idIssue"),
					resultSet.getBoolean("inFavor"),
					resultSet.getDate("date").toLocalDate()
				);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return stance;
	}

	@Override
	public iStance addStance(iStance stance)
	{
		iStance result = null;
		String query = "INSERT INTO stance (VotingEntity_idVotingEntity, Issue_idIssue, inFavor, date) VALUES (?, ?, ?, ?);";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, stance.getVotingEntityId());
			preparedStatement.setInt(2, stance.getIssueId());
			preparedStatement.setBoolean(3, stance.getInFavor());
			preparedStatement.setDate(4, Date.valueOf(stance.getDate()));

			if(preparedStatement.executeUpdate() > 0)
			{
				result = getStance(stance.getVotingEntityId(), stance.getIssueId(), stance.getDate());
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public iStance setStance(int stanceId, iStance stance)
	{
		iStance result = null;
		String query = "UPDATE stance SET VotingEntity_idVotingEntity=?, Issue_idIssue=?, inFavor=?, date=? WHERE idStance=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, stance.getVotingEntityId());
			preparedStatement.setInt(2, stance.getIssueId());
			preparedStatement.setBoolean(3, stance.getInFavor());
			preparedStatement.setDate(4, Date.valueOf(stance.getDate()));

			if(preparedStatement.executeUpdate() > 0)
			{
				result = getStance(stanceId);
			}
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return result;
	}

	@Override
	public boolean deleteStance(int stanceId)
	{
		boolean success = false;
		String query = "DELETE FROM stance WHERE idStance=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, stanceId);

			if(preparedStatement.executeUpdate() > 0)
			{
				success = true;
			}
		}
		catch(Exception exception)
		{
			success = false;
			exception.printStackTrace();
		}
		finally
		{
			try
			{
				close();
			}
			catch(Exception exception)
			{
				exception.printStackTrace();
			}
		}

		return success;
	}
}
