package server;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;

class Rate implements RelationshipType {
	public int rate;
	public int courseNumber;

	@Override
	public String name() {
		return "Rate";
	}
};

enum Teach implements RelationshipType {
	Teach
};

public class Tutor {
	//final static Label TutorLabel = DynamicLabel.label("Tutor");
	public String name;
	public Byte[] image;
	public Integer SalaryPerHour;
	public Double Rating;
	public Integer NumOfTutorRated;
	public Long nodeId;

	public Tutor() {
		name = "";
		image = new Byte[1];
		image[0] = 0;
		SalaryPerHour = 0;
		Rating = (double) 0;
		NumOfTutorRated = 0;
		nodeId = (long) 0;
	}

	public void fillNode(Node node) {
		nodeId = node.getId();
		node.setProperty("name", name);
		node.setProperty("image", image);
		node.setProperty("SalaryPerHour", SalaryPerHour);
		node.setProperty("Rating", Rating);
		node.setProperty("NumOfTutorRated", NumOfTutorRated);
	}
	public String toString() {
		return name;
	}
}
