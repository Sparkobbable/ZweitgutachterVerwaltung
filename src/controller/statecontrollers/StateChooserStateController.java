package controller.statecontrollers;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import controller.JSONController;
import model.Model;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

/**
 * Handles the Application when in ApplicationState
 * {@link ApplicationState#JSON_CHOOSER}
 */
public class StateChooserStateController extends AbstractStateController {
	
	private String filepath;
	
	public StateChooserStateController(View view, ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.STATE_CHOOSER, view, applicationStateController, model);
		GregorianCalendar now = new GregorianCalendar();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		this.filepath = "C:\\Users\\" + System.getenv("USERNAME") + "\\Desktop\\systemstate_" + df.format(now.getTime()) + ".json";
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.LOAD_STATE, (params) -> loadState());
		this.registerEvent(EventId.SAVE_STATE, (params) -> saveState());
		this.registerEvent(EventId.CHOOSE_FILEPATH, (params) -> setFilePath((String) params[0].get()));
	}
	
	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void loadState() {
		File file = new File(filepath);
		if(file.exists()) {
			JSONController json = new JSONController(filepath);
			try {
				ArrayList<Reviewer> reviewers = json.loadReviewers();
				this.model.setReviewers(reviewers);
				this.view.alert("Der Systemstatus wurde erfolgreich geladen", JOptionPane.INFORMATION_MESSAGE);
			} catch(Exception e) {
				this.view.alert("Es ist ein Fehler beim Laden des Systemstandes aufgetreten. \n Versuchen Sie es mit einer gültigen Datei.", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			this.view.alert("Die ausgewählte Datei existiert nicht", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	/**
	 * This Method handles the Event {@link EventId#SAVE_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void saveState() {
		File file = new File(filepath);
		if(file.exists()) {
			int result = this.view.alert("Die Datei exisiert bereits. \n Wollen Sie diese überschreiben?", JOptionPane.QUESTION_MESSAGE);
			if(result == JOptionPane.YES_OPTION) {
				JSONController json = new JSONController(filepath);
				json.saveReviewers(model.getReviewers());
				this.view.alert("Der Systemzustand wurde erfolgreich gespeichert.", JOptionPane.INFORMATION_MESSAGE);
			} else {
				this.view.alert("Speichern des Systemzustands wurde abgebrochen", JOptionPane.INFORMATION_MESSAGE);
			}
		} else {
			JSONController json = new JSONController(filepath);
			json.saveReviewers(model.getReviewers());
			this.view.alert("Der Systemzustand wurde erfolgreich gespeichert.", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	private void setFilePath(String filepath) {
		if(filepath.substring(filepath.length() - 5, filepath.length()).equals(".json")) {
			this.filepath = filepath;
			this.view.alert("Der Dateipfad wurde erfolgreich geändert", JOptionPane.INFORMATION_MESSAGE);
		} else {
			if(filepath.contains(".")) {
				this.filepath = filepath.substring(0, filepath.indexOf(".")) + ".json";
				this.view.alert("Der Dateipfad wurde erfolgreich geändert", JOptionPane.INFORMATION_MESSAGE);
			} else {
				this.filepath = filepath + ".json";
				this.view.alert("Der Dateipfad wurde erfolgreich geändert", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
}