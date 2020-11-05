package controller.statecontrollers;

import java.util.ArrayList;
import java.util.function.Supplier;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import controller.JSONController;
import model.Model;
import model.data.BachelorThesis;
import model.data.Review;
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
			jsonFiles.add(path);
			
			JSONController json = new JSONController(path);
			ArrayList<Reviewer> reviewers = json.loadReviewers();
			if(reviewers != null) {
				this.data.setReviewers(reviewers);
			}
		}
	
	}
}
