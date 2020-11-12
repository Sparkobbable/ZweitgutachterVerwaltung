package view.collaboration;

import java.awt.Color;
import java.util.List;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JTextField;

import model.EventSource;
import model.Model;
import model.data.Reviewer;
import model.enums.EventId;
import view.panelstructure.AbstractViewPanel;
import view.panelstructure.DefaultViewPanel;

public class CollaborationOptionsPanel extends DefaultViewPanel {

	private static final long serialVersionUID = 1L;

	private Optional<Reviewer> optReviewer;
	private Model model;

	private JLabel reviewername;
	private JTextField nameField;

	private AbstractViewPanel chooseData;
	private AbstractViewPanel choosePresentation;


	public CollaborationOptionsPanel(Model model) {
		super("");
		this.model = model;

		this.setBackground(Color.GREEN);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializedPropertyChangeConsumers();
		this.addObservables(this.model);
	}

	private void createUIElements() {
		this.reviewername = new JLabel("Ausgewählter Gutachter:");
		this.nameField = new JTextField();

		String[] options = { "Nur Erstgutachter", "Nur Zweitgutachter", "Zweit- & Erstgutachter" };
		this.chooseData = new CollaborationComboBoxPanel("Ansicht", options, EventId.CHOOSE_DATA_FOR_COLLABORATION);
		String[] options2 = { "Tabelle", "Tortendiagramm" };
		this.choosePresentation = new CollaborationComboBoxPanel("Darstellung", options2,
				EventId.CHOOSE_PRESENTATION_FOR_COLLABORATION);

	}

	private void addUIElements() {
		this.add(reviewername);
		this.add(nameField);
		this.add(chooseData);
		this.add(choosePresentation);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.chooseData, this.choosePresentation);
	}

	@SuppressWarnings("unchecked")
	protected void initializedPropertyChangeConsumers() {
		this.onPropertyChange(Model.SELECTED_REVIEWER,
				(evt) -> updateSelectedReviewer((Optional<Reviewer>) evt.getNewValue()));
	}

	private void updateSelectedReviewer(Optional<Reviewer> selectedReviewer) {
		this.optReviewer = selectedReviewer;
		this.optReviewer.ifPresent(this::setReviewerField);
		this.optReviewer.ifPresent(reviewer -> addObservables(reviewer));
		this.repaint();
	}

	private void setReviewerField(Reviewer reviewer) {
		this.nameField.setText(reviewer.getName());
	}

}
