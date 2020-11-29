package view.overview;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import controller.events.EventSource;
import model.domain.Reviewer;
import model.enums.EventId;
import view.View;
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
		this.setBackground(View.background);
	}

	private void createUIElements() {
		this.edit = new JButton("Bearbeiten");
		this.delete = new JButton("Löschen");
		this.newReviewer = new JButton("Neu");
		this.showCollaboration = new JButton("Zusammenarbeit anzeigen");
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
