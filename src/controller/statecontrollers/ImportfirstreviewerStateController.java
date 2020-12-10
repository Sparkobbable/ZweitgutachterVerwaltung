package controller.statecontrollers;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import controller.CSVController;
import controller.Controller;
import controller.JSONController;
import controller.commands.model.LoadSystemStateCommand;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.persistence.PersistenceHandler;
import view.View;

//TODO @leoniemersmann javadoc
public class ImportfirstreviewerStateController extends AbstractStateController {

	private String filepath;

	public ImportfirstreviewerStateController(View view, Controller controller, Model model) {
		super(ApplicationState.FIRSTREVIEWER_IMPORT, view, controller, model);

		GregorianCalendar now = new GregorianCalendar();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		this.filepath = "C:\\Users\\" + System.getenv("USERNAME") + "\\Desktop\\systemstate_" + df.format(now.getTime())
				+ ".csv";

	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.LOAD, (params) -> loadReview());
		this.registerEvent(EventId.SAVE, (params) -> saveCSV());
		this.registerEvent(EventId.CHOOSE_FILEPATH, (params) -> setFilePath((String) params[0].get()));
	}

	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a
	 * JFileChooser and giving the chosen path to the {@link PersistenceHandler}
	 * 
	 * @param params
	 */

	private void loadReview() {
		File file = new File(filepath);
		if (file.exists()) {
			PersistenceHandler persistence = new CSVController(filepath);
			try {
				this.execute(new LoadSystemStateCommand(persistence, this.model));
				this.view.alert("Die Datei wurde erfolgreich geladen", JOptionPane.INFORMATION_MESSAGE);
			} catch (Exception e) {
				e.printStackTrace();
				this.view.alert(
						"Es ist ein Fehler beim Laden der Datei aufgetreten. \n Versuchen Sie es mit einer gültigen Datei.",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			this.view.alert("Die ausgewählte Datei existiert nicht", JOptionPane.ERROR_MESSAGE);

		}

	}

	private void saveCSV() {
		File file = new File(filepath);
		if (file.exists()) {
			int result = this.view.alert("Die Datei exisiert bereits. \n Wollen Sie diese überschreiben?",
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.NO_OPTION) {
				this.view.alert("Speichern der Datei wurde abgebrochen", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		PersistenceHandler persistence = new CSVController(filepath);
		persistence.save(this.model.getReviewers(), this.model.getTheses());
		this.view.alert("Die Datei wurde erfolgreich gespeichert.", JOptionPane.INFORMATION_MESSAGE);
	}

	private void setFilePath(String filepath) {
		if (filepath.endsWith(".csv")) {
			this.filepath = filepath;
			this.view.alert("Der Dateipfad wurde erfolgreich geändert", JOptionPane.INFORMATION_MESSAGE);
		} else {
			this.view.alert("Bitte wählen Sie eine gültige .csv-Datei aus", JOptionPane.ERROR_MESSAGE);
		}
	}
}
