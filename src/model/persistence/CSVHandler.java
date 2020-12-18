package model.persistence;

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

import controller.CSVMapper;
import model.Pair;
import model.domain.BachelorThesis;
import model.domain.Reviewer;

/**
 * {@link PersistenceHandler} that handles CSV persistance.
 * <p>
 * Can be used to save and load data in or from CSV files.
 */
public class CSVHandler implements PersistenceHandler {

	private String filename;

	/**
	 * Creates a CSVContoller for the file with the given name.
	 * 
	 * @param filename Name including path and format of the CSV file.
	 */
	public CSVHandler(String filename) {
		this.filename = filename;
	}

	/**
	 * Saves attributes from the model in a CSV file
	 * 
	 * 
	 * @param reviewers      ArrayList of current reviewers and bachelorThesises to
	 *                       be saved.
	 * @param bachelorTheses ArrayList of current bachelorTheses and
	 *                       bachelorThesises to be saved.
	 */
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

	/**
	 * Load attributs from the model in a CSV file
	 * 
	 * 
	 * @throws Exception when the loaded CSV file is not in the correct format
	 */
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
