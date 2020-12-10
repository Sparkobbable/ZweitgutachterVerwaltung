package view.panels.analyse;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.events.CustomEventSource;
import controller.events.EventSource;
import model.Model;
import model.domain.Reviewer;
import model.enums.EventId;
import view.ViewProperties;
import view.ViewState;
import view.eventsources.TableClickEventSource;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.AnalysisReviewerTableModel;
import view.tableModels.ReviewerOverviewTableModel;
import view.widgets.BarChart;
import view.widgets.BarChartHorizontal;
import view.widgets.PieChart;

/**
 * Panel showing multiple analysis of reviews
 */
public class AnalysisPanel extends DefaultPanel {

	private static final long serialVersionUID = 1L;
	
	private final String TABLE = "table";
	private final String PIECHART = "pieChart";
	private final String BARCHART = "barChart";
	private final String BARCHARTHORIZONTAL = "barChartHorizontal";
	
	private Model model;
	private CardLayout cardLayout;
	
	private AbstractViewPanel options;
	private JPanel chart;
	
	private AnalysisReviewerTableModel tableModel;
	private PieChart pieChart;
	private Component barChartHorizontal;
	private Component barChart;
	private JTable table;
	private JScrollPane tableScrollPane;
	
	private CustomEventSource initializeEventSource;

	public AnalysisPanel(Model model) {
		super("Verteilung Gutachten");
		this.model = model;
		this.cardLayout = new CardLayout();
		this.initializeEventSource = new CustomEventSource(EventId.INITIALIZE);
		this.setBackground(ViewProperties.BACKGROUND_COLOR);
		this.setLayout(new BorderLayout());
		
		this.createUIElemenets();
		this.addUIElements();
		this.registerEventSources();
		this.initializePropertyChangeHandlers();
		this.observe(this.model);
	}
	
	private void createUIElemenets() {
		this.pieChart = new PieChart("Gutachten Verteilung", this.model);
		this.barChart = new BarChart("Gutachten Verteilung", this.model);
		this.barChartHorizontal = new BarChartHorizontal("Gutachten Verteilung", this.model);
		this.tableModel = new AnalysisReviewerTableModel(this.model);
		this.table = new JTable(this.tableModel);
		this.tableScrollPane = new JScrollPane(this.table);
		this.table.setFillsViewportHeight(true);
		this.table.setAutoCreateRowSorter(true);
		this.tableModel.updateData();
		
		this.options = new AnalysisOptionsPanel(this.model);
		this.chart = new JPanel();
		this.chart.setLayout(cardLayout);
	}
	
	private void addUIElements() {
		this.add(options, BorderLayout.PAGE_START);
		this.add(chart, BorderLayout.CENTER);
		this.chart.add(tableScrollPane, TABLE);
		this.chart.add(pieChart, PIECHART);
		this.chart.add(barChart, BARCHART);
		this.chart.add(barChartHorizontal, BARCHARTHORIZONTAL);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.options, this.initializeEventSource, new TableClickEventSource(EventId.SELECT, table, 1, () -> getselectedReviewer()));
	}
	
	/**
	 * Switching the presentation of analysis for each {@link ViewState}
	 */
	@Override
	public void initializeState(ViewState viewState) {
		switch (viewState) {
		case TABLE:
			this.cardLayout.show(chart, TABLE);
			break;
		case PIECHART:
			this.cardLayout.show(chart, PIECHART);
			break;
		case BARCHART:
			this.cardLayout.show(chart, BARCHART);
			break;
		case BARCHARTHORIZONTAL:
			this.cardLayout.show(chart, BARCHARTHORIZONTAL);
			break;
		default:
			throw new IllegalArgumentException("Invalid ViewState");
		}
		initializeEventSource.trigger();
	}
	
	/**
	 * Defines the methods that should be called when an observed property is
	 * changed
	 */
	private void initializePropertyChangeHandlers() {
		this.onPropertyChange(Model.ANALYSE_REVIEWERS, (evt) -> this.tableModel.updateData());
	}
	
	private Reviewer getselectedReviewer() {
		return IntStream.of(this.table.getSelectedRow()).map(this.table::convertRowIndexToModel).mapToObj(this.tableModel::getByIndex).findAny().orElse(null);
	}

}
