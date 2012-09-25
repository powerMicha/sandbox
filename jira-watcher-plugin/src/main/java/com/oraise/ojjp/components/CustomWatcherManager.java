package com.oraise.ojjp.components;

import com.atlassian.crowd.embedded.api.User;
import com.atlassian.jira.association.UserAssociationStore;
import com.atlassian.jira.config.properties.ApplicationProperties;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.index.IssueIndexManager;
import com.atlassian.jira.issue.watchers.WatcherManager;
import com.atlassian.jira.issue.watchers.DefaultWatcherManager;
import com.atlassian.jira.user.util.UserManager;
import org.ofbiz.core.entity.GenericValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: malbers
 * Date: 23.08.12
 * Time: 15:42
 * To change this template use File | Settings | File Templates.
 */
public class CustomWatcherManager extends DefaultWatcherManager implements WatcherManager {

    private static final Logger log = LoggerFactory.getLogger(CustomWatcherManager.class);

    public CustomWatcherManager(UserAssociationStore userAssociationStore, ApplicationProperties applicationProperties, IssueIndexManager indexManager, UserManager userManager) {
        super(userAssociationStore, applicationProperties, indexManager, userManager);
    }

    @Override
    public void startWatching(User user, Issue issue) {
        super.startWatching(user, issue);

        notifyNewWatcher(user, issue.getKey());
    }

    @Override
    public void startWatching(User user, GenericValue issue) {
        super.startWatching(user, issue);

        notifyNewWatcher(user, (String) issue.get("key"));
    }


    private void notifyNewWatcher(User user, String key) {
        log.info("{} is now watching {}", user.getDisplayName(), key);
    }
}
