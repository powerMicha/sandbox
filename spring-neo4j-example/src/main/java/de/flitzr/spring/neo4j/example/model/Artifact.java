package de.flitzr.spring.neo4j.example.model;

import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

@NodeEntity
public class Artifact {

	private String artifactId;
	
	private String groupId;
	
	private String version;

	@RelatedTo(direction = Direction.OUTGOING)
	private Set<Artifact> dependencies;
	
	@PersistenceConstructor
	public Artifact(String groupId, String artifactId, String version) {
		super();
		this.artifactId = artifactId;
		this.groupId = groupId;
		this.version = version;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Set<Artifact> getDependencies() {
		return dependencies;
	}

	public void setDependencies(Set<Artifact> dependencies) {
		this.dependencies = dependencies;
	}
	
	
	
}
