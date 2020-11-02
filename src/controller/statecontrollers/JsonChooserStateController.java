package controller.statecontrollers;

import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.JSONController;
import model.Model;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

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
	
	private void loadJson(Supplier<?>[] params) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Json Dateien", "json");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getAbsolutePath();
			jsonFiles.add(path);
			
			JSONController json = new JSONController(path);
			json.loadReviewers();
		}
		
		
		
	}
	
	private void saveJson(Supplier<?>[] params) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getAbsolutePath();
			System.out.println(path);
			JSONController json = new JSONController(path);
			json.saveReviewers(data.getReviewers());
		}
	}
}
