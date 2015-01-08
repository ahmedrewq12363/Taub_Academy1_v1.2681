package server;
import org.neo4j.graphdb.Node;
import org.neo4j.rest.graphdb.query.RestCypherQueryEngine;
import org.neo4j.rest.graphdb.util.QueryResult;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Course {
	//final static Label CourseLabel = DynamicLabel.label("Course");
	public String name;
	public Long nodeId;
	public Integer courseId;
	public Course()
	{
		name = "";
		nodeId = (long) -1;
		courseId = 0;
	}
	public void fillNode(Node node)
	{
		nodeId = node.getId();
		node.setProperty("name", name);
		node.setProperty("courseId", courseId);
	}
    static public ArrayList<Tutor> getTutorOfCourse(String Course)
    {
        RestCypherQueryEngine engine = GraphDataBase.getEngine();
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("courseId",Integer.parseInt(Course));
        QueryResult<Map<String,Object>> result = engine.query("match (u:Tutor-[r]->c1:Course{courseId={courseid}) return u",params);
        ArrayList<Tutor> tutors = new ArrayList<Tutor>();
        for(Map<String,Object> map : result)
        {
            Tutor t = new Tutor();
            t.fillNode((Node)map.get("u"));
            tutors.add(t);
        }
        return tutors;
    }
}

