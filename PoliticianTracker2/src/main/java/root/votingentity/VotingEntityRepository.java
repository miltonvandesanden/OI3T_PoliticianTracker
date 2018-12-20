package root.votingentity;

import org.springframework.stereotype.Component;
import root.DatabaseController;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class VotingEntityRepository extends DatabaseController implements iVotingEntityRepository
{

	@Override
	public List<VotingEntity> getVotingEntities() throws SQLException {
		List<VotingEntity> votingEntities = new ArrayList<>();
		query = "SELECT * FROM votingentity";

		try
		{
//			if(!open())
//			{
//				throw new SQLException();
//			}

			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				VotingEntity votingEntity = new VotingEntity
					(
						resultSet.getInt("idVotingEntity"),
						resultSet.getString("name"),
						resultSet.getDate("dateOfFounding").toLocalDate()
					);

				votingEntities.add(votingEntity);
			}
		}
		catch (SQLException e)
		{
			//e.printStackTrace();
			throw e;
		}
//		finally
//		{
//			try
//			{
//				close();
//			}
//			catch(SQLException e)
//			{
//				throw e;
//			}
//		}

		return votingEntities;
	}

	@Override
	public VotingEntity getVotingEntity(int votingEntityId) throws SQLException {
		VotingEntity votingEntity = null;
		query = "SELECT * FROM votingentity WHERE idVotingEntity=?";

		try
		{
//			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);
			resultSet = preparedStatement.executeQuery();

			if(resultSet.first())
			{
				votingEntity = new VotingEntity
					(
						votingEntityId,
						resultSet.getString("name"),
						resultSet.getDate("dateOfFounding").toLocalDate()
					);
			}
		}
		catch(SQLException e)
		{
			throw e;
			//e.printStackTrace();
		}
//		finally
//		{
//			try
//			{
//				close();
//			}
//			catch (SQLException e)
//			{
//				throw e;
//			}
//		}

		return votingEntity;
	}

	@Override
	public VotingEntity addVotingEntity(VotingEntity votingEntity) throws SQLException
	{
		query = "INSERT INTO votingentity (name, dateOfFounding) VALUES (?, ?)";

		try
		{
//			open();

			preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, votingEntity.getName());
			preparedStatement.setDate(2, Date.valueOf(votingEntity.getDateOfFounding()));

			preparedStatement.executeUpdate();

			resultSet = preparedStatement.getGeneratedKeys();

			if(resultSet.first())
			{
				votingEntity.setId(resultSet.getInt(1));
			}
			else
			{
				votingEntity = null;
			}
		}
		catch (SQLException e)
		{
			throw e;
			//e.printStackTrace();
		}
//		finally
//		{
//			try
//			{
//				close();
//			}
//			catch(SQLException e)
//			{
//				throw e;
//			}
//		}

		return votingEntity;
	}

	@Override
	public VotingEntity setVotingEntity(VotingEntity votingEntity) throws SQLException {
		query = "UPDATE votingentity SET name=?, dateOfFounding=? WHERE idVotingEntity=?";

		try
		{
//			open();

			preparedStatement = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
			preparedStatement.setString(1, votingEntity.getName());
			preparedStatement.setDate(2, Date.valueOf(votingEntity.getDateOfFounding()));
			preparedStatement.setInt(3, votingEntity.getId());

			if(preparedStatement.executeUpdate() > 0)
			{
				votingEntity = getVotingEntity(votingEntity.getId());
			}
			else
			{
				votingEntity = null;
			}

//			resultSet = preparedStatement.getGeneratedKeys();

//			if(resultSet.first())
//			{
//				votingEntity.setId(resultSet.getInt(1));
//			}
//			else
//			{
//				votingEntity = null;
//			}
		}
		catch (SQLException e)
		{
			throw e;
		}
//		finally
//		{
//			try
//			{
//				close();
//			}
//			catch (SQLException e)
//			{
//				throw e;
//			}
//		}

		return votingEntity;
	}

	@Override
	public boolean deleteVotingEntity(int votingEntityId) throws SQLException {
		boolean result = false;
		query = "DELETE FROM votingentity WHERE idVotingEntity=?";

		try
		{
//			open();

			preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt(1, votingEntityId);

			if(preparedStatement.executeUpdate() > 0)
			{
				result = true;
			}
		}
		catch (SQLException e)
		{
			throw e;
			//e.printStackTrace();
		}
//		finally
//		{
//			try
//			{
//				close();
//			}
//			catch(SQLException e)
//			{
//				throw e;
//			}
//		}

		return result;
	}
}
