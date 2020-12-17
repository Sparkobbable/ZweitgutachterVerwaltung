package view.panels.overview;

import java.awt.Color;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import controller.events.EventSource;
import model.Model;
import model.domain.BachelorThesis;
import model.domain.Reviewer;
import model.domain.SecondReview;
import model.enums.EventId;
import model.enums.ReviewStatus;
import view.eventsources.ButtonEventSource;
import view.panels.prototypes.AbstractActionPanel;
import view.widgets.StackedBarChartWidget;

/**
 * {@link AbstractActionPanel} that is responsible for supplying actions in the {@link ThesesOverviewPanel}.
 */
public class ThesesOverviewActionPanel extends AbstractActionPanel<BachelorThesis> {

	private static final long serialVersionUID = 1L;

	private JComboBox<Reviewer> reviewers;
	private JButton select;
	private StackedBarChartWidget stackedBarChartWidget;
	private JLabel acceptedReviewsLabel;

	private Model model;

	/**
	 * Provides a section with several {@link JButton} for interaction with the table.
	 * 
	 * @param selectedElementsSupplier supplies the currently selected (list of) 
	 *                                 {@link BachelorThesis}
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

		this.onPropertyChange(Reviewer.MAX_SUPERVISED_THESES, evt -> updateReviewerPreview());
		this.onPropertyChange(Reviewer.SECOND_REVIEWS,
				(evt) -> updateSecondReviews((List<SecondReview>) evt.getOldValue(),
						(List<SecondReview>) evt.getNewValue()));
		this.onPropertyChange(Reviewer.MAX_SUPERVISED_THESES, (evt) -> updateReviewerPreview());
		this.onPropertyChange(SecondReview.STATUS, (evt) -> updateReviewerPreview());

		this.reviewers.addActionListener(e -> updateReviewerPreview());
		this.updateReviewerPreview();
	}

	private void updateSecondReviews(List<SecondReview> oldValue, List<SecondReview> newValue) {
		this.stopObserving(oldValue);
		this.observe(newValue);
		this.updateReviewerPreview();
	}

	private void updateReviewerPreview() {
		Reviewer reviewer = (Reviewer) this.reviewers.getSelectedItem();
		if (reviewer == null) {
			this.acceptedReviewsLabel.setVisible(false);
			this.stackedBarChartWidget.setVisible(false);
			return;
		}

		this.acceptedReviewsLabel.setText(String.format("Akzeptierte Gutachten: %d / %d; angefragt: %d",
				reviewer.getReviewCountForStatus(ReviewStatus.APPROVED) + reviewer.getFirstReviewCount(),
				reviewer.getMaxSupervisedThesis(),
				reviewer.getReviewCountForStatus(ReviewStatus.REQUESTED, ReviewStatus.RESERVED)));

		this.acceptedReviewsLabel.setVisible(true);
		this.stackedBarChartWidget.setVisible(true);

		this.stackedBarChartWidget.update(reviewer.getFirstReviews().size(),
				reviewer.getUnrejectedSecondReviews().size(), reviewer.getMaxSupervisedThesis());
	}

	private void updateReviewers(List<Reviewer> oldValue, List<Reviewer> newValue) {
		this.stopObserving(oldValue);
		this.reviewers.removeAllItems();
		newValue.stream().forEach(this.reviewers::addItem);
		this.updateSecondReviews(
				oldValue.stream().flatMap(r -> r.getUnrejectedSecondReviews().stream()).collect(Collectors.toList()),
				newValue.stream().flatMap(r -> r.getUnrejectedSecondReviews().stream()).collect(Collectors.toList()));
		newValue.stream().flatMap(r -> r.getUnrejectedSecondReviews().stream()).forEach(this::observe);
		this.repaint();
		this.observe(newValue);

	}

	private void createUIElements() {
		this.select = this.buttonFactory.createButton("Als Zweitgutachter hinzufügen");
		this.stackedBarChartWidget = StackedBarChartWidget.getInstance();
		this.acceptedReviewsLabel = new JLabel();
		this.acceptedReviewsLabel.setForeground(Color.WHITE);

		this.reviewers = new JComboBox<>();
		this.reviewers.addActionListener(e -> this.updateReviewerPreview());

		this.model.getReviewers().stream().forEach(this.reviewers::addItem);

	}

	private void addUIElements() {
		this.add(reviewers);
		this.add(select);
		this.add(stackedBarChartWidget);
		this.add(acceptedReviewsLabel);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(ButtonEventSource.of(EventId.SELECT, this.select, this.selectedElementsSupplier,
				this.reviewers::getSelectedItem));
	}

}
