package view.panels.editor;

import java.awt.Component;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import controller.events.EventSource;
import controller.events.SingleEventSource;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.AbstractActionPanel;

/**
 * {@link AbstractActionPanel} that is responsible for supplying actions in the {@link ThesesAssignmentPanel}.
 */
public class ThesesAssignmentActionPanel extends AbstractActionPanel<BachelorThesis> {

	private static final long serialVersionUID = 1L;
	
	//UI-Elements
	private JButton addThesis;
	private JButton showCollaboration;
	
	/**
	 * Initializes this {@link AbstractActionPanel} with the given elements {@link Supplier}
	 * @param selectedElementsSupplier	{@link Supplier} of the given elements which are added to the {@link SingleEventSource}
	 */
	ThesesAssignmentActionPanel(Supplier<List<BachelorThesis>> selectedElementsSupplier) {
		super(selectedElementsSupplier);
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	protected void createUIElements() {
		this.addThesis = this.buttonFactory.createButton(null);
		this.showCollaboration = this.buttonFactory.createButton(null);
	}

	protected void addUIElements() {
		this.add(new Component[] {this.addThesis, this.showCollaboration});
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.ADD_THESIS_TO_REVIEWER, addThesis, selectedElementsSupplier),
						ButtonEventSource.of(EventId.SHOW_COLLABORATION, showCollaboration, selectedElementsSupplier));
	}

	void setTextForReviewer(Reviewer selectedReviewer) {
		this.addThesis.setText(createAddButtonText(selectedReviewer));
		
		if (this.selectedElementsSupplier.get().size() == 1) {
			this.showCollaboration.setEnabled(true);
			this.showCollaboration.setText(createCollabButtonText());
		} else {
			this.showCollaboration.setEnabled(false);
			this.showCollaboration.setText("Zeige Zusammenarbeit");
		}
	}
	
	private String createCollabButtonText() {
		return String.format("Zeige Zusammenarbeit mit %s", this.selectedElementsSupplier.get().get(0).getAuthor().getName());
	}

	private String createAddButtonText(Reviewer selectedReviewer) {
		return String.format("Zweitgutachten %s zuordnen", selectedReviewer.getName());
	}

}
