package view.panels.overview;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import controller.events.EventSource;
import model.domain.Reviewer;
import model.enums.EventId;
import view.ViewProperties;
import view.eventsources.ButtonEventSource;

public class ReviewerOverviewActionPanel extends OverviewActionPanel<Reviewer> {

	private static final long serialVersionUID = 1L;

	private JButton edit;
	private JButton delete;
	private JButton newReviewer;
	private JButton showCollaboration;
	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param id Unique ID from {@link ViewId}
	 */
	public ReviewerOverviewActionPanel(Supplier<List<Reviewer>> selectedRowIndexSupplier) {
		super(selectedRowIndexSupplier);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
	}

	private void createUIElements() {
		this.edit = this.buttonFactory.createButton("Bearbeiten");
		this.delete = this.buttonFactory.createButton("Löschen");
		this.newReviewer = this.buttonFactory.createButton("Neu");
		this.showCollaboration = this.buttonFactory.createButton("Zusammenarbeit anzeigen");
	}

	private void addUIElements() {
		this.add(edit);
		this.add(delete);
		this.add(newReviewer);
		this.add(showCollaboration);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.EDIT, edit, selectedElementsSupplier),
				ButtonEventSource.of(EventId.DELETE, delete, selectedElementsSupplier),
				ButtonEventSource.of(EventId.CREATE, newReviewer),
				ButtonEventSource.of(EventId.SHOW_COLLABORATION, showCollaboration, selectedElementsSupplier));
	}

}
