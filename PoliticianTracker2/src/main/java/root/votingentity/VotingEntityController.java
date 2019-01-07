package root.votingentity;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import root.issue.Issue;
import root.issue.IssueController;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class VotingEntityController
{
	private final VotingEntityRepository votingEntityRepository;
	private final VotingEntityResourceAssembler votingEntityResourceAssembler;

	private final IssueController issueController;

	public VotingEntityController(VotingEntityRepository votingEntityRepository, VotingEntityResourceAssembler votingEntityResourceAssembler, IssueController issueController)
	{
		this.votingEntityRepository = votingEntityRepository;
		this.votingEntityResourceAssembler = votingEntityResourceAssembler;

		this.issueController = issueController;
	}

	//@CrossOrigin(origins = "httpL//localhost")
	@GetMapping("/votingentities")
	public Resources<Resource<VotingEntity>> /*ResponseEntity<?>*/ getVotingEntities()
	{
		Resources<Resource<VotingEntity>> results;

		try
		{
			votingEntityRepository.open();

			List<VotingEntity> votingEntities = votingEntityRepository.getVotingEntities();

			votingEntityRepository.close();

			List<Resource<VotingEntity>> votingEntitiesResources = new ArrayList<>();

			for (VotingEntity votingEntity : votingEntities)
			{
				votingEntitiesResources.add(votingEntityResourceAssembler.getResource(false, true, false, votingEntity));
			}

			//List<Resource<VotingEntity>> votingEntitiesResources = votingEntities.stream().map(votingEntityResourceAssembler::toResource).collect(Collectors.toList());

			results = new Resources<>
				(
					votingEntitiesResources//,
					//linkTo(methodOn(VotingEntityController.class).getVotingEntities()).withSelfRel()
				);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			results = new Resources<>
				(
					new ArrayList<>()//,
					//linkTo(methodOn(VotingEntityController.class).getVotingEntities()).withSelfRel()
				);
		}

//		HttpHeaders headers = new HttpHeaders();
//		headers.add("Access-Control-Allow-Origin", "*");
//		headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
//		//headers.add("Access-Control-Allow-Origin", "GET");
//		ResponseEntity<?> responseEntity = new ResponseEntity(results, headers, HttpStatus.OK);

//		return responseEntity;

		return results;
	}

//	@PostMapping("/votingentities")
//	public ResponseEntity<?> addVotingEntity(@RequestBody VotingEntity newVotingEntity) throws URISyntaxException
//	{
//		try
//		{
//			votingEntityRepository.open();
//
//			VotingEntity votingEntity = votingEntityRepository.addVotingEntity(newVotingEntity);
//
//			votingEntityRepository.close();
//
//			Resource<VotingEntity> votingEntityResource = votingEntityResourceAssembler
//				.toResource(votingEntity);
//
//			HttpHeaders headers = new HttpHeaders();
//			headers.add("Access-Control-Allow-Origin", "https://localhost:8080/votingEntities");
//			ResponseEntity<?> responseEntity = new ResponseEntity(votingEntityResource, headers, HttpStatus.OK);
//
//			return responseEntity;
//		}
//		catch (Exception e)
//		{
//			throw new VotingEntityNotAddedException();
//		}
//	}

	@GetMapping("/votingentities/{id}")
	public /*Dictionary<Resource<VotingEntity>, Resources<Resource<Issue>>>*/ Resource<VotingEntity> getVotingEntity(@PathVariable int id)
	{
		try
		{
			votingEntityRepository.open();

			VotingEntity votingEntity = votingEntityRepository.getVotingEntity(id);

			votingEntityRepository.close();

			Resource<VotingEntity> votingEntityResource = votingEntityResourceAssembler.getResource(false, true, true, votingEntity);

			//Resources<Resource<Issue>> issues = issueController.getIssuesFromVotingEntity(id);
			//Dictionary<Resource<VotingEntity>, Resources<Resource<Issue>>> result = new Hashtable<>();

			//result.put(votingEntityResource, issues);

			return votingEntityResource;
			//return result;
		}
		catch (Exception e)
		{
			throw new VotingEntityNotFoundException(id);
		}
	}

	//@CrossOrigin(origins = "httpL//localhost")
//	@PutMapping("/votingentities/{id}")
//	public Resource<VotingEntity> setVotingEntity(@RequestBody VotingEntity votingEntity, @PathVariable int id)
//	{
//		VotingEntity updatedVotingEntity = votingEntity;
//		updatedVotingEntity.setId(id);
//
//		try
//		{
//			votingEntityRepository.open();
//
//			updatedVotingEntity = votingEntityRepository.setVotingEntity(updatedVotingEntity);
//
//			if(updatedVotingEntity == null)
//			{
//				updatedVotingEntity = votingEntityRepository.addVotingEntity(votingEntity);
//			}
//
//			votingEntityRepository.close();
//
//			Resource<VotingEntity> votingEntityResource = votingEntityResourceAssembler.toResource(updatedVotingEntity);
//
//			return votingEntityResource;
////			return ResponseEntity
////				.created(new URI(votingEntityResource.getId().expand().getHref()))
////				.body(votingEntityResource);
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			throw new VotingEntityNotSetException();
//		}
//	}

//	//@CrossOrigin(origins = "httpL//localhost")
//	@DeleteMapping("votingentities/{id}")
//	public ResponseEntity<?> deleteVotingEntity(@PathVariable int id)
//	{
//		try
//		{
//			votingEntityRepository.open();
//
//			votingEntityRepository.deleteVotingEntity(id);
//
//			votingEntityRepository.close();
//
//			return ResponseEntity.noContent().build();
//		}
//		catch (Exception e)
//		{
//			e.printStackTrace();
//			return ResponseEntity.notFound().build();
//		}
//	}
}
