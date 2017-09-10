package br.com.chart;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class FrameLayoutActitivy extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.framelayout);
		
		String[] itens = new String[] {"Item 1", "Item2", "Item 3", "Item 4", "Item 5", "Item 6", "Item 7", "Item 8", "Item 9", "Item 10", "Item 11", "Item 12"};
		
		ListView lista = (ListView) findViewById(R.id.lista);
		
		ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, itens);
		
		lista.setAdapter(adapter);
		
		/**
		 * para criar um botão estilizado:
		 * http://angrytools.com/android/button/
		 */
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		return super.onCreateOptionsMenu(menu);
		
	}

}
