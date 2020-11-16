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


}
