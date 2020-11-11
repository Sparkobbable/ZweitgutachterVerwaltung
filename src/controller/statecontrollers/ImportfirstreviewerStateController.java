package controller.statecontrollers;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.function.Supplier;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.JSONController;
import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import view.View;

public class ImportfirstreviewerStateController extends AbstractStateController{

	private String filepath;
	
	public ImportfirstreviewerStateController(View view, ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.FIRSTREVIEWER_IMPORT, view, applicationStateController, model);
		GregorianCalendar now = new GregorianCalendar();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		this.filepath = "C:\\Users\\" +System.getenv("USERNAME") + "\\Desktop\\systemstate_" + df.format(now.getTime()) + ".csv";
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.IMPORT_FIRST_REVIEWERS, (params) -> loadReview());
		
	}
	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void loadReview() {
		File file = new File(filepath);
		if(file.exists()) {
			//TODO in CSVController
			JSONController json = new JSONController(filepath);
			try {
				ArrayList<Reviewer> reviewers = json.loadReviewers();
				this.model.setReviewers(reviewers);
				} catch(Exception e) {
				this.view.assumeState(ApplicationState.STATE_CHOOSER).alert("Es ist ein Fehler beim Laden des Systemstandes aufgetreten. \n Versuchen Sie es mit einer gültigen Datei.", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			this.view.assumeState(ApplicationState.STATE_CHOOSER).alert("Die ausgewählte Datei existiert nicht", JOptionPane.ERROR_MESSAGE);
		}
	
	}

}
