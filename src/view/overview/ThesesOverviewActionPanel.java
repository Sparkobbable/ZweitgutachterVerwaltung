package view.overview;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;

public class ThesesOverviewActionPanel extends OverviewActionPanel {

	private static final long serialVersionUID = 1L;

	private JButton edit;

	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param id Unique ID from {@link ViewId}
	 */
	public ThesesOverviewActionPanel(ViewId id, Supplier<int[]> selectedRowIndexSupplier) {
		super(selectedRowIndexSupplier);
		
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	private void createUIElements() {
		this.edit = new JButton("Bearbeiten");
	}

	private void addUIElements() {
		this.add(edit);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.EDIT, this.edit, this.selectedRowIndexSupplier));
	}

}
