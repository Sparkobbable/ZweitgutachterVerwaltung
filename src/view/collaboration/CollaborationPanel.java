package view.collaboration;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Optional;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.EventSource;
import model.Model;
import model.data.Reviewer;
import model.enums.EventId;
import model.enums.PresentationMode;
import model.enums.ViewId;
import view.AbstractView;
import view.eventsources.ButtonEventSource;

public class CollaborationPanel extends AbstractView {

	private static final long serialVersionUID = 1L;
	
	public static final String SELECTED_PRESENTATIONMODE = "selectedPresentationMode";
	
	private Model model;
	private PresentationMode selectedPresentationMode;
	
	private AbstractView options;
	private AbstractView chart;
	
	public CollaborationPanel(ViewId viewId, Model model) {
		super(viewId, "Zusammenarbeit anzeigen");
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
		this.options = new CollaborationOptionsPanel(ViewId.COLLABORATION_OPTIONS, this.model);
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
