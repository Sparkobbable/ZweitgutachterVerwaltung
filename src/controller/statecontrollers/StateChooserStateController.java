package controller.statecontrollers;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

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
		JSONController json = new JSONController(filepath);
		ArrayList<Reviewer> reviewers = json.loadReviewers();
		if(!reviewers.isEmpty()) {
			this.model.setReviewers(reviewers);
		}
	}
	
	/**
	 * This Method handles the Event {@link EventId#SAVE_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void saveState() {
		File file = new File(filepath);
		if(file.exists()) {
			JSONController json = new JSONController(filepath);
			json.saveReviewers(model.getReviewers());
			System.out.println("Der Systemzustand wurde erfolgreich unter \"" + filepath + "\" gespeichert.");
		} else {
			System.out.println("Die ausgew�hlte Datei existiert nicht.");
		}
	}
	
	private void setFilePath(String filepath) {
		if(filepath.substring(filepath.length() - 5, filepath.length()).equals(".json")) {
			this.filepath = filepath;
			System.out.println("Dateipfad \"" + this.filepath + "\" wurde erfolgreich ge�ndert");
		} else {
			if(filepath.contains(".")) {
				this.filepath = filepath.substring(0, filepath.indexOf(".")) + ".json";
				System.out.println("Dateipfad \"" + this.filepath + "\" wurde erfolgreich ge�ndert");
			} else {
				this.filepath = filepath + ".json";
				System.out.println("Dateipfad \"" + this.filepath + "\" wurde erfolgreich ge�ndert");
			}
			
		}
		
	}
}
