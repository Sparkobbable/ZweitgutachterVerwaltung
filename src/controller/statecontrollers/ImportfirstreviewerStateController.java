package controller.statecontrollers;

import java.io.File;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import javax.swing.JOptionPane;

import controller.CSVController;
import controller.Controller;
import model.Model;
import model.domain.Reviewer;
import model.enums.ApplicationState;
import model.enums.EventId;
import model.persistence.PersistenceHandler;
import view.View;

public class ImportfirstreviewerStateController extends AbstractStateController{

	private String filepath;
	
	public ImportfirstreviewerStateController(View view, Controller controller, Model model) {
		super(ApplicationState.FIRSTREVIEWER_IMPORT, view, controller, model);

		GregorianCalendar now = new GregorianCalendar();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT);
		this.filepath = "C:\\Users\\" +System.getenv("USERNAME") + "\\Desktop\\systemstate_" + df.format(now.getTime()) + ".csv";

	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.IMPORT_FIRST_REVIEWERS, (params) -> loadReview());
		
	}
	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a JFileChooser and giving the chosen path to the {@link PersistenceHandler}
	 * @param params
	 */

	private void loadReview() {
		File file = new File(filepath);
		if(file.exists()) {
			//TODO in CSVController
			CSVController csv = new CSVController(filepath);
			try {
				ArrayList<Reviewer> reviewers = csv.loadcsvImport();
				//this.model.setReviewers(reviewers);
				this.view.assumeState(ApplicationState.FIRSTREVIEWER_IMPORT).alert("Die Datei wurde erfolgreich geladen", JOptionPane.INFORMATION_MESSAGE);
				} catch(Exception e) {
				this.view.assumeState(ApplicationState.FIRSTREVIEWER_IMPORT).alert("Es ist ein Fehler beim Laden der Datei aufgetreten. \n Versuchen Sie es mit einer gültigen Datei.", JOptionPane.ERROR_MESSAGE);
			}
		} else {
			this.view.assumeState(ApplicationState.FIRSTREVIEWER_IMPORT).alert("Die ausgewählte Datei existiert nicht", JOptionPane.ERROR_MESSAGE);

		}
	
	}

}
