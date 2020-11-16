package model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import model.data.Author;
import model.data.BachelorThesis;
import model.data.Review;
import model.data.Reviewer;
import model.enums.ApplicationState;

/**
 * Data store for all reviewers
 * <p>
 */
public class Model implements ChangeableProperties, PropertyChangeListener {

	public static final String APPLICATION_STATE = "applicationState";
	public static final String SELECTED_REVIEWER = "selectedReviewer";
	public static final String REVIEWERS = "reviewers";
	public static final String THESES = "theses";

	protected final PropertyChangeSupport propertyChangeSupport;

	private List<BachelorThesis> theses;
	private List<Reviewer> reviewers;
	private Optional<Reviewer> selectedReviewer;
	private ApplicationState applicationState;

	private PropertyChangeManager propertyChangeManager;

	public Model(List<BachelorThesis> theses, List<Reviewer> reviewers) {
		this();
		theses.forEach(this::addThesis);
		reviewers.forEach(this::addReviewer);
	}

	public Model(List<BachelorThesis> theses) {
		this();
		theses.forEach(this::addThesis);
	}

	/**
	 * Creates a new Model without any theses or reviewers.
	 */
	public Model() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.propertyChangeManager = new PropertyChangeManager();
		this.selectedReviewer = Optional.empty();
		this.theses = new ArrayList<>();
		this.reviewers = new ArrayList<>();

