package view.tableModels;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.data.Reviewer;

public class ReviewerOverviewTableModel extends AbstractDataTableModel<Reviewer> {

	private static final long serialVersionUID = 1L;
	private Model model;

	private static final Column<Reviewer, String> NAME = Column.of("Name", Reviewer::getName, String.class);
	private static final Column<Reviewer, Integer> THESIS_COUNT = Column.of("Anzahl betreute Bachelorarbeiten",
			Reviewer::getTotalReviewCount, Integer.class);
	private static final Column<Reviewer, Integer> OCCUPATION = Column.of("Auslastung",
			r -> (int) (r.getOccupation() * 100), Integer.class);

	public ReviewerOverviewTableModel(List<Column<Reviewer, ?>> columns, List<Predicate<Reviewer>> filters, Model model) {
		super(columns, filters);
		this.model = model;
	}

	public ReviewerOverviewTableModel(Model model) {
		this(List.of(NAME, THESIS_COUNT, OCCUPATION), Collections.emptyList(), model);
	}

	@Override
	protected Collection<Reviewer> getUnfilteredData() {
		return model.getReviewers();
	}

}
