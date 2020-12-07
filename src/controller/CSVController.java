package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import model.Pair;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.persistence.PersistenceHandler;

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
		BufferedReader reader = null;
		CSVParser csvParser = null;
		try {
			reader = Files.newBufferedReader(Paths.get(filename));
			String firstLine;
			do {
				reader.mark(2048);
				firstLine = reader.readLine();
				System.out.println(firstLine);
			} while (firstLine.matches("([;]|\s)*"));

			// go to the last marker before the correct line
			reader.reset();

			csvParser = new CSVParser(reader, CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreHeaderCase()
					.withIgnoreEmptyLines().withTrim().withDelimiter(';'));

			Pair<List<Reviewer>, List<BachelorThesis>> returnIn = CSVMapper.maptoModel(csvParser);

			csvParser.close();
			return returnIn;
		} catch (IOException e) {
			throw new RuntimeException(e);

		}

	}

}
