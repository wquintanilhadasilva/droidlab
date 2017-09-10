package br.com.casadocodigo.boaviagem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import br.com.casadocodigo.boaviagem.bd.Constantes;
import br.com.casadocodigo.boaviagem.dao.ViagemDao;
import br.com.casadocodigo.boaviagem.domain.Viagem;

public class NovaViagemActivity extends Activity{
	
	//Campos da tela
	private EditText destino;
	
	private RadioGroup tipo;
	
	private Button btnDataChegada;
	
	private Button btnDataSaida;
	
	private EditText orcamento;
	
	private EditText qtdPessoas;
	
	//Miscelaneous
	private SimpleDateFormat dataFormat;
	
	private Calendar calendar;
	
	private Date dataSaida;
	
	private Date dataChegada;
	
	private Integer idBtnDataClicado;
	
	private ViagemDao dao;
	
	private Viagem viagem;
	
	private String id;
	
	private OnDateSetListener dataListener = new OnDateSetListener() {
		
		@Override
		public void onDateSet(DatePicker view, int ano, int mes, int dia) {
			
			ajustarData(view, ano, mes, dia);
			
		}
	};
	
	@SuppressLint("SimpleDateFormat")
	@Override
	public void onCreate(Bundle savedInstanceState){
		
		super.onCreate(savedInstanceState);
		
		this.dao = new ViagemDao(this);
		
		this.viagem = new Viagem();
		
		this.dataFormat = new SimpleDateFormat("dd/MM/yyyy");
		
		setContentView(R.layout.novaviagem);
		
		//Campos da tela
		this.destino = (EditText) findViewById(R.id.destino);
		
		this.tipo = (RadioGroup) findViewById(R.id.tipoViagem);
		
		this.orcamento = (EditText) findViewById(R.id.orcamento);
		
		this.qtdPessoas = (EditText) findViewById(R.id.quantidadePessoas);
		
		this.btnDataChegada = (Button) findViewById(R.id.dataChegada);
		
		this.btnDataSaida = (Button) findViewById(R.id.dataSaida);
		
		//Misc
		this.calendar = Calendar.getInstance();
		
		this.ajusteTextoBotoesData(this.btnDataChegada, calendar.getTime());
		
		this.ajusteTextoBotoesData(this.btnDataSaida, calendar.getTime());
		
		this.dataChegada = new Date();
		
		this.dataSaida = new Date();
		
		//Recupera o id caso seja edição
		this.id = getIntent().getStringExtra(Constantes.VIAGEM_ID);
		
		Button btnSalvar = (Button) findViewById(R.id.btnSalvar);
		
		if(this.id != null && !this.id.isEmpty()){
			
			this.preparaEdicao();
			
			btnSalvar.setText(R.string.alterar_viagem_btn_salvar);
		
		}else{
			
			btnSalvar.setText(R.string.nova_viagem_btn_salvar);
			
		}
		
	}
	
	private void preparaEdicao() {
		
		this.viagem = dao.consultar(Long.valueOf(id));
		
		this.tipo.check( viagem.getTipoViagem() == Constantes.VIAGEM_LAZER ? R.id.lazer : R.id.negocios);
		
		this.destino.setText(this.viagem.getDestino());
		
		this.dataChegada = this.viagem.getDataChegada();
		
		this.dataSaida = this.viagem.getDataSaida();
		
		this.ajusteTextoBotoesData(btnDataSaida, dataSaida);
		
		this.ajusteTextoBotoesData(btnDataChegada, dataChegada);
		
		this.qtdPessoas.setText(String.valueOf(this.viagem.getQtdePessoas()));
		
		this.orcamento.setText(this.viagem.getOrcamento().toString());
		
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		
		if(id == R.id.dataChegada || id == R.id.dataSaida){
			
			this.calendar.setTime(id == R.id.dataChegada ? this.dataChegada : this.dataSaida);
			
			this.idBtnDataClicado = id;
			
			return new DatePickerDialog(this, this.dataListener,
					this.calendar.get(Calendar.YEAR),
					this.calendar.get(Calendar.MONTH),
					this.calendar.get(Calendar.DAY_OF_MONTH));
			
		}
		
		this.idBtnDataClicado = null;
		
		return null;
		
	}
	
	private void ajustarData(View view, int ano, int mes, int dia){
		
		if(this.idBtnDataClicado != null && (this.idBtnDataClicado == R.id.dataChegada || this.idBtnDataClicado == R.id.dataSaida)){
			
			Calendar data = Calendar.getInstance();
			
			data.set(Calendar.YEAR, ano);
			
			data.set(Calendar.MONTH, mes);
			
			data.set(Calendar.DAY_OF_MONTH, dia);
			
			if(this.idBtnDataClicado == R.id.dataChegada){

				this.dataChegada = data.getTime();
				
				this.ajusteTextoBotoesData(this.btnDataChegada, this.dataChegada);
				
			}else{
				
				this.dataSaida = data.getTime();
				
				this.ajusteTextoBotoesData(this.btnDataSaida, this.dataSaida);
				
			}
			
		}
		
	}
	
	public void ajusteTextoBotoesData(Button button, Date data){
		
		button.setText(this.dataFormat.format(data));
		
	}
	
	@SuppressWarnings("deprecation")
	public void selecionarData(View view){
		
		super.showDialog(view.getId());
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		
		MenuInflater inflater = getMenuInflater();
		
		inflater.inflate(R.menu.crud_menu, menu);
		
		return true;
		
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item){
		
		switch(item.getItemId()){
		
			case R.id.menu_novo:
				startActivity(new Intent(this, GastoActivity.class));
				
				return true;
				
			case R.id.menu_remover:
				
				this.removerViagem();
				
				return true;
				
			default:
				
				return super.onMenuItemSelected(featureId, item);
		}
	}
	
	private void removerViagem(){
		
		if(this.id == null || this.id.isEmpty()) return;
		
		this.dao.remove( Long.valueOf(this.id));
		
		//Encerra a activity
		finish();
		
	}
	
	public void salvarViagem(View view){
		
		this.viagem.setDestino(this.destino.getText().toString());
		
		this.viagem.setDataChegada(this.dataChegada);
		
		this.viagem.setDataSaida(this.dataSaida);
		
		this.viagem.setOrcamento(Double.valueOf(this.orcamento.getText().toString()));
		
		this.viagem.setQtdePessoas(Integer.valueOf(this.qtdPessoas.getText().toString()));
		
		int tipo = this.tipo.getCheckedRadioButtonId();
		
		int tp = tipo == R.id.lazer ? Constantes.VIAGEM_LAZER : Constantes.VIAGEM_NEGOCIOS;
		
		this.viagem.setTipoViagem(tp);
		
		try{
			
			if(this.id != null && !this.id.isEmpty()){
				
				dao.atualizar(this.viagem);
				
				setResult(Activity.RESULT_OK); //Indica que ocorreu tudo bem
				
			}else{
			
				this.viagem = dao.inserir(this.viagem);
	
			}
			
			Toast.makeText(this, getString(R.string.registro_salvo), Toast.LENGTH_SHORT).show();
			
		}catch(Exception e){
			
			Toast.makeText(this, getString(R.string.erro_salvar), Toast.LENGTH_SHORT).show();
			
		}
		
		//Encerra a activity
		finish();
		
	}
	
	@Override
	protected void onDestroy() {
		
		this.dao.finalize();
		
		super.onDestroy();
		
	}

}
