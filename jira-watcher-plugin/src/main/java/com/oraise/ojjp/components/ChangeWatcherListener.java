package com.oraise.ojjp.components;

import com.atlassian.jira.event.issue.IssueEvent;

public interface ChangeWatcherListener {

    public void onIssueEvent(IssueEvent issueEvent);

}