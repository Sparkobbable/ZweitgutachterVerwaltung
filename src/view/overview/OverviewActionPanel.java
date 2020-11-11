package view.overview;

import java.awt.FlowLayout;
import java.util.function.Supplier;

import model.enums.ViewId;
import view.AbstractView;

public abstract class OverviewActionPanel extends AbstractView {
	private static final long serialVersionUID = 1L;

	protected Supplier<int[]> selectedRowIndexSupplier;

	public OverviewActionPanel(ViewId viewId, Supplier<int[]> selectedRowIndexSupplier) {
		super(viewId, "");
		this.selectedRowIndexSupplier = selectedRowIndexSupplier;
		this.setBorder(UNTITLED_BORDER);
		this.setLayout(new FlowLayout()); // TODO Not quite sure which is the best layout
	}

}
