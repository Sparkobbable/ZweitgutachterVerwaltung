package view.overview;

import java.awt.FlowLayout;
import java.util.function.Supplier;

import view.panelstructure.DefaultViewPanel;


public abstract class OverviewActionPanel extends DefaultViewPanel{
	private static final long serialVersionUID = 1L;

	protected Supplier<int[]> selectedRowIndexSupplier;

	public OverviewActionPanel(Supplier<int[]> selectedRowIndexSupplier) {
		super("");
		this.selectedRowIndexSupplier = selectedRowIndexSupplier;
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); // TODO Not quite sure which is the best layout
	}

}
