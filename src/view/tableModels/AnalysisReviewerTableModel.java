package view.tableModels;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

import model.Model;
import model.domain.Reviewer;

public class AnalysisReviewerTableModel extends AbstractDataTableModel<Reviewer> {

		private static final long serialVersionUID = 1L;
		private Model model;

		public static final Column<Reviewer, String> NAME = Column.of("Name", Reviewer::getName, String.class);

		private static final List<Column<Reviewer, ?>> createColumns(Model model) {
			return List.of(Column.of("Name", reviewer -> reviewer.getName(), String.class),
						   Column.of("Anzahl Erstgutachten", reviewer -> model.getAnalyseReviewers().get(reviewer).getLeft().orElseGet(() -> 0), Integer.class),
						   Column.of("Anzahl Zweitgutachter", reviewer -> model.getAnalyseReviewers().get(reviewer).getRight().orElseGet(() -> 0), Integer.class));
		}
		
		public AnalysisReviewerTableModel(List<Column<Reviewer, ?>> columns, List<Predicate<Reviewer>> filters, Model model) {
			super(columns, filters);
			this.model = model;
		}

		public AnalysisReviewerTableModel(Model model) {
			this(createColumns(model), Collections.emptyList(), model);
		}

		@Override
		protected Collection<Reviewer> getUnfilteredData() {
			return this.model.getAnalyseReviewers().keySet();
		}
}
