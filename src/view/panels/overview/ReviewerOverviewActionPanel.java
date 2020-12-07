package view.panels.overview;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import controller.events.EventSource;
import model.domain.Reviewer;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.AbstractActionPanel;

public class ReviewerOverviewActionPanel extends AbstractActionPanel<Reviewer> {

	private static final long serialVersionUID = 1L;

	private JButton edit;
	private JButton delete;
	private JButton newReviewer;
	private JButton showCollaboration;
	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param selectedElementsSupplier supplies the currently selected Reviewers
	 */
	public ReviewerOverviewActionPanel(Supplier<List<Reviewer>> selectedElementsSupplier) {
		super(selectedElementsSupplier);
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	protected void createUIElements() {
		this.edit = this.buttonFactory.createButton("Bearbeiten");
		this.delete = this.buttonFactory.createButton("L�schen");
		this.newReviewer = this.buttonFactory.createButton("Neu");
		this.showCollaboration = this.buttonFactory.createButton("Zusammenarbeit anzeigen");
	}

	protected void addUIElements() {
		this.add(edit, delete, newReviewer, showCollaboration);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.EDIT, edit, selectedElementsSupplier),
				ButtonEventSource.of(EventId.DELETE, delete, selectedElementsSupplier),
				ButtonEventSource.of(EventId.CREATE, newReviewer),
				ButtonEventSource.of(EventId.SHOW_COLLABORATION, showCollaboration, selectedElementsSupplier));
	}

}
