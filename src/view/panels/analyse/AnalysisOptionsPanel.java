package view.panels.analyse;

import java.util.List;

import controller.events.EventSource;
import model.Model;
import model.enums.EventId;
import view.ViewProperties;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;
import view.widgets.ComboBoxPanel;

/**
 * Options in the {@link AnalysisPanel} including the comboBoxes
 * to choose the presentation and data
 */
public class AnalysisOptionsPanel extends DefaultPanel {

	private static final long serialVersionUID = 1L;
	
	private Model model;
	
	private AbstractViewPanel chooseData;
	private AbstractViewPanel choosePresentation;

	public AnalysisOptionsPanel(Model model) {
		super("");
		this.model = model;
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.observe(this.model);
	}
	
	private void createUIElements() {
		String[] options = { "Nur Erstgutachten", "Nur Zweitgutachten", "Erst- & Zweitgutachten" };
		this.chooseData = new ComboBoxPanel("Ansicht", options, EventId.ANALYSIS_CHOOSE_DATA);	
		String[] options2 = {"Tabelle", "Tortendiagramm", "Säulendiagramm", "Balkendiagramm"};
		this.choosePresentation = new ComboBoxPanel("Darstellung", options2, EventId.ANALYSIS_CHOOSE_PRESENTATION);
	}
	
	private void addUIElements() {
		this.add(chooseData);
		this.add(choosePresentation);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.chooseData, this.choosePresentation);
	}

}
