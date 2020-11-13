package view.collaboration;

import java.util.List;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import model.EventSource;
import view.panelstructure.DefaultViewPanel;

public class PieChart extends DefaultViewPanel{

	private DefaultPieDataset dataset;
	private JFreeChart chart;
	private ChartPanel panel;
	
	public PieChart() {
		super("");
		this.dataset = new DefaultPieDataset();
		
		this.createDataset();
		this.createUIElements();
	}
	
	private void createDataset() {
		this.dataset.setValue( "IPhone 5s" , new Double( 20 ) );  
	    this.dataset.setValue( "SamSung Grand" , new Double( 20 ) );   
	    this.dataset.setValue( "MotoG" , new Double( 40 ) );    
	    this.dataset.setValue( "Nokia Lumia" , new Double( 10 ) );  
	}
	
	private void createUIElements() {
		this.chart = ChartFactory.createPieChart("Zusammenarbeit mit Gutachtern", this.dataset, true, true, false);
		this.panel = new ChartPanel(chart);
		this.add(this.panel);
	}

	@Override
	protected List<EventSource> getEventSources() {
		// TODO Auto-generated method stub
		return null;
	}
}
