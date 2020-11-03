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
public class JsonChooserStateController extends AbstractStateController {
	
	private ArrayList<String> jsonFiles;
	
	public JsonChooserStateController(View view, ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.JSON_CHOOSER, view, applicationStateController, model);
		jsonFiles = new ArrayList<>();
	}
	
	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.LOAD_JSON, (params) -> loadJson(params));
		this.registerEvent(EventId.SAVE_JSON, (params) -> saveJson(params));
	}
	
	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void loadJson(Supplier<?>[] params) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json Dateien", "json");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getAbsolutePath();
			jsonFiles.add(path);
			
			JSONController json = new JSONController(path);
			ArrayList<Reviewer> reviewers = json.loadReviewers();
			if(reviewers != null) {
				this.data.setReviewers(reviewers);
			}
		}
	
	}
	
	/**
	 * This Method handles the Event {@link EventId#SAVE_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void saveJson(Supplier<?>[] params) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json Dateien", "json");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getAbsolutePath();
			System.out.println(path);
			JSONController json = new JSONController(path);
			json.saveReviewers(data.getReviewers());
		}
	}
}
