package server;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.rest.graphdb.RestGraphDatabase;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;

public class GraphDataBase {
	private static GraphDatabaseService graphDb;
	private static RestCypherQueryEngine engine;
	private static GraphDataBase instance = null;
	static final String DB_PATH = "http://taubacademy.cloudapp.net:7474/db/data";

	private GraphDataBase() {
		graphDb = new RestGraphDatabase(DB_PATH);
		registerShutdownHook(graphDb);
	}

	public static GraphDatabaseService getInstance() {
		if (instance == null) {
			instance = new GraphDataBase();
			engine = new RestCypherQueryEngine(
					((RestGraphDatabase) graphDb).getRestAPI());
			return graphDb;
		}
		return graphDb;
	}

	public static RestCypherQueryEngine getEngine() {
		if (instance == null) {
			instance = new GraphDataBase();
			engine = new RestCypherQueryEngine(
					((RestGraphDatabase) graphDb).getRestAPI());
			return engine;
		}
		return engine;
	}

	public GraphDatabaseService getGraphDb() {
		return graphDb;
	}

	public static void ResetDataBase() {
		if (instance == null) {
			return;
		}
		@SuppressWarnings("deprecation")
		Iterable<Node> nodes = graphDb.getAllNodes();
		for (Node node : nodes) {
			for (Relationship rel : node.getRelationships()) {
				rel.delete();
			}
			node.delete();
		}
	}

	private static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	public static void shutDown() {
		System.out.println("Shutting down database ...");
		graphDb.shutdown();
		instance = null;
	}
}