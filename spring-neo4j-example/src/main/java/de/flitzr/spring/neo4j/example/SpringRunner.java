/**
 * 
 */
package de.flitzr.spring.neo4j.example;

import de.flitzr.spring.neo4j.example.dao.ArtifactRepository;
import de.flitzr.spring.neo4j.example.model.Artifact;
import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.neo4j.conversion.Result;
import org.springframework.data.neo4j.core.GraphDatabase;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import scala.Console;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author malbers
 *
 */
public class SpringRunner{


    private static GraphDatabaseService database;


	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
		SpringExample example = context.getBean(SpringExample.class);
        database = example.getDatabaseService();
        example.run();

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
	}


    /**
     * Execute some query string
     *
     * @param queryString
     */
    private static void executeQuery(String queryString) {

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

}
