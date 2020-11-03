package main;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Model;
import model.data.Author;
import model.data.BachelorThesis;
import model.data.Review;
import model.data.Reviewer;
import util.ReviewStatus;
import view.View;

public class Main {

	//TODO move to main.Main
		public static void main(String[] args) {
			Model data = mockReviewerList();
			View view = new View(data);
			new Controller(data, view);
			view.setVisible();
		}


	/**
	 * Creates a sample list of reviewers, bachelor theses, authors and reviews
	 * <p>

	 * TODO remove this before deploying to prod -> why? testdata would be cool for presentation
	 * TODO put this back into Controller when this gets a Model of all Data
	 */
	private static Model mockReviewerList() {
		List<Reviewer> reviewerList = new ArrayList<>();
		String[] firstname = {"Fynn", "Luca", "Paul", "Lasse", "Nils", "Ben", "Sören", "Alexander", "Sven", "Finn", "Oliver", "Jan", "Thomas", "Elias", "Noah", "Lukas", "Johannes", "Levin", "Peer", "Sebastian", "Emma", "Lina", "Anna", "Marie", "Johanna", "Ella", "Charlotte", "Hanna", "Laura", "Sophie", "Pauline", "Luise", "Eva", "Katharina"};
		String[] surname = {"Müller", "Schmidt", "Schneider", "Fischer", "Meyer", "Weber", "Hofmann", "Wagner", "Becker", "Schulz", "Koch", "Klein", "Richter", "Schröder", "Lange", "Schmitt", "Krüger", "Schmitz", "Wolf"};
		String[] studyGroup = {"WI 60/19", "WI 61/19", "IG 58/19", "BW 52/19", "WI 43/18", "WI 42/17", "IG 68/20", "IG 67/20", "WI 63/20"};
		String[] topic = {"Simulation von Elektronenbahnen in Perkeo 2",
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
				"Analyse des Projektes E.ON Managed Print Services und Entwicklung von Handlungsempfehlungen für zukünftige Outsourcingaktivitäten"};
		for (int i = 0; i < 100; i++) {
			String name = firstname[(int)(Math.random() * firstname.length)] + " " + surname[(int)(Math.random() * surname.length)];
			Reviewer reviewer = new Reviewer(name);
			reviewerList.add(reviewer);
		}
		for(int i = 0; i < topic.length; i++) {
			String name = firstname[(int)(Math.random() * firstname.length)] + " " + surname[(int)(Math.random() * surname.length)];
			String studygroup = studyGroup[(int)(Math.random() * studyGroup.length)];
			Author author = new Author(name, studygroup);
			BachelorThesis thesis = new BachelorThesis(topic[i], author);
			
			Reviewer r1 = reviewerList.get((int)(Math.random() * reviewerList.size()));
			Reviewer r2 = reviewerList.get((int)(Math.random() * reviewerList.size()));
			while(r1.getName().equals(r2.getName())) {
				r2 = reviewerList.get((int)(Math.random() * reviewerList.size()));
			}
			
			thesis.setFirstReview(new Review(r1, true, ReviewStatus.REQUESTED, thesis));
			thesis.setSecondReview(new Review(r2, false, ReviewStatus.REQUESTED, thesis));
			r1.addBachelorThesis(thesis);
			r2.addBachelorThesis(thesis);
		}
		return new Model(reviewerList);
	}
}
