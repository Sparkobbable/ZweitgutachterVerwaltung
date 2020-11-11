package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;

import model.data.Author;
import model.data.BachelorThesis;
import model.data.Review;
import model.data.Reviewer;
import model.enums.ReviewStatus;

public class CSVController {
private String filename;
 
public CSVController(String filename) {
	this.filename = filename;
}
public ArrayList<Reviewer> loadcsvImport() throws Exception {
	FileReader fr;
	JsonStructure struct;
	try {
		fr = new FileReader(filename);
		/*
		 * JsonReader reader = Json.createReader(fr); struct = reader.read(); JsonValue
		 * value = struct; reader.close(); fr.close();
		 * 
		 * if(value.getValueType() == ValueType.OBJECT) { JsonObject object =
		 * (JsonObject) value; return createObjects(object.getJsonArray("reviewers")); }
		 * else { throw new
		 * Exception("Die geladene Json Datei beinhaltet keinen Systemstatus"); }
		 */
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
			
			try {
				JsonObject jFirstReview = jThesis.getJsonObject("firstReview");
				Review firstReview = new Review(reviewer, true, ReviewStatus.valueOf(jFirstReview.getString("status")), thesis);
				thesis.setFirstReview(firstReview);
			} catch(NullPointerException e) {
				
			}
			
			try {
				JsonObject jSecondReview = jThesis.getJsonObject("secondReview");
				Review secondReview = new Review(reviewer, false, ReviewStatus.valueOf(jSecondReview.getString("status")), thesis);
				thesis.setSecondReview(secondReview);
			} catch(NullPointerException e) {
				
			}
			reviewer.addBachelorThesis(thesis);
		}
		result.add(reviewer);
	}
	return result;
}
}
