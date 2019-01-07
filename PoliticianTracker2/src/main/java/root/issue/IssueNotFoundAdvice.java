package root.issue;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class IssueNotFoundAdvice
{
	@ResponseBody
	@ExceptionHandler({IssueNotFoundException.class})
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String issueNotFoundHandler(IssueNotFoundException issueNotFoundException)
	{
		return issueNotFoundException.getMessage();
	}
}
