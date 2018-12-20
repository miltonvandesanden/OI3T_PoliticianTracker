package root.votingentity;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class VotingEntityNotSetAdvice
{
	@ResponseBody
	@ExceptionHandler({VotingEntityNotSetException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public String votingEntityNotSetHandler(VotingEntityNotSetException votingEntityNotSetException)
	{
		return votingEntityNotSetException.getMessage();
	}


}
