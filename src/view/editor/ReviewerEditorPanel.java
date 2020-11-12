package view.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.EventSource;
import model.Model;
import model.data.Reviewer;
import model.enums.EventId;
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
	private JTextField email;
	private JTextField comment;
	private JLabel nameFieldLabel;
	private JLabel maxSupervisedLabel;
	private JLabel emailLabel;
	private JLabel commentLabel;
	private JLabel supervisedLabel;
	private JButton save;
	private JButton addBachelorThesis;
	private JButton deleteThesis;
	private JButton approveSecReview;

	/**
	 * Creates a view containing a table presenting the reviewer's supervised
	 * bachelorThesis and other data of the reviewer
	 * 
	 * @param viewId Unique viewId from {@link ViewId}
	 * @param model  Needs the model as data access
	 */
	public ReviewerEditorPanel(Model model) {
		super("Dozenteneditor");
		this.model = model;
		
		this.nameField = new JTextField();
		this.maxSupervised = new JTextField();
		this.email = new JTextField();
		this.comment = new JTextField();
		this.nameFieldLabel = new JLabel("Name: ");
		this.maxSupervisedLabel = new JLabel("Max. Anzahl Bachelorarbeiten: ");
		this.emailLabel = new JLabel("Email: ");
		this.commentLabel = new JLabel("Bemerkung: ");
		this.supervisedLabel = new JLabel("Betreute Bachelorarbeiten: ");
		
		this.optReviewer = Optional.empty();

		this.setLayout(new GridLayout(7, 2));
		this.setBackground(Color.GRAY);

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
		this.approveSecReview = new JButton("Zweitgutachten bestätigen");

		this.supervisedThesisTableModel = new SupervisedThesisTableModel(this.optReviewer);
		this.supervisedThesisTable = new JTable(supervisedThesisTableModel);
		this.supervisedThesisTable.setFillsViewportHeight(true);
		// TODO observe sorting behavior when bachelor thesis count >= 10
		this.supervisedThesisTable.setAutoCreateRowSorter(true);
		this.supervisedThesisPane = new JScrollPane(this.supervisedThesisTable);
	}

	private void addUIElements() {
		this.add(this.nameFieldLabel);
		this.nameFieldLabel.setLabelFor(this.nameField);
		this.add(this.nameField);
		this.add(this.maxSupervisedLabel);
		this.maxSupervisedLabel.setLabelFor(this.maxSupervised);
		this.add(this.maxSupervised);
		this.add(this.emailLabel);
		this.emailLabel.setLabelFor(this.email);
		this.add(this.email);					// TODO Supply mockdata
		this.add(this.commentLabel);
		this.commentLabel.setLabelFor(this.comment);
		this.add(this.comment);					// and for this
		this.add(this.supervisedLabel);
		this.supervisedLabel.setLabelFor(this.supervisedThesisPane);
		this.add(this.supervisedThesisPane);
		this.add(this.save);
		this.add(this.addBachelorThesis);
		this.add(this.deleteThesis);
		this.add(this.approveSecReview);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.SAVE_REVIEWER, save, () -> getReviewer()),
				new ButtonEventSource(EventId.ADD_THESIS, addBachelorThesis, () -> getReviewer()),
				new ButtonEventSource(EventId.DELETE_THESIS, deleteThesis, () -> getTheses()),
				new ButtonEventSource(EventId.APPROVE_SEC_REVIEW, approveSecReview, () -> getTheses()));
	}

	@SuppressWarnings("unchecked")
	protected void initializePropertyChangeConsumers() {
		this.onPropertyChange(Model.SELECTED_REVIEWER,
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
		this.email.setText(reviewer.getEmail());
		this.comment.setText(reviewer.getComment());
	}

	private Reviewer getReviewer() {
		this.optReviewer.get().setName(this.getNameFieldText());
		this.optReviewer.get().setMaxSupervisedThesis(Integer.valueOf(this.maxSupervised.getText()));
		this.optReviewer.get().setEmail(this.email.getText());
		this.optReviewer.get().setComment(this.comment.getText());
		return optReviewer.get();
	}

	private String getNameFieldText() {
		return this.nameField.getText();
	}

	private int[] getTheses() {
		return this.supervisedThesisTable.getSelectedRows();
	}
	
	public boolean validateFields() {
		return !(this.getNameFieldText().isBlank() || this.maxSupervised.getText().isBlank());
	}
}
