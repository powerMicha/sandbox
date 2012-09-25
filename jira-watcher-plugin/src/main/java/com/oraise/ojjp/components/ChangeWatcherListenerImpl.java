package com.oraise.ojjp.components;

import com.atlassian.event.api.EventListener;
import com.atlassian.event.api.EventPublisher;
import com.atlassian.jira.event.issue.IssueEvent;
import com.atlassian.jira.issue.Issue;
import com.atlassian.jira.issue.watchers.WatcherManager;
import com.atlassian.jira.project.ProjectManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

public class ChangeWatcherListenerImpl implements ChangeWatcherListener{
    private static final Logger log = LoggerFactory.getLogger(ChangeWatcherListenerImpl.class);

    /* DI - Autowiring an own bean, looked up by component scan */
    @Autowired
    private MySpringBean mySpringBean;

    /* DI - Autowiring a bean, provided by atlasian */
    @Autowired
    private ProjectManager projectManager;

    @Autowired
    private WatcherManager watcherManager;

    @Autowired
    private EventPublisher eventPublisher;


    /* DI - via PicoContainer */
    public ChangeWatcherListenerImpl(EventPublisher eventPublisher) {
        log.debug("Hello JIRA - ChangeWatcherListenerImpl with event publisher!");

        // now done via autowiring
        // eventPublisher.register(this);
    }


    @PostConstruct
    public void init(){
        this.eventPublisher.register(this);
    }

    @PreDestroy
    public void destroy(){
        this.eventPublisher.unregister(this);
    }


    @EventListener
    @Override
    public void onIssueEvent(IssueEvent issueEvent) {
        Long eventTypeId = issueEvent.getEventTypeId();
        Issue issue = issueEvent.getIssue();

        log.info("Received event for issue {} with type {}", issue.getKey(), eventTypeId);

        List<String> watchers = watcherManager.getCurrentWatcherUsernames(issue);
        for(String watcher : watchers){
            log.info(" + watcher: {}", watcher);
        }
    }



}