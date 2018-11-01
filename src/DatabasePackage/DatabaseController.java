package DatabasePackage;

import VotingEntityPackage.VotingEntity;
import VotingEntityPackage.iVotingEntity;

import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DatabaseController implements iDatabaseController
{
	private static final String URL = "jdbc:mysql://localhost:3306/mydb";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	private static Connection connection;
	private static PreparedStatement preparedStatement;
	private static ResultSet resultSet;

	public static void main(String args[])
	{
		DatabaseController databaseController = new DatabaseController();
	}

	public DatabaseController()
	{
		try
		{
			List<iVotingEntity> votingEntities = getVotingEntities();
			System.out.println(votingEntities.size());

			iVotingEntity votingEntity = getVotingEntity(1);
			System.out.println(votingEntity.getName());

			iVotingEntity newVotingEntity = new VotingEntity("testName2", LocalDate.of(1982, 2, 2));

			iVotingEntity result1 = addVotingEntity(newVotingEntity);
			System.out.println("VotingEntity added");
			System.out.println(result1.getName());

			iVotingEntity updatedVotingEntity = result1;
			updatedVotingEntity.setName("testName3");
			updatedVotingEntity.setDateOfFounding(LocalDate.of(1983, 3, 3));

			iVotingEntity result2 = setVotingEntity(updatedVotingEntity.getId(), updatedVotingEntity);
			System.out.println("VotingEntity updated");
			System.out.println(result2.getName());

			deleteVotingEntity(result2.getId());
			System.out.println("VotingEntity deleted");

			getVotingEntity(result2.getId());

		}
		catch(NoSuchElementException noSuchELementException)
		{
			System.out.println("See! deleted!");
		}
		catch(Exception exception)
		{
			exception.printStackTrace();
		}
	}

	private void open()
	{
		try
		{
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
		}
		catch(Exception exception)
		{
			System.out.println(exception);
		}
	}

	private void close()
	{
		try
		{
			if(!connection.isClosed())
			{
				connection.close();
			}
		}
		catch (Exception exception)
		{
			//exception.printStackTrace();
		}

		try
		{
			if(!resultSet.isClosed())
			{
				resultSet.close();
			}
		}
		catch(Exception exception)
		{
			//exception.printStackTrace();
		}

		try
		{
			if(!preparedStatement.isClosed())
			{
				preparedStatement.close();
			}
		}
		catch(Exception exception)
		{
			//exception.printStackTrace();
		}
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
	public iVotingEntity getVotingEntity(int votingEntityId) throws NoSuchElementException
	{
		iVotingEntity votingEntity = null;

		String query = "SELECT * FROM votingentity WHERE idVotingEntity=?";

		try
		{
			open();

			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);
			ResultSet resultSet = preparedStatement.executeQuery();

			if(!resultSet.first())
			{
				throw new NoSuchElementException("no votingEntity located with votingEntityId: " + votingEntityId);
			}
			else
			{
				votingEntity = new VotingEntity(votingEntityId, resultSet.getString("name"), resultSet.getDate("dateOfFounding").toLocalDate());
			}
		}
		catch(NoSuchElementException noSuchElementException)
		{
			throw noSuchElementException;
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
	public iVotingEntity getVotingEntity(String name, LocalDate dateOfFounding) throws NoSuchElementException
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

			if(!resultSet.first())
			{
				throw new NoSuchElementException("no votingEntity located with name: " + name + "and dateOfFounding: " + dateOfFounding.toString());
			}
			else
			{
				votingEntity = new VotingEntity(resultSet.getInt("idVotingEntity"), resultSet.getString("name"), resultSet.getDate("dateOfFounding").toLocalDate());
			}
		}
		catch(NoSuchElementException noSuchElementException)
		{
			throw noSuchElementException;
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
	public iVotingEntity addVotingEntity(iVotingEntity votingEntity) throws Exception //must be some specific FailedToAddException or something?
	{
		String query = "INSERT INTO votingentity (name, dateOfFounding) VALUES (?, ?);";
		iVotingEntity result = null;

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, votingEntity.getName());
			preparedStatement.setDate(2, Date.valueOf(votingEntity.getDateOfFounding()));

			if(preparedStatement.executeUpdate() <= 0)
			{
				throw new Exception("adding votingEntity failed");
			}

			result = getVotingEntity(votingEntity.getName(), votingEntity.getDateOfFounding());
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
	public iVotingEntity setVotingEntity(int votingEntityId, iVotingEntity votingEntity) throws NoSuchElementException
	{
		String query = "UPDATE votingentity SET name=?, dateOfFounding=? WHERE idVotingEntity=?";
		iVotingEntity result = null;

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setString(1, votingEntity.getName());
			preparedStatement.setDate(2, Date.valueOf(votingEntity.getDateOfFounding()));
			preparedStatement.setInt(3, votingEntityId);

			if(preparedStatement.executeUpdate() <= 0)
			{
				throw new NoSuchElementException("votingEntity not deleted because votingEntity not located");
			}

			result = getVotingEntity(votingEntityId);
		}
		catch(NoSuchElementException noSuchElementException)
		{
			throw noSuchElementException;
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
	public void deleteVotingEntity(int votingEntityId) throws NoSuchElementException
	{
		String query = "DELETE FROM votingentity WHERE idVotingEntity=?";

		try
		{
			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);

			if(preparedStatement.executeUpdate() <= 0)
			{
				throw new NoSuchElementException("votingEntity not deleted because votingEntity not located");
			}
		}
		catch(NoSuchElementException noSuchElementException)
		{
			throw noSuchElementException;
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
	}
}
