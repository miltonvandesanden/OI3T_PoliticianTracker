package root;

import java.sql.*;

public class DatabaseController
{
	private static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false";
	private static final String USER = "root";
	private static final String PASSWORD = "root";

	protected Connection connection;
	protected PreparedStatement preparedStatement;
	protected ResultSet resultSet;

	protected String query;

	public boolean open() throws SQLException {
		boolean success;

		try
		{
			connection = DriverManager.getConnection(URL, USER, PASSWORD);
			success = !connection.isClosed();
		} catch (SQLException e)
		{
			//success = false;
			//e.printStackTrace();
			throw e;
		}

		return success;
	}

	public boolean close() throws SQLException {
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
		catch (SQLException e)
		{
			throw e;
			//e.printStackTrace();
		}

		return success;
	}
}
