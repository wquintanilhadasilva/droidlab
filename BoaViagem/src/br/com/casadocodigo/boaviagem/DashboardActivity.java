package br.com.casadocodigo.boaviagem;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity implements OnClickListener{
	
	ProgressDialog progress;
	
	@Override
	public void onClick(DialogInterface dialog, int item) {
		
		Toast.makeText(this, "Item: " + item, Toast.LENGTH_SHORT).show();
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.dashboard);
		
		progress = new ProgressDialog(this);
		
		progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		
		progress.setTitle("Barra de progresso");
		
		progress.setMessage("Mensagem");
		
		progress.setCancelable(true);
		
		progress.setMax(800);
		
		progress.setButton(Dialog.BUTTON_NEGATIVE, "Abortar", this);
		
		progress.setButton(Dialog.BUTTON_NEUTRAL, "Pausar", this);
		
		progress.setButton(Dialog.BUTTON_POSITIVE, "Continuar", this);
		
	}
	
	public void selecionarOpcao(View view){
		
		TextView tv = (TextView) view;
		
		String opcao = tv.getText().toString();
		
		switch(view.getId()){
		
			case R.id.nova_viagem:
				
				startActivity(new Intent(this, NovaViagemActivity.class));
				
				break;
				
			case R.id.novo_gasto:
				
				startActivity(new Intent(this, GastoActivity.class));
				
				break;
				
			case R.id.minhas_viagens:
				
				startActivity(new Intent(this, ViagemListActivity.class));
				
				break;
				
			case R.id.configuracoes:
				
				startActivity(new Intent(this, ConfiguracoesActivity.class));
				
				break;
		}
		
		Toast.makeText(this, opcao, Toast.LENGTH_SHORT).show();
		
	}
	
	public void openProgress(View view){
		progress.show();		
	}
	
	public void openContacts(View view){
		
		Intent itent = new Intent(this, ContatosActivity.class);
		
		startActivity(itent);
		
	}
	
	public void openTestes(View v){
		
		setContentView(R.layout.testes);
		
	}
	
	public void clicou(View v){
		
		Toast.makeText(this, "YATAAAAAHHHH", Toast.LENGTH_LONG).show();
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.dashboard_menu, menu);
		
		return true;
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		
		finish();
		
		return true;
		
	}

}
