package view.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.EventSource;
import model.Model;
import model.data.Reviewer;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.eventsources.ButtonEventSource;
import view.tableModels.SupervisedThesisTableModel;

public class ReviewerEditorPanel extends AbstractView {
	private static final long serialVersionUID = 1L;
	private Optional<Reviewer> optReviewer;
	private Model model;

	private SupervisedThesisTableModel supervisedThesisTableModel;
	private JTable supervisedThesisTable;
	private JScrollPane supervisedThesisPane;

	private JTextField nameField;
	private JTextField maxSupervised;
	private JButton save;
	private JButton addBachelorThesis;
	private JButton deleteThesis;

	/**
	 * Creates a view containing a table presenting the reviewer's supervised
	 * bachelorThesis and other data of the reviewer
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param title  Needs a title
	 * @param model  Needs the model as data access
	 */
	public ReviewerEditorPanel(ViewId id, String title, Model model) {
		super(id, title);
		this.model = model;
		this.nameField = new JTextField();
		this.maxSupervised = new JTextField();
		this.optReviewer = Optional.empty();

		this.setBackground(Color.MAGENTA); // TODO only for component identification, remove before launch
		this.setLayout(new GridLayout(4, 1));

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeConsumers();
		this.addObservables(this.model);
	}

	private void createUIElements() {
		this.save = new JButton("Speichern");
		this.addBachelorThesis = new JButton("Bachelorarbeit hinzufügen");
		this.deleteThesis = new JButton("Bachelorarbeit löschen");

		this.supervisedThesisTableModel = new SupervisedThesisTableModel(this.optReviewer);
		this.supervisedThesisTable = new JTable(supervisedThesisTableModel);
		this.supervisedThesisTable.setFillsViewportHeight(true);
		// TODO observe sorting behavior when bachelor thesis count >= 10
		this.supervisedThesisTable.setAutoCreateRowSorter(true);
		this.supervisedThesisPane = new JScrollPane(this.supervisedThesisTable);
	}

	private void addUIElements() {
		this.add(this.nameField);
		this.add(this.supervisedThesisPane);
		this.add(this.maxSupervised);
		this.add(this.save);
		this.add(this.addBachelorThesis);
		this.add(this.deleteThesis);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.SAVE_REVIEWER, save, () -> getReviewer()),
				new ButtonEventSource(EventId.ADD_THESIS, addBachelorThesis, () -> getReviewer()),
				new ButtonEventSource(EventId.DELETE_THESIS, deleteThesis, () -> getThesis()));
	}

	@SuppressWarnings("unchecked")
	protected void initializePropertyChangeConsumers() {
		this.onPropertyChange("selectedReviewer",
				(evt) -> updateSelectedReviewer((Optional<Reviewer>) evt.getNewValue()));
	}

	private void updateSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		this.optReviewer = selectedReviewer;
		this.optReviewer.ifPresent(this::setReviewerFields);
		this.supervisedThesisTableModel.setSelectedReviewer(optReviewer);
		this.optReviewer.ifPresent(reviewer -> addObservables(reviewer));
		this.supervisedThesisTableModel.fireTableDataChanged();
		this.repaint();
	}

	private void setReviewerFields(Reviewer reviewer) {
		this.nameField.setText(reviewer.getName());
		this.maxSupervised.setText(String.valueOf(reviewer.getMaxSupervisedThesis()));
	}

	private Reviewer getReviewer() {
		this.optReviewer.get().setName(this.getNameFieldText());
		this.optReviewer.get().setMaxSupervisedThesis(Integer.valueOf(this.maxSupervised.getText()));
		return optReviewer.get();
	}

	private String getNameFieldText() {
		return this.nameField.getText();
	}

	private int[] getThesis() {
		return this.supervisedThesisTable.getSelectedRows();
	}
}
