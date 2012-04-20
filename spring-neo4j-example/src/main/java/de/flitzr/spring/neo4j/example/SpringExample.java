/**
 * 
 */
package de.flitzr.spring.neo4j.example;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import de.flitzr.spring.neo4j.example.dao.ArtifactRepository;
import de.flitzr.spring.neo4j.example.model.Artifact;

/**
 * @author malbers
 *
 */
@Component
public class SpringExample implements Runnable {

	@Autowired
	private ArtifactRepository artifactDao;
	
	@Transactional
	public void run() {		
		Artifact springData = new Artifact("org.springframework.data", "spring-data", "2.0.1.RELEASE");
		artifactDao.save(springData);
		
		Artifact springBeans = new Artifact("org.springframework", "spring-beans", "3.0.7.RELEASE");
		artifactDao.save(springBeans);
		
		Artifact springTx= new Artifact("org.springframework", "spring-tx", "3.0.7.RELEASE");
		artifactDao.save(springTx);
		
		Set<Artifact> dependencies = new HashSet<Artifact>();
		springData.setDependencies(dependencies);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		context.getBean(SpringExample.class).run();
	}

}
