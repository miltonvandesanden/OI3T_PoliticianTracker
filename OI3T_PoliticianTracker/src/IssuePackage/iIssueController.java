package IssuePackage;

import EntitiesPackage.iIssue;

import java.util.List;

public interface iIssueController
{
	List<iIssue> getIssues();

	iIssue getIssue(int issueId);
	iIssue getIssue(String name, String description);

	iIssue addIssue(iIssue issue);
	iIssue setIssue(int issueId, iIssue issue);
	boolean deleteIssue(int issueId);
}
