package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static util.UmlautFormatter.format;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import model.Pair;
import model.domain.Author;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;

/**
 * Maps CSV imputs to domain model objects.
 *
 */
public class CSVMapper {

	/**
	 * Map the data of the csv file to the model
	 * 
	 * @param csvParser map the csv file to Strings
	 * @return a Pair with lists of reviewers and bachelortheses
	 */
	public static Pair<List<Reviewer>, List<BachelorThesis>> maptoModel(CSVParser csvParser) {
		List<BachelorThesis> bachelorThesis = new ArrayList<BachelorThesis>();
		List<Reviewer> listreviewer = new ArrayList<Reviewer>();
		List<Author> listauthor = new ArrayList<>();
		for (CSVRecord csvRecord : csvParser) {
			appendObjectsFromRecord(csvRecord, bachelorThesis, listreviewer, listauthor);
		}
		return Pair.of(listreviewer, bachelorThesis);
	}

	private static void appendObjectsFromRecord(CSVRecord csvRecord, List<BachelorThesis> bachelorThesis,
			List<Reviewer> listreviewer, List<Author> listauthor) {
		String name = format(csvRecord.get("Name, Vorname"));
		String studyGroup = format(csvRecord.get("Studien-\n" + "gruppe"));
		String company = format(csvRecord.get("Praxispartner"));
		String topic = format(csvRecord.get("Themenvorschlag Bachelor Thesis"));
		String firstreviewer = format(csvRecord.get("Dozent\n" + "1. Gutachten"));
		String secondreviewer = format(csvRecord.get("Dozent\n" + "2. Gutachten"));
		String commant = format(csvRecord.get("Bemerkung"));

		if (name == null || name.isBlank()) {
			return;
		}
		Reviewer reviewer = searchReviewer(firstreviewer, listreviewer);

		Author author = searchAuthor(name, studyGroup, company, listauthor);

		BachelorThesis bThesis = new BachelorThesis(topic, author, reviewer, commant);

		bachelorThesis.add(bThesis);

		if (secondreviewer != null && !secondreviewer.isBlank()) {
			Reviewer sereviewer = searchReviewer(secondreviewer, listreviewer);
			SecondReview seReview = new SecondReview(sereviewer, bThesis);
			bThesis.setSecondReview(seReview);
			sereviewer.addSecondReview(seReview);

		}

	}

	private static Author searchAuthor(String name, String studyGroup, String company, List<Author> listauthor) {
		Optional<Author> optional = listauthor.stream().filter(author -> Objects.equals(author.getName(), name))
				.filter(author -> Objects.equals(author.getStudyGroup(), studyGroup))
				.filter(author -> Objects.equals(author.getCompany(), company)).findAny();

		if (optional.isPresent()) {
			return optional.get();
		}
		Author author = new Author(name, studyGroup, company);
		listauthor.add(author);
		return author;
	}

	private static Reviewer searchReviewer(String name, List<Reviewer> listreviewer) {
		Optional<Reviewer> optional = listreviewer.stream().filter(reviewer -> reviewer.getName().equals(name))
				.findAny();

		if (optional.isPresent()) {
			return optional.get();
		}
		Reviewer reviewer = new Reviewer(name, 10);
		listreviewer.add(reviewer);
		return reviewer;
	}

	/**
	 * gets all bachelorTheses from the model and map these and other attributes to
	 * a csv file
	 * 
	 * @param csvPrinter     writes to csv file
	 * @param bachelorTheses list of all bachlorthesis from the model
	 */
	public static void maptoCSV(CSVPrinter csvPrinter, List<BachelorThesis> bachelorTheses) {

		for (BachelorThesis bachelorThesis : bachelorTheses) {

			createObjekts(csvPrinter, bachelorThesis);
		}

	}

	private static void createObjekts(CSVPrinter csvPrinter, BachelorThesis bachelorThesis) {

		String commant;
		if (bachelorThesis.getComment() == null) {
			commant = "";
		} else {
			commant = bachelorThesis.getComment();
		}
		String topic = bachelorThesis.getTopic();
		String name = bachelorThesis.getAuthor().getName();
		String studyGroup = bachelorThesis.getAuthor().getStudyGroup();
		String company = bachelorThesis.getAuthor().getCompany();
		String firstreviewer = bachelorThesis.getFirstReview().getReviewer().getName();
		String secondreviewer = bachelorThesis.getSecondReview().map(review -> review.getReviewer().getName())
				.orElse("");

		try {
			csvPrinter.printRecord(name, studyGroup, company, topic, firstreviewer, secondreviewer, commant);
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
