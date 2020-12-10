package view.tableModels;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.domain.Reviewer;

public class SingleAnalysisReviewerTableModel extends AbstractDataTableModel<Reviewer> {

		private static final long serialVersionUID = 1L;
		private Model model;

		public static final Column<Reviewer, String> NAME = Column.of("Name", Reviewer::getName, String.class);

		private static final List<Column<Reviewer, ?>> createColumns(Model model) {
			return List.of(Column.of("Anzahl Erstgutachten", reviewer -> model.getSingleReviews().getLeft().orElseGet(() -> 0), Integer.class),
						   Column.of("Anzahl Zweitgutachter", reviewer -> model.getSingleReviews().getRight().orElseGet(() -> 0), Integer.class));
		}
		
		public SingleAnalysisReviewerTableModel(List<Column<Reviewer, ?>> columns, List<Predicate<Reviewer>> filters, Model model) {
			super(columns, filters);
			this.model = model;
		}

		public SingleAnalysisReviewerTableModel(Model model) {
			this(createColumns(model), Collections.emptyList(), model);
		}

		@Override
		protected Collection<Reviewer> getUnfilteredData() {
			ArrayList<Reviewer> result = new ArrayList<>();
			result.add(this.model.getSelectedReviewer().orElseThrow(() -> new IllegalStateException("Missing selected Reviewer in Single Analysis")));
			return result;
		}
}
