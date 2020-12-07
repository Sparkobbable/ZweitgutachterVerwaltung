package view.panels.analyse;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Collections;
import java.util.List;

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
import view.widgets.PieChart;

public class AnalysisPanel extends DefaultPanel {

	private static final long serialVersionUID = 1L;
	
	private Model model;
	
	private AbstractViewPanel options;
	private Component chart;
	
	private PieChart pieChart;
	private Component barChart;
	private JTable table;
	
	private CustomEventSource initializeEventSource;

	public AnalysisPanel(Model model) {
		super("Verteilung Gutachten");
		this.model = model;
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
		this.table = new JTable(new ReviewerOverviewTableModel(List.of(ReviewerOverviewTableModel.NAME, 
				ReviewerOverviewTableModel.FIRSTREVIEW_COUNT, ReviewerOverviewTableModel.SECONDREVIEW_COUNT),
				Collections.emptyList(), model));
		
		this.options = new AnalysisOptionsPanel(this.model);
		this.chart = this.table;
	}
	
	private void addUIElements() {
		this.add(options, BorderLayout.PAGE_START);
		this.add(chart, BorderLayout.CENTER);
	}

	@Override
	protected List<EventSource> getEventSources() {
		return List.of(this.options, this.initializeEventSource);
	}
	
	@Override
	public void initializeState(ViewState viewState) {
		switch (viewState) {
		case TABLE:
			this.remove(chart);
			this.chart = this.table;
			this.add(chart);
			break;
		case PIECHART:
			this.remove(chart);
			this.chart = this.pieChart;
			this.add(chart);
			break;
		case BARCHART:
			this.remove(chart);
			this.chart = this.barChart;
			this.add(chart);
			break;
		default:
			throw new IllegalArgumentException("Invalid ViewState");
		}
		initializeEventSource.trigger();
	}

}
