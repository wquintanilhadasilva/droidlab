package br.com.chart;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Main extends Activity {
	
	 private int[] valores = {6, 7, 5, 3, 2, 7, 8, 4, 5, 1, 9, 6};
     
     private int[] valores2 = {2, 12, 8, 4, 1, 3, 16, 3, 9, 2, 10, 15};
     
     private String[] meses = {"Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.main);
		
		this.grafico();
		
	}
	
	public void showBarra(View v){
		
		
		Intent i = ChartFactory.getBarChartIntent(this, getLineDataSerires(), getLineSerieRenderer(), Type.DEFAULT);
		
		startActivity(i);
		
	}
	
	public void showLinha(View v){
		
		Intent i = ChartFactory.getLineChartIntent(this, getLineDataSerires(), getLineSerieRenderer());
		
		startActivity(i);
		
	}
	
	public void showFrame(View v){
		
		startActivity(new Intent(this, FrameLayoutActitivy.class));
		
	}
	
	public void showServer(View v){
		
		startActivity(new Intent(this, WebServerActivity.class));
		
	}
	
	public void showSearchView(View v){
		
		startActivity(new Intent(this, Testes.class));
		
	}

	private void grafico() {
		
		LinearLayout layout = (LinearLayout) findViewById(R.id.grafico);
		
		XYMultipleSeriesRenderer renderer = getBarDemoRenderer();
		
		setChartSettings(renderer);
	  
		GraphicalView chartView = ChartFactory.getBarChartView(this, getLineDataSerires(), getLineSerieRenderer(), Type.DEFAULT);
		
		layout.addView(chartView);
	  
	 }
	 
	 public XYMultipleSeriesRenderer getBarDemoRenderer() {
		 
	     XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
	     
	     renderer.setAxisTitleTextSize(15);
	     
	     renderer.setBarSpacing(1);
	     
	     renderer.setLegendTextSize(40);
	     
	     renderer.setLabelsTextSize(20);
	     
	     SimpleSeriesRenderer r = new SimpleSeriesRenderer();
	     
	     r.setColor(Color.BLUE);
	     
	     r.setDisplayChartValues(true);
	     
	     renderer.addSeriesRenderer(r);
	     
	     
	     SimpleSeriesRenderer r2 = new SimpleSeriesRenderer();
	     
	     r2.setColor(Color.GREEN);
	     
	     r2.setDisplayChartValues(true);
	     
	     renderer.addSeriesRenderer(r2);
	     
	     return renderer;
	     
	 }
	 
	 private void setChartSettings(XYMultipleSeriesRenderer renderer) {
		 
	     renderer.setShowLegend(true);
	     
	     renderer.setAxesColor(Color.DKGRAY);
	     
	     renderer.setXAxisMin(0.5);
	     
	     renderer.setXAxisMax(12.5);
	     
	     renderer.setYAxisMin(0);
	     
	     renderer.setYLabelsAlign(Align.RIGHT);
	     
	     renderer.setXLabels(0);
	     
	     renderer.setZoomEnabled(true, true);
	     
	     renderer.setShowCustomTextGrid(true);
	     
	     renderer.setShowGridY(true);
	     
	     renderer.setShowGridX(true);
	     
	     renderer.setXTitle("Meses");
	     
	     renderer.setYTitle("Valores");
	     
	     for(int i = 0; i < meses.length; i++){
	    	 
	    	 renderer.addXTextLabel(i+1, meses[i]);
	    	 
	     }
	     
	 }
	 
	 private void populaSerie (XYSeries serie, int[] valores){
		 
		 for (int i = 0; i < meses.length; i++) {
	    	 
			 serie.add(i+1, valores[i]);
	    	 
	     }
		 
	 }
	 
	 
	 private XYMultipleSeriesDataset getLineDataSerires(){
		 
		 XYSeries series = new XYSeries("Água");
		 
		 XYSeries series2 = new XYSeries("Eletricidade");
		 
		 populaSerie(series, valores2);
		 
		 populaSerie(series2, valores);
		 
		 XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		 
		 dataset.addSeries(series);
		 
		 dataset.addSeries(series2);
		 
		 return dataset;
		 
	 }
	 
	 private XYMultipleSeriesRenderer getLineSerieRenderer(){
		 
		// Now we create the renderer
		 XYSeriesRenderer renderer = new XYSeriesRenderer();
		 
		 renderer.setLineWidth(2);
		 
		 renderer.setColor(Color.RED);
		 
		 // Include low and max value
		 renderer.setDisplayBoundingPoints(true);
		 
		 // we add point markers
		 renderer.setPointStyle(PointStyle.CIRCLE);
		 
		 renderer.setPointStrokeWidth(3);
		 
		 
		 // Now we create the renderer
		 XYSeriesRenderer renderer2 = new XYSeriesRenderer();
		 
		 renderer2.setLineWidth(2);
		 
		 renderer2.setColor(Color.BLUE);
		 
		 // Include low and max value
		 renderer2.setDisplayBoundingPoints(true);
		 
		 // we add point markers
		 renderer2.setPointStyle(PointStyle.CIRCLE);
		 
		 renderer2.setPointStrokeWidth(3);
		 
		 XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();
		 
		 setChartSettings(mRenderer);
		 
		 mRenderer.setLabelsTextSize(20); //Tamanho do texto dos VALORES do Eixo (x & y)
		 
		 mRenderer.setAxisTitleTextSize(15); //Tamanho do texto dos TÍTULOS do Eixo (x & y)
		 
		 mRenderer.setLegendTextSize(15); //Tamanho do texto da LEGENDA do Gráfico
		 
		 mRenderer.addSeriesRenderer(renderer);
		 
		 mRenderer.addSeriesRenderer(renderer2);
		 
		 return mRenderer;
		 
	 }

}
