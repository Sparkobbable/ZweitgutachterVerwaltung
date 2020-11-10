package model.data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.IntStream;

import model.AbstractModel;

@SuppressWarnings("deprecation")
public class Reviewer extends AbstractModel {
	private String name;
	private int maxSupervisedThesis;
	private float occupation;
	private ArrayList<BachelorThesis> supervised;
	
	/**
	 * Creates a empty Reviewer
	 */
	public Reviewer() {
		this.supervised = new ArrayList<BachelorThesis>();
	}
	
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
		this.addBachelorThesis(bachelorThesis);
		this.supervised = supervised;
	}

	public void addBachelorThesis(BachelorThesis bachelorThesis) {
		this.supervised.add(bachelorThesis);
		this.occupation = (float) this.supervised.size() / this.maxSupervisedThesis;
		this.setChanged();
		this.notifyObservers();
	}
	
	/**
	 * Never add a Thesis by adding it to the by this method returned ArrayList.
	 * Use instead {@link Reviewer#addBachelorThesis(BachelorThesis)}
	 * @return Returns the ArrayList of supervised bachelorThesis only for reading.
	 */
	public ArrayList<BachelorThesis> getSupervisedThesis() {
		return this.supervised;
	}
	
	public void removeThesisByIndices(int[] thesisIndices) {
		IntStream.of(thesisIndices).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder()).forEach(thesisIdx -> removeThesisByIndex(thesisIdx));
	}
	
	public void removeThesisByIndex(int thesisIdx) {
		this.supervised.remove(thesisIdx);
		this.setChanged();
		this.notifyObservers();
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
		this.setChanged();
		this.notifyObservers(this);
	}

	public int getMaxSupervisedThesis() {
		return maxSupervisedThesis;
	}

	public void setMaxSupervisedThesis(int maxSupervisedThesis) {
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.occupation = (float) this.supervised.size() / this.maxSupervisedThesis;
		this.setChanged();
		this.notifyObservers();
	}

	public float getOccupation() {
		return occupation;
	}
}
