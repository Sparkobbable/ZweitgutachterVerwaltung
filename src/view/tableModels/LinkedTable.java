package view.tableModels;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JTable;

public class LinkedTable<T> extends JTable {

	private static final Cursor HAND_CURSOR = new Cursor(Cursor.HAND_CURSOR);
	private static final Cursor DEFAULT_CURSOR = new Cursor(Cursor.DEFAULT_CURSOR);
	private static final long serialVersionUID = 1L;

	public LinkedTable(AbstractTableModel<T> tableModel) {
		super(tableModel);

		this.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				LinkedTable.this.updateCursor(tableModel, e);
			}

		});
	}

	protected void updateCursor(AbstractTableModel<T> tableModel, MouseEvent e) {
		int row = rowAtPoint(e.getPoint());
		int column = columnAtPoint(e.getPoint());

		if (row >= 0 && column >= 0
				&& tableModel.isReference(convertRowIndexToModel(row), convertColumnIndexToModel(column))) {
			this.setCursor(HAND_CURSOR);
		} else {
			this.setCursor(DEFAULT_CURSOR);
		}
	}

}
