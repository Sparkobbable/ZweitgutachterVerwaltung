package controller;

public class AbstractSubController {
	protected Controller mainController;
	
	protected AbstractSubController(Controller mainController)
	{
		this.mainController = mainController;
	}
}
