package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.IntStream;

import model.ChangeableProperties;

public class Reviewer implements ChangeableProperties {

	private PropertyChangeSupport propertyChangeSupport;

	// Descriptors
	private static final String NAME = "name";
	private static final String MAX_SUPERVISED_THESES = "maxSupervisedTheses";
	private static final String SUPERVISED_THESES = "supervisedTheses";

	// Data
	private String name;
	private int maxSupervisedThesis;
	private float occupation;
	private ArrayList<BachelorThesis> supervised;

	/**
	 * Creates an empty Reviewer
	 */
	public Reviewer() {
		this.propertyChangeSupport = new PropertyChangeSupport(this);
		this.supervised = new ArrayList<BachelorThesis>();
	}

	/**
	 * Creates a Reviewer for BachelorThesis
	 * 
	 * @param name Name of the Reviewer
	 */
	public Reviewer(String name) {
		this();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public int getMaxSupervisedThesis() {
		return maxSupervisedThesis;
	}

	/**
	 * Never add a Thesis by adding it to the by this method returned ArrayList.
	 * Use instead {@link Reviewer#addBachelorThesis(BachelorThesis)}
	 * @return Returns the ArrayList of supervised bachelorThesis only for reading.
	 */
	public ArrayList<BachelorThesis> getSupervisedThesis() {
		return this.supervised;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.propertyChangeSupport.firePropertyChange(NAME, old, name);
	}

	public void setMaxSupervisedThesis(int maxSupervisedThesis) {
		int old = this.maxSupervisedThesis;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.occupation = (float) this.supervised.size() / this.maxSupervisedThesis;
		this.propertyChangeSupport.firePropertyChange(MAX_SUPERVISED_THESES, old, maxSupervisedThesis);
	}

	public void addBachelorThesis(BachelorThesis bachelorThesis) {
		ArrayList<BachelorThesis> old = new ArrayList<>(this.supervised);
		this.supervised.add(bachelorThesis);
		this.occupation = (float) this.supervised.size() / this.maxSupervisedThesis;
		this.propertyChangeSupport.firePropertyChange(SUPERVISED_THESES, old, this.supervised);
	}

	public void removeThesisByIndex(int thesisIdx) {
		ArrayList<BachelorThesis> old = new ArrayList<>(this.supervised);
		this.supervised.remove(thesisIdx);
		this.propertyChangeSupport.firePropertyChange(SUPERVISED_THESES, old, this.supervised);
	}

	public void removeThesisByIndices(int[] thesisIndices) {
		ArrayList<BachelorThesis> old = new ArrayList<>(this.supervised);
		IntStream.of(thesisIndices).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder())
				.mapToInt(Integer::intValue).forEach(this.supervised::remove);
		this.propertyChangeSupport.firePropertyChange(SUPERVISED_THESES, old, this.supervised);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public float getOccupation() {
		return occupation;
	}
}
