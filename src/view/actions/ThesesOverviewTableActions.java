package view.actions;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.eventsources.ButtonEventSource;

public class ThesesOverviewTableActions extends AbstractView {

	private static final long serialVersionUID = 1L;

	private JButton edit;
//	private JButton delete;
//	private JButton newReviewer;
//	private Supplier<?> reviewerName;

	private Supplier<?> thesisIndex;

	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param id Unique ID from {@link ViewId}
	 *           <p>
	 *           TODO From my Understanding, this should not be an AbstractView as
	 *           it is not a top-level-view. Maybe we need to add another Abstract
	 *           class?
	 */
	public ThesesOverviewTableActions(ViewId id, Supplier<?> thesisIndex) {
		super(id, "");
		this.thesisIndex = thesisIndex;
		
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); // TODO Not quite sure which is the best layout
		this.setBackground(Color.CYAN);
		
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
		return List.of(ButtonEventSource.of(EventId.EDIT, this.edit, this.thesisIndex));
	}

}
