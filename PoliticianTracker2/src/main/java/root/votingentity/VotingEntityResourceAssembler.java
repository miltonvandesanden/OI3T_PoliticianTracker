package root.votingentity;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class VotingEntityResourceAssembler implements ResourceAssembler<VotingEntity, Resource<VotingEntity>>
{
	private boolean withSelf = true;
	private boolean withParent = true;

	public Resource<VotingEntity> getResource(boolean withSelf, boolean withParent, VotingEntity votingEntity)
	{
		this.withSelf = withSelf;
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

		if(withParent)
		{
			resourceVotingEntity.add(linkTo(methodOn(VotingEntityController.class).getVotingEntities()).withRel("votingentities"));
		}

		return resourceVotingEntity;
	}
}
