package model.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import model.AbstractModel;

@SuppressWarnings("deprecation")
public class Reviewer extends AbstractModel {
	private String name;
	private ArrayList<BachelorThesis> supervised;
	private Optional<BachelorThesis> selectedThesis;
	
	/**
	 * Creates a empty Reviewer
	 */
	public Reviewer() {
		this.supervised = new ArrayList<BachelorThesis>();
		this.selectedThesis = Optional.empty();
	}
	
	/**
	 * Creates a Reviewer for BachelorThesis
	 * @param name Name of the Reviewer
	 */
	public Reviewer(String name) {
		this.name = name;
		this.supervised = new ArrayList<BachelorThesis>();
		this.selectedThesis = Optional.empty();
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
		this.selectedThesis = Optional.empty();
	}
	
	public Optional<BachelorThesis> getSelectedThesis() {
		return selectedThesis;
	}

	public void setSelectedThesis(BachelorThesis selectedThesis) {
		this.selectedThesis = Optional.of(selectedThesis);
		this.setChanged();
		this.notifyObservers();
	}

	public void addBachelorThesis(BachelorThesis bachelorThesis) {
		this.supervised.add(bachelorThesis);
		this.setChanged();
		this.notifyObservers();
	}
	
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
}
