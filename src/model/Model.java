package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import model.data.Reviewer;

/**
 *	Data store for all reviewers 
 * <p>
 * TODO extend AbstractModel??
 */
@SuppressWarnings("deprecation") // using the Observable pattern and therefore Observable is encouraged in this project
public class Model extends Observable{

	private List<Reviewer> reviewers;

	public Model() {
		this.reviewers = new ArrayList<>();
	}
	
	public Model(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}
	
	public List<Reviewer> getReviewers() {
		return reviewers;
	}

	public void setReviewers(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}
	
	public void addReviewer(Reviewer reviewer) {
		this.reviewers.add(reviewer);
		this.setChanged();
		this.notifyObservers();
	}

	public List<Reviewer> getReviewer() {
		return reviewers;
	}

	public void setReviewer(List<Reviewer> reviewer) {
		this.reviewers = reviewer;
		this.setChanged();
		this.notifyObservers();
	}
	
	public Reviewer findReviewerByName(String name) {
		for (Reviewer reviewer : this.reviewers) {
			if (reviewer.getName().equals(name)) {
				return reviewer;
			}
		}
		return null;
	}
	
}
