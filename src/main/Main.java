package main;

import java.util.List;

import controller.Controller;
import model.Model;
import model.domain.Author;
import model.domain.BachelorThesis;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import view.SwingView;
import view.View;

/**
 * Class that handles program startup, creates the Model, View and Container
 */
public class Main {

	public static void main(String[] args) {
		Model data = mockData();
		View view = new SwingView(data);
		new Controller(data, view);
		view.setVisible();
	}

	/**
	 * Creates a sample list of reviewers, bachelor theses, authors and reviews
	 * <p>
	 * TODO remove this before deploying to prod
	 */
	private static Model mockData() {
		String[] studyGroup = { "WI 60/19", "WI 61/19", "IG 58/19", "BW 52/19", "WI 43/18", "WI 42/17", "IG 68/20",
				"IG 67/20", "WI 63/20" };
		String[] topic = { "Simulation von Elektronenbahnen in Perkeo 2",
				"Marketingkonzeption für Gastronomiebetriebe unter besonderer Betrachtung der Kommunikationspolitik",
				"Welchen Einflus hat Product Placement auf die Rezipienten? Und kann sich die EInstellung zu einer Marke durch angewandtes Product Placement verändern?",
				"Computerbasierte Bearbeitung technischer Inhalte zur Effizienzsteigerung und Kommunikationsverbesserung in der Modellpflege in der Automobilindustrie",
				"Design und Umsetzung einer verlässlichen Internet-Infrastruktur auf Basis von lizenzfreiem Richtfunk",
				"Analyse und Optimierung der Usability des Apple iPhones mit Hilfe von Nutzertests",
				"Präzisierung des Nachhaltigkeitsbegriffes anhand messbarer Entropiezunahme - Formulierung produktbezogener Bewertungsansätze",
				"Empirische Usability Evualation von drei Nutzerführungsvarianten für ein Assistenzsystem zur technischen Dokumentation",
				"Evaluation von Optimierungsmaßnahmen bezüglich des Datencaches anhand von einfachen Algorithmen aus der Bildverarbeitung",
				"Nachhalitkeitsbewertung eines kfW-Effizienzhauses-40 der Firma E.L.F. Hallen- undMaschinenbau GmbH, unter besonderer Berüksichtigung des Klimaschutzes und entropischer Betrachtung",
				"Markenaufbau mithilfe von Netzwerken am Beispiel des Unternehmensbereichs ProVita der ORNAMIN Kunststoffwerke W. Zschetzsche GmbH & Co. KG",
				"Produktanalysen als Vorbereitung von Sortimentsbereinigungen am Beispiel ausgewählter Produktlinien der W. Neudorff GmbH KG",
				"Analyse und Optimierung des bestehenden Bewerbungsverfahrens für die Auswahl von Studenten am Beispiel der E.ON Avacon AG",
				"Marktanalyse des Windenergie-Marktes in Deutschland und Konzeptionierung eines vertrieblichen Betreuungskonzeptes für das Industrie und Key Account Management der Phoenix Contact Deutschland GmbH",
				"Chancen und Risiken der Bereitstellung negativer Minutenreserve durch nach dem EEG geförderte Anlagen und mögliche Auswirkungen auf die Übertragungsnetzbetreiber",
				"Konzept und Potentiale eines internationalen Key Account Managements für die Wilkhahn Wilkening + Hahne GmbH + Co. KG",
				"Messbarkeit und Erfolgsfaktoren der Marketingansätze im Rahmen des Ausbildungsmarketings am Beispiel des E.ON Konzerns",
				"Entwicklung eines Konzeptes zur Umsetzung der qualitativen Personalplanung unter besonderer Berücksichtigung der Rahmenbedingungen des E.ON Konzerns am Beispiel der E.ON Westfalen Weser AG",
				"Der konzeptionelle Entwurf zur Implementierung eines kennzahlenorientierten Bestandscontrollings in der konzerninternen Materialdisposition von Eigenerzeugnissenam Beispiel der WAGO Unternehmensgruppe - Werk Minden",
				"Analyse der Personalfluktuation nach der Ausbildungszeit sowie die Erstellung von Handlungsempfehlungen zur Mitarbeiterbindung bei der WAGO Kontakttechnik GmbH & Co. KG",
				"Entwicklung eines Konzepts zur Implementierung einer Balanced Scorecard im Bereich des strategischen Controlling unter Bezugnahme des Strategieprojekts Wilkhahn 2017dargelegt am Beispiel der Wilkhahn Wilkening + Hahne GmbH + Co. KG",
				"Optimierung und Ausweitung des bestehenden operativen Projektcontrollings der E.ON Kernkraft GmbH im Hinblick auf die besonderen Anforderungen von Kernkraftwerksneubauten",
				"Die Integration der Lieferantenbeurteilung bzw. -bewertung im Rahmen des Aufbaus und der Implementierung eines Lieferantenmanagementsystems am Beispiel der WeserGoldGetränkeindustrie GmbH & Co. KG",
				"Implementierung effizienter Beschaffungsprozesse von C-Teilen anhand des e-Procurement im Rahmen einer geplanten Einkaufszentralisierung der inländischenProduktionsstandorte am Beispiel der WeserGold GmbH & Co. KG",
				"OneE.ON als Konzept zur strategischen Unternehmensführung: Analyse und Handlungsempfehlungen für die E.ON Westfalen Weser AG",
				"Aufbau einer Vertriebskooperation in Kanada am Beispiel des Vertriebszweiges ProVita der ORNAMIN Kunststoffwerke W. Zschetzsche GmbH & Co. KG",
				"Analyse des Projektes E.ON Managed Print Services und Entwicklung von Handlungsempfehlungen für zukünftige Outsourcingaktivitäten" };
		String[] comment = { "", "Hallo", "Den mag ich besonders", "Der ist immer zu spät" };

		Author[] authors = new Author[30];
		for (int i = 0; i < authors.length; i++) {
			authors[i] = new Author(generateName(), randomFrom(studyGroup));
		}
		Reviewer[] reviewers = new Reviewer[20];
		for (int i = 0; i < reviewers.length; i++) {
			String firstName = randomFrom(firstname);
			String lastName = randomFrom(surname);
			reviewers[i] = new Reviewer(String.format("%s, %s", lastName, firstName), 10,
					String.format("%s.%s@hsw.de", firstName, lastName), randomFrom(comment));
		}

		BachelorThesis[] bts = new BachelorThesis[topic.length];
		for (int i = 0; i < bts.length; i++) {
			bts[i] = new BachelorThesis(topic[i], randomFrom(authors), randomFrom(reviewers));
		}

		for (BachelorThesis bt : bts) {
			if (Math.random() < 0.5) {
				continue;
			}
			Reviewer r2 = randomFrom(reviewers);
			if (r2 != bt.getFirstReview().getReviewer()) {
				SecondReview review = new SecondReview(r2, bt);
				bt.setSecondReview(review);
				r2.addSecondReview(review);
			}

		}
		return new Model(List.of(bts), List.of(reviewers));
	}

	private static String[] firstname = { "Fynn", "Luca", "Paul", "Lasse", "Nils", "Ben", "Sören", "Alexander", "Sven",
			"Finn", "Oliver", "Jan", "Thomas", "Elias", "Noah", "Lukas", "Johannes", "Levin", "Peer", "Sebastian",
			"Emma", "Lina", "Anna", "Marie", "Johanna", "Ella", "Charlotte", "Hanna", "Laura", "Sophie", "Pauline",
			"Luise", "Eva", "Katharina" };
	private static String[] surname = { "Müller", "Schmidt", "Schneider", "Fischer", "Meyer", "Weber", "Hofmann",
			"Wagner", "Becker", "Schulz", "Koch", "Klein", "Richter", "Schröder", "Lange", "Schmitt", "Krüger",
			"Schmitz", "Wolf" };

	private static String generateName() {
		return randomFrom(surname) + ", " + randomFrom(firstname);
	}

	private static <T> T randomFrom(T[] arr) {
		return arr[(int) (Math.random() * arr.length)];
	}
}
