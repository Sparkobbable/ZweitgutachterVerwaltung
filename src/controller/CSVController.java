package controller;

import java.io.FileReader;
import java.util.ArrayList;

import model.data.Reviewer;

public class CSVController {
private String filename;
 
public CSVController(String filename) {
	this.filename = filename;
}
public ArrayList<Reviewer> loadcsvImport() throws Exception {
	FileReader fr;
	//TODO csv strucktur auf user Datenmodel mappen
	return null;
}
}
