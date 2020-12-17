package controller.statecontrollers;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JOptionPane;

import controller.Controller;
import controller.commands.model.LoadSystemStateCommand;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.persistence.JSONHandler;
import model.persistence.PersistenceHandler;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#STATE_CHOOSER}
 */
public class StateChooserStateController extends AbstractStateController {

	private String filepath;

	public StateChooserStateController(View view, Controller controller, Model model) {
		super(ApplicationState.STATE_CHOOSER, view, controller, model);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy.MM.dd-HH.mm.ss");
		LocalDateTime now = LocalDateTime.now();
		this.filepath = "C:\\Users\\" + System.getenv("USERNAME") + "\\Desktop\\systemstate_" + dtf.format(now)
				+ ".json";
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.LOAD, (params) -> loadState());
		this.registerEvent(EventId.SAVE, (params) -> saveState());
		this.registerEvent(EventId.CHOOSE_FILEPATH, (params) -> setFilePath((String) params[0].get()));
	}

	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a
	 * JFileChooser and giving the chosen path to the {@link PersistenceHandler}
	 * 
	 * @param params
	 */
	private void loadState() {
		File file = new File(filepath);
		if (file.exists()) {
			PersistenceHandler persistence = new JSONHandler(filepath);
			try {
				this.execute(new LoadSystemStateCommand(persistence, this.model, true));
				this.popupInfo("Der Systemstatus wurde erfolgreich geladen");
			} catch (Exception e) {
				e.printStackTrace();
				this.popupError(
						"Es ist ein Fehler beim Laden des Systemstandes aufgetreten. \n Versuchen Sie es mit einer gültigen Datei.");
				e.printStackTrace();
			}
		} else {
			this.popupError("Die ausgewählte Datei existiert nicht");
		}

	}

	/**
	 * This Method handles the Event {@link EventId#SAVE_JSON} starting a
	 * JFileChooser and giving the chosen path to the {@link PersistenceHandler}
	 * 
	 * @param params
	 */
	private void saveState() {
		File file = new File(filepath);
		if (file.exists()) {
			int result = this.view.alert("Die Datei exisiert bereits. \n Wollen Sie diese überschreiben?",
					JOptionPane.QUESTION_MESSAGE);
			if (result == JOptionPane.NO_OPTION) {
				this.view.alert("Speichern des Systemzustands wurde abgebrochen", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		PersistenceHandler persistence = new JSONHandler(filepath);
		persistence.save(this.model.getReviewers(), this.model.getTheses());
		this.popupInfo("Der Systemzustand wurde erfolgreich gespeichert.");
	}

	private void setFilePath(String filepath) {
		if (filepath.substring(filepath.length() - 5, filepath.length()).equals(".json")) {
			this.filepath = filepath;
			this.popupInfo("Der Dateipfad wurde erfolgreich geändert");
		} else {
			if (filepath.contains(".")) {
				this.filepath = filepath.substring(0, filepath.indexOf(".")) + ".json";
				this.popupInfo("Der Dateipfad wurde erfolgreich geändert");
			} else {
				this.filepath = filepath + ".json";
				this.popupInfo("Der Dateipfad wurde erfolgreich geändert");
			}
		}
	}
}