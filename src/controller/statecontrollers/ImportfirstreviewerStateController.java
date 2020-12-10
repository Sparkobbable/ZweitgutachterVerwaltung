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
		this.registerEvent(EventId.LOAD, (params) -> loadReview((boolean) params[0].get()));
		this.registerEvent(EventId.SAVE, (params) -> saveCSV());
		this.registerEvent(EventId.CHOOSE_FILEPATH, (params) -> setFilePath((String) params[0].get()));
	}

	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a
	 * JFileChooser and giving the chosen path to the {@link PersistenceHandler}
	 * @param override 
	 * 
	 * @param params
	 */

	private void loadReview(boolean override) {
		File file = new File(filepath);
		if (file.exists()) {
			PersistenceHandler persistence = new CSVController(filepath);
			try {
				this.execute(new LoadSystemStateCommand(persistence, this.model, override));
				if (override) {
					this.popupInfo("Die Datei wurde erfolgreich geladen");
				} else {
					this.popupInfo("Alle noch nicht vorhandenen Einträge wurden geladen");
				}
			} catch (Exception e) {
				e.printStackTrace();
				this.popupError(
						"Es ist ein Fehler beim Laden der Datei aufgetreten. \n Versuchen Sie es mit einer gültigen Datei.");
				e.printStackTrace();
			}
		} else {
			this.popupError("Die ausgewählte Datei existiert nicht");

		}

	}

	private void saveCSV() {
		File file = new File(filepath);
		if (file.exists()) {
			int result = this.view.alert("Die Datei exisiert bereits. \n Wollen Sie diese überschreiben?",
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.NO_OPTION) {
				this.popupInfo("Speichern der Datei wurde abgebrochen");
				return;
			}
		}
		PersistenceHandler persistence = new CSVController(filepath);
		persistence.save(this.model.getReviewers(), this.model.getTheses());
		this.popupInfo("Die Datei wurde erfolgreich gespeichert.");
	}

	private void setFilePath(String filepath) {
		if (filepath.substring(filepath.length() - 5, filepath.length()).equals(".csv")) {
			this.filepath = filepath;
			this.popupInfo("Der Dateipfad wurde erfolgreich geändert");
		} else {
			if (filepath.contains(".")) {
				this.filepath = filepath.substring(0, filepath.indexOf(".")) + ".csv";
				this.popupInfo("Der Dateipfad wurde erfolgreich geändert");
			} else {
				this.filepath = filepath + ".csv";
				this.popupInfo("Der Dateipfad wurde erfolgreich geändert");
			}
		}
	}
}
