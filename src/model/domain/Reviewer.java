package model.domain;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import controller.propertychangelistener.ChangeableProperties;
import model.enums.CascadeMode;

public class Reviewer implements ChangeableProperties {

	protected final PropertyChangeSupport propertyChangeSupport;

	// Descriptors
	public static final String NAME = "name";
	public static final String MAX_SUPERVISED_THESES = "maxSupervisedTheses";
	public static final String FIRST_REVIEWS = "firstReviews";
	public static final String SECOND_REVIEWS = "secondReviews";
	public static final String EMAIL = "email";
	public static final String COMMENT = "comment";

	// Data
	private String name;
	private String email;
	private String comment;
	private int maxSupervisedThesis;
	private float occupation;
	private float firstOccupation;
	private float secOccupation;

	private List<FirstReview> firstReviews;
	private List<SecondReview> secondReviews;

	/**
	 * Creates a Reviewer for BachelorThesis
	 * 
	 */
	public Reviewer(String name, int maxSupervisedThesis, String email, String comment) {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.firstReviews = new ArrayList<>();
		this.secondReviews = new ArrayList<>();
		this.name = name;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.email = email;
		this.comment = comment;
	}

	public String getName() {
		return this.name;
	}

	public int getMaxSupervisedThesis() {
		return maxSupervisedThesis;
	}

	public String getEmail() {
		return this.email;
	}

	public String getComment() {
		return this.comment;
	}

	public List<Review> getAllReviews() {
		return Stream.concat(this.firstReviews.stream(), this.secondReviews.stream()).collect(Collectors.toList());
	}

	public int getTotalReviewCount() {
		return this.firstReviews.size() + this.secondReviews.size();
	}

	public float getOccupation() {
		return occupation;
	}

	/**
	 * @return all bachelor theses that are second-reviewed by this reviewer
	 */
	public List<SecondReview> getSecondReviews() {
		return this.secondReviews;
	}

	/**
	 * 
	 * @return all bachelor theses that are first-reviewed by this reviewer
	 * 
	 */
	public List<FirstReview> getFirstReviews() {
		return Collections.unmodifiableList(this.firstReviews);
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.propertyChangeSupport.firePropertyChange(NAME, old, name);
	}

	public void setEmail(String email) {
		String old = this.email;
		this.email = name;
		this.propertyChangeSupport.firePropertyChange(EMAIL, old, email);
	}

	public void setComment(String comment) {
		String old = this.comment;
		this.comment = name;
		this.propertyChangeSupport.firePropertyChange(COMMENT, old, comment);
	}

	public void setMaxSupervisedThesis(int maxSupervisedThesis) {
		int old = this.maxSupervisedThesis;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(MAX_SUPERVISED_THESES, old, maxSupervisedThesis);
	}

	/**
	 * Adds a bachelorThesis to this (Second-)Reviewer .
	 * 
	 * @param bachelorThesis
	 * @param reviewType
	 */
	//TODO remove a similar method exists already
	public void addBachelorThesis(BachelorThesis bachelorThesis) {
		this.addSecondReviewerReview(new SecondReview(this, bachelorThesis), CascadeMode.CASCADE);
	}

	/**
	 * Deletes a collection of SecondReviews.
	 * 
	 * @param reviews
	 */
	public void deleteSecondReviews(Collection<SecondReview> reviews) {
		reviews.forEach(this::deleteSecondReview);
	}

	/**
	 * Add a FirstReview to this Reviewer. If the given CascadeMode is set to
	 * CASCADE, also adds the review to the BachelorThesis referenced within
	 * theReview.
	 * 
	 * @see #addReview(Review, CascadeMode)
	 * 
	 * @param review
	 * @param cascadeMode
	 */
	void addFirstReviewerReview(FirstReview review) {
		ArrayList<Review> old = new ArrayList<>(this.firstReviews);
		this.firstReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEWS, old, this.firstReviews);
	}

	/**
	 * Add a SecondReview to this Reviewer. If the given CascadeMode is set to
	 * CASCADE, also adds the review to the BachelorThesis referenced within
	 * theReview.
	 * 
	 * @see #addReview(Review, CascadeMode)
	 * 
	 * @param review
	 * @param cascadeMode
	 */
	public void addSecondReviewerReview(SecondReview review, CascadeMode cascadeMode) {
		ArrayList<Review> old = new ArrayList<>(this.secondReviews);
		this.secondReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEWS, old, this.secondReviews);
		if (cascadeMode == CascadeMode.CASCADE) {
			review.getBachelorThesis().setSecondReview(review, CascadeMode.STOP);
		}
	}

	private void deleteSecondReview(SecondReview review) {
		ArrayList<SecondReview> old = new ArrayList<>(this.secondReviews);
		this.secondReviews.remove(review);
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEWS, old, this.secondReviews);
	}

	private void updateOppucation() {
		this.occupation = (float) (this.firstReviews.size() + this.secondReviews.size()) / this.maxSupervisedThesis;
		this.firstOccupation = (float) this.firstReviews.size() / this.maxSupervisedThesis;
		this.secOccupation = (float) this.secondReviews.size() / this.maxSupervisedThesis;
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	@Override
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		this.propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * 
	 * @param thesis
	 * @return True if and only if this Reviewer reviews the given thesis as first
	 *         or second reviewer
	 */
	public boolean reviewsThesis(BachelorThesis thesis) {
		return this.getAllReviews().stream().map(Review::getBachelorThesis).anyMatch(t -> t == thesis);
	}

	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * 
	 * @param bachelorThesis
	 * @param reviewType
	 */
	public void removeSecondReviewBachelorThesis(BachelorThesis bachelorThesis) {
		this.getSecondReviews().stream().filter(r -> r.getBachelorThesis() == bachelorThesis).findAny()
				.ifPresent(this::deleteSecondReview);

	}

	public float getFirstOccupation() {
		return this.firstOccupation;
	}

	public float getSecOccupation() {
		return this.secOccupation;
	}

}
