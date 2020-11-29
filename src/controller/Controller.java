package controller;

import model.Model;
import view.View;

/**
 * Controls the application flow
 *
 */
public class Controller {
	private Model data;
	private View view;
	private ApplicationStateController applicationStateController;

	public Controller(Model data, View view) {
		this.data = data;
		this.view = view;
		this.applicationStateController = new ApplicationStateController(data, view);
		
		//TODO expand or remove this class
	}

}
