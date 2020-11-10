package view.tableModels;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class ProgressRenderer extends JProgressBar implements TableCellRenderer {

	private static final int MAX_VALUE = 100;
	private static final int MIN_VALUE = 0;
	private static final long serialVersionUID = 1L;
	
	public ProgressRenderer() {
		super(MIN_VALUE, MAX_VALUE);
		this.setStringPainted(true);
		this.setBorderPainted(false);
		this.setForeground(Color.RED);
	}
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		this.setValue((Integer) value);
	    return this;
	}

}
