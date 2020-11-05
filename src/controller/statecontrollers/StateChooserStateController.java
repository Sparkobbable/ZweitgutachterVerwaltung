package controller.statecontrollers;

import java.util.ArrayList;
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
		filepath = "systemstate.json";
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
			if(reviewers != null) {
				this.model.setReviewers(reviewers);
			}
	}
	
	/**
	 * This Method handles the Event {@link EventId#SAVE_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void saveState() {
			JSONController json = new JSONController(filepath);
			json.saveReviewers(model.getReviewers());
	}
	
	private void setFilePath(String filepath) {
		this.filepath = filepath;
		System.out.println(filepath);
	}
}
