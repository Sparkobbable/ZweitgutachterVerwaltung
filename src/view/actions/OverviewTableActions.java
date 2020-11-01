package view.actions;

import java.awt.Color;
import java.awt.FlowLayout;
import java.util.List;
import java.util.Observable;
import java.util.function.Supplier;

import javax.swing.JButton;

import model.EventSource;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.eventsources.ButtonEventSource;

@SuppressWarnings("deprecation")
public class OverviewTableActions extends AbstractView {

	private static final long serialVersionUID = 1L;

	private JButton edit;
	private JButton delete;
	private JButton newReviewer;
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
	public OverviewTableActions(ViewId id, Supplier<?> reviewerName) {
		super(id, "");
		this.reviewerName = reviewerName;
		this.setBorder(UNTITLED_BORDER);

		this.createUIElements();
		this.registerEventSources();
	}

	@Override
	public void init() {
		this.setLayout(new FlowLayout()); // TODO Not quite sure which is the best layout
		this.setBackground(Color.CYAN);
		this.add(edit);
		this.add(delete);
		this.add(newReviewer);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new ButtonEventSource(EventId.EDIT, edit, reviewerName), 
						new ButtonEventSource(EventId.DELETE, delete, reviewerName),
						new ButtonEventSource(EventId.NEW, newReviewer));
	}

	@Override
	protected void createUIElements() {
		this.edit = new JButton("Bearbeiten");
		this.delete = new JButton("Löschen");
		this.newReviewer = new JButton("Neu");
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}

}
