package main;

import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import model.Model;
import model.data.BachelorThesis;
import model.data.Reviewer;
import view.View;

public class Main {

	//TODO move to main.Main
		public static void main(String[] args) {
			Model data = mockReviewerList();
			View view = new View(data);
			new Controller(data, view);
			view.setVisible();
		}


	/**
	 * Creates a sample list of reviewers
	 * <p>
	 * TODO add valid BachelorThesises TODO remove this before deploying to prod
	 */
	private static Model mockReviewerList() {
		List<Reviewer> reviewerList = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			Reviewer reviewer = new Reviewer(String.format("Dozent #%d", i));
			int rand = (int) (Math.random() * 5);
			for (int j = 0; j < rand; j++) {
				reviewer.addBachelorThesis(new BachelorThesis(null, null, null));
			}
			reviewerList.add(reviewer);
		}
		return new Model(reviewerList);
	}
}
