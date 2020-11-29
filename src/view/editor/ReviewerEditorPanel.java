package view.editor;

import static view.tableModels.SupervisedThesisTableModel.AUTHOR_NAME;
import static view.tableModels.SupervisedThesisTableModel.AUTHOR_STUDY_GROUP;
import static view.tableModels.SupervisedThesisTableModel.STATUS;
import static view.tableModels.SupervisedThesisTableModel.TITLE;
import static view.tableModels.SupervisedThesisTableModel.TYPE;

import java.awt.GridLayout;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import controller.events.EventSource;
import model.Model;
import model.domain.Review;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ButtonEventSource;
import view.eventsources.TextFieldEventSource;
import view.panelstructure.DefaultPanel;
import view.tableModels.SupervisedThesisTableModel;

public class ReviewerEditorPanel extends DefaultPanel {
	private static final long serialVersionUID = 1L;
	private Optional<Reviewer> selectedReviewer;
	private Model model;

	private SupervisedThesisTableModel supervisedThesisTableModel;
	private JTable supervisedThesisTable;
	private JScrollPane supervisedThesisPane;

	private JTextField name;
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

		this.name = new JTextField();
		this.maxSupervised = new JTextField();
		this.email = new JTextField();
		this.comment = new JTextField();
		this.nameFieldLabel = new JLabel("Name: ");
		this.maxSupervisedLabel = new JLabel("Max. Anzahl Bachelorarbeiten: ");
		this.emailLabel = new JLabel("Email: ");
		this.commentLabel = new JLabel("Bemerkung: ");
		this.supervisedLabel = new JLabel("Betreute Bachelorarbeiten: ");

		this.selectedReviewer = this.model.getSelectedReviewer();

		this.setLayout(new GridLayout(7, 2));
		this.setBackground(ViewProperties.BACKGROUND_COLOR);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeConsumers();
		this.observe(this.model);

		// observe reviewer and the theses where they are second reviewers
		this.selectedReviewer.ifPresent(this::observeReviewer);
	}

	private void createUIElements() {
		this.save = this.buttonFactory.createButton("Speichern");
		this.addBachelorThesis = this.buttonFactory.createButton("Bachelorarbeit hinzufügen");
		this.deleteThesis = this.buttonFactory.createButton("Bachelorarbeit löschen");
		this.approveSecReview = this.buttonFactory.createButton("Zweitgutachten bestätigen");
		
		this.supervisedThesisTableModel = new SupervisedThesisTableModel(
				List.of(TITLE, AUTHOR_NAME, AUTHOR_STUDY_GROUP, TYPE, STATUS), Collections.emptyList(),
				this.selectedReviewer);
		this.supervisedThesisTable = new JTable(supervisedThesisTableModel);
		this.supervisedThesisTable.setFillsViewportHeight(true);
		this.supervisedThesisTable.setAutoCreateRowSorter(true);
		this.supervisedThesisPane = new JScrollPane(this.supervisedThesisTable);
	}

	private void addUIElements() {
		this.add(this.nameFieldLabel);
		this.nameFieldLabel.setLabelFor(this.name);
		this.add(this.name);
		this.add(this.maxSupervisedLabel);
		this.maxSupervisedLabel.setLabelFor(this.maxSupervised);
		this.add(this.maxSupervised);
		this.add(this.emailLabel);
		this.emailLabel.setLabelFor(this.email);
		this.add(this.email);
		this.add(this.commentLabel);
		this.commentLabel.setLabelFor(this.comment);
		this.add(this.comment);
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
		return List.of(new ButtonEventSource(EventId.SAVE_REVIEWER, save),
				new ButtonEventSource(EventId.ADD_THESIS, addBachelorThesis),
				new ButtonEventSource(EventId.REJECT, deleteThesis, () -> getSelectedReviews()),
				new ButtonEventSource(EventId.APPROVE_SEC_REVIEW, approveSecReview, () -> getSelectedReviews()),
				new TextFieldEventSource(EventId.NAME_CHANGED, this.name, () -> this.name.getText()),
				new TextFieldEventSource(EventId.MAX_SUPERVISED_THESES_CHANGED, this.maxSupervised,
						() -> this.maxSupervised.getText()),
				new TextFieldEventSource(EventId.EMAIL_CHANGED, this.email, () -> this.email.getText()),
				new TextFieldEventSource(EventId.COMMENT_CHANGED, this.comment, () -> this.comment.getText()));
	}

	@SuppressWarnings("unchecked")
	private void initializePropertyChangeConsumers() {
		this.onPropertyChange(Model.SELECTED_REVIEWER,
				(evt) -> updateSelectedReviewer((Optional<Reviewer>) evt.getNewValue()));
		this.onPropertyChange(Reviewer.SECOND_REVIEWS,
				(evt) -> updateObserversForSecondReviews((List<Review>) evt.getOldValue(),
						(List<Review>) evt.getNewValue()));
		this.onPropertyChange(SecondReview.STATUS, (evt) -> this.refreshTableModel());
		this.onPropertyChange(Reviewer.NAME, e -> this.name.setText((String) e.getNewValue()));
		this.onPropertyChange(Reviewer.EMAIL, e -> this.email.setText((String) e.getNewValue()));
		this.onPropertyChange(Reviewer.COMMENT, e -> this.comment.setText((String) e.getNewValue()));
		this.onPropertyChange(Reviewer.MAX_SUPERVISED_THESES,
				e -> this.maxSupervised.setText((String) e.getNewValue()));
	}

	private void updateObserversForSecondReviews(List<Review> oldReviews, List<Review> newReviews) {
		this.observe(newReviews);
		this.refreshTableModel();
	}

	private void refreshTableModel() {
		this.supervisedThesisTableModel.updateData();
		this.repaint();
	}

	private void updateSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		this.selectedReviewer = selectedReviewer;
		// observe reviewer and the theses where they are second reviewers
		if (this.selectedReviewer.isPresent()) {
			Reviewer reviewer = this.selectedReviewer.get();
			this.updateReviewerFields(reviewer);
			this.observeReviewer(reviewer);
			this.supervisedThesisTableModel.setSelectedReviewer(reviewer);

		}

		this.repaint();
	}

	private void observeReviewer(Reviewer reviewer) {
		this.observe(reviewer);
		this.observe(reviewer.getUnrejectedSecondReviews());
	}

	private void updateReviewerFields(Reviewer reviewer) {
		this.name.setText(reviewer.getName());
		this.maxSupervised.setText(String.valueOf(reviewer.getMaxSupervisedThesis()));
		this.email.setText(reviewer.getEmail());
		this.comment.setText(reviewer.getComment());
	}

	private List<Review> getSelectedReviews() {
		return IntStream.of(this.supervisedThesisTable.getSelectedRows())
				.map(this.supervisedThesisTable::convertRowIndexToModel)
				.mapToObj(this.supervisedThesisTableModel::getByIndex).collect(Collectors.toList());
	}

	public boolean validateFields() {
		return !(this.name.getText().isBlank() || this.maxSupervised.getText().isBlank());
	}
}
