package model.persistence;

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

import controller.JSONMapper;
import model.Pair;
import model.domain.BachelorThesis;
import model.domain.Reviewer;

/**
 * {@link PersistenceHandler} that handles JSON persistance.
 * <p>
 * Can be used to save and load Lists of Reviewers and BachelorTheses in or from
 * JSON files.
 *
 */
public class JSONHandler implements PersistenceHandler {

	private String filename;

	/**
	 * Creates a JSONContoller for the file with the given name.
	 * 
	 * @param filename Name including path and format of the Json file.
	 */
	public JSONHandler(String filename) {
		this.filename = filename;
	}

	/**
	 * Saves all reviewers including every other Object from the current model in a
	 * JSON file.
	 * 
	 * @param reviewers<List<Reviewer>> ArrayList of current {@link Reviewer} in the system
	 * @param bachelorThesis<List<BachelorThesis>> ArrayList of current {@link BachelorThesis} in the system
	 */
	public void save(List<Reviewer> reviewers, List<BachelorThesis> bachelorTheses) {
		JsonWriter jsonWriter = null;
		try {
			jsonWriter = Json.createWriter(new FileWriter(filename));
			jsonWriter.writeObject(JSONMapper.mapToJson(reviewers, bachelorTheses));
			if (jsonWriter != null) {
				jsonWriter.close();
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Loads all reviewers including every other Object from the Json file.
	 * 
	 * @return data<Pair<List<Reviewer>, List<BachelorThesis>>
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
			List<Reviewer> reviewers = JSONMapper.mapToReviewers(object.getJsonArray(JSONMapper.REVIEWERS));
			List<BachelorThesis> bachelorTheses = JSONMapper
					.mapToBachelorTheses(object.getJsonArray(JSONMapper.BACHELOR_THESES), reviewers);

			return Pair.of(reviewers, bachelorTheses);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (reader != null) {
				reader.close();
			}
		}

	}

}
