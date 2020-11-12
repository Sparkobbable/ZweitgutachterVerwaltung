package view.collaboration;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;

import model.EventSource;
import model.Model;
import model.enums.PresentationMode;
import view.panelstructure.AbstractViewPanel;
import view.panelstructure.DefaultViewPanel;

public class CollaborationPanel extends DefaultViewPanel {

	private static final long serialVersionUID = 1L;
	
	public static final String SELECTED_PRESENTATIONMODE = "selectedPresentationMode";
	
	private Model model;
	private PresentationMode selectedPresentationMode;
	
	private AbstractViewPanel options;
	private AbstractViewPanel chart;
	
	public CollaborationPanel(Model model) {
		super("Zusammenarbeit anzeigen");
		this.model = model;
		this.selectedPresentationMode = PresentationMode.TABLE;
		
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(new GridLayout(3,2));
		
		this.onPropertyChange(SELECTED_PRESENTATIONMODE, evt -> switchPresentationMode((PresentationMode) evt.getNewValue()));
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.addObservables(this.model);
	}
	
	private void createUIElements() {
		this.options = new CollaborationOptionsPanel(this.model);
	}
	
	private void addUIElements() {
		this.add(this.options);
	}
	
	public void setPresentationMode(PresentationMode presentationMode) {
		this.selectedPresentationMode = presentationMode;
	}
	
	public PresentationMode getPresentationMode() {
		return this.selectedPresentationMode;
	}
	
	private void switchPresentationMode(PresentationMode presentationMode) {
		if(presentationMode == PresentationMode.TABLE) {
			System.out.println("Wechsle zu Tabelle");
		} else {
			System.out.println("Wechsle zu Tortendiagramm");
		}
	}
	
	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.options);
	}
}
