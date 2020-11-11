package view.overview;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

public class ReviewerOverviewActionPanel extends OverviewActionPanel {

	private static final long serialVersionUID = 1L;

	private JButton edit;
	private JButton delete;
	private JButton newReviewer;
	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param id Unique ID from {@link ViewId}
	 */
	public ReviewerOverviewActionPanel(ViewId id, Supplier<int[]> selectedRowIndexSupplier) {
		super(id, selectedRowIndexSupplier);

		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	private void createUIElements() {
		this.edit = new JButton("Bearbeiten");
		this.delete = new JButton("Löschen");
		this.newReviewer = new JButton("Neu");
	}

	private void addUIElements() {
		this.add(edit);
		this.add(delete);
		this.add(newReviewer);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.EDIT, edit, selectedRowIndexSupplier),
				ButtonEventSource.of(EventId.DELETE, delete, selectedRowIndexSupplier),
				ButtonEventSource.of(EventId.NEW, newReviewer));
	}

}
