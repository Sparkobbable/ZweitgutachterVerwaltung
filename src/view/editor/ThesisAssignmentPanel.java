package view.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Optional;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import model.EventSource;
import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.eventsources.ButtonEventSource;
import view.tableModels.ThesisTableModel;

@SuppressWarnings("deprecation")
public class ThesisAssignmentPanel extends AbstractView {

	private static final long serialVersionUID = 1L;
	private ArrayList<BachelorThesis> thesisList;
	private Optional<Reviewer> selectedReviewer;
	
	private JTable thesisTable;
	private JScrollPane thesisScrollPane;
	private JButton addThesis;

	/**
	 * Creates a view containing a table presenting the bachelorThesis without a second review and other data of the thesis
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param title  Needs a title
	 * @param model  Needs the model as data access
	 */
	public ThesisAssignmentPanel(ViewId viewId, String title, Model model) {
		super(viewId, title);
		this.thesisList = model.getThesisMissingSecReview();
		this.selectedReviewer = model.getSelectedReviewer();
		
		addObservables(model);
			
		this.setBackground(Color.DARK_GRAY); // TODO only for component identification, remove before launch
		this.setLayout(new GridLayout(4, 1));
		
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(	new ButtonEventSource(EventId.ADD_THESIS_TO_REVIEWER, this.addThesis, () -> getThesis()));
	}

	private int[] getThesis() {
		return this.thesisTable.getSelectedRows();
	}

	private void createUIElements() {
			this.thesisTable = new JTable(new ThesisTableModel(this.thesisList));
			this.thesisScrollPane = new JScrollPane(this.thesisTable);
			this.addThesis = new JButton(String.format("Zweitgutachten %s zuordnen", this.selectedReviewer.map(reviewer -> reviewer.getName()).orElse("X")));
	}

	private void addUIElements() {
		this.add(this.thesisScrollPane);
		this.add(this.addThesis);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o.getClass().equals(Model.class)) {
			this.selectedReviewer = ((Model) o).getSelectedReviewer();
			this.removeAll();
			this.createUIElements();
			this.addUIElements();
		}
		this.repaint();
	}

}
