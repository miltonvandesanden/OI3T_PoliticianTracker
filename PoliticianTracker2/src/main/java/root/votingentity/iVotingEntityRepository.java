package root.votingentity;

import java.sql.SQLException;
import java.util.List;

public interface iVotingEntityRepository
{
	List<VotingEntity> getVotingEntities() throws SQLException;
	VotingEntity getVotingEntity(int votingEntityId) throws SQLException;
	VotingEntity addVotingEntity(VotingEntity votingEntity) throws SQLException;
	VotingEntity setVotingEntity(VotingEntity votingEntity) throws SQLException;
	boolean deleteVotingEntity(int votingEntityId) throws SQLException;
}
