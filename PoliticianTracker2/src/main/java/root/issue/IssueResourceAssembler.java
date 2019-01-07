package root.issue;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;
import root.stance.StanceController;
import root.votingentity.VotingEntityController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class IssueResourceAssembler implements ResourceAssembler<Issue, Resource<Issue>>
{
	private boolean withSelf = true;
	private boolean withParent = true;
	private int votingEntityId = -1;
	private boolean withStances = false;

	public Resource<Issue> getResource(boolean withSelf, boolean withParent, Issue issue, int votingEntityId, boolean withStances)
	{
		this.withSelf = withSelf;
		this.withParent = withParent;
		this.votingEntityId = votingEntityId;
		this.withStances = withStances;

		return toResource(issue);
	}

	@Override
	public Resource<Issue> toResource(Issue issue)
	{

		Resource<Issue> resourceIssue = new Resource<>
			(
				issue
			);

		if(withSelf)
		{
			resourceIssue.add(linkTo(methodOn(IssueController.class).getIssue(issue.getId(), votingEntityId)).withSelfRel());
		}

		if(withParent)
		{
			resourceIssue.add(linkTo(methodOn(IssueController.class).getIssuesFromVotingEntity(votingEntityId)).withRel("issues"));
		}

		if(withStances)
		{
			resourceIssue.add(linkTo(methodOn(StanceController.class).getStances(votingEntityId, issue.getId())).withRel("stances"));
		}

		//resourceIssue.add(linkTo(methodOn(VotingEntityController.class).getVotingEntity()));

		return resourceIssue;
	}
}
