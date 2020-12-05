package view.panels.collaboration;

import java.util.List;
import java.util.Optional;

import javax.swing.JLabel;
import javax.swing.JTextField;

import controller.events.EventSource;
import model.Model;
import model.domain.Reviewer;
import model.enums.EventId;
import view.ViewProperties;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;

@SuppressWarnings("serial") // should not be serialized
public class CollaborationOptionsPanel extends DefaultPanel {

	private Optional<Reviewer> optReviewer;
	private Model model;

	private JLabel reviewername;
	private JTextField nameField;

	private AbstractViewPanel chooseData;
	private AbstractViewPanel choosePresentation;


	public CollaborationOptionsPanel(Model model) {
		super("");
		this.model = model;

		this.setBackground(ViewProperties.BACKGROUND_COLOR);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.initializedPropertyChangeConsumers();
		this.observe(this.model);
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
		if (this.optReviewer.isPresent()) {
			Reviewer reviewer = this.optReviewer.get();
			this.nameField.setText(reviewer.getName());
			this.observe(reviewer);
		}
		this.repaint();
	}


}
