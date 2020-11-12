package view.actions;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.eventsources.ButtonEventSource;
import view.panelstructure.DefaultViewPanel;

public class OverviewTableActions extends DefaultViewPanel {

	private static final long serialVersionUID = 1L;

	private JButton edit;
	private JButton delete;
	private JButton newReviewer;
	private JButton collaboration;
	private Supplier<?> reviewerName;

	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param id Unique ID from {@link ViewId}
	 *           <p>
	 *           TODO From my Understanding, this should not be an AbstractView as
	 *           it is not a top-level-view. Maybe we need to add another Abstract
	 *           class?
	 */
	public OverviewTableActions(Supplier<?> reviewerName) {
		super("");
		this.reviewerName = reviewerName;
		
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); // TODO Not quite sure which is the best layout
		this.setBackground(Color.CYAN);
		
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();
	}

	private void createUIElements() {
		this.edit = new JButton("Bearbeiten");
		this.delete = new JButton("Löschen");
		this.newReviewer = new JButton("Neu");
		this.collaboration = new JButton("Zusammenarbeit anzeigen");
	}

	private void addUIElements() {
		this.add(edit);
		this.add(delete);
		this.add(newReviewer);
		this.add(collaboration);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.EDIT, edit, reviewerName),
				ButtonEventSource.of(EventId.DELETE, delete, reviewerName),
				ButtonEventSource.of(EventId.NEW, newReviewer),
				ButtonEventSource.of(EventId.SHOW_COLLABORATION, collaboration, reviewerName));
	}

}
