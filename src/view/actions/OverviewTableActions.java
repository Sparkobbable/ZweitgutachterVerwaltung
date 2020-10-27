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

public class OverviewTableActions extends AbstractView {

	private static final long serialVersionUID = 1L;

	private JButton edit;
	private Supplier<?>[] reviewerNames;

	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param id Unique ID from
	 *           <p>
	 *           TODO From my Understanding, this should not be an AbstractView as
	 *           it is not a top-level-view. Maybe we need to add another Abstract
	 *           class?
	 */
	public OverviewTableActions(ViewId id, Supplier<?>... reviewerNames) {
		super(id, "");
		this.reviewerNames = reviewerNames;
		this.setBorder(UNTITLED_BORDER);

		this.createUIElements();
		this.registerEventSources();
	}

	@Override
	public void init() {
		this.setLayout(new FlowLayout()); // TODO Not quite sure which is the best layout
		this.setBackground(Color.CYAN);
		this.add(edit);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.EDIT, edit, reviewerNames));
	}

	@Override
	protected void createUIElements() {
		this.edit = new JButton("Bearbeiten");
	}

}
