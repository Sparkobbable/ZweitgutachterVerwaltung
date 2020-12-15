package controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

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
		BufferedWriter writer = null;
		CSVPrinter csvPrinter = null;
		try {
			writer = Files.newBufferedWriter(Paths.get(filename), Charset.forName("ISO-8859-1"));
			csvPrinter = new CSVPrinter(writer,
					CSVFormat.EXCEL.withHeader("Name, Vorname", "Studien-\n" + "gruppe", "Praxispartner",
							"Themenvorschlag Bachelor Thesis", "Dozent\n" + "1. Gutachten", "Dozent\n" + "2. Gutachten",
							"Bemerkung").withDelimiter(';'));

			CSVMapper.maptoCSV(csvPrinter, bachelorTheses);
			csvPrinter.flush();
			csvPrinter.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public Pair<List<Reviewer>, List<BachelorThesis>> load() {
		BufferedReader reader = null;
		CSVParser csvParser = null;
		try {
			reader = Files.newBufferedReader(Paths.get(filename), Charset.forName("ISO-8859-1"));
			String firstLine;
			do {
				reader.mark(2048);
				firstLine = reader.readLine();
				
			} while (firstLine.matches("([;]|\\s)*"));

			// go to the last marker before the correct line
			reader.reset();

			csvParser = new CSVParser(reader,
					CSVFormat.EXCEL.withFirstRecordAsHeader().withIgnoreEmptyLines(true).withDelimiter(';'));

			Pair<List<Reviewer>, List<BachelorThesis>> returnIn = CSVMapper.maptoModel(csvParser);

			csvParser.close();
			return returnIn;
		} catch (IOException e) {
			throw new RuntimeException(e);

		}

	}

}
