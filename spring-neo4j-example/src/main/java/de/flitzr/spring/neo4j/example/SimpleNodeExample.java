/**
 * 
 */
package de.flitzr.spring.neo4j.example;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.*;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import scala.Console;

import java.io.File;
import java.util.Iterator;

/**
 * Create nodes in Neo4j (without spring-data)
 * 
 * @author malbers
 *
 */
public class SimpleNodeExample implements Runnable {
	
	private final RelationshipType DEPENDS_ON = DynamicRelationshipType.withName("dependsOn");
	
	private GraphDatabaseService database;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new SimpleNodeExample().run();
	}
	
	/**
	 * Basic application loop
	 * 
	 */
	public void run() {
		initDB();
		createNodes();
		
		// wait for input
		String query;
		while(true){
			query = Console.readLine();
			if("exit".equals(query)){
				break;
			} else {
				executeQuery(query);
			}
		}
		
		cleanup();
	}
		
	/**
	 * Create some nodes
	 */
	private void createNodes() {
		Transaction tx = database.beginTx();
		
		try{
			Node springData = database.createNode();
			springData.setProperty("groupId", "org.springframework.data");
			springData.setProperty("artifactId", "spring-data");
			springData.setProperty("version", "2.0.1.RELEASE");
			
			Node springBeans = database.createNode();
			springBeans.setProperty("groupId", "org.springframework");
			springBeans.setProperty("artifactId", "spring-beans");
			springBeans.setProperty("version", "3.0.7.RELEASE");
			
			Node springTx = database.createNode();
			springTx.setProperty("groupId", "org.springframework");
			springTx.setProperty("artifactId", "spring-tx");
			springTx.setProperty("version", "3.0.7.RELEASE");
			
			springData.createRelationshipTo(springBeans, DEPENDS_ON);
			springData.createRelationshipTo(springTx, DEPENDS_ON);
	
			tx.success();
		} finally {
			tx.finish();
		}
	}
	
	/**
	 * Execute some query string
	 * 
	 * @param queryString
	 */
	private void executeQuery(String queryString) {
		
		if(queryString == null || queryString.isEmpty()){
			return;
		}
		
		ExecutionEngine engine = new ExecutionEngine(database);
		
		// for detailed output see: http://docs.neo4j.org/chunked/stable/tutorials-cypher-java.html
		try{
			ExecutionResult result = engine.execute(queryString);
			Console.out().println(result.dumpToString());
		} catch (Exception exp) {
			Console.err().println(exp.getMessage());
		}
		
	}
	

	private void initDB() {
		String tmpDir = System.getProperty("java.io.tmpdir");
		File storeDir = new File(tmpDir, "spring-neo4j-example");
		database = new EmbeddedGraphDatabase(storeDir.getAbsolutePath());
	}
	
	private void cleanup() {
		// remove all nodes
		Transaction tx = database.beginTx();
		try{
			Iterator<Node> nodes = database.getAllNodes().iterator();
			while(nodes.hasNext()){
				Node node = nodes.next();
				Iterator<Relationship> relationships = node.getRelationships().iterator();
				while(relationships.hasNext()){
					Relationship relationship = relationships.next();
					relationship.delete();
				}
				node.delete();
			}
			tx.success();
		} finally {
			tx.finish();
		}
		
		database.shutdown();
	}




}
