package view.editor;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import model.EventSource;
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
	private JTextField nameField;
	private JTable supervisedThesisTable;
	private JScrollPane supervisedThesisPane;
	
	/**
	 * Creates a view containing a table presenting the reviewer's supervised bachelorThesis and other data of the reviewer
	 * 
	 * @param viewId    Unique viewId from {@link ViewId}
	 * @param title		Needs a title
	 */
	public ReviewerEditor(ViewId id, String title) {
		super(id, title);
		this.reviewer = new Reviewer();
		this.reviewer.addObserver(new Observer() {
			
			@Override
			public void update(Observable o, Object arg) {
				ReviewerEditor.this.nameField.setText(((Reviewer) o).getName());
			}
		});
		this.nameField = new JTextField();
		initEditorWindow();
		this.createUIElements();
		this.registerEventSources();
	}

	private void initEditorWindow() {
		this.setBackground(Color.MAGENTA); // TODO only for component identification, remove before launch
		this.setLayout(new GridLayout(2, 1));
	}

	public JTextField getNameField() {
		return nameField;
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(new TextFieldEventSource(EventId.VALUE_ENTERED, nameField, () -> getNameFieldValue()));
	}

	private String getNameFieldValue() {
		return this.nameField.getText();
	}

	@Override
	protected void createUIElements() {
		this.supervisedThesisTable = new JTable(new SupervisedThesisTableModel(reviewer.getSupervisedThesis()));
		this.supervisedThesisTable.setFillsViewportHeight(true);
		
		this.supervisedThesisPane = new JScrollPane(this.supervisedThesisTable);
		
		this.add(this.nameField);
		this.add(this.supervisedThesisPane);
	}
}
