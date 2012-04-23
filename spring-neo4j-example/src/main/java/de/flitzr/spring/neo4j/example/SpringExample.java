/**
 * 
 */
package de.flitzr.spring.neo4j.example;

import de.flitzr.spring.neo4j.example.dao.ArtifactRepository;
import de.flitzr.spring.neo4j.example.model.Artifact;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author malbers
 *
 */
@Component
public class SpringExample implements Runnable {

    @Autowired
    private Neo4jTemplate template;

	@Autowired
	private ArtifactRepository artifactDao;

    @Transactional
	public void run() {
        cleanUp();
        setupDb();
        find();
	}

    @Transactional
    void setupDb() {
        Artifact springData = new Artifact("org.springframework.data", "spring-data", "2.0.1.RELEASE");
        Artifact springBeans = new Artifact("org.springframework", "spring-beans", "3.0.7.RELEASE");
        Artifact springTx= new Artifact("org.springframework", "spring-tx", "3.0.7.RELEASE");

        Set<Artifact> dependencies = new HashSet<Artifact>();
        dependencies.add(springBeans);
        dependencies.add(springTx);
        springData.setDependencies(dependencies);
        artifactDao.save(springData);
    }

    @Transactional
    void cleanUp() {
        // cleanup
        for(Artifact toDelete : artifactDao.findAll()){
            artifactDao.delete(toDelete);
        }
    }

    @Transactional(readOnly = true)
    void find(){
        Artifact artifact = artifactDao.findByPropertyValue("artifactId", "spring-data");
        print(artifact, null);
    }


    @Transactional(readOnly = true)
    void print(Artifact artifact, String prefix){
        if(prefix == null){
            prefix = "";
        }
        System.out.println(prefix + artifact.getGroupId() + ":" + artifact.getArtifactId() + ":" + artifact.getVersion());
        if(artifact.getDependencies() != null){
            for(Artifact dependency : artifact.getDependencies()){
                print(dependency, prefix + "|-");
            }
        }

    }

    public GraphDatabaseService getDatabaseService(){
        return template.getGraphDatabaseService();
    }

}
