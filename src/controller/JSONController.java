package controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonValue.ValueType;
import javax.json.JsonWriter;

import model.Pair;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.persistence.PersistenceHandler;

public class JSONController implements PersistenceHandler {

	private String filename;

	/**
	 * Creates a JSONContoller for the file with the given name.
	 * 
	 * @param filename Name including path and format of the Json file.
	 */
	public JSONController(String filename) {
		this.filename = filename;
	}

	/**
	 * Saves all reviewers including every other Object from the current model in a
	 * Json file.
	 * 
	 * @param list ArrayList of current reviewers in the system.
	 */
	public void save(List<Reviewer> reviewers, List<BachelorThesis> bachelorTheses) {
		JsonWriter jsonWriter = null;
		try {
			jsonWriter = Json.createWriter(new FileWriter(filename));
			jsonWriter.writeObject(ObjectMapper.mapToJson(reviewers, bachelorTheses));
			System.out.println("Save-success");
			if (jsonWriter != null) {
				jsonWriter.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
//		} finally {
//			
		}
	}

	/**
	 * Loads all reviewers including every other Object from the Json file.
	 * 
	 * @throws Exception when the loaded Json file is not in the correct format
	 */
	public Pair<List<Reviewer>, List<BachelorThesis>> load() {
		JsonReader reader = null;
		try {
			reader = Json.createReader(new FileReader(filename));
			JsonValue value = reader.read();
			if (value.getValueType() != ValueType.OBJECT) {
				throw new RuntimeException("Ungültiges Json-Format");
			}
			JsonObject object = (JsonObject) value;
			List<Reviewer> reviewers = ObjectMapper.mapToReviewers(object.getJsonArray(ObjectMapper.REVIEWERS));
			List<BachelorThesis> bachelorTheses = ObjectMapper
					.mapToBachelorTheses(object.getJsonArray(ObjectMapper.BACHELOR_THESES), reviewers);
			System.out.println("Load-success");

			return Pair.createPair(reviewers, bachelorTheses);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

	}

}
