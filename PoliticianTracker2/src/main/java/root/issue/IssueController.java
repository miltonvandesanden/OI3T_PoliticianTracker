package root.issue;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.stance.Stance;
import root.stance.StanceController;
import root.votingentity.VotingEntityController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class IssueController
{
	//private final IssueRepository issueRepository;
	private final IssueResourceAssembler issueResourceAssembler;

	private final StanceController stanceController;
	private List<Issue> issues;

	public IssueController(/*IssueRepository IssueRepository,*/ IssueResourceAssembler issueResourceAssembler, StanceController stanceController)
	{
		//this.votingEntityRepository = votingEntityRepository;
		this.issueResourceAssembler = issueResourceAssembler;

		this.stanceController = stanceController;

		issues = new ArrayList<>();
		issues.add(new Issue(1, "European Union", "The degree of support or opposement for the European Union"));
		issues.add(new Issue(2, "Party membership", ""));
	}

	/*@GetMapping("/issues")
	public Resources<Resource<Issue>> getIssues()
	{
		Resources<Resource<Issue>> results;

		List<Issue> issues = new ArrayList<>();

		List<Resource<Issue>> issueResources = new ArrayList<>();

		for(Issue issue : issues)
		{
			issueResources.add(issueResourceAssembler.getResource(true, false, issue));
		}

		results = new Resources<>
			(
				issueResources,
				linkTo(methodOn(IssueController.class).getIssues()).withSelfRel()
			);

		return results;
	}*/

	@GetMapping("/issues/_{votingEntityId}")
	public Resources<Resource<Issue>> getIssuesFromVotingEntity(@PathVariable int votingEntityId)
	{
		List<Stance> stances = stanceController.getStances(votingEntityId);
		List<Issue> results = new ArrayList<>();

		for(Stance stance : stances)
		{
			Issue issue = getIssueObject(stance.getIssueId());

			for(Issue result : results)
			{
				if(result.getId() == issue.getId())
				{
					issue = null;
				}
			}

			if(issue != null)
			{
				results.add(issue);
			}
		}

		List<Resource<Issue>> issueResources = new ArrayList<>();

		for(Issue issue : results)
		{
			issueResources.add(issueResourceAssembler.getResource(true, false, issue, votingEntityId, false));
		}

		Resources<Resource<Issue>> issueResourcesResult;
		issueResourcesResult = new Resources<>
			(
				issueResources,
				linkTo(methodOn(IssueController.class).getIssuesFromVotingEntity(votingEntityId)).withSelfRel(),
				linkTo(methodOn(VotingEntityController.class).getVotingEntity(votingEntityId)).withRel("votingEntity")
			);

		return issueResourcesResult;
	}

	public Issue getIssueObject(int id)
	{
		Issue result = null;

		for(Issue issue : issues)
		{
			if(issue.getId() == id)
			{
				result = issue;
			}
		}

		return result;
	}

	@GetMapping("/issues/{id}_{votingEntityId}")
	public Resource<Issue> getIssue(@PathVariable int id, @PathVariable int votingEntityId)
	{
		Issue result = null;

		for(Issue issue : issues)
		{
			if(issue.getId() == id)
			{
				result = issue;
			}
		}

		if(result == null)
		{
			throw new IssueNotFoundException(id);
		}
		else
		{
			Resource<Issue> issueResource = issueResourceAssembler.getResource(false, false, result, votingEntityId, true);
			issueResource.add(linkTo(methodOn(IssueController.class).getIssuesFromVotingEntity(votingEntityId)).withRel("Issues"));
			return issueResource;
		}
	}

	//@CrossOrigin(origins = "httpL//localhost")
	/*@PutMapping("/votingentities/{id}")
	public ResponseEntity<?> setVotingEntity(@RequestBody VotingEntity votingEntity, @PathVariable int id)
	{
		VotingEntity updatedVotingEntity = votingEntity;
		updatedVotingEntity.setId(id);

		try
		{
			votingEntityRepository.open();

			updatedVotingEntity = votingEntityRepository.setVotingEntity(updatedVotingEntity);

			if(updatedVotingEntity == null)
			{
				updatedVotingEntity = votingEntityRepository.addVotingEntity(votingEntity);
			}

			votingEntityRepository.close();

			Resource<VotingEntity> votingEntityResource = votingEntityResourceAssembler.toResource(updatedVotingEntity);

			return ResponseEntity
				.created(new URI(votingEntityResource.getId().expand().getHref()))
				.body(votingEntityResource);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new VotingEntityNotSetException();
		}
	}*/

	//@CrossOrigin(origins = "httpL//localhost")
	/*@DeleteMapping("votingentities/{id}")
	public ResponseEntity<?> deleteVotingEntity(@PathVariable int id)
	{
		try
		{
			votingEntityRepository.open();

			votingEntityRepository.deleteVotingEntity(id);

			votingEntityRepository.close();

			return ResponseEntity.noContent().build();
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.notFound().build();
		}
	}*/
}
