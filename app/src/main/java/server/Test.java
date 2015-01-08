package server;

import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;

import java.util.HashMap;
import java.util.Map;

public class Test {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		RestCypherQueryEngine q = GraphDataBase.getEngine();
		Map<String, Object> params = new HashMap<String,Object>();
		params.put("node", new Course());
		for(Node x : GraphDataBase.getInstance().getAllNodes())
		{
			System.out.println(x);
		}
		/*
		 * for(Map<String,Object> map : tmpResult) { Node node =
		 * (Node)map.get("u"); System.out.println(node.getProperty("name")); }
		 */
	}

}
