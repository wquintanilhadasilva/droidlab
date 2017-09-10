package br.com.casadocodigo.boaviagem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;

public class GastoListActivity extends ListActivity implements OnItemClickListener{
	
	private List<Map<String, Object>> gastos;
	
	private String dataAnterior = "";
	
	private class GastoViewBinder implements ViewBinder{

		@Override
		public boolean setViewValue(View view, Object data, String textRepresetation) {
			
			if(view.getId() == R.id.lst_gasto_data){
				
				if(!dataAnterior.equals(data)){
					
					TextView textViewData = (TextView) view;
					
					textViewData.setText(textRepresetation);
					
					dataAnterior = textRepresetation;
					
					view.setVisibility(View.VISIBLE);
					
				}else{
					
					view.setVisibility(View.GONE);
					
				}
				
				return true;
				
			}
			
			if(view.getId() == R.id.lst_gasto_categoria){
				
				Integer id = Integer.parseInt(data.toString());	
				
				view.setBackgroundColor(getResources().getColor(id));
				
				return true;
			}
			
			return false;
		}
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		String[] de = {"data","descricao","valor", "categoria"};
		
		int[] para = {R.id.lst_gasto_data, R.id.lst_gasto_descricao, R.id.lst_gasto_valor, R.id.lst_gasto_categoria};
		
		SimpleAdapter adapter = new SimpleAdapter(this, this.listarGastos(), R.layout.lista_gasto, de, para);
		
		adapter.setViewBinder(new GastoViewBinder()); //viewbinder para os itens da tela
		
		setListAdapter(adapter);
		
		super.getListView().setOnItemClickListener(this);
		
		registerForContextMenu(this.getListView()); //registra o menu de contexto

	}

	private List<Map<String, Object>> listarGastos() {
		
		this.gastos = new ArrayList<>();
		
		Map<String, Object> item;
		
		for(int i = 0; i < 5; i++){
			item = new HashMap<>();
			item.put("data", "04/02/2012");
			item.put("descricao","Diária de Hotel");
			item.put("valor", "R$ 260,00");
			item.put("categoria", R.color.categoria_hospedagem);
			this.gastos.add(item);
		}
		
		for(int i = 0; i < 3; i++){
			item = new HashMap<>();
			item.put("data", "05/02/2012");
			item.put("descricao","Diária de Hotel");
			item.put("valor", "R$ 100,55");
			item.put("categoria", R.color.categoria_hospedagem);
			this.gastos.add(item);
		}
		
		for(int i = 0; i < 3; i++){
			item = new HashMap<>();
			item.put("data", "10/02/2012");
			item.put("descricao","Alimentação");
			item.put("valor", "R$ 50,00");
			item.put("categoria", R.color.categoria_alimentacao);
			this.gastos.add(item);
		}
		
		for(int i = 0; i < 5; i++){
			item = new HashMap<>();
			item.put("data", "11/02/2012");
			item.put("descricao","Alimentação");
			item.put("valor", "R$ 25,00");
			item.put("categoria", R.color.categoria_alimentacao);
			this.gastos.add(item);
		}
		
		for(int i = 0; i < 10; i++){
			item = new HashMap<>();
			item.put("data", "20/02/2012");
			item.put("descricao","Taxi");
			item.put("valor", "R$ 35,00");
			item.put("categoria", R.color.categoria_transporte);
			this.gastos.add(item);
		}
		
		for(int i = 0; i < 5; i++){
			item = new HashMap<>();
			item.put("data", "21/02/2012");
			item.put("descricao","Moto Taxi");
			item.put("valor", "R$ 15,00");
			item.put("categoria", R.color.categoria_transporte);
			this.gastos.add(item);
		}
		
		return this.gastos;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

		Map<String, Object> item = this.gastos.get(position);
		
		String msg = "Gasto selecionado: " + item.get("descricao");
		
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.crud_menu, menu);
		
		return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		
		switch(item.getItemId()){
		
		case R.id.menu_novo:
			
			startActivity(new Intent(this, GastoActivity.class));
			
			return true;
			
		case R.id.menu_remover:
			
			//TODO remover do bd
			
			return true;
		
		default:
				
			return super.onMenuItemSelected(featureId, item);
				
		}
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.list_context_menu, menu);
		
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.ctx_remover){
			
			AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
			
			this.gastos.remove(info.position);
			
			getListView().invalidateViews();
			
			this.dataAnterior = "";
			
			//TODO remover do bd
			
			return true;
			
		}
		
		return super.onContextItemSelected(item);
	}
	
}
