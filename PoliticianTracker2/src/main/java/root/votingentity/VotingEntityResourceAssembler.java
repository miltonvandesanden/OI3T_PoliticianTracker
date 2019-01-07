package root.votingentity;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import root.issue.IssueController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class VotingEntityResourceAssembler implements ResourceAssembler<VotingEntity, Resource<VotingEntity>>
{
	private boolean withSelf = false;
	private boolean withIssues = false;
	private boolean withParent = false;

	public Resource<VotingEntity> getResource(boolean withSelf, boolean withIssues, boolean withParent, VotingEntity votingEntity)
	{
		this.withSelf = withSelf;
		this.withIssues = withIssues;
		this.withParent = withParent;

		return toResource(votingEntity);
	}

	@Override
	public Resource<VotingEntity> toResource(VotingEntity votingEntity)
	{

		Resource<VotingEntity> resourceVotingEntity = new Resource<>
			(
				votingEntity
			);

		if(withSelf)
		{
			resourceVotingEntity.add(linkTo(methodOn(VotingEntityController.class).getVotingEntity(votingEntity.getId())).withSelfRel());
		}

		if(withIssues)
		{
			resourceVotingEntity.add(linkTo(methodOn(IssueController.class).getIssuesFromVotingEntity(votingEntity.getId())).withRel("issues"));
		}

		if(withParent)
		{
			resourceVotingEntity.add(linkTo(methodOn(VotingEntityController.class).getVotingEntities()).withRel("votingentities"));
		}

		return resourceVotingEntity;
	}
}
