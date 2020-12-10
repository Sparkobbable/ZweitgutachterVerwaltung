package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;

import model.Pair;
import model.domain.Author;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;

public class CSVMapper {
	public static Pair<List<Reviewer>, List<BachelorThesis>> maptoModel(CSVParser csvParser) {
		List<BachelorThesis> bachelorThesis = new ArrayList<BachelorThesis>();
		List<Reviewer> listreviewer = new ArrayList<Reviewer>();
		List<Author> listauthor = new ArrayList<>();
		csvParser.getHeaderNames().forEach(s -> System.out.println(s + "\n"));
		for (CSVRecord csvRecord : csvParser) {

			Pair<List<Reviewer>, List<BachelorThesis>> returnIn = createObjekts(csvRecord, bachelorThesis, listreviewer,
					listauthor);
		}
		return Pair.of(listreviewer, bachelorThesis);
	}

	private static Pair<List<Reviewer>, List<BachelorThesis>> createObjekts(CSVRecord csvRecord,
			List<BachelorThesis> bachelorThesis, List<Reviewer> listreviewer, List<Author> listauthor) {
		String name = csvRecord.get("Name, Vorname");
		String studyGroup = csvRecord.get("Studien-\n" + "gruppe");
		String company = csvRecord.get("Praxispartner");
		String topic = csvRecord.get("Themenvorschlag Bachelor Thesis");
		String firstreviewer = csvRecord.get("Dozent\n" + "1. Gutachten");
		String secondreviewer = csvRecord.get("Dozent\n" + "2. Gutachten");
		String commant = csvRecord.get("Bemerkung");

// wenn die Header nicht so  wichtig sind und wir davon ausgehen, das die Datei im richtigen Format ist
//		String name = csvRecord.get(0);
//		String studyGroup = csvRecord.get(1);
//		String company = csvRecord.get(2);
//		String topic = csvRecord.get(3);
//		String firstreviewer = csvRecord.get(4);
//		String secondreviewer = csvRecord.get(5);
//		String commant = csvRecord.get(6);

		System.out.println(name + firstreviewer + secondreviewer);
		if (name == null || name.isBlank()) {
			return Pair.of(listreviewer, bachelorThesis);
		}
		Reviewer reviewer = searchReviewer(firstreviewer, listreviewer);

		Author author = searchAuthor(name, studyGroup, company, listauthor);

		BachelorThesis bThesis = new BachelorThesis(topic, author, reviewer, commant);

		bachelorThesis.add(bThesis);

		if (secondreviewer != null && !secondreviewer.isBlank()) {
			Reviewer sereviewer = searchReviewer(secondreviewer, listreviewer);
			SecondReview seReview = new SecondReview(sereviewer, bThesis);
			bThesis.setSecondReview(seReview);

		}
		return Pair.of(listreviewer, bachelorThesis);

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

	public static void maptoCSV(CSVPrinter csvPrinter, List<BachelorThesis> bachelorTheses) {

		for (BachelorThesis bachelorThesis : bachelorTheses) {

			createObjekts(csvPrinter, bachelorThesis);
		}

	}

	private static void createObjekts(CSVPrinter csvPrinter, BachelorThesis bachelorThesis) {
		String topic = bachelorThesis.getTopic();
		String commant = bachelorThesis.getComment();
		String name = bachelorThesis.getAuthor().getName();
		String studyGroup = bachelorThesis.getAuthor().getStudyGroup();
		String company = bachelorThesis.getAuthor().getCompany();
		String firstreviewer = bachelorThesis.getFirstReview().getReviewer().getName();
		String secondreviewer = bachelorThesis.getSecondReview().get().getReviewer().getName();

		try {
			csvPrinter.printRecord(name, studyGroup, company, topic, firstreviewer, secondreviewer, commant);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
