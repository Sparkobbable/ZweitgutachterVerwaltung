package view.panels.analyse;

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
import view.widgets.ComboBoxPanel;

/**
 * Options in the {@link CollaborationPanel} including the comboBoxes
 * to choose the presentation and data
 */
@SuppressWarnings("serial") // should not be serialized
public class SingleAnalysisOptionsPanel extends DefaultPanel {

	private Optional<Reviewer> optReviewer;
	private Model model;

	private JLabel reviewername;
	private JLabel nameField;

	private AbstractViewPanel chooseData;
	private AbstractViewPanel choosePresentation;

	public SingleAnalysisOptionsPanel(Model model) {
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
		this.reviewername.setForeground(ViewProperties.FONT_COLOR);
		this.nameField = new JLabel();
		this.nameField.setForeground(ViewProperties.FONT_COLOR);

		String[] options = { "Nur Erstgutachten", "Nur Zweitgutachten", "Erst- & Zweitgutachten" };
		this.chooseData = new ComboBoxPanel("Ansicht", options, EventId.CHOOSE_REVIEW_FILTER);
		String[] options2 = { "Tabelle", "Tortendiagramm", "Säulendiagramm"};
		this.choosePresentation = new ComboBoxPanel("Darstellung", options2,
				EventId.CHOOSE_PRESENTATION);

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
