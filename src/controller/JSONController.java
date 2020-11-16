package controller;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonStructure;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;

import java.util.List;

import model.Model;
import model.data.Reviewer;


public class JSONController {

	private String filename;
	private ObjectMapper mapper;
	
	/**
	 * Creates a JSONContoller for the file with the given name.
	 * @param filename Name including path and format of the Json file.
	 */
	public JSONController(String filename, Model model) {
		this.filename = filename;
		this.mapper = new ObjectMapper(model);
	}
	
	/**
	 * Saves all reviewers including every other Object from the current model in a Json file.
	 * @param list ArrayList of current reviewers in the system.
	 */
	public void saveReviewers(List<Reviewer> list) {
		try {
			FileWriter fw = new FileWriter(filename);
			JsonWriter jsonWriter = Json.createWriter(fw);
			jsonWriter.writeObject(mapper.createJsonFromObject(list));
			jsonWriter.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads all reviewers including every other Object from the Json file.
	 * 
	 * @throws Exception when the loaded Json file is not in the correct format
	 */
	public void loadReviewers() throws Exception {
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
				mapper.createObjectsFromJson(object.getJsonArray("reviewers"));
			} else {
				throw new Exception("Die geladene Json Datei beinhaltet keinen Systemstatus");
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
