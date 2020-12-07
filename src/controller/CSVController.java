package controller;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonValue.ValueType;

import model.Pair;
import model.domain.Author;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.persistence.PersistenceHandler;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

//TODO javadoc
public class CSVController implements PersistenceHandler {

	private String filename;

	public CSVController(String filename) {
		this.filename = filename;
	}

	public void save(List<Reviewer> reviewers, List<BachelorThesis> bachelorTheses) {
//		JsonWriter jsonWriter = null;
//		try {
//			jsonWriter = Json.createWriter(new FileWriter(filename));
//			jsonWriter.writeObject(CSVMapper.mapToCSV(reviewers, bachelorTheses));
//			if (jsonWriter != null) {
//				jsonWriter.close();
//			}
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}
	}

	public Pair<List<Reviewer>, List<BachelorThesis>> load() {
		Reader reader = null;
		CSVParser csvParser = null;
		try {
			reader = Files.newBufferedReader(Paths.get(filename));
			csvParser = new CSVParser(reader,
					CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim().withDelimiter(';'));
			List<BachelorThesis> bachelorThesis = new ArrayList<BachelorThesis>();
			List<Reviewer> listreviewer = new ArrayList<Reviewer>();

			for (CSVRecord csvRecord : csvParser) {
				String name = csvRecord.get("Name");
				String studyGroup = csvRecord.get("Studien-gruppe");
				String company = csvRecord.get("Praxispartner");
				String topic = csvRecord.get("Themenvorschlag Bachelor Thesis");
				String firstreviewer = csvRecord.get("Dozent 1. Gutachten");
				String secondreviewer = csvRecord.get("Dozent 2. Gutachten");
				String commant = csvRecord.get("Bemerkung");

				Reviewer reviewer = new Reviewer(firstreviewer, 10);
				Reviewer sereviewer = new Reviewer(secondreviewer, 10);
				Author author = new Author(name, studyGroup, company);
				BachelorThesis bThesis = new BachelorThesis(topic, author, reviewer, commant);
				SecondReview seReview = new SecondReview(sereviewer, bThesis);
				bThesis.setSecondReview(seReview);

				bachelorThesis.add(bThesis);
				listreviewer.add(reviewer);
				listreviewer.add(sereviewer);

			}
			
			csvParser.close();
			return Pair.of(listreviewer, bachelorThesis);
		} catch (IOException e) {
			throw new RuntimeException(e);

		}

	}

}