		this.initializePropertyChangeHandlers();

	}

	/**
	 * Returns a reviewer with the given name, if any exist.
	 * 
	 * @param name
	 * @return
	 */
	public Optional<Reviewer> findReviewerByName(String name) {
		return this.reviewers.stream().filter(reviewers -> reviewers.getName().equals(name)).findAny();
	}

	/**
	 * @return The current State of the Application
	 */
	public ApplicationState getApplicationState() {
		return applicationState;
	}

	/**
	 * @return The selected Reviewer, if any exist
	 */
	public Optional<Reviewer> getSelectedReviewer() {
		return selectedReviewer;
	}

	/**
	 * Set the ApplicationState and notify any observers
	 * 
	 * @param applicationState
	 */
	public void setApplicationState(ApplicationState applicationState) {
		ApplicationState old = this.applicationState;
		this.applicationState = applicationState;
		this.propertyChangeSupport.firePropertyChange(APPLICATION_STATE, old, this.applicationState);
	}

	/**
	 * Set the selected Reviewer and notify any observers
	 * 
	 * @param selectedReviewer
	 */
	public void setSelectedReviewer(Reviewer selectedReviewer) {
		Optional<Reviewer> old = this.selectedReviewer;
		this.selectedReviewer = Optional.ofNullable(selectedReviewer);
		this.propertyChangeSupport.firePropertyChange(SELECTED_REVIEWER, old, this.selectedReviewer);
	}

	/**
	 * Set the selected Reviewer by its index in the reviewers list
	 * 
	 * @param reviewerIndex
	 */
	public void setSelectedReviewer(int reviewerIndex) {
		this.setSelectedReviewer(this.reviewers.get(reviewerIndex));
	}

	/**
	 * Add a reviewer to the reviewers list if it is not already included therein.
	 * If the reviewer references any theses not included in the theses list, those
	 * are added there as well. Notify observers.
	 * 
	 * @param reviewer
	 */
	public void addReviewer(Reviewer reviewer) {
		if (this.reviewers.contains(reviewer)) {
			return;
		}

		List<Reviewer> old = new ArrayList<>(this.reviewers);
		this.reviewers.add(reviewer);
		reviewer.addPropertyChangeListener(this);
		this.addThesesForReviewer(reviewer);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	/**
	 * Add a thesis to the theses list if it is not already included therein. If the
	 * thesis references any reviewers not included in the reviewers list, those are
	 * added there as well. Notify observers.
	 * 
	 * @param thesis
	 */
	public void addThesis(BachelorThesis thesis) {
		if (this.theses.contains(thesis)) {
			return;
		}
		List<BachelorThesis> old = new ArrayList<>(this.theses);
		this.theses.add(thesis);
		thesis.addPropertyChangeListener(this);
		this.addReviewersForThesis(thesis);
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);
	}

	/**
	 * @return A read-only view of the reviewer list
	 */
	public List<Reviewer> getReviewers() {
		return Collections.unmodifiableList(this.reviewers);
	}

	/**
	 * @return A read-only view of the theses list
	 */
	public List<BachelorThesis> getTheses() {
		return Collections.unmodifiableList(this.theses);
	}

	/**
	 * Search a thesis for the given attributes. If not already existing, create a
	 * new one
	 * 
	 * @param topic         topic attribute of the thesis
	 * @param author        author attribute of the thesis
	 * @param firstreviewer firstreviewer of the thesis
	 * @return BachelorThesis that is linked within the model
	 */
	public Optional<BachelorThesis> findThesis(String topic, Author author, Reviewer firstreviewer) {
		for (BachelorThesis thesis : this.getTheses()) {
			if (thesis.getTopic().equals(topic) && thesis.getAuthor().equals(author)
					&& thesis.getFirstReview().getReviewer().equals(firstreviewer)) {
				return Optional.of(thesis);
			}
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @return A list of theses with a missing second Reviewer.
	 */
	public List<BachelorThesis> getThesisMissingSecReview() {
		return this.getTheses().stream().filter(thesis -> thesis.getSecondReview().isEmpty())
				.collect(Collectors.toList());
	}

	public void updateReviewer(Reviewer reviewer, Reviewer newReviewer) {
		this.reviewers.set(this.reviewers.indexOf(reviewer), newReviewer); // TODO hacky, waits for command pattern
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		this.propertyChangeManager.propertyChange(evt);
	}

	/**
	 * Defines the methods that should be called when an observed property is
	 * changed
	 */
	@SuppressWarnings("unchecked")
	private void initializePropertyChangeHandlers() {
		this.propertyChangeManager.onPropertyChange(Reviewer.FIRST_REVIEWS,
				(evt) -> addThesesFromReviews((List<Review>) evt.getNewValue()));
		this.propertyChangeManager.onPropertyChange(Reviewer.SECOND_REVIEWS,
				(evt) -> addThesesFromReviews((List<Review>) evt.getNewValue()));
		this.propertyChangeManager.onPropertyChange(BachelorThesis.FIRST_REVIEW,
				(evt) -> addReviewer(((Review) evt.getNewValue()).getReviewer()));
		this.propertyChangeManager.onPropertyChange(BachelorThesis.SECOND_REVIEW,
				(evt) -> ((Optional<Review>) evt.getNewValue()).map(Review::getReviewer).ifPresent(this::addReviewer));
	}

	/**
	 * Adds all theses from a list of reviews
	 * 
	 * @see #addThesis(BachelorThesis)
	 * @param reviews
	 */
	private void addThesesFromReviews(List<Review> reviews) {
		reviews.stream().map(Review::getBachelorThesis).forEach(this::addThesis);
	}

	/**
	 * Add all reviewers referenced by the given thesis to the reviewers list
	 * 
	 * @param thesis
	 */
	private void addReviewersForThesis(BachelorThesis thesis) {
		this.addReviewer(thesis.getFirstReview().getReviewer());
		thesis.getSecondReview().ifPresent(secondReview -> this.addReviewer(secondReview.getReviewer()));
	}

	/**
	 * Add all theses referenced by the given reviewer to the theses list
	 * 
	 * @param reviewer
	 */
	private void addThesesForReviewer(Reviewer reviewer) {
		reviewer.getAllReviews().stream().map(Review::getBachelorThesis).forEach(this::addThesis);
	}

	private void clearReviewers() {
		List<Reviewer> old = this.reviewers;
		this.reviewers = new ArrayList<>();
		this.propertyChangeSupport.firePropertyChange(REVIEWERS, old, this.reviewers);

	}

	public void clear() {
		this.clearReviewers();
		this.theses = new ArrayList<>();
		this.setSelectedReviewer(null);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}

}
