package controller;

import model.CurrentData;
import model.Reviewer;
import view.MainWindow;

public class Controller {
	private CurrentData data;
	private MainWindow home;
	
	public Controller() {
		this.data = new CurrentData();
		home = new MainWindow();
		//Mock:
		Reviewer reviewer1 = new Reviewer("KlauPe Wennemann7Stellig");
		this.data.getReviewer().add(reviewer1);
	}
	
	public static void main(String[] args) {
		Controller controller = new Controller();
		controller.initWindow();
		
	}

	private void initWindow() {
		home.init(data);		
	}
}
