package controller.statecontrollers;

import java.io.File;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.commands.model.LoadSystemStateCommand;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.persistence.CSVHandler;
import model.persistence.PersistenceHandler;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#FIRSTREVIEWER_IMPORT}
 */
public class ImportFirstReviewerStateController extends AbstractStateController {

	private static final String FILE_SUFFIX = ".csv";
	private String filepath;

	public ImportFirstReviewerStateController(View view, Controller controller, Model model) {
		super(ApplicationState.FIRSTREVIEWER_IMPORT, view, controller, model);

		
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
		LocalDateTime now = LocalDateTime.now();
		this.filepath = "C:\\Users\\" + System.getenv("USERNAME") + "\\Desktop\\systemstate_" + dtf.format(now)
				+ FILE_SUFFIX;

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
	 * 
	 * @param override
	 * 
	 * @param params
	 */

	private void loadReview(boolean override) {
		File file = new File(filepath);
		if (file.exists()) {
			PersistenceHandler persistence = new CSVHandler(filepath);
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
		PersistenceHandler persistence = new CSVHandler(filepath);
		persistence.save(this.model.getReviewers(), this.model.getTheses());
		this.popupInfo("Die Datei wurde erfolgreich gespeichert.");
	}

	private void setFilePath(String filepath) {
		if (filepath.endsWith(FILE_SUFFIX)) {
			this.filepath = filepath;
		} else {
			this.filepath = filepath + FILE_SUFFIX;
		}
		this.popupInfo("Der Dateipfad wurde erfolgreich geändert");
	}
}
