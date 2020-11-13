package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import model.ChangeableProperties;
import model.enums.CascadeMode;
import model.enums.ReviewType;

public class Reviewer implements ChangeableProperties {

	private PropertyChangeSupport propertyChangeSupport;

	// Descriptors
	public static final String NAME = "name";
	public static final String MAX_SUPERVISED_THESES = "maxSupervisedTheses";
	public static final String FIRST_REVIEWS = "firstReviews";
	public static final String SECOND_REVIEWS = "secondReviews";
	public static final String EMAIL = "email";
	public static final String COMMENT = "comment";

	// Data
	private String name;
	private int maxSupervisedThesis;
	private float occupation;
	private ArrayList<FirstReview> firstReviews;
	private ArrayList<SecondReview> secondReviews;

	private String email;
	private String comment;

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
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(MAX_SUPERVISED_THESES, old, maxSupervisedThesis);
	}

	public void addReview(Review review, CascadeMode cascadeMode) {
		if (review.getReviewType() == ReviewType.FIRST_REVIEW) {
			this.addFirstReviewerReview((FirstReview) review, cascadeMode);
		} else if (review.getReviewType() == ReviewType.SECOND_REVIEW) {
			this.addSecondReviewerReview((SecondReview) review, cascadeMode);
		} else {
			throw new IllegalArgumentException(String.format(
					"ReviewType must be one of FIRST_REVIEW or SECOND_REVIEW, but was %s. Review cannot be added to Reviewer.",
					review.getReviewType()));
		}
	}

	public void addBachelorThesis(BachelorThesis bachelorThesis, ReviewType reviewType) {
		if (reviewType == ReviewType.FIRST_REVIEW) {
			this.addReview(new FirstReview(this, bachelorThesis), CascadeMode.CASCADE);
		} else if (reviewType == ReviewType.SECOND_REVIEW) {
			this.addReview(new SecondReview(this, bachelorThesis), CascadeMode.CASCADE);
		} else {
			throw new IllegalArgumentException(String.format(
					"ReviewType must be one of FIRST_REVIEW or SECOND_REVIEW, but was %s. Review cannot be added to Reviewer.",
					reviewType));
		}
	}

	public void addFirstReviewerReview(FirstReview review, CascadeMode cascadeMode) {
		ArrayList<Review> old = new ArrayList<>(this.firstReviews);
		this.firstReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEWS, old, this.firstReviews);
		if (cascadeMode == CascadeMode.CASCADE) {
			review.getBachelorThesis().setFirstReview(review, CascadeMode.STOP);
		}
	}

	public void addSecondReviewerReview(SecondReview review, CascadeMode cascadeMode) {
		ArrayList<Review> old = new ArrayList<>(this.firstReviews);
		this.secondReviews.add(review);
		this.updateOppucation();
		this.propertyChangeSupport.firePropertyChange(SECOND_REVIEWS, old, this.firstReviews);
		if (cascadeMode == CascadeMode.CASCADE) {
			review.getBachelorThesis().setSecondReview(review, CascadeMode.STOP);
		}
	}

	public void deleteReviews(Collection<Review> reviews) {
		reviews.forEach(this::deleteReview);
	}

	public void deleteReview(Review review) {
		if (review.getReviewType() == ReviewType.FIRST_REVIEW) {
			this.deleteFirstReview((FirstReview) review);
		} else if (review.getReviewType() == ReviewType.SECOND_REVIEW) {
			this.deleteSecondReview((SecondReview) review);
		}
	}

	private void deleteFirstReview(FirstReview review) {
		ArrayList<FirstReview> old = new ArrayList<>(this.firstReviews);
		this.firstReviews.remove(review);
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEWS, old, this.firstReviews);
	}

	private void deleteSecondReview(SecondReview review) {
		ArrayList<SecondReview> old = new ArrayList<>(this.secondReviews);
		this.secondReviews.remove(review);
		this.propertyChangeSupport.firePropertyChange(FIRST_REVIEWS, old, this.secondReviews);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public float getOccupation() {
		return occupation;
	}

	/**
	 * Searches for all Bachelortheses that are second-reviewed by this reviewer
	 * 
	 * @return List of Bachelortheses with second review
	 */
	public List<SecondReview> getSecondReviews() {
		return this.secondReviews;
	}

	private void updateOppucation() {
		this.occupation = (float) (this.firstReviews.size() + this.secondReviews.size()) / this.maxSupervisedThesis;
	}

	public List<Review> getSupervisedTheses() {
		return Stream.concat(this.firstReviews.stream(), this.secondReviews.stream()).collect(Collectors.toList());
	}

	public int getSupervisedThesesSize() {
		return this.firstReviews.size() + this.secondReviews.size();
	}

}
