package view.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.EventSource;
import model.Model;
import model.data.Reviewer;
import model.enums.EventId;
import model.enums.ViewId;
import view.AbstractView;
import view.eventsources.ButtonEventSource;
import view.eventsources.TextFieldEventSource;

@SuppressWarnings("deprecation")
public class ReviewerEditor extends AbstractView {
	private static final long serialVersionUID = 1L;
	private Reviewer reviewer;
	private Model data;
	private JTextField nameField;
	private JTable supervisedThesisTable;
	private JScrollPane supervisedThesisPane;
	private JButton save;
	private JButton addBachelorThesis;
	
	/**
	 * Creates a view containing a table presenting the reviewer's supervised bachelorThesis and other data of the reviewer
	 * 
	 * @param viewId    Unique viewId from {@link ViewId}
	 * @param title		Needs a title
	 */
	public ReviewerEditor(ViewId id, String title, Model reviewers) {
		super(id, title);
		this.data = reviewers;
		this.reviewer = reviewers.getSelectedReviewer();
		this.nameField = new JTextField();
		initEditorWindow();
		this.createUIElements();
		this.registerEventSources();
		addObservables(this.data);
	}

	private void initEditorWindow() {
		this.setBackground(Color.MAGENTA); // TODO only for component identification, remove before launch
		this.setLayout(new GridLayout(4, 1));
	}

	public JTextField getNameField() {
		return nameField;
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new TextFieldEventSource(EventId.VALUE_ENTERED, nameField, () -> getNameFieldValue()),
						new ButtonEventSource(EventId.SAVE_REVIEWER, save, () -> getReviewer()),
						new ButtonEventSource(EventId.ADD_THESIS, addBachelorThesis, () -> getReviewer()));
	}

	private String getNameFieldValue() {
		return this.nameField.getText();
	}

	@Override
	protected void createUIElements() {
		this.supervisedThesisTable = new JTable(new SupervisedThesisTableModel(reviewer.getSupervisedThesis()));
		this.supervisedThesisTable.setFillsViewportHeight(true);
		
		this.supervisedThesisPane = new JScrollPane(this.supervisedThesisTable);
		
		this.save = new JButton("Speichern");
		this.addBachelorThesis = new JButton("Bachelorarbeit hinzufügen");
		
		this.add(this.nameField);
		this.add(this.supervisedThesisPane);
		this.add(this.save);
		this.add(this.addBachelorThesis);
	}

	@Override
	public void update(Observable o, Object arg) {
		this.nameField.setText(((Model) o).getSelectedReviewer().getName());
	}

	private Reviewer getReviewer() {
		this.reviewer.setName(this.getNameFieldValue());
		return reviewer;
	}
}
