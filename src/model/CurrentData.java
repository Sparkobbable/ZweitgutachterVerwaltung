package model;

import java.util.ArrayList;

/**
 * Data-Access for the model
 * <p>
 * TODO decide if we need really this all-knowing data store
 * 
 */
@SuppressWarnings("deprecation")
public class CurrentData extends AbstractModel {
	private ArrayList<Reviewer> reviewer;

	public CurrentData() {
		this.reviewer = new ArrayList<Reviewer>();
	}
	
	public void addReviewer(Reviewer reviewer) {
		this.reviewer.add(reviewer);
		this.notifyObservers();
		this.setChanged();
	}

	public ArrayList<Reviewer> getReviewer() {
		return reviewer;
	}

	public void setReviewer(ArrayList<Reviewer> reviewer) {
		this.reviewer = reviewer;
		this.notifyObservers();
		this.setChanged();
	}
}
