package controller;

import model.CurrentData;
import view.MainWindow;

public class Controller {
	private CurrentData data;
	
	public Controller() {
		this.data = new CurrentData();
	}
	
	public static void main(String[] args) {
		MainWindow home = new MainWindow();
		home.init();
	}
}
