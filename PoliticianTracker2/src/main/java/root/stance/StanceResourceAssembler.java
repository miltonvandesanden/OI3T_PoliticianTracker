package root.stance;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import root.issue.IssueController;
import root.votingentity.VotingEntityController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class StanceResourceAssembler implements ResourceAssembler<Stance, Resource<Stance>>
{
	private boolean withVotingEntities = false;
	private boolean withIssue = false;
	private boolean withSelf = true;
	private boolean withParent = true;

	public Resource<Stance> getResource(boolean withVotingEntities, boolean withIssue, boolean withSelf, boolean withParent, Stance stance)
	{
		this.withVotingEntities = withVotingEntities;
		this.withIssue = withIssue;
		this.withSelf = withSelf;
		this.withParent = withParent;

		return toResource(stance);
	}

	@Override
	public Resource<Stance> toResource(Stance stance)
	{

		Resource<Stance> resourceStance = new Resource<>
			(
				stance
			);

		if(withVotingEntities)
		{
			resourceStance.add(linkTo(methodOn(VotingEntityController.class).getVotingEntity(stance.getVotingEntityId())).withSelfRel());
		}

		if(withIssue)
		{
			resourceStance.add(linkTo(methodOn(IssueController.class).getIssue(stance.getIssueId(), stance.getVotingEntityId())).withSelfRel());
		}

		if(withSelf)
		{
			resourceStance.add(linkTo(methodOn(StanceController.class).getStance(stance.getId())).withSelfRel());
		}

		if(withParent)
		{
			resourceStance.add(linkTo(methodOn(StanceController.class).getStances(stance.getVotingEntityId())).withSelfRel());
		}

		return resourceStance;
	}
}
