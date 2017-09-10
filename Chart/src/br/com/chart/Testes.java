package br.com.chart;

//import android.app.ActionBar;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;

public class Testes extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.testes);
		
		ActionBar ab = getActionBar();
		
		ab.setDisplayHomeAsUpEnabled(true);
		
//		//Tabs
//		ab.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
//		
//		Tab tab1 = ab.newTab();
//		tab1.setText("Tab 1");
//		tab1.setIcon(R.drawable.ic_launcher);
//		tab1.setTabListener(new NavegacaoTabs(new Fragment1()));
//		
//		ab.addTab(tab1,false);
//		
//		Tab tab2 = ab.newTab();
//		tab2.setText("Tab 2");
//		tab2.setIcon(R.drawable.ic_launcher);
//		tab2.setTabListener(new NavegacaoTabs(new Fragment1()));
//		
//		ab.addTab(tab2,false);
//		
//		Tab tab3 = ab.newTab();
//		tab3.setText("Tab 3");
//		tab3.setIcon(R.drawable.ic_launcher);
//		tab3.setTabListener(new NavegacaoTabs(new Fragment1()));
//		
//		ab.addTab(tab3,false);
//		
//		if(savedInstanceState != null){
//			
//			int indice = savedInstanceState.getInt("indice");
//			
//			ab.setSelectedNavigationItem(indice);
//			
//		}else{
//			ab.setSelectedNavigationItem(0);
//		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu, menu);
		
		SearchView sv = (SearchView) menu.findItem(R.id.item1).getActionView();
		
		sv.setOnQueryTextListener(new SearchFiltro());
		
		return true;
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		getActionBar().setTitle(item.getTitle());
		
		switch(item.getItemId()){
		
			case android.R.id.home:
				Toast.makeText(this, "Homeee!", Toast.LENGTH_SHORT).show();
				break;
		
			case R.id.item1:
				Toast.makeText(this, "Item1", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.item2:
				Toast.makeText(this, "Item2", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.item3:
				Toast.makeText(this, "Item3", Toast.LENGTH_SHORT).show();
				break;
				
			case R.id.item4:
				Toast.makeText(this, "Item4", Toast.LENGTH_SHORT).show();
				break;
		}
		
		return true;
		
	}
	
//	@Override
//	protected void onSaveInstanceState(Bundle outState) {
//		super.onSaveInstanceState(outState);
//		
//		outState.putInt("indice", getActionBar().getSelectedNavigationIndex());
//		
//	}
//	
//	private class NavegacaoTabs implements ActionBar.TabListener{
//
//		private Fragment frag;
//
//		public NavegacaoTabs(Fragment frag){
//			
//			this.frag = frag;
//			
//		}
//		
//		@Override
//		public void onTabSelected(Tab tab, FragmentTransaction ft) {
//			
//			//Temos que colocar o fragmento da tab 2 como conteúdo principal e retirar o anterior
//			ft.replace(R.id.fragmentContainer, frag); //TODO está dando pau na hora de carregar o fragmento
//			ft.commit();
//		}
//
//		@Override
//		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
//			
//			//Temos que retirar o anterior
//			ft.remove(frag);
//			ft.commit();
//			
//		}
//
//		@Override
//		public void onTabReselected(Tab tab, FragmentTransaction ft) {
//			Toast.makeText(getParent(), "onTabReselected", Toast.LENGTH_SHORT).show();
//		}
//
//		
//	}
//	
	private class SearchFiltro implements OnQueryTextListener{

		@Override
		public boolean onQueryTextSubmit(String query) {
			
			Log.i("Script", "onQueryTextSubmit ->" + query);
			
			return false; //false fala para o sv trabalhar de forma padrão e true, diz q vc irá tratar 
			
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			
			Log.i("Script", "onQueryTextChange ->" + newText);
			
			return false; //false fala para o sv trabalhar de forma padrão e true, diz q vc irá tratar 
			
		}
		
		
	}
	
}
