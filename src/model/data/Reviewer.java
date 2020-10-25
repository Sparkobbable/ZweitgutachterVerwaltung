package model.data;

import java.util.ArrayList;

import model.AbstractModel;

@SuppressWarnings("deprecation")
public class Reviewer extends AbstractModel {
	private String name;
	private ArrayList<BachelorThesis> supervised;
	
	/**
	 * Creates a Reviewer for BachelorThesis
	 * @param name Name of the Reviewer
	 */
	public Reviewer(String name) {
		this.name = name;
		this.supervised = new ArrayList<BachelorThesis>();
	}

	/**
	 * Creates a Reviewer for BachelorThesis with one supervised thesis
	 * @param name Name of the Reviewer
	 * @param bachelorThesis Supervised BachelorThesis
	 */
	public Reviewer(String name, BachelorThesis bachelorThesis) {
		this.name = name;
		ArrayList<BachelorThesis> supervised = new ArrayList<BachelorThesis>();
		supervised.add(bachelorThesis);
		this.supervised = supervised;
	}
	
	public void addBachelorThesis(BachelorThesis bachelorThesis) {
		this.supervised.add(bachelorThesis);
		this.notifyObservers();
		this.setChanged();
	}
	
	public ArrayList<BachelorThesis> getSupervisedThesis() {
		return this.supervised;
	}
	
	public String getName() {
		return this.name;
	}
}
