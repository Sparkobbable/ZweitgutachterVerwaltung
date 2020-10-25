package model;

import java.util.ArrayList;

import model.data.Reviewer;

/**
 * Data-Access for the model
 * <p>
 * TODO revisit when saving state gets relevant
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
