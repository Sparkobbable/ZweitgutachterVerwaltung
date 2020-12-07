package view.panels.overview;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JComboBox;

import controller.events.EventSource;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.enums.EventId;
import view.eventsources.ButtonEventSource;
import view.widgets.StackedBarChartWidget;
import view.panels.prototypes.AbstractActionPanel;

public class ThesesOverviewActionPanel extends AbstractActionPanel<BachelorThesis> {

	private static final long serialVersionUID = 1L;

	private JComboBox<Reviewer> reviewers;
	private JButton select;
	private StackedBarChartWidget stackedBarChartWidget;

	private Model model;

	/**
	 * Provides a section with several buttons for interaction with the table.
	 * 
	 * @param selectedElementsSupplier supplies the currently selected
	 *                                 {@link BachelorThesis}s
	 */
	@SuppressWarnings("unchecked")
	public ThesesOverviewActionPanel(Model model, Supplier<List<BachelorThesis>> selectedElementsSupplier) {
		super(selectedElementsSupplier);
		this.model = model;
		this.createUIElements();
		this.addUIElements();
		this.registerEventSources();

		this.observe(model);
		this.observe(model.getReviewers());
		this.onPropertyChange(Model.REVIEWERS,
				(evt) -> updateReviewers((List<Reviewer>) evt.getOldValue(), (List<Reviewer>) evt.getNewValue()));
		this.reviewers.addActionListener(e -> updateReviewerPreview());
		this.select.addActionListener(e -> updateReviewerPreview());
	}

	protected void updateReviewerPreview() {
		Reviewer reviewer = (Reviewer) this.reviewers.getSelectedItem();
		if (reviewer == null) {
			return;
		}
		this.stackedBarChartWidget.update(reviewer.getFirstReviews().size(),
				reviewer.getUnrejectedSecondReviews().size(), reviewer.getMaxSupervisedThesis());
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
		this.select = this.buttonFactory.createButton("Als Zweitgutachter hinzufügen");
		this.stackedBarChartWidget = StackedBarChartWidget.getInstance();

		this.reviewers = new JComboBox<>();
		this.reviewers.addActionListener(e -> this.updateReviewerPreview());

		this.addFilteredReviewers(this.model.getReviewers());
	}

	private void addUIElements() {
		this.add(reviewers);
		this.add(stackedBarChartWidget);
		this.add(select);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.SELECT, this.select, this.selectedElementsSupplier,
				this.reviewers::getSelectedItem));
	}

}
