package view.tableModels;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import javax.swing.table.TableModel;

import model.Model;
import model.domain.Reviewer;

/**
 * This {@link AbstractDataTableModel} contains the {@link Column}s {@link #NAME}, {@link #THESIS_COUNT} and {@link #OCCUPATION}
 */
public class ReviewerOverviewTableModel extends AbstractDataTableModel<Reviewer> {

	private static final long serialVersionUID = 1L;
	private Model model;

	public static final Column<Reviewer, String> NAME = Column.of("Name", Reviewer::getName, String.class);
	public static final Column<Reviewer, Integer> THESIS_COUNT = Column.of("Anzahl betreute Bachelorarbeiten",
			Reviewer::getTotalReviewCount, Integer.class);
	public static final Column<Reviewer, Double[]> OCCUPATION = Column.of("Auslastung",
			 r -> new Double[] { r.getFirstOccupation(), r.getSecOccupation(), r.getOccupation() }, Double[].class);

	/**
	 * Creates a new {@link TableModel} for presenting a list of {@link Reviewer}
	 * @param columns	The {@link Column}s to be presented
	 * @param filters	The filters to be applied to the list
	 * @param model		Needs the {@link Model} for data accessing
	 */
	public ReviewerOverviewTableModel(List<Column<Reviewer, ?>> columns, List<Predicate<Reviewer>> filters, Model model) {
		super(columns, filters);
		this.model = model;
	}

	/**
	 * Creates a new {@link TableModel} for presenting a list of {@link Reviewer}
	 * <p> features the {@link Column}s {@link #NAME}, {@link #THESIS_COUNT} and {@link #OCCUPATION}
	 * @param model		Needs the {@link Model} for data accessing
	 */
	public ReviewerOverviewTableModel(Model model) {
		this(List.of(NAME, THESIS_COUNT, OCCUPATION), Collections.emptyList(), model);
	}

	@Override
	protected Collection<Reviewer> getUnfilteredData() {
		return model.getReviewers();
	}

}
