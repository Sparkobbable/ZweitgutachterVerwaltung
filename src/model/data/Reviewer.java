package model.data;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.IntStream;

import model.ChangeableProperties;

public class Reviewer implements ChangeableProperties {

	private PropertyChangeSupport propertyChangeSupport;

	private String name;
	private int maxSupervisedThesis;
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

	public void addBachelorThesis(BachelorThesis bachelorThesis) {
		ArrayList<BachelorThesis> old = new ArrayList<>(this.supervised);
		this.supervised.add(bachelorThesis);
		this.propertyChangeSupport.firePropertyChange("supervisedTheses", old, this.supervised);
	}

	public ArrayList<BachelorThesis> getSupervisedThesis() {
		return this.supervised;
	}

	public void removeThesisByIndices(int[] thesisIndices) {
		ArrayList<BachelorThesis> old = new ArrayList<>(this.supervised);
		IntStream.of(thesisIndices).mapToObj(Integer::valueOf).sorted(Comparator.reverseOrder())
				.mapToInt(Integer::intValue).forEach(this.supervised::remove);
		this.propertyChangeSupport.firePropertyChange("supervisedTheses", old, this.supervised);
	}

	public void removeThesisByIndex(int thesisIdx) {
		ArrayList<BachelorThesis> old = new ArrayList<>(this.supervised);
		this.supervised.remove(thesisIdx);
		this.propertyChangeSupport.firePropertyChange("supervisedTheses", old, this.supervised);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		String old = this.name;
		this.name = name;
		this.propertyChangeSupport.firePropertyChange("name", old, name);
	}

	public int getMaxSupervisedThesis() {
		return maxSupervisedThesis;
	}

	public void setMaxSupervisedThesis(int maxSupervisedThesis) {
		int old = this.maxSupervisedThesis;
		this.maxSupervisedThesis = maxSupervisedThesis;
		this.propertyChangeSupport.firePropertyChange("maxSupervisedThesis", old, maxSupervisedThesis);
	}

	@Override
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		this.propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}
}
