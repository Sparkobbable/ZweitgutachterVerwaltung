package view.panels.analyse;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.util.Collections;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import controller.events.CustomEventSource;
import controller.events.EventSource;
import model.Model;
import model.enums.EventId;
import view.ViewProperties;
import view.ViewState;
import view.panels.prototypes.AbstractViewPanel;
import view.panels.prototypes.DefaultPanel;
import view.tableModels.ReviewerOverviewTableModel;
import view.widgets.BarChart;
import view.widgets.PieChart;

public class AnalysisPanel extends DefaultPanel {

	private static final long serialVersionUID = 1L;
	
	private final String TABLE = "table";
	private final String PIECHART = "pieChart";
	private final String BARCHART = "barChart";
	
	private Model model;
	private CardLayout cardLayout;
	
	private AbstractViewPanel options;
	private JPanel chart;
	
	private ReviewerOverviewTableModel tableModel;
	private PieChart pieChart;
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
		this.observe(this.model);
	}
	
	private void createUIElemenets() {
		this.pieChart = new PieChart("Gutachter Verteilung", this.model);
		this.barChart = new BarChart("Gutachter Verteilung", this.model);
		this.tableModel = new ReviewerOverviewTableModel(List.of(ReviewerOverviewTableModel.NAME, 
				ReviewerOverviewTableModel.FIRSTREVIEW_COUNT, ReviewerOverviewTableModel.SECONDREVIEW_COUNT),
				Collections.emptyList(), model);
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
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.options, this.initializeEventSource);
	}
	
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
		default:
			throw new IllegalArgumentException("Invalid ViewState");
		}
		initializeEventSource.trigger();
	}

}
