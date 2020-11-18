package view.overview;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComboBox;

import controller.events.EventSource;
import model.Model;
import model.domain.Reviewer;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;

public class ThesesOverviewActionPanel extends OverviewActionPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox<Reviewer> reviewers;
	private JButton select;

	private Model model;

	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param id Unique ID from {@link ViewId}
	 */
	@SuppressWarnings("unchecked")
	public ThesesOverviewActionPanel(Model model, Supplier<int[]> selectedRowIndexSupplier) {
		super(selectedRowIndexSupplier);
		this.model = model;
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();

		this.observe(model);
		this.observe(model.getReviewers());
		this.onPropertyChange(Model.REVIEWERS,
				(evt) -> updateReviewers((List<Reviewer>) evt.getOldValue(), (List<Reviewer>) evt.getNewValue()));
	}

	private void updateReviewers(List<Reviewer> oldValue, List<Reviewer> newValue) {
		this.stopObserving(oldValue);
		this.reviewers.removeAllItems();
		this.addFilteredReviewers(newValue);
		this.observe(newValue);
		this.repaint();
	}

	protected void addFilteredReviewers(List<Reviewer> newValue) {
		newValue.stream().filter(r -> r.getTotalReviewCount() < r.getMaxSupervisedThesis())
				.forEach(this.reviewers::addItem);
	}

	private void createUIElements() {
		this.select = new JButton("Als Zweitgutachter hinzufügen");
		this.reviewers = new JComboBox<>();
		this.addFilteredReviewers(this.model.getReviewers());
	}

	private void addUIElements() {
		this.add(reviewers);
		this.add(select);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.SELECT, this.select, this.selectedRowIndexSupplier,
				this.reviewers::getSelectedItem));
	}

}
