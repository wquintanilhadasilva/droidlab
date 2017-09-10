package br.com.casadocodigo.boaviagem;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.Toast;
import br.com.casadocodigo.boaviagem.bd.Constantes;
import br.com.casadocodigo.boaviagem.bd.DatabaseHelper;
import br.com.casadocodigo.boaviagem.shared.ConfiguracoesDoSistema;

public class ViagemListActivity extends ListActivity implements OnItemClickListener, OnClickListener, ViewBinder{
	
	private int EDITAR_VIAGEM = 1;
	
	private List<Map<String, Object>> viagens;
	
	private AlertDialog alertDialog;
	
	private AlertDialog alertDialogConfirmacao;

	private int viagemSelecionada;
	
	private DatabaseHelper helper;
	
	private Double valorLimite;
	
	private SimpleDateFormat dateFormat;
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		this.helper = new DatabaseHelper(this);
		
		this.dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		SharedPreferences preferences = ConfiguracoesDoSistema.getPreferences(this);
		
		String valorPref = preferences.getString("valor_limite", "-1");
		
		this.valorLimite = Double.valueOf(valorPref);
		
		String[] de = {"imagem","destino","data", "total", "barraProgresso"};
		
		int[] para = {R.id.lst_viag_tipoViagem, R.id.lst_viag_destino, 
				R.id.lst_viag_data, R.id.lst_viag_valor, R.id.lst_viag_barraProgresso};
		
		SimpleAdapter adapter = new SimpleAdapter(this, this.listarViagens(), R.layout.lista_viagem, de, para);
		
		adapter.setViewBinder(this);
		
		setListAdapter(adapter);
		
		setTitle("Viagens realizadas");
		
		getListView().setOnItemClickListener(this);
		
		this.alertDialog = this.criarAlertDialog();
		
		this.alertDialogConfirmacao = this.criarAlertDialogConfirmacao();
	}

	private AlertDialog criarAlertDialogConfirmacao() {
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMessage(R.string.menu_titulo_exclusao);
		
		builder.setPositiveButton(getString(R.string.menu_sim), this);
		
		builder.setNegativeButton(getString(R.string.menu_nao), this);
		
		return builder.create();
	}

	private AlertDialog criarAlertDialog() {
		
		final CharSequence[] itens = {
				
				getString(R.string.dashboard_lbl_novo_gasto), //0
				
				getString(R.string.ver_gastos),				  //1
				
				getString(R.string.editar), 				  //2
				
				getString(R.string.menu_remover)              //3
				
		};
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setTitle(R.string.opcoes);
		
		builder.setItems(itens, this);
		
		return builder.create();
		
	}

	private List<Map<String, Object>> listarViagens() {
		
		SQLiteDatabase db = this.helper.getReadableDatabase();
		
		Cursor cursor = db.rawQuery("SELECT _id, tipo_viagem, destino, data_chegada, data_saida, " +
				"orcamento FROM viagem ORDER BY data_saida", null);
		
		cursor.moveToFirst();
		
		if(this.viagens == null){
			
			this.viagens = new ArrayList<>();
			
		}else{
			
			this.viagens.clear();
			
		}
		
		Map<String, Object> item;
		
		for(int i = 0; i < cursor.getCount(); i++){
			
			item = new HashMap<>();
			item.put("id", cursor.getInt(0));
			item.put("imagem", R.drawable.ic_launcher);
			item.put("destino", cursor.getString(2));
			
			String periodo = dateFormat.format(new Date(cursor.getLong(3))) + 
					" a " + dateFormat.format(new Date(cursor.getLong(4)));
			item.put("data", periodo);
			
			double totalGasto = this.calcularTotalGasto(db, cursor.getString(0));
			item.put("total", "Gasto total R$ " + totalGasto);
			
			double orcamento = cursor.getDouble(5);
			double alerta = orcamento * this.valorLimite / 100;
			Double[] valores = new Double[] {orcamento, alerta, totalGasto};
			item.put("barraProgresso", valores);
			
			this.viagens.add(item);
			
			cursor.moveToNext();
			
		}
		
		cursor.close();
		
		return this.viagens;
		
	}
	
	private Double calcularTotalGasto(SQLiteDatabase db, String idViagem){
		
		Cursor cursor = db.rawQuery("SELECT SUM(valor) FROM gasto WHERE viagem_id =  ?", 
				new String[]{ idViagem });
		
		cursor.moveToFirst();
		
		double total = cursor.getDouble(0);
		
		cursor.close();
		
		return total;
		
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Map<String, Object> item = this.viagens.get(position);
		
		this.viagemSelecionada = position;
		
		this.alertDialog.setTitle(getString(R.string.opcoes) + ": " + item.get("destino"));
		
		this.alertDialog.show();
		
	}

	@Override
	public void onClick(DialogInterface dialog, int item) {

		Map<String, Object> viagem = this.viagens.get(viagemSelecionada);
		
		switch(item){
		
		case 0:
			
			Intent i = new Intent(this, GastoActivity.class);
			
			i.putExtra(Constantes.VIAGEM_ID, viagem.get("id").toString());
			
			startActivity(i);
			
			break;
			
		case 1:
			
			startActivity(new Intent(this, GastoListActivity.class));
			
			break;
			
		case 2:
			
			//editar viagem
			Intent intent = new Intent(this, NovaViagemActivity.class);
			
			intent.putExtra(Constantes.VIAGEM_ID, viagem.get("id").toString());
			
			startActivityForResult(intent, EDITAR_VIAGEM);
			
			break;
			
		case 3:
			
			this.alertDialogConfirmacao.show();
			
			break;
			
//###########Opções da segunda caixa de diálogo!
			
		case DialogInterface.BUTTON_POSITIVE:
			
			//remove do bd
			this.removerViagemDb(viagem.get("id").toString());
			
			this.viagens.remove(this.viagemSelecionada);
			
			getListView().invalidateViews();
			
			Toast.makeText(this, getString(R.string.menu_confirm_exclusao), Toast.LENGTH_SHORT).show();
			
			break;
			
		case DialogInterface.BUTTON_NEGATIVE:
			
			this.alertDialogConfirmacao.dismiss();
			
			break;
			
		}
		
	}
	
	private void removerViagemDb(String id){
		
		SQLiteDatabase db = this.helper.getWritableDatabase();
		
		String where[] = new String[]{ id };
		
		db.delete("gasto", "viagem_id = ?", where);

		db.delete("viagem", "_id = ?", where);
		
		db.close();
		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if(requestCode == EDITAR_VIAGEM){
			
			if(resultCode == RESULT_OK){
			
				this.listarViagens();
				
				getListView().invalidateViews();

			}
			
		}
		
	}

	@Override
	public boolean setViewValue(View view, Object data, String textRepresentation) {
		
		if(view.getId() == R.id.lst_viag_barraProgresso){
			
			Double valores[] = (Double[]) data;
			
			ProgressBar progressBar = (ProgressBar) view;
			
			progressBar.setMax(valores[0].intValue());
			
			progressBar.setSecondaryProgress(valores[1].intValue());
			
			progressBar.setProgress(valores[2].intValue());
			
			return true;
			
		}
		
		return false;
	}

}
