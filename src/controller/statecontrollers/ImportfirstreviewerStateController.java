package controller.statecontrollers;

import java.util.ArrayList;
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

	private BachelorThesis firstreview;
	public ImportfirstreviewerStateController(View view, ApplicationStateController applicationStateController, Model model) {
		super(ApplicationState.FIRSTREVIEWER_IMPORT, view, applicationStateController, model);
		firstreview = new BachelorThesis();
	}

	@Override
	protected void registerEvents() {
		this.registerEvent(EventId.IMPORT_FIRST_REVIEWERS, (params) -> loadReview(params));
		
	}
	/**
	 * This Method handles the Event {@link EventId#LOAD_JSON} starting a JFileChooser and giving the chosen path to the {@link JSONController}
	 * @param params
	 */
	private void loadReview(Supplier<?>[] params) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV Dateien", "csv");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile().getAbsolutePath();
			
			JSONController json = new JSONController(path);
			/*@Leonie die Änderung musste ich machen, weil ich im JsonController was ändern müsste.
			 * Bin mir aber nicht ganz sicher, ob dich das wirklich betrifft, wenn du fertig bist.
			 */
			try {
				ArrayList<Reviewer> reviewers = json.loadReviewers();
				this.model.setReviewers(reviewers);
			} catch(Exception e) {
				this.view.assumeState(ApplicationState.FIRSTREVIEWER_IMPORT).alert("Es ist ein Fehler beim Laden der Erstgutachter aufgetreten. \n Versuchen Sie es mit einer gültigen Datei.", JOptionPane.ERROR_MESSAGE);
			}
		}
	
	}
}
