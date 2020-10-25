package view.editor;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.data.Reviewer;
import view.AbstractView;

public class ReviewerEditor {
	private Reviewer reviewer;
	private JTextField nameField;
	private JTable supervisedThesisTable;
	private JScrollPane supervisedThesisPane;

	public ReviewerEditor(String id, String title, Reviewer reviewer) {
		this.reviewer = reviewer;
		this.nameField = new JTextField(reviewer.getName());
		initTable(reviewer);
	}
	
	public ReviewerEditor(String id, String title) {
		this.reviewer = new Reviewer();
		this.nameField = new JTextField();
		initTable(this.reviewer);
	}
	
	private void initTable(Reviewer reviewer) {
		this.supervisedThesisTable = new JTable(new SupervisedThesisTableModel(reviewer.getSupervisedThesis()));
		this.supervisedThesisTable.setFillsViewportHeight(true);
	}

//	public void init() {
//		this.setBackground(Color.MAGENTA); // TODO only for component identification, remove before launch
//		this.setLayout(new GridLayout(2, 1));
//		
//		this.supervisedThesisPane = new JScrollPane(this.supervisedThesisTable);
//		
//		this.add(this.nameField);
//		this.add(this.supervisedThesisPane);
//	}

	public JTextField getNameField() {
		return nameField;
	}
}
