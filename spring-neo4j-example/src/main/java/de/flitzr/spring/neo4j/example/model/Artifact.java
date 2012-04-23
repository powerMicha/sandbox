package de.flitzr.spring.neo4j.example.model;

import org.neo4j.graphdb.Direction;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.neo4j.annotation.*;

import java.util.Set;

@NodeEntity
public class Artifact {

    @GraphId
    private Long id;

    @Indexed
	private String artifactId;

	private String groupId;

	private String version;

	@RelatedTo(direction = Direction.OUTGOING, type = "dependsOn", elementClass = Artifact.class)
	private Set<Artifact> dependencies;

    public Artifact(){
        super();
    }

	public Artifact(String groupId, String artifactId, String version) {
		super();
		this.artifactId = artifactId;
		this.groupId = groupId;
		this.version = version;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
