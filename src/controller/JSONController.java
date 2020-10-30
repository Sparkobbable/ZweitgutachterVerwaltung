package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;

import java.util.ArrayList;
import java.util.Map.Entry;

import model.data.Author;
import model.data.BachelorThesis;
import model.data.Review;
import model.data.Reviewer;
import util.ReviewStatus;


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
	 * @param reviewers ArrayList of current reviewers in the system.
	 */
	public void saveReviewers(ArrayList<Reviewer> reviewers) {
		JsonObjectBuilder builder = Json.createObjectBuilder();
		JsonArrayBuilder arr = Json.createArrayBuilder();
		for(Reviewer r : reviewers) {
			JsonObjectBuilder reviewerbuilder = Json.createObjectBuilder();
			reviewerbuilder.add("name", r.getName());
			
			JsonArrayBuilder arrb = Json.createArrayBuilder();
			for(BachelorThesis b : r.getSupervisedThesis()) {
				JsonObjectBuilder thesisbuilder = Json.createObjectBuilder();
				thesisbuilder.add("topic", b.getTopic());
				
				JsonObjectBuilder authorbuilder = Json.createObjectBuilder();
				authorbuilder.add("name", b.getAuthor().getName());
				authorbuilder.add("studyGroup", b.getAuthor().getStudyGroup());
				thesisbuilder.add("author", authorbuilder);
				
				JsonObjectBuilder firstreviewBuilder = Json.createObjectBuilder();
				firstreviewBuilder.add("reviewer", reviewerbuilder);
				firstreviewBuilder.add("status", b.getFirstReview().getStatus().getName());
				firstreviewBuilder.add("bachelorThesis", thesisbuilder);
				thesisbuilder.add("firstReview", firstreviewBuilder);
				
				JsonObjectBuilder secondreviewBuilder = Json.createObjectBuilder();
				secondreviewBuilder.add("reviewer", reviewerbuilder);
				secondreviewBuilder.add("status", b.getSecondReview().getStatus().getName());
				secondreviewBuilder.add("bachelorThesis", thesisbuilder);
				thesisbuilder.add("secondReview", secondreviewBuilder);
				arrb.add(thesisbuilder);
			}
			
			reviewerbuilder.add("supervised", arrb);
			reviewerbuilder.add("selected", r.isSelected());
			arr.add(reviewerbuilder);
		}
		builder.add("reviewers", arr);
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
				value = object.get("reviewers");
			}
			return createObjects(value);
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Erstelle aus dem Json Value alle Objekte
	 * @param value - Jsonvalue from the Json file
	 * @return ArrayList including all objects from the Json file
	 */
	private ArrayList<Reviewer> createObjects(JsonValue value) {
		ArrayList<Reviewer> result = new ArrayList<Reviewer>();
		JsonObject object = (JsonObject) value;
		for(Entry<String, JsonValue> set : object.entrySet())
		{
			JsonObject r = (JsonObject) set.getValue();
			
			Reviewer reviewer = new Reviewer(r.getString("name"));
			reviewer.setSelected(r.getBoolean("selected"));
			for(JsonValue tvalue : r.getJsonArray("supervised")) {
				JsonObject t = (JsonObject) tvalue;
				
				JsonObject a = t.getJsonObject("author");
				Author author = new Author(a.getString("name"), a.getString("studyGroup"));
				BachelorThesis thesis = new BachelorThesis(t.getString("topic"), author);
				
				JsonObject fR = t.getJsonObject("firstReview");
				Review firstReview = new Review(reviewer, true, ReviewStatus.valueOf(fR.getString("status")), thesis);
				thesis.setFirstReview(firstReview);
				
				JsonObject sR = t.getJsonObject("secondReview");
				Review secondReview = new Review(reviewer, false, ReviewStatus.valueOf(sR.getString("status")), thesis);
				reviewer.addBachelorThesis(thesis);
			}
			result.add(reviewer);
		}
		return result;
	}
}
