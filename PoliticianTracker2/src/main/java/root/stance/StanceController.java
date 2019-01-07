package root.stance;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.issue.IssueController;
import root.votingentity.VotingEntityController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class StanceController
{
	private List<Stance> stances = new ArrayList<>();

	//private final IssueRepository issueRepository;
	private final StanceResourceAssembler stanceResourceAssembler;

	public StanceController(/*IssueRepository IssueRepository,*/ StanceResourceAssembler stanceResourceAssembler)
	{
		//this.votingEntityRepository = votingEntityRepository;
		this.stanceResourceAssembler = stanceResourceAssembler;

		stances.add(new Stance(1, 1, 2, "Republican", LocalDate.of(1987, 1, 1)));
		stances.add(new Stance(2, 1, 2, "Independent", LocalDate.of(1999, 1, 1)));
		stances.add(new Stance(3, 1, 2, "Democrats", LocalDate.of(2001, 1, 1)));
		stances.add(new Stance(4, 1, 2, "Republican", LocalDate.of(2009, 1, 1)));
		stances.add(new Stance(5, 1, 2, "Independent", LocalDate.of(2011, 1, 1)));
		stances.add(new Stance(6, 1, 2, "Republican", LocalDate.of(2012, 1, 1)));
	}

	@GetMapping("/stances/{votingEntityId}/{issueId}")
	public Resources<Resource<Stance>> getStances(@PathVariable int votingEntityId, @PathVariable int issueId)
	{
		List<Resource<Stance>> stanceResources = new ArrayList<>();

		for(Stance stance : stances)
		{
			if(stance.getVotingEntityId() == votingEntityId && stance.getIssueId() == issueId)
			{
				stanceResources.add(stanceResourceAssembler.getResource(false, false, true, false, stance));
			}
		}

		Resources<Resource<Stance>> results = new Resources<>
			(
				stanceResources,
				linkTo(methodOn(VotingEntityController.class).getVotingEntity(votingEntityId)).withSelfRel(),
				linkTo(methodOn(IssueController.class).getIssue(issueId, votingEntityId)).withSelfRel()
				//linkTo(methodOn(StanceController.class).getStances(votingEntityId, issueId)).withSelfRel()

			);

		return results;
	}

	public List<Stance> getStances(int votingEntityId)
	{
		List<Stance> results = new ArrayList<>();

		for(Stance stance : stances)
		{
			if(stance.getVotingEntityId() == votingEntityId)
			{
				results.add(stance);
			}
		}

		return results;
	}

	@GetMapping("/stances/{id}")
	public Resource<Stance> getStance(@PathVariable int id)
	{
		Stance result = null;

		for(Stance stance : stances)
		{
			if(stance.getId() == id)
			{
				result = stance;
			}
		}

		if(result == null)
		{
			throw new StanceNotFoundException(id);
		}
		else
		{
			return stanceResourceAssembler.getResource(true, true, true, false, result);
		}
	}
}
