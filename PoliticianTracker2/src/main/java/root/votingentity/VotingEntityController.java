package root.votingentity;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class VotingEntityController
{
	private final VotingEntityRepository votingEntityRepository;
	private final VotingEntityResourceAssembler votingEntityResourceAssembler;

	public VotingEntityController(VotingEntityRepository votingEntityRepository, VotingEntityResourceAssembler votingEntityResourceAssembler)
	{
		this.votingEntityRepository = votingEntityRepository;
		this.votingEntityResourceAssembler = votingEntityResourceAssembler;
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
				votingEntitiesResources.add(votingEntityResourceAssembler.getResource(true, false, votingEntity));
			}

			//List<Resource<VotingEntity>> votingEntitiesResources = votingEntities.stream().map(votingEntityResourceAssembler::toResource).collect(Collectors.toList());

			results = new Resources<>
				(
					votingEntitiesResources,
					linkTo(methodOn(VotingEntityController.class).getVotingEntities()).withSelfRel()
				);
		}
		catch (Exception e)
		{
			e.printStackTrace();

			results = new Resources<>
				(
					new ArrayList<>(),
					linkTo(methodOn(VotingEntityController.class).getVotingEntities()).withSelfRel()
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

	@CrossOrigin(origins = "httpL//localhost")
	@PostMapping("/votingentities")
	public ResponseEntity<?> addVotingEntity(@RequestBody VotingEntity newVotingEntity) throws URISyntaxException
	{
		try
		{
			votingEntityRepository.open();

			VotingEntity votingEntity = votingEntityRepository.addVotingEntity(newVotingEntity);

			votingEntityRepository.close();

			Resource<VotingEntity> votingEntityResource = votingEntityResourceAssembler
				.toResource(votingEntity);

//			ResponseEntity responseEntity =	ResponseEntity.created(new URI(votingEntityResource.getId().expand().getHref()))
//			.body(votingEntityResource);

			HttpHeaders headers = new HttpHeaders();
			headers.add("Access-Control-Allow-Origin", "https://localhost:8080/votingEntities");
			ResponseEntity<?> responseEntity = new ResponseEntity(votingEntityResource, headers, HttpStatus.OK);

			return responseEntity;

//			return ResponseEntity.created(new URI(votingEntityResource.getId().expand().getHref()))
//				.body(votingEntityResource);
		}
		catch (Exception e)
		{
//			return ResponseEntity.created
//				(
//					new URI
//						(
//							linkTo(methodOn(VotingEntityController.class).getVotingEntities())
//								.withRel("votingentities").getHref()
//						)
//				)
//				.body(null);

			throw new VotingEntityNotAddedException();
		}
	}

	@CrossOrigin(origins = "httpL//localhost")
	@GetMapping("/votingentities/{id}")
	public Resource<VotingEntity> getVotingEntity(@PathVariable int id)
	{
		try
		{
			votingEntityRepository.open();

			VotingEntity votingEntity = votingEntityRepository.getVotingEntity(id);

			votingEntityRepository.close();

			//votingEntityResourceAssembler.withSelf = true;
			Resource<VotingEntity> votingEntityResource = votingEntityResourceAssembler.getResource(true, true, votingEntity);

			//List<Resource<VotingEntity>> votingEntityResources = new ArrayList<>();
			//votingEntityResources.add(votingEntityResource);

//			Resources<Resource<VotingEntity>> results = new Resources<>
//				(
//					votingEntityResources,
//					linkTo(methodOn(VotingEntityController.class).getVotingEntity(id)).withSelfRel()
//				);

			return votingEntityResource;
			//return results;
		}
		catch (Exception e)
		{
			throw new VotingEntityNotFoundException(id);
		}
	}

	@CrossOrigin(origins = "httpL//localhost")
	@PutMapping("/votingentities/{id}")
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
	}

	@CrossOrigin(origins = "httpL//localhost")
	@DeleteMapping("votingentities/{id}")
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
	}
}
