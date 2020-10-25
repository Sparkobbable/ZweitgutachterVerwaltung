package view.actions;

import static model.constants.ButtonId.EDIT;
import static model.constants.ButtonId.DELETE;
import static model.constants.ButtonId.NEW;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;

import view.AbstractView;

public class OverviewTableActions extends AbstractView<OverviewTableActions>{

	private static final long serialVersionUID = 1L;

	/**
	 * Provides a section with several buttons for interaction with the table.
	 * @param id Unique ID from 
	 */
	public OverviewTableActions(String id) {
		super(id, "");
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); //TODO Not quite sure which is the best layout
		this.setBackground(Color.BLUE);
		addButton(EDIT);
		addButton(DELETE);
		addButton(NEW);
	}

	@Override
	protected void createButtons() {
		super.createButtons();
		buttons.put(EDIT, new JButton("Bearbeiten"));
		buttons.put(DELETE, new JButton("Löschen"));
		buttons.put(NEW, new JButton("Neu"));
		//TODO More Buttons needed
	}

}
