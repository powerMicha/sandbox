/**
 * 
 */
package de.flitzr.spring.neo4j.example.dao;

import de.flitzr.spring.neo4j.example.model.Artifact;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

/**
 * @author malbers
 *
 */
public interface ArtifactRepository extends GraphRepository<Artifact> {

    @Query("START dependency=node({0}) MATCH artifact-[:dependsOn]-> dependency RETURN artifact")
    Iterable<Artifact> getDependentBy(Artifact artifact);

}
