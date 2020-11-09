package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;

import java.util.ArrayList;
import java.util.List;
import model.data.Author;
import model.data.BachelorThesis;
import model.data.Review;
import model.data.Reviewer;
import model.enums.ReviewStatus;


public class JSONController {

	private String filename;
	
	/**
	 * Creates a JSONContoller for the file with the given name.
	 * @param filename Name including path and format of the Json file.
	 */
	public JSONController(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Saves all reviewers including every other Object from the current model in a Json file.
	 * @param list ArrayList of current reviewers in the system.
	 */
	public void saveReviewers(List<Reviewer> list) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder reviewers = Json.createArrayBuilder();
		for(Reviewer r : list) {
			JsonObjectBuilder reviewerbuilder = Json.createObjectBuilder();
			
			JsonArrayBuilder supervised = Json.createArrayBuilder();
			for(BachelorThesis b : r.getSupervisedThesis()) {
				JsonObjectBuilder thesisbuilder = Json.createObjectBuilder();
				
				JsonObjectBuilder authorbuilder = Json.createObjectBuilder();
				authorbuilder.add("name", b.getAuthor().getName());
				authorbuilder.add("studyGroup", b.getAuthor().getStudyGroup());
				
				JsonObjectBuilder firstreviewBuilder = Json.createObjectBuilder();
				firstreviewBuilder.add("reviewer", r.getName());
				b.getFirstReview().ifPresent(review -> firstreviewBuilder.add("status", review.getStatus().getName()));;
				firstreviewBuilder.add("bachelorThesis", thesisbuilder);
				
				JsonObjectBuilder secondreviewBuilder = Json.createObjectBuilder();
				secondreviewBuilder.add("reviewer", r.getName());
				b.getSecondReview().ifPresent(review -> secondreviewBuilder.add("status", review.getStatus().getName()));
				secondreviewBuilder.add("bachelorThesis", thesisbuilder);
				
				thesisbuilder.add("topic", b.getTopic());
				thesisbuilder.add("author", authorbuilder);
				thesisbuilder.add("firstReview", firstreviewBuilder);
				thesisbuilder.add("secondReview", secondreviewBuilder);
				supervised.add(thesisbuilder);
			}
			
			reviewerbuilder.add("name", r.getName());
			reviewerbuilder.add("supervised", supervised);
			reviewers.add(reviewerbuilder);
		}
		builder.add("reviewers", reviewers);
		JsonObject jo = builder.build();
		try {
			FileWriter fw = new FileWriter(filename);
			JsonWriter jsonWriter = Json.createWriter(fw);
			jsonWriter.writeObject(jo);
			jsonWriter.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads all reviewers including every other Object from the Json file.
	 * @return ArrayList including all objects from the Json file
	 */
	public ArrayList<Reviewer> loadReviewers() {
		FileReader fr;
		JsonStructure struct;
		try {
			fr = new FileReader(filename);
			JsonReader reader = Json.createReader(fr);
			struct = reader.read();
			JsonValue value = struct;
			reader.close();
			fr.close();
			
			if(value.getValueType() == ValueType.OBJECT) {
				JsonObject object = (JsonObject) value;
				return createObjects(object.getJsonArray("reviewers"));
			} else {
				System.out.println("Error: Die geladene Json Datei beinhaltet keinen Systemstatus");
				return new ArrayList<Reviewer>();
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return new ArrayList<Reviewer>();
	}
	
	/**
	 * Erstelle aus dem Json Value alle Objekte
	 * @param value - Jsonvalue from the Json file
	 * @return ArrayList including all objects from the Json file
	 */
	private ArrayList<Reviewer> createObjects(JsonArray reviewers) {
		ArrayList<Reviewer> result = new ArrayList<Reviewer>();
		for(JsonValue set : reviewers)
		{
			JsonObject jReviewer = set.asJsonObject();
			
			Reviewer reviewer = new Reviewer(jReviewer.getString("name"));
			for(JsonValue supervised : jReviewer.getJsonArray("supervised")) {
				JsonObject jThesis = supervised.asJsonObject();
				
				JsonObject jAuthor = jThesis.getJsonObject("author");
				Author author = new Author(jAuthor.getString("name"), jAuthor.getString("studyGroup"));
				
				BachelorThesis thesis = new BachelorThesis(jThesis.getString("topic"), author);
				
				JsonObject jFirstReview = jThesis.getJsonObject("firstReview");
				Review firstReview = new Review(reviewer, true, ReviewStatus.valueOf(jFirstReview.getString("status")), thesis);
				thesis.setFirstReview(firstReview);
				
				JsonObject jSecondReview = jThesis.getJsonObject("secondReview");
				Review secondReview = new Review(reviewer, false, ReviewStatus.valueOf(jSecondReview.getString("status")), thesis);
				thesis.setSecondReview(secondReview);
				reviewer.addBachelorThesis(thesis);
			}
			result.add(reviewer);
		}
		return result;
	}
}
