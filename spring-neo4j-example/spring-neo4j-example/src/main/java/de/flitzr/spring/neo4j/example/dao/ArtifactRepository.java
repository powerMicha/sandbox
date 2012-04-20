/**
 * 
 */
package de.flitzr.spring.neo4j.example.dao;

import org.springframework.data.neo4j.repository.GraphRepository;

import de.flitzr.spring.neo4j.example.model.Artifact;

/**
 * @author malbers
 *
 */
public interface ArtifactRepository extends GraphRepository<Artifact> {

}
